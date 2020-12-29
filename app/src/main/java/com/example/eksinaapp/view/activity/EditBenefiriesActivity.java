package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Country;
import com.example.eksinaapp.model.CountryResponse;
import com.example.eksinaapp.model.EditBenefiries;
import com.example.eksinaapp.model.Register;
import com.example.eksinaapp.model.ShowBeneficiery;
import com.example.eksinaapp.model.Wallet;
import com.example.eksinaapp.model.WalletResponse;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.fragments.LoginFragment;
import com.example.eksinaapp.view.fragments.MyBenfiriesFragment;
import com.google.gson.JsonIOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class EditBenefiriesActivity extends AppCompatActivity {
    Button btnAdd;
    ImageView imgBack;
    Spinner select_city;
    List<Country> countryList;
    EditText edtCity,edtEnterFirstName,edtLastName,edtNickName,edtMobile,edtBankAccount,edtIfsc,edtWalletId,edtBankname;
    String strCity,mStrCountryName,mStrType;
    String strFirstName;
    String strLastName;
    String strNickName;
    String strMobile;
    String strBankAccount,strbankname;
    String strIfsc;
    String strWalletId;
    String strType;
    String ben_id;
    List<Wallet> wallets;
    RadioGroup rg;
    RadioButton rb_buisness,rb_family,rb_friend,rb_other;
    Fragment fragment;
    int country = 0;
    int wallet_type=0;
    Spinner wallet_id_fk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_edit_benefiries);

        btnAdd=findViewById(R.id.btnAdd);

        imgBack=findViewById(R.id.imgBack);

        select_city=findViewById(R.id.select_city);

        edtCity=findViewById(R.id.edtCity);

        edtEnterFirstName=findViewById(R.id.edtEnterFirstName);

        edtLastName=findViewById(R.id.edtLastName);

        edtNickName=findViewById(R.id.edtNickName);

        edtMobile=findViewById(R.id.edtMobile);

        edtBankAccount=findViewById(R.id.edtBankAccount);

        edtBankname = findViewById(R.id.edtBankname);

        edtIfsc=findViewById(R.id.edtIfsc);

        edtWalletId=findViewById(R.id.edtWalletId);

        wallet_id_fk=findViewById(R.id.wallet_id_fk);

        rg=findViewById(R.id.rg);

        rb_buisness=findViewById(R.id.rb_buisness);

        rb_family=findViewById(R.id.rb_family);

        rb_friend=findViewById(R.id.rb_friend);

        rb_other=findViewById(R.id.rb_other);

        Intent intent = getIntent();
        ben_id = intent.getStringExtra("ben_id");

        loadCity();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int option = rg.getCheckedRadioButtonId();
                switch (option)
                {
                    case R.id.rb_buisness:
                        if (rb_buisness.isChecked()){
                            strType =rb_buisness.getText().toString();
                        }
                    case R.id.rb_family:
                        if (rb_family.isChecked()){
                            strType =rb_family.getText().toString();
                        }
                    case R.id.rb_friend:
                        if (rb_friend.isChecked()){
                            strType =rb_friend.getText().toString();
                        }
                    case R.id.rb_other:
                        if (rb_other.isChecked()){
                            strType =rb_other.getText().toString();
                        }
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), BenefiriesDetails.class);
                intent.putExtra( "ben_id",ben_id);
           startActivity(intent);
            }
        });

        select_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position != 0) {
                    country = Integer.parseInt(countryList.get(position).getId());
          //      }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        wallet_id_fk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                wallet_type = Integer.parseInt(wallets.get(position-1).getId());
                Log.d("wallet_type", String.valueOf(wallet_type));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                   /* if(validateCountry() && validateCity() && validateFirstName() && validateLastname() && validateNickName() && validateMobileno()
                            && validateankAccount() && validateIfsc() && validateWalletId() && validateRadioGroup()
                    )*/
                    {

                        strCity = edtCity.getText().toString().trim();
                        strFirstName = edtEnterFirstName.getText().toString().trim();
                        strLastName = edtLastName.getText().toString().trim();
                        strNickName= edtNickName.getText().toString().trim();
                        strMobile=edtMobile.getText().toString();
                        strbankname=edtBankname.getText().toString();
                        strBankAccount=edtBankAccount.getText().toString();
                        strIfsc=edtIfsc.getText().toString();
                        strWalletId=edtWalletId.getText().toString();


                        if (validateCountry() && validateCity() && validateFirstName() && validateLastname() && validateNickName()  && validateMobileno() && validatebankname() && validateaccountnumber() && validateifsccode() &&
                                validatewalletId() && validatespinnerwallet() && validateRadioGroup()){
                            Log.d("paramstosend",country+" " +strCity+" " +strFirstName+" " +strLastName+"" +strNickName+" "+strMobile+" " +strbankname+" " +strBankAccount+" " +strIfsc+" " +strWalletId+" " +wallet_type+" " +strType);
                            updateBenefiries(country, strCity, strFirstName, strLastName, strNickName, strMobile,strbankname,strBankAccount,strIfsc,strWalletId,wallet_type,strType);
                        }
//                        if (validateCountry() && validateCity() && validateFirstName() && validateLastname() && validateNickName() && validateMobileno()){
//                            Log.d("valuestosend",country+strCity+strFirstName+strLastName+strNickName+strMobile+strType+strWalletId+strBankAccount+strIfsc);
//                            updateBenefiries(country, strCity, strFirstName, strLastName, strNickName, strMobile,strType,strWalletId,strbankname,strBankAccount,strIfsc,walletId);
//                        }

                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

    }

    private void getBenefiries() {
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(EditBenefiriesActivity.this, getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<ShowBeneficiery> loginCall;
            loginCall = apiService.benefiriesDetails(Integer.parseInt(ben_id));
            loginCall.enqueue(new Callback<ShowBeneficiery>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<ShowBeneficiery> call,
                                       Response<ShowBeneficiery> response) {
                    pd.hide();/*
                     */
                    try {
                        if (response.body() != null) {
                            ShowBeneficiery showBeneficiery = response.body();
                            if (showBeneficiery.getStatus() == 200) {
                                mStrCountryName = response.body().getCountryName();
                                for (int i = 0; i < countryList.size(); i++) {
                                    if (mStrCountryName.equalsIgnoreCase(countryList.get(i).getCountryName())) {
                                        select_city.setSelection(i);
                                    }
                                }

                                mStrType = response.body().getWalletIdFk();
                                Log.d("mstrtype",mStrType);
//                                for (int i = 0; i < wallets.size(); i++) {
//                                    if (mStrType.equalsIgnoreCase(wallets.get(i).getId())) {
                                        wallet_id_fk.setSelection(Integer.parseInt(mStrType));
//                                    }
//                                }

                                strCity = response.body().getCityName();
                                Log.d("city",strCity);
                                edtCity.setText(strCity);

                                strFirstName = response.body().getFirstName();
                                edtEnterFirstName.setText(strFirstName);

                                strLastName = response.body().getLastName();
                                edtLastName.setText(strLastName);

                                strNickName = response.body().getNickName();
                                edtNickName.setText(strNickName);

                                strMobile = response.body().getMobile();
                                edtMobile.setText(strMobile);

                                strBankAccount = response.body().getBankAccNo();
                                edtBankAccount.setText(strBankAccount);

                                strIfsc = response.body().getIfscCode();
                                edtIfsc.setText(strIfsc);

                                strWalletId = response.body().getWalletId();
                                edtWalletId.setText(strWalletId);

                                strbankname = response.body().getBankName();
                                edtBankname.setText(strbankname);

                                if (response.body().getPurposeFor().equals("Buisness")) {
                                    rb_buisness.setChecked(true);

                                } else if (response.body().getPurposeFor().equals("Family")) {
                                    rb_family.setChecked(true);

                                } else if (response.body().getPurposeFor().equals("Friend")) {
                                    rb_friend.setChecked(true);

                                } else if (response.body().getPurposeFor().equals("Other")) {
                                    rb_other.setChecked(true);

                                }


                            }


                        }
                    } catch (NullPointerException e) {
                        System.out.println(e);
                    }
                }

                @Override
                public void onFailure(Call<ShowBeneficiery> call,
                                      Throwable t) {
                    pd.hide();

                }
            });
        } catch (JsonIOException exception) {
            System.out.println("Thrown exception: " + exception.getMessage());
        }
    }
        // loadCity();
      /*  for (int i = 0; i < countryList.size(); i++) {
            if (countryList.get(i).getCountryId().equalsIgnoreCase(countryList.get(0).getCountryId())) {
                select_city.setSelection(i);
                if (countryList.get(0).getId().equalsIgnoreCase("1")) {
                } else {
                }
                loadCity(countryList.get(i).getId());
            }
        }*/

    private void loadWalletType(){
        ApiInterface apiService = ApiHandler.getApiService();
        final Call<WalletResponse> loginCall = apiService.walletType();
        loginCall.enqueue(new Callback<WalletResponse>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<WalletResponse> call,
                                   Response<WalletResponse> response) {

                try {
                    WalletResponse walletResponse=response.body();

                    if (response.isSuccessful()) {
                        if (walletResponse.getStatus()==200)
                            wallets = response.body().getWallet();


                        List<String> listSpinner = new ArrayList<>();
                        try{
                            listSpinner.add("Wallet type");
                        }catch (IllegalStateException i){
                            System.out.println(i);
                        }
                        for (int i = 0; i < wallets.size(); i++) {
                            if (wallets.get(i).getCountryCode() != null && wallets.get(i).getCountryId() != null) {
                                String country = wallets.get(i).getCountryId();
                                Log.d("scountry",country);
                                listSpinner.add(wallets.get(i).getCountryId());

                                // SharedPrefManager.storeCountry(walletResponse,Addactivity.this);
                               // wallets.get(i).getCountryId();
                            }else {
                                Toast.makeText(EditBenefiriesActivity.this,response.message(),Toast.LENGTH_LONG).show();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditBenefiriesActivity.this,
                                android.R.layout.simple_spinner_item, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                      //  wallet_id_fk.setSelection(adapter.getPosition(String.valueOf(2)));
                        wallet_id_fk.setAdapter(adapter);
                        getBenefiries();

                    } else {

                    }

                }catch (NullPointerException e){
                    System.out.println(e);
                }

            }

            @Override
            public void onFailure(Call<WalletResponse> call,
                                  Throwable t) {
                Toast.makeText(EditBenefiriesActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadCity(/*String catId*/){
        ApiInterface apiService = ApiHandler.getApiService();
        final Call<CountryResponse> loginCall = apiService.getCountry();
        loginCall.enqueue(new Callback<CountryResponse>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<CountryResponse> call,
                                   Response<CountryResponse> response) {

                try {
                    if (response.isSuccessful()) {
                        countryList = response.body().getCountry();
                        List<String> listSpinner = new ArrayList<String>();


                        for (int i = 0; i < countryList.size(); i++) {
                            if (countryList.get(i).getCountryName() != null && countryList.get(i).getCountryName() != null) {
                                listSpinner.add(countryList.get(i).getCountryName());

                            }else {
                                Toast.makeText(EditBenefiriesActivity.this,response.message(),Toast.LENGTH_LONG).show();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditBenefiriesActivity.this,
                                android.R.layout.simple_spinner_item, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        select_city.setSelection(adapter.getPosition(String.valueOf(7)));
                        select_city.setAdapter(adapter);
                       loadWalletType();

//                        SharedPreferences prefs = getPreferences(0);
//                        select_city.setSelection(prefs.getInt("spinnerSelection",0));
//                        Log.d("city",strCity);

                       /* if (compareValue != null) {
                            int spinnerPosition = adapter.getPosition(compareValue);
                            select_city.setAdapter(adapter);
                            select_city.setSelection(spinnerPosition);
                        }*/
                    } else {
                    }

                }catch (NullPointerException e){
                    System.out.println(e);
                }

            }

            @Override
            public void onFailure(Call<CountryResponse> call,
                                  Throwable t) {
                Toast.makeText(EditBenefiriesActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBenefiries(final int country_id, String city_name, String first_name, String last_name, String nick_name,
                                  String mobile,String bank_name,String bank_acc_no,String ifsc_code,String wallet_id,int wallet_id_fk,String purpose_for){
        try {
            final int userId=0;

            final ProgressDialog pd = ViewUtils.getProgressBar(EditBenefiriesActivity.this,  getString(R.string.loading), getString(R.string.wait));
            ApiInterface apiService = ApiHandler.getApiService();
            final Call<EditBenefiries> loginCall = apiService.editbenefiries(Integer.parseInt(userId+ SharedPrefManager.getLoginObject(EditBenefiriesActivity.this).getUserId()), Integer.parseInt(ben_id),
                    country_id, city_name, first_name,last_name, nick_name, mobile,bank_name,bank_acc_no,ifsc_code,wallet_id,wallet_id_fk,purpose_for);

            loginCall.enqueue(new Callback<EditBenefiries>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<EditBenefiries> call,
                                       Response<EditBenefiries> response) {
                    pd.hide();

                    try {

                        if (response.isSuccessful()){
                            EditBenefiries editBenefiries = response.body();
                            if (editBenefiries.getStatus().equals(200)) {

                                Toast.makeText(EditBenefiriesActivity.this, editBenefiries.getMessage(), Toast.LENGTH_LONG).show();
                                fragment=new MyBenfiriesFragment();
                                replaceFragment(fragment);

                            } else{
                                Toast.makeText(EditBenefiriesActivity.this, editBenefiries.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }catch (Exception e) {
                        System.out.println(e);
                    }
                }

                @Override
                public void onFailure(Call<EditBenefiries> call,
                                      Throwable t) {
                    pd.hide();
                    Toast.makeText(EditBenefiriesActivity.this,"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }

            });

        } catch (JsonIOException e) {
            System.out.println("Thrown exception: " + e.getMessage());
        }

    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        getFragmentManager().popBackStack();
        transaction.commit();
    }
    private boolean validateCountry(){
        if (select_city.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(select_city.getSelectedItem());
        } else {
            Toast.makeText(EditBenefiriesActivity.this,"Please select country",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private boolean validateCity() {
        String city = edtCity.getText().toString().trim();
        if (city.isEmpty()) {
            Toast.makeText(EditBenefiriesActivity.this, "Please Enter city", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateNickName() {
        String nickName = edtNickName.getText().toString().trim();
        if (nickName.isEmpty()) {
            Toast.makeText(EditBenefiriesActivity.this, "Please Enter Nick name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateFirstName() {
        String firstName = edtEnterFirstName.getText().toString().trim();
        if (firstName.isEmpty()) {
            Toast.makeText(EditBenefiriesActivity.this, getString(R.string.enterFirst), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private boolean validateLastname() {
        String lastName = edtLastName.getText().toString().trim();
        if (lastName.isEmpty()) {
            Toast.makeText(EditBenefiriesActivity.this, getString(R.string.enterLastName), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateMobileno() {
        String mobileNo = edtMobile.getText().toString().trim();
        if (mobileNo.isEmpty()) {
            Toast.makeText(EditBenefiriesActivity.this, getString(R.string.enterMobileNumber), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateRadioGroup(){
        if(rg.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(this, "Please select type", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            // not checked
        }

        return true;
    }

    private boolean validatespinnerwallet() {
        if (wallet_id_fk.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(wallet_id_fk.getSelectedItem());
        } else {
            Toast.makeText(EditBenefiriesActivity.this,"Please select wallet type",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validatewalletId() {
        String walletid = edtWalletId.getText().toString().trim();
        if (walletid.isEmpty()) {
            Toast.makeText(EditBenefiriesActivity.this, "Please Enter Wallet Id", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateifsccode() {
        String ifsc = edtIfsc.getText().toString().trim();
        if (ifsc.isEmpty()) {
            Toast.makeText(EditBenefiriesActivity.this, "Please Enter IFSC", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateaccountnumber() {
        String bankaccount = edtBankAccount.getText().toString().trim();
        if (bankaccount.isEmpty()) {
            Toast.makeText(EditBenefiriesActivity.this, "Please Enter Bank Account Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatebankname() {
        String bankname = edtBankname.getText().toString().trim();
        if (bankname.isEmpty()) {
            Toast.makeText(EditBenefiriesActivity.this, "Please Enter Bank name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}