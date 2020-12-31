package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.DeleteBenefiries;
import com.example.eksinaapp.model.ShowBeneficiery;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.fragments.DashboardFragment;
import com.example.eksinaapp.view.fragments.MyBenfiriesFragment;
import com.google.gson.JsonIOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BenefiriesDetails extends AppCompatActivity {
    TextView edtCity,edtEnterFirstName,edtLastName,edtNickName,edtMobile,edtBankName,edtBankAccount,edtIfsc,edtWalletId,edtCountry,edtWalletType;
    String strCity,strFirstName,strLastName,strNickName,strMobile,strbankname,strBankAccount,strIfsc,strWalletId,strType,strCountry,ben_id,wallet_type;
    RadioGroup rg;
    RadioButton rb_buisness,rb_family,rb_friend,rb_other;
    ImageView imgEdit,imgDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benefiries_details);

        edtCity=findViewById(R.id.edtCity);

        edtCountry=findViewById(R.id.select_city);

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

        imgEdit=findViewById(R.id.imgEdit);

        imgDelete=findViewById(R.id.imgDelete);

        edtWalletType=findViewById(R.id.wallet_type);

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           open(v);
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BenefiriesDetails.this, EditBenefiriesActivity.class);
                intent.putExtra("ben_id",ben_id);
                startActivity(intent);
                finish();
            }
        });
        Intent intent = getIntent();
        ben_id = intent.getStringExtra("ben_id");
        Log.d("ben_id",ben_id);

        getBenefiries();
    }

    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteBenefiries();
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void getBenefiries() {
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(BenefiriesDetails.this,  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<ShowBeneficiery> loginCall;
            loginCall = apiService.benefiriesDetails(Integer.parseInt(ben_id));

            loginCall.enqueue(new Callback<ShowBeneficiery>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<ShowBeneficiery> call,
                                       Response<ShowBeneficiery> response) {
                    pd.hide();
                    try {
                        if (response.body()!=null) {
                            ShowBeneficiery showBeneficiery = response.body();

                            if (showBeneficiery.getStatus()!=null){


                                strCountry=response.body().getCountryName();
                                edtCountry.setText(strCountry);

                                strCity =response.body().getCityName();
                                edtCity.setText(strCity);

                                strFirstName=response.body().getFirstName();
                                edtEnterFirstName.setText(strFirstName);

                                strLastName = response.body().getLastName();
                                edtLastName.setText(strLastName);

                                strNickName = response.body().getNickName();
                                edtNickName.setText(strNickName);

                                strMobile = response.body().getMobile();
                                edtMobile.setText(strMobile);

                                strbankname = response.body().getBankName();
                                edtBankName.setText(strbankname);

                                strBankAccount = response.body().getBankAccNo();
                                edtBankAccount.setText(strBankAccount);

                                strIfsc=response.body().getIfscCode();
                                edtIfsc.setText(strIfsc);

                                strWalletId=response.body().getWalletId();
                                edtWalletId.setText(strWalletId);

                                 if (response.body().getWalletIdFk().equals("1")){
                                     edtWalletType.setText("Paytm");
                                 }else if(response.body().getWalletIdFk().equals("2")) {
                                     edtWalletType.setText("Orange Money");
                                 }else{
                                     edtWalletType.setText("Wallet Type");
                                 }

                                if (response.body().getPurposeFor().equals("Buisness")){
                                    rb_buisness.setChecked(true);
                                    rb_family.setEnabled(false);
                                    rb_friend.setEnabled(false);
                                    rb_other.setEnabled(false);
                                }else if(response.body().getPurposeFor().equals("Family")){
                                    rb_family.setChecked(true);
                                    rb_buisness.setEnabled(false);
                                    rb_friend.setEnabled(false);
                                    rb_other.setEnabled(false);
                                }else if (response.body().getPurposeFor().equals("Friend")){
                                    rb_friend.setChecked(true);
                                    rb_family.setEnabled(false);
                                    rb_buisness.setEnabled(false);
                                    rb_other.setEnabled(false);
                                }else if (response.body().getPurposeFor().equals("Other")){
                                    rb_other.setChecked(true);
                                    rb_family.setEnabled(false);
                                    rb_friend.setEnabled(false);
                                    rb_buisness.setEnabled(false);
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
    private void deleteBenefiries() {

        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(BenefiriesDetails.this,  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<DeleteBenefiries> loginCall = apiService.deleteBenefiries(Integer.parseInt(ben_id));

            loginCall.enqueue(new Callback<DeleteBenefiries>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<DeleteBenefiries> call,
                                       Response<DeleteBenefiries> response) {
                    pd.hide();
                    try {
                        if (response.isSuccessful()){
                            DeleteBenefiries deleteBenefiries = response.body();
                            if (deleteBenefiries.getStatus().equals(200)) {
                                Intent intent=new Intent(BenefiriesDetails.this,HomeActivity.class);
                                intent.putExtra("addactivity","one");
                                startActivity(intent);
                                /* Fragment fragment=new DashboardFragment();
                                 replaceFragment(fragment);*/

                                Toast.makeText(BenefiriesDetails.this,deleteBenefiries.getMessage(), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(BenefiriesDetails.this,deleteBenefiries.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception e) {
                        Log.d("exception",e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<DeleteBenefiries> call,
                                      Throwable t) {
                    pd.hide();
                    Toast.makeText(BenefiriesDetails.this,"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (JsonIOException e){
            System.out.println("Thrown exception: " + e.getMessage());
        }

    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BenefiriesDetails.this, HomeActivity.class);
        intent.putExtra("addactivity","one");

        startActivity(intent);
    }
}