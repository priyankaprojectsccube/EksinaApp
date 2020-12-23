package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.example.eksinaapp.model.AddBenificiery;
import com.example.eksinaapp.model.Country;
import com.example.eksinaapp.model.CountryResponse;
import com.example.eksinaapp.model.Wallet;
import com.example.eksinaapp.model.WalletResponse;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.fragments.MyBenfiriesFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddWalletBenefiriesActivity extends AppCompatActivity {
    Button btnAdd;
    ImageView imgBack,imgAdd;
    Spinner select_city,wallet_id_fk;
    List<Country> countryList;
    List<Wallet> wallets;
    EditText edtCity,edtEnterFirstName,edtLastName,edtNickName,edtBankName,edtMobile,edtBankAccount,edtIfsc,edtWalletId;
    String strCity,strFirstName,strLastName,strNickName,strMobile,strbankname,strBankAccount,strIfsc,strWalletId,strType;
    RadioGroup rg;
    RadioButton rb_buisness,rb_family,rb_friend,rb_other;
    Fragment fragment;
    int country = 0;
    String inputCountry = String.valueOf(country);
    private final int REQUEST_CODE=99;
    int wallet_type=0;
    String walletName=String.valueOf(wallet_type);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wallet_benefiries);

        btnAdd=findViewById(R.id.btnAdd);

        imgBack=findViewById(R.id.imgBack);

        imgAdd=findViewById(R.id.imgAdd);

        select_city=findViewById(R.id.select_city);
boolean isCheckedbtn;

        edtCity=findViewById(R.id.edtCity);

        edtEnterFirstName=findViewById(R.id.edtEnterFirstName);

        edtLastName=findViewById(R.id.edtLastName);

        edtNickName=findViewById(R.id.edtNickName);

        edtMobile=findViewById(R.id.edtMobile);

        edtBankName = findViewById(R.id.edtBankName);
        edtBankAccount=findViewById(R.id.edtBankAccount);

        edtIfsc=findViewById(R.id.edtIfsc);

        edtWalletId=findViewById(R.id.edtWalletId);

        wallet_id_fk=findViewById(R.id.wallet_id_fk);

        rg=findViewById(R.id.rg);

        rb_buisness=findViewById(R.id.rb_buisness);

        rb_family=findViewById(R.id.rb_family);

        rb_friend=findViewById(R.id.rb_friend);

        rb_other=findViewById(R.id.rb_other);


        try {
            showContacts();
        }catch (Exception e){
            System.out.println(e);
        }


        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
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
                Intent intent = new Intent(AddWalletBenefiriesActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        select_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    country = Integer.parseInt(countryList.get(position-1).getId());
                }
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

                    strCity = edtCity.getText().toString().trim();
                    strFirstName = edtEnterFirstName.getText().toString().trim();
                    strLastName = edtLastName.getText().toString().trim();
                    strNickName= edtNickName.getText().toString().trim();
                    strMobile=edtMobile.getText().toString();
                    strbankname =edtBankName.getText().toString();
                    strBankAccount=edtBankAccount.getText().toString();
                    strIfsc=edtIfsc.getText().toString();
                    strWalletId=edtWalletId.getText().toString();
                    walletName=wallet_id_fk.getSelectedItem().toString().trim();
                    inputCountry=select_city.getSelectedItem().toString().trim();
                    if (validateWalletId()){
                        addBenifiries(country, strCity, strFirstName, strLastName, strNickName, strMobile,strbankname,strBankAccount,strIfsc,strWalletId,strType,wallet_type);
                    }else {

                    }


                } catch (Exception e) {
                    System.out.println(e);
                }
            }


        });
        loadCity();
        loadWalletType();
    }


    private void loadCity(){
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
                                Toast.makeText(AddWalletBenefiriesActivity.this,response.message(),Toast.LENGTH_LONG).show();
                            }
                        }

                        SharedPreferences.Editor editor = getPreferences(0).edit();
                        int selectedPosition = select_city.getSelectedItemPosition();
                        editor.putInt("spinnerSelection", selectedPosition);
                        editor.apply();

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddWalletBenefiriesActivity.this,
                                android.R.layout.simple_spinner_item, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        select_city.setAdapter(adapter);
                    } else {
                    }

                }catch (NullPointerException e){
                    System.out.println(e);
                }

            }

            @Override
            public void onFailure(Call<CountryResponse> call,
                                  Throwable t) {
                Toast.makeText(AddWalletBenefiriesActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
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
                        }                        for (int i = 0; i < wallets.size(); i++) {
                            if (wallets.get(i).getCountryCode() != null && wallets.get(i).getCountryId() != null) {
                                listSpinner.add(wallets.get(i).getCountryId());
                                // SharedPrefManager.storeCountry(walletResponse,Addactivity.this);
                                wallets.get(i).getCountryId();
                            }else {
                                Toast.makeText(AddWalletBenefiriesActivity.this,response.message(),Toast.LENGTH_LONG).show();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddWalletBenefiriesActivity.this,
                                android.R.layout.simple_spinner_item, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        wallet_id_fk.setAdapter(adapter);
                    } else {

                    }

                }catch (NullPointerException e){
                    System.out.println(e);
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call,
                                  Throwable t) {
                Toast.makeText(AddWalletBenefiriesActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBenifiries(final int country_id, String city_name, String first_name, String last_name, String nick_name,
                               String mobile,String bank_name,String bank_acc_no,String ifsc_code,String wallet_id,String purpose_for,int wallet_id_fk) {

        int loginId;
        loginId=0;
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(AddWalletBenefiriesActivity.this,  getString(R.string.loading), getString(R.string.wait));
            ApiInterface apiService = ApiHandler.getApiService();
            final Call<AddBenificiery> loginCall = apiService.addbenificiery(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(AddWalletBenefiriesActivity.this).getUserId()), country_id, city_name, first_name,
                    last_name, nick_name, mobile,bank_name,bank_acc_no,ifsc_code,wallet_id,purpose_for,wallet_id_fk);

            loginCall.enqueue(new Callback<AddBenificiery>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<AddBenificiery> call,
                                       Response<AddBenificiery> response) {
                    pd.hide();
                    Log.w("myresponse",new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    try {
                        AddBenificiery addBenificiery = response.body();


                        if (addBenificiery.getStatus() != null) {
                            if (addBenificiery.getStatus().equals(200)) {
                                Toast.makeText(AddWalletBenefiriesActivity.this, addBenificiery.getMessage(), Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(AddWalletBenefiriesActivity.this,AddBenefiariesActivity.class);
                                startActivity(intent);
                                finish();
                                // clearUser();
                            } else{
                                Toast.makeText(AddWalletBenefiriesActivity.this, addBenificiery.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }

                @Override
                public void onFailure(Call<AddBenificiery> call,
                                      Throwable t) {
                    pd.hide();
                    Toast.makeText(AddWalletBenefiriesActivity.this,"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }

            });

        } catch (JsonIOException e) {
            System.out.println("Thrown exception: " + e.getMessage());
        }
    }





    private boolean validateWalletId() {
        String city = edtWalletId.getText().toString().trim();
        if (city.isEmpty()) {
            Toast.makeText(AddWalletBenefiriesActivity.this, "Please Enter wallet Id", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
        }
    }


    /*  private boolean validateRadioGroup(){
          if (rg.getCheckedRadioButtonId() == -1)
          {
              Toast.makeText(Addactivity.this, "Please Select type", Toast.LENGTH_SHORT).show();
               return false;
          }
        return true;
      }*/
    @Override public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        try {
            switch (reqCode) {
                case (REQUEST_CODE):
                    if (resultCode == Activity.RESULT_OK) {
                        Uri contactData = data.getData();
                        Cursor c = getContentResolver().query
                                (contactData, null, null, null, null);
                        if (c.moveToFirst()) {
                            String contactId = c.getString(c.getColumnIndex
                                    (ContactsContract.Contacts._ID));
                            String hasNumber = c.getString(c.getColumnIndex
                                    (ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            String num = "";
                            String name = "";
                            if (Integer.valueOf(hasNumber) == 1) {
                                Cursor numbers = getContentResolver().query
                                        (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                                while (numbers.moveToNext()) {
                                    num = numbers.getString(numbers.getColumnIndex
                                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
//Toast.makeText(MainActivity.this, "Number="+num, Toast.LENGTH_LONG).show();
                                    name = numbers.getString(numbers.getColumnIndex
                                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                                    edtEnterFirstName.setText(""+name);
                                    edtMobile.setText(""+num);
                                }
                            }
                        }
                        break;
                    }
            }
        }catch (Exception e){
            System.out.println(e);
        }

    }
}