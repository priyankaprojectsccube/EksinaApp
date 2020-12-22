package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Register;
import com.example.eksinaapp.model.SaveCard;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.fragments.LoginFragment;
import com.google.gson.JsonIOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AddCardActivity extends AppCompatActivity {
      Button addCard;
      EditText edtEnterName,edtEnterEmail,edtAccountNo,edtMonth,edtyear,edtCVC;
      String strName,strEmail,strAccount,strMonth,strYear,strCvc,strAmount,strEdtAmount,benId,strTransferMode,countryId;
      int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);


        edtEnterName=findViewById(R.id.edtEnterName);

        edtEnterEmail=findViewById(R.id.edtEnterEmail);

        edtAccountNo=findViewById(R.id.edtAccountNo);

        edtMonth=findViewById(R.id.edtMonth);

        edtyear=findViewById(R.id.edtyear);

        edtCVC=findViewById(R.id.edtCVC);

        addCard=findViewById(R.id.btnAddCard);

        edtAccountNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (count <= edtAccountNo.getText().toString().length()
                        &&(edtAccountNo.getText().toString().length()==4
                        ||edtAccountNo.getText().toString().length()==9
                        ||edtAccountNo.getText().toString().length()==14)){
                    edtAccountNo.setText(edtAccountNo.getText().toString()+" ");
                    int pos = edtAccountNo.getText().length();
                    edtAccountNo.setSelection(pos);
                }else if (count >= edtAccountNo.getText().toString().length()
                        &&(edtAccountNo.getText().toString().length()==4
                        ||edtAccountNo.getText().toString().length()==9
                        ||edtAccountNo.getText().toString().length()==14)){
                    edtAccountNo.setText(edtAccountNo.getText().toString().substring(0,edtAccountNo.getText().toString().length()-1));
                    int pos = edtAccountNo.getText().length();
                    edtAccountNo.setSelection(pos);
                }
                count = edtAccountNo.getText().toString().length();
            }
        });

        Intent intent=getIntent();
        strAmount=intent.getStringExtra("Totalamount");
        strEdtAmount=intent.getStringExtra("amount");
        benId=intent.getStringExtra("benId");
        strTransferMode=intent.getStringExtra("transferMode");
        Log.d("benId",benId);
        countryId=intent.getStringExtra("countryId");

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName=edtEnterName.getText().toString();
                strEmail=edtEnterEmail.getText().toString();
                strAccount=edtAccountNo.getText().toString();
                strMonth=edtMonth.getText().toString();
                strYear=edtyear.getText().toString();
                strCvc=edtCVC.getText().toString();

                if (validateFirstName() && validateEmail() && validateAccount() && validateMonth() && validateyear() && validateCvc()){
                    saveCard(strAccount,strMonth,strYear,strCvc,strName,strEmail);
                }

            }
        });
    }

    private void saveCard(String card_no, String exp_month, String exp_year, String cvc, String name, String email) {
    int loginId;
      loginId=0;


        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(AddCardActivity.this,  getString(R.string.loading), getString(R.string.wait));
            ApiInterface apiService = ApiHandler.getApiService();
            final Call<SaveCard> loginCall = apiService.saveCard(card_no,exp_month,exp_year,cvc,name,email, Integer.parseInt(loginId+ SharedPrefManager.getLoginObject(AddCardActivity.this).getUserId()));

            loginCall.enqueue(new Callback<SaveCard>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<SaveCard> call,
                                       Response<SaveCard> response) {
                    pd.hide();

                    try {

                        if (response.isSuccessful()){
                            SaveCard saveCard = response.body();
                            if (saveCard.getStatus().equals(200)) {
                                Toast.makeText(AddCardActivity.this, saveCard.getMessage(), Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(AddCardActivity.this,PaymentDeatilsActvity.class);
                                intent.putExtra("Totalamount",strAmount);
                                intent.putExtra("amount",strEdtAmount);
                                intent.putExtra("name",strName);
                                intent.putExtra("email",strEmail);
                                intent.putExtra("cvc",strCvc);
                                intent.putExtra("cardNo",strAccount);
                                intent.putExtra("month",strMonth);
                                intent.putExtra("year",strYear);
                                intent.putExtra("benId",benId);
                                intent.putExtra("transferMode",strTransferMode);
                                intent.putExtra("countryId",countryId);
                                startActivity(intent);
                                clearUser();
                            } else if (saveCard.getStatus().equals(400)){
                                Toast.makeText(AddCardActivity.this, saveCard.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }catch (Exception e) {
                        System.out.println(e);
                    }
                }

                @Override
                public void onFailure(Call<SaveCard> call,
                                      Throwable t) {
                    pd.hide();
                    Toast.makeText(AddCardActivity.this,"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }

            });

        } catch (JsonIOException e) {
            System.out.println("Thrown exception: " + e.getMessage());
        }
    }
    private void clearUser(){
        edtEnterName.setText("");
        edtEnterEmail.setText("");
        edtAccountNo.setText("");
        edtMonth.setText("");
        edtyear.setText("");
        edtCVC.setText("");
    }
    private boolean validateFirstName() {
        String firstName = edtEnterName.getText().toString().trim();
        if (firstName.isEmpty()) {
            Toast.makeText(AddCardActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String email = edtEnterEmail.getText().toString().trim();
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

        }else {
            Toast.makeText(AddCardActivity.this, getString(R.string.invalidamail)
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateAccount() {
        String accountNo = edtAccountNo.getText().toString().trim();
        if (accountNo.isEmpty()) {
            Toast.makeText(AddCardActivity.this, "Enter Card number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateMonth() {
        String month = edtMonth.getText().toString().trim();
        if (month.isEmpty()) {
            Toast.makeText(AddCardActivity.this, "Enter expiry month", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateyear() {
        String year = edtyear.getText().toString().trim();
        if (year.isEmpty()) {
            Toast.makeText(AddCardActivity.this, "Enter expiry year", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateCvc() {
        String cvc = edtCVC.getText().toString().trim();
        if (cvc.isEmpty()) {
            Toast.makeText(AddCardActivity.this, "Enter CVC", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}