package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Beneficiary;
import com.example.eksinaapp.model.BeneficiaryResponse;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class AddBenefiariesActivity extends AppCompatActivity {
Button btnAdd;
ImageView imgBack;
Spinner savedBenefiries;
List<Beneficiary> beneficiaryList;
String strAmount,strEdtAmount,benId,countryId,strTransferMode,strcurrency;
TextView txtBen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_benefiaries);

        btnAdd=findViewById(R.id.btnAdd);

        imgBack=findViewById(R.id.imgBack);

        savedBenefiries=findViewById(R.id.savedBenefiries);

        txtBen=findViewById(R.id.txtBen);

        txtBen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddBenefiariesActivity.this,AddWalletBenefiriesActivity.class);
                startActivity(intent);
                finish();
            }
        });


        savedBenefiries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    benId = beneficiaryList.get(position - 1).getId();
                    countryId = beneficiaryList.get(position - 1).getCountryId();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent=getIntent();
        strAmount=intent.getStringExtra("Totalamount");
        strEdtAmount=intent.getStringExtra("amount");
        strTransferMode=intent.getStringExtra("transferMode");
        strcurrency= intent.getStringExtra("currency");

       /* if (strTransferMode =="wallet"){*/
            loadBenefiriesWallet(strTransferMode);
       /* }else {
            loadBenefiries();
        }*/



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateBenefiries()){
                    Intent intent=new Intent(AddBenefiariesActivity.this,PaymentActivity.class);
                    intent.putExtra("Totalamount",strAmount);
                    intent.putExtra("amount",strEdtAmount);
                    intent.putExtra("currency",strcurrency);
                    intent.putExtra("benId",benId);
                    intent.putExtra("countryId",countryId);
                    intent.putExtra("transferMode",strTransferMode);
                    startActivity(intent);
                }
            }
        });
    }

    private void loadBenefiriesWallet(String pay_method) {
        int loginId;
        loginId=0;
        ApiInterface apiService = ApiHandler.getApiService();
        final Call<BeneficiaryResponse> loginCall = apiService.showWalletBenefiries(Integer.parseInt(loginId+ SharedPrefManager.getLoginObject(AddBenefiariesActivity.this).getUserId()),pay_method);
        loginCall.enqueue(new Callback<BeneficiaryResponse>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<BeneficiaryResponse> call,
                                   Response<BeneficiaryResponse> response) {
                try {
                    List<String> listSpinner = new ArrayList<String>();

                    if (response.code()==200){

                    }
                    if (response.isSuccessful()) {
                        beneficiaryList = response.body().getBeneficiaries();
                        try {
                            listSpinner.add("Select from saved benefiries");
                        } catch (IllegalStateException i) {
                            System.out.println(i);
                        }
                        for (int i = 0; i < beneficiaryList.size(); i++) {
                            if (beneficiaryList.get(i).getFirstName() != null && beneficiaryList.get(i).getLastName() != null) {
                                listSpinner.add(beneficiaryList.get(i).getFirstName() + " " + beneficiaryList.get(i).getLastName());
                            } else {
                                listSpinner.add("No record found");
                                Toast.makeText(AddBenefiariesActivity.this, response.message(), Toast.LENGTH_LONG).show();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddBenefiariesActivity.this,
                                android.R.layout.simple_spinner_item, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        savedBenefiries.setAdapter(adapter);

                    } else {

                        try {
                            listSpinner.add("Select from saved benefiries");
                        } catch (IllegalStateException i) {
                            System.out.println(i);
                        }

                    }

                } catch (NullPointerException e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onFailure(Call<BeneficiaryResponse> call,
                                  Throwable t) {
                Toast.makeText(AddBenefiariesActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBenefiries(){
        int loginId;
        loginId=0;
        ApiInterface apiService = ApiHandler.getApiService();
        final Call<BeneficiaryResponse> loginCall = apiService.showbenificiery(Integer.parseInt(loginId+ SharedPrefManager.getLoginObject(AddBenefiariesActivity.this).getUserId()));
        loginCall.enqueue(new Callback<BeneficiaryResponse>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<BeneficiaryResponse> call,
                                   Response<BeneficiaryResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        beneficiaryList = response.body().getBeneficiaries();
                        List<String> listSpinner = new ArrayList<String>();
                        try {
                            listSpinner.add("Select from saved benefiries");
                        } catch (IllegalStateException i) {
                            System.out.println(i);
                        }
                        for (int i = 0; i < beneficiaryList.size(); i++) {
                            if (beneficiaryList.get(i).getFirstName() != null && beneficiaryList.get(i).getLastName() != null) {
                                listSpinner.add(beneficiaryList.get(i).getFirstName() + " " + beneficiaryList.get(i).getLastName());
                            } else {
                                Toast.makeText(AddBenefiariesActivity.this, response.message(), Toast.LENGTH_LONG).show();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddBenefiariesActivity.this,
                                android.R.layout.simple_spinner_item, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        savedBenefiries.setAdapter(adapter);

                    } else {
                    }

                } catch (NullPointerException e) {
                    System.out.println(e);
                }
            }

            @Override
            public void onFailure(Call<BeneficiaryResponse> call,
                                  Throwable t) {
                Toast.makeText(AddBenefiariesActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validateBenefiries() {
        if (savedBenefiries.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(savedBenefiries.getSelectedItem());
        } else {
            Toast.makeText(this, "Select benefiries", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}