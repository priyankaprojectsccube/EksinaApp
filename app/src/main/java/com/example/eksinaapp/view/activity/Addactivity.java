package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.gson.JsonIOException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Addactivity extends AppCompatActivity {
    Button btnAdd;
    ImageView imgBack,imgAdd;
    Spinner select_city;
    List<Country> countryList;
    List<Wallet> wallets;
    EditText edtCity,edtEnterFirstName,edtLastName,edtNickName,edtMobile,edtBankName,edtBankAccount,edtIfsc,edtWalletId;
    String strCity,strFirstName,strLastName,strNickName,strMobile,strbankname="",strBankAccount="",strIfsc="",strWalletId="",strType="",strtakenumber="";
    RadioGroup rg,rgwallet;
    RadioButton rb_buisness,rb_family,rb_friend,rb_other,rb_orangemoney,rb_paytm;
    Fragment fragment;
    int country = 0;
    String inputCountry = String.valueOf(country);

    int wallet_type=0;


    private final int REQUEST_CODE=99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);

        btnAdd=findViewById(R.id.btnAdd);

        imgBack=findViewById(R.id.imgBack);

        imgAdd=findViewById(R.id.imgAdd);

        select_city=findViewById(R.id.select_city);

        edtCity=findViewById(R.id.edtCity);

        edtEnterFirstName=findViewById(R.id.edtEnterFirstName);

        edtLastName=findViewById(R.id.edtLastName);

        edtNickName=findViewById(R.id.edtNickName);

        edtMobile=findViewById(R.id.edtMobile);
        edtBankName = findViewById(R.id.edtBankName);
        edtBankAccount=findViewById(R.id.edtBankAccount);

        edtIfsc=findViewById(R.id.edtIfsc);

        edtWalletId=findViewById(R.id.edtWalletId);



        rg=findViewById(R.id.rg);

        rb_buisness=findViewById(R.id.rb_buisness);

        rb_family=findViewById(R.id.rb_family);

        rb_friend=findViewById(R.id.rb_friend);

        rb_other=findViewById(R.id.rb_other);

        rgwallet = findViewById(R.id.rgwallet);

//        rb_orangemoney = findViewById(R.id.rb_orangemoney);
//
//        rb_paytm = findViewById(R.id.rb_paytm);



           try {
               showContacts();
           }catch (Exception e){
               System.out.println(e);
           }


        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calContctPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                calContctPickerIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(calContctPickerIntent, 1);
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        ContactsContract.Contacts.CONTENT_URI);
//                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        rgwallet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                for(int i=0; i<rg.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) rg.getChildAt(i);
                    if(btn.getId() == checkedId) {
                        String text = String.valueOf(btn.getId());
                        wallet_type = Integer.parseInt(text);
                        Log.d("getvaluewt", String.valueOf(wallet_type));
                        // do something with text
                        return;
                    }
                }
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

           //     onBackPressed();
                Intent intent = new Intent(Addactivity.this, HomeActivity.class);
                intent.putExtra("addactivity","one");

                startActivity(intent);
            }
        });

        select_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    country = Integer.parseInt(countryList.get(position).getId());
                    Log.d("Selectedcountry", String.valueOf(country));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        wallet_id_fk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position != 0) {
//                    wallet_type = Integer.parseInt(wallets.get(position-1).getId());
//                    Log.d("wallet_type", String.valueOf(wallet_type));
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                            strCity = edtCity.getText().toString().trim();
                            strFirstName = edtEnterFirstName.getText().toString().trim();
                            strLastName = edtLastName.getText().toString().trim();
                            strNickName= edtNickName.getText().toString().trim();
                            strMobile=edtMobile.getText().toString().trim();
                    strbankname =edtBankName.getText().toString();
                            strBankAccount=edtBankAccount.getText().toString();
                            strIfsc=edtIfsc.getText().toString();
                            strWalletId=edtWalletId.getText().toString();
                            inputCountry=select_city.getSelectedItem().toString().trim();



//validateRadioGroup()

                            if (validateCountry() && validateCity() && validateFirstName() && validateLastname() && validateNickName()  && validateMobileno() &&   validatewalletId() &&  validatewallettype()){




                                                Log.d("values",country+""+strCity+""+strFirstName+""+strLastName+""+strNickName+""+strMobile+""+strbankname+""+strBankAccount+""+strIfsc+""+strWalletId+""+wallet_type+""+strType);
                                                addBenifiries(country, strCity, strFirstName, strLastName, strNickName, strMobile,strbankname,strBankAccount,strIfsc,strWalletId,wallet_type,strType);



                            }


                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        loadCity();
        loadWalletType();
    }




//    private boolean validatespinnerwallet() {
//        if (wallet_id_fk.getSelectedItemPosition() > 0) {
//
//            String itemvalue = String.valueOf(wallet_id_fk.getSelectedItem());
//            String walletid = edtWalletId.getText().toString().trim();
//            if (walletid.isEmpty()) {
//                Toast.makeText(Addactivity.this, "Please Enter Wallet Id", Toast.LENGTH_SHORT).show();
//                return false;
//            }else{
//
//            }
//
//
//        } else {
////            Toast.makeText(AddWalletBenefiriesActivity.this,"Please select wallet type",Toast.LENGTH_LONG).show();
////            return false;
//        }
//        return true;
//    }

    private boolean validatewallettype() {
        if(rgwallet.getCheckedRadioButtonId() == -1)
        {
//            Toast.makeText(this, "Please select type", Toast.LENGTH_SHORT).show();
//            return false;
        }
        else
        {
            String walletid = edtWalletId.getText().toString().trim();
            if (walletid.isEmpty()) {
                Toast.makeText(Addactivity.this, "Please Enter Wallet Id", Toast.LENGTH_SHORT).show();
                return false;
            }else{

            }
        }

        return true;
    }

    private boolean validatewalletId() {
        String walletid = edtWalletId.getText().toString().trim();
        if (walletid.isEmpty()) {
//            Toast.makeText(AddWalletBenefiriesActivity.this, "Please Enter Wallet Id", Toast.LENGTH_SHORT).show();
//            return false;
        }
        else{
//            if (wallet_id_fk.getSelectedItemPosition() > 0) {
//                String itemvalue = String.valueOf(wallet_id_fk.getSelectedItem());
//            }
//            else {
//                Toast.makeText(Addactivity.this,"Please select wallet type",Toast.LENGTH_LONG).show();
//                return false;
//            }
            if(rgwallet.getCheckedRadioButtonId() == -1)
            {
                Toast.makeText(this, "Please select wallet type", Toast.LENGTH_SHORT).show();
                return false;
            }
            else
            {
                // not checked
            }
        }
        return true;
    }



    private boolean validateifsccode() {
        String ifsc = edtIfsc.getText().toString().trim();
        if (ifsc.isEmpty()) {
            Toast.makeText(Addactivity.this, "Please Enter IFSC", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateaccountnumber() {
        String bankaccount = edtBankAccount.getText().toString().trim();
        if (bankaccount.isEmpty()) {
            Toast.makeText(Addactivity.this, "Please Enter Bank Account Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatebankname() {
        String bankname = edtBankName.getText().toString().trim();
        if (bankname.isEmpty()) {
            Toast.makeText(Addactivity.this, "Please Enter Bank name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
                    CountryResponse countryResponse=response.body();
                    if (response.isSuccessful()) {
                        if (countryResponse.getStatus()==200)
                        countryList = response.body().getCountry();
                        List<String> listSpinner = new ArrayList<String>();
                        for (int i = 0; i < countryList.size(); i++) {
                            if (countryList.get(i).getCountryName() != null && countryList.get(i).getCountryName() != null) {
                                listSpinner.add(countryList.get(i).getCountryName());
                                SharedPrefManager.storeCountry(countryResponse,Addactivity.this);
                                countryList.get(i).getCountryName();
                            }else {
                                Toast.makeText(Addactivity.this,response.message(),Toast.LENGTH_LONG).show();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Addactivity.this,
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
                Toast.makeText(Addactivity.this, t.toString(), Toast.LENGTH_SHORT).show();
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
                        }
                        for (int i = 0; i < wallets.size(); i++) {
                            if (wallets.get(i).getCountryCode() != null && wallets.get(i).getCountryId() != null) {
                                listSpinner.add(wallets.get(i).getCountryId());
                               // SharedPrefManager.storeCountry(walletResponse,Addactivity.this);
                                wallets.get(i).getCountryId();
                            }else {
                                Toast.makeText(Addactivity.this,response.message(),Toast.LENGTH_LONG).show();
                            }
                        }
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Addactivity.this,
//                                android.R.layout.simple_spinner_item, listSpinner);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                        RadioButton button;
                        for(int i = 0; i < wallets.size(); i++) {
                            button = new RadioButton(Addactivity.this);
                            button.setText("" +wallets.get(i).getCountryId());
                            button.setId(Integer.parseInt(wallets.get(i).getId()));
                            rgwallet.addView(button);
                        }
                     //   wallet_id_fk.setAdapter(adapter);
                    } else {

                    }

                }catch (NullPointerException e){
                    System.out.println(e);
                }

            }

            @Override
            public void onFailure(Call<WalletResponse> call,
                                  Throwable t) {
                Toast.makeText(Addactivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addBenifiries(final int country_id, String city_name, String first_name, String last_name, String nick_name,
                               String mobile,String bank_name,String bank_acc_no,String ifsc_code,String wallet_id,int wallet_id_fk,String purpose_for) {

         int loginId;
         loginId=0;
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(Addactivity.this,  getString(R.string.loading), getString(R.string.wait));
            ApiInterface apiService = ApiHandler.getApiService();
            final Call<AddBenificiery> loginCall = apiService.addbenificiery(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(Addactivity.this).getUserId()), country_id, city_name, first_name,
                    last_name, nick_name, mobile,bank_name,bank_acc_no,ifsc_code,wallet_id,wallet_id_fk,purpose_for);

            loginCall.enqueue(new Callback<AddBenificiery>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<AddBenificiery> call,
                                       Response<AddBenificiery> response) {
                    pd.hide();
                   // Log.w("myresponse",new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    try {
                        AddBenificiery addBenificiery = response.body();

                        if (addBenificiery.getStatus() != null) {
                            if (addBenificiery.getStatus().equals(200)) {
                                Toast.makeText(Addactivity.this, addBenificiery.getMessage(), Toast.LENGTH_LONG).show();
                                fragment = new MyBenfiriesFragment();
                                replaceFragment(fragment);
                               // clearUser();
                            } else{
                                Toast.makeText(Addactivity.this, addBenificiery.getMessage(), Toast.LENGTH_LONG).show();
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
                     Toast.makeText(Addactivity.this,"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }

            });

        } catch (JsonIOException e) {
            System.out.println("Thrown exception: " + e.getMessage());
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private boolean validateCountry(){
        if (select_city.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(select_city.getSelectedItem());
        } else {
          Toast.makeText(Addactivity.this,"Please select country",Toast.LENGTH_LONG).show();
            return false;
        }
    return true;
    }


    private boolean validateCity() {
        String city = edtCity.getText().toString().trim();
        if (city.isEmpty()) {
            Toast.makeText(Addactivity.this, "Please Enter city", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateNickName() {
        String nickName = edtNickName.getText().toString().trim();
        if (nickName.isEmpty()) {
            Toast.makeText(Addactivity.this, "Please Enter Nick name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateFirstName() {
        String firstName = edtEnterFirstName.getText().toString().trim();
        if (firstName.isEmpty()) {
            Toast.makeText(Addactivity.this, getString(R.string.enterFirst), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private boolean validateLastname() {
        String lastName = edtLastName.getText().toString().trim();
        if (lastName.isEmpty()) {
            Toast.makeText(Addactivity.this, getString(R.string.enterLastName), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateMobileno() {
        String mobileNo = edtMobile.getText().toString().trim();

            if (mobileNo.isEmpty()|| mobileNo.length() < 10 ) {
                Toast.makeText(Addactivity.this, getString(R.string.enterMobileNumber), Toast.LENGTH_SHORT).show();
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
//    @Override public void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//        try {
//            switch (reqCode) {
//                case (REQUEST_CODE):
//                    if (resultCode == Activity.RESULT_OK) {
//                        Uri contactData = data.getData();
//                        Cursor c = getContentResolver().query
//                                (contactData, null, null, null, null);
//                        if (c.moveToFirst()) {
//                            String contactId = c.getString(c.getColumnIndex
//                                    (ContactsContract.Contacts._ID));
//                            String hasNumber = c.getString(c.getColumnIndex
//                                    (ContactsContract.Contacts.HAS_PHONE_NUMBER));
//                            String num = "";
//                            String name = "";
//                            if (Integer.valueOf(hasNumber) == 1) {
//                                Cursor numbers = getContentResolver().query
//                                        (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
//                                while (numbers.moveToNext()) {
//                                    num = numbers.getString(numbers.getColumnIndex
//                                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
////Toast.makeText(MainActivity.this, "Number="+num, Toast.LENGTH_LONG).show();
//                                    name = numbers.getString(numbers.getColumnIndex
//                                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//
//                                    edtEnterFirstName.setText(""+name);
//                                    edtMobile.setText(""+num);
//                                    strtakenumber ="fromphonebook";
//
//                                }
//                            }
//                        }
//                        break;
//                    }
//            }
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data)
    {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode)
        {
            case (1) :
                if (resultCode == Activity.RESULT_OK)
                {
                    Uri contctDataVar = data.getData();

                    Cursor contctCursorVar = getContentResolver().query(contctDataVar, null,
                            null, null, null);
                    if (contctCursorVar.getCount() > 0)
                    {
                        while (contctCursorVar.moveToNext())
                        {
                            String ContctUidVar = contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.Contacts._ID));

                            String ContctNamVar = contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            edtEnterFirstName.setText(ContctNamVar);
                            Log.i("Names", ContctNamVar);

                            if (Integer.parseInt(contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                            {
                                // Query phone here. Covered next
                                String ContctMobVar = contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                ContctMobVar = ContctMobVar.replaceAll( " ","");
                                edtMobile.setText(ContctMobVar);
                                Log.i("Number", ContctMobVar);
                            }

                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}