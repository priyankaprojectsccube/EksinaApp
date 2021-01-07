package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.BeneficiaryResponse;
import com.example.eksinaapp.model.ConvertCurrency;
import com.example.eksinaapp.model.Country;
import com.example.eksinaapp.model.CountryResponse;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.adapter.ConvertCurrencyAdapter;
import com.example.eksinaapp.view.adapter.SavedBeneficiariesAdapter;
import com.google.gson.JsonIOException;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ConstantConditions")
public class YourTransferActivity extends AppCompatActivity {
Button btnFollowing;
ImageView imgBack;
Spinner spReceived;
List<Country> countryList;
TextView yousend,donation,freefees,totaltopay,yousendbelow,jtext,recevies,viareceives;
ConvertCurrencyAdapter convertCurrencyAdapter;
String strAmount,inputCurrency,strEdtAmount,strTransferMode,strConvertedMode;
RadioButton radioPassport/*,radioBank*/,radioMoney;
RadioGroup radioGroup;
EditText edtEnterAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_transfer);

        viareceives = findViewById(R.id.viareceives);
          btnFollowing=findViewById(R.id.btnFollowing);

          imgBack=findViewById(R.id.imgBack);

          edtEnterAmount=findViewById(R.id.edtEnterAmount);

          spReceived=findViewById(R.id.spReceived);
        yousend = findViewById(R.id.yousend);
        donation= findViewById(R.id.donation);
        freefees = findViewById(R.id.freefees);
        totaltopay = findViewById(R.id.totaltopay);
        yousendbelow = findViewById(R.id.yousendbelow);
        jtext = findViewById(R.id.jtext);
        recevies = findViewById(R.id.recevies);



          radioPassport=findViewById(R.id.radioPassport);

        // radioBank=findViewById(R.id.radioBank);

         radioMoney=findViewById(R.id.radioMoney);

         radioGroup=findViewById(R.id.radioGroup);


        spReceived.setPrompt("Select your favorite Planet!");

         showCountry();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YourTransferActivity.this, HomeActivity.class);
                intent.putExtra("YourTransferActivity","one");

                startActivity(intent);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int option = radioGroup.getCheckedRadioButtonId();
                switch (option)
                {
                    case R.id.radioPassport:
                        if (radioPassport.isChecked()){
                            strTransferMode =radioPassport.getText().toString();
                            strConvertedMode="cash withdrawal";
                        }
                   /* case R.id.radioBank:
                        if (radioBank.isChecked()){
                            strTransferMode=radioBank.getText().toString();
                            strConvertedMode="bank transfer";
                        }*/
                    case R.id.radioMoney:
                        if (radioMoney.isChecked()){
                            strTransferMode=radioMoney.getText().toString();
                            strConvertedMode="wallet";

                        }
                }
            }
        });

        btnFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateAmount() && validateConvert() && validatecountry() && validateradiogroup()){
                    Intent intent=new Intent(YourTransferActivity.this,AddBenefiariesActivity.class);
                    intent.putExtra("Totalamount",strAmount);
                    intent.putExtra("currency",inputCurrency);
                    intent.putExtra("amount",strEdtAmount);
                    intent.putExtra("transferMode",strConvertedMode);
                    startActivity(intent);
                }
            }
        });

        spReceived.setEnabled(false);



        edtEnterAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Before user enters the text
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //On user changes the text
                if(s.toString().trim().length()==0) {
                    spReceived.setEnabled(false);
                    Toast.makeText(YourTransferActivity.this, "Text can not be empty..",
                            Toast.LENGTH_SHORT).show();
                } else {
                    spReceived.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                //After user is done entering the text

            }
        });

        spReceived.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                      inputCurrency= countryList.get(position).getCurrencyName();
                      strEdtAmount=edtEnterAmount.getText().toString();
                    try{
                        Log.d("params",inputCurrency+" "+strEdtAmount);
                        convertCurrency(inputCurrency,strEdtAmount);
                    }catch (NullPointerException e){
                        System.out.println(e);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean validateradiogroup() {
        if(radioGroup.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(this, "Please select receipt of transfer", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            // not checked
        }

        return true;
    }

    private boolean validatecountry() {
        if (spReceived.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(spReceived.getSelectedItem());
        } else {
            Toast.makeText(YourTransferActivity.this,"Please select country",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void showCountry() {
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(YourTransferActivity.this,  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<CountryResponse> loginCall;
            loginCall = apiService.getCountry();

            loginCall.enqueue(new Callback<CountryResponse>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(@NotNull Call<CountryResponse> call,
                                       @NotNull Response<CountryResponse> response) {
                    pd.hide();

                    CountryResponse countryResponse = response.body();

                    if (response.isSuccessful()) {
                        if (countryResponse.getCountry() != null) {
                            countryList = response.body().getCountry();
                            convertCurrencyAdapter = new ConvertCurrencyAdapter(countryList, YourTransferActivity.this);
                            spReceived.setAdapter(convertCurrencyAdapter);
                        }
                    }
                }
                @Override
                public void onFailure(Call<CountryResponse> call,
                                      Throwable t) {
                    pd.hide();
                    Log.d("error",t.toString());

                }
            });
        } catch (JsonIOException exception) {
            System.out.println("Thrown exception: " + exception.getMessage());
        }
    }


    private void convertCurrency(String currency,String stramount) {
        if (stramount.isEmpty()) {
            edtEnterAmount.setError(getString(R.string.enterAmount));
            edtEnterAmount.requestFocus();
            return;
        }

        int loginId;
        loginId=0;
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(YourTransferActivity.this,  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();

            final Call<ConvertCurrency> loginCall;

            loginCall = apiService.convertCurrency(Integer.parseInt(loginId+ SharedPrefManager.getLoginObject(YourTransferActivity.this).getUserId()),currency,stramount);

            loginCall.enqueue(new Callback<ConvertCurrency>() {
                @SuppressWarnings("NullableProblems")
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<ConvertCurrency> call,
                                       Response<ConvertCurrency> response) {
                    pd.hide();

                    ConvertCurrency convertCurrency = response.body();
String res = String.valueOf(response.body());
Log.d("response",res);
                    if (response.body().getStatus() == 200) {
                        Log.d("onsuccess","onsuccess");
//                        if (convertCurrency.getEroReciveAmount() != null) {


                            yousend.setText(response.body().getEroReciveAmount()  + " " +  "EUR");
                            donation.setText("0 EUR");
                            freefees.setText(response.body().getEroFees().toString() + " " +  "EUR");
                            totaltopay.setText(response.body().getEroTotalAmount().toString() + " " +  "EUR");
                            viareceives.setText(response.body().getBenReciveAmount());

//                            yousendbelow.setText(response.body().getBenAmount());
//                            jtext.setText(response.body().getBenFees());
//                            recevies.setText(response.body().getBenReciveAmount());
//                        }else {
//                            totaltopay.setText("Failed to convert");
//                        }
                    }else {
                        totaltopay.setText("Failed to convert");
                    }
                }

                @Override
                public void onFailure(Call<ConvertCurrency> call,
                                      Throwable t) {
                    pd.hide();
                    Log.d("error",t.toString());

                }
            });
        } catch (JsonIOException exception) {
            System.out.println("Thrown exception: " + exception.getMessage());
        }
    }

    private boolean validateAmount() {
        String city = edtEnterAmount.getText().toString().trim();
        if (city.isEmpty()) {
            Toast.makeText(YourTransferActivity.this, "Please enter amount"
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*private boolean validateCurrency() {
        if (spReceived.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(spReceived.getSelectedItem());
        } else {
            Toast.makeText(this, "Select country", Toast.LENGTH_SHORT).show();
                        return false;
        }
        return true;
   }*/
    private boolean validateConvert() {
            if (totaltopay.getText().toString().equals("Failed to convert")) {
                Toast.makeText(YourTransferActivity.this, "Failed to convert amount", Toast.LENGTH_SHORT).show();
                return false;
        }
        return true;
    }


}