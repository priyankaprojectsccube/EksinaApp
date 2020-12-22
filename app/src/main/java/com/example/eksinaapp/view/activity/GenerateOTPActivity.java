package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.AccountValidationStep03;
import com.example.eksinaapp.model.Login;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.google.gson.JsonIOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateOTPActivity extends AppCompatActivity {
Button btnDone;
EditText edtEnterOTP;
String strOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_o_t_p);

        btnDone=findViewById(R.id.btnDone);
        edtEnterOTP=findViewById(R.id.edtEnterOTP);


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strOTP=edtEnterOTP.getText().toString();
                 checkOTP(strOTP);
            }
        });
    }
    private void checkOTP(final String otp) {
        int loginId;
        loginId=0;
        if (otp.isEmpty()) {
            edtEnterOTP.setError(getString(R.string.enterEmail));
            edtEnterOTP.requestFocus();
            return;
        }


        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(GenerateOTPActivity.this, getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<AccountValidationStep03> loginCall = apiService.account_validation_step03(Integer.parseInt(loginId+ SharedPrefManager.getLoginObject(GenerateOTPActivity.this).getUserId()), otp);

            loginCall.enqueue(new Callback<AccountValidationStep03>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<AccountValidationStep03> call,
                                       Response<AccountValidationStep03> response) {

                    pd.hide();

                    try {
                        if (response.isSuccessful()) {
                            AccountValidationStep03 accountValidationStep03 = response.body();
                            if (accountValidationStep03.getStatus().equals(200)) {

                                Intent intent=new Intent(GenerateOTPActivity.this,YourTransferActivity.class);
                                startActivity(intent);
                               clearUser();
                            } else {
                                Toast.makeText(GenerateOTPActivity.this, accountValidationStep03.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception e) {
                        Log.d("exception", e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<AccountValidationStep03> call,
                                      Throwable t) {
                    pd.hide();
                    Toast.makeText(GenerateOTPActivity.this, "Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JsonIOException e) {
            System.out.println("Thrown exception: " + e.getMessage());
        }

    }

    private void clearUser() {
        edtEnterOTP.setText("");
    }
}