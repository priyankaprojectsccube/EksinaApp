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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.ChangePassword;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.google.gson.JsonIOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
EditText edtOldPass,edtNewPass,edtEnterConfirmPass;
String strOldPass,strNewPass;
Button submit;
ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chnage_password);

        edtOldPass=findViewById(R.id.edtOldPass);
        edtNewPass=findViewById(R.id.edtNewPass);
        imgBack= findViewById(R.id.imgBack);
        edtEnterConfirmPass=findViewById(R.id.edtEnterConfirmPass);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        submit=findViewById(R.id.btnSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strOldPass=edtOldPass.getText().toString().trim();

                strNewPass=edtNewPass.getText().toString().trim();

                try {
                    if (validateOldPass() && validateNewPass() && validateConfirmPass() && validateCheckPassword())
                    {
                        changePassword(strOldPass,strNewPass);

                    }
                }catch (Exception e){
                    System.out.println("Thrown exception: " + e.getMessage());
                    Log.d("Exception:" ,e.getMessage());

                }

            }
        });
    }

    private void changePassword(String old_password,String new_password) {
        int loginId=0;
        try {

            ApiInterface apiService = ApiHandler.getApiService();
            final ProgressDialog pd = ViewUtils.getProgressBar(ChangePasswordActivity.this,  getString(R.string.loading), getString(R.string.wait));

            final Call<ChangePassword> loginCall = apiService.changePassword(Integer.parseInt(loginId+ SharedPrefManager.getLoginObject(ChangePasswordActivity.this).getUserId()),old_password,new_password);

            loginCall.enqueue(new Callback<ChangePassword>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<ChangePassword> call,
                                       Response<ChangePassword> response) {
                    ChangePassword changePassword=response.body();
                    pd.hide();

                    if (response.isSuccessful()){
                        if (changePassword.getStatus()!=null){
                            if (changePassword.getStatus().equals(200)
                            ) {
                                Toast.makeText(ChangePasswordActivity.this, changePassword.getMessage(), Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(ChangePasswordActivity.this,HomeActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ChangePasswordActivity.this,changePassword.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<ChangePassword> call,
                                      Throwable t) {
                    pd.hide();
                    //Toast.makeText(ChangePasswordActivity.this,"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (JsonIOException e){
            System.out.println("Thrown exception: " + e.getMessage());

        }

    }

    private boolean validateOldPass() {
        String password = edtOldPass.getText().toString().trim();
        if (password.isEmpty()) {
            Toast.makeText(ChangePasswordActivity.this, "Enter old password"
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateNewPass() {
        String newPass = edtNewPass.getText().toString();
        if (newPass.isEmpty()) {
            Toast.makeText(ChangePasswordActivity.this, "Enter new password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validateConfirmPass() {
        String confirmPassword = edtEnterConfirmPass.getText().toString();
        if (confirmPassword.isEmpty()) {
            Toast.makeText(ChangePasswordActivity.this, "Confirm Password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean validateCheckPassword() {
        String password = edtNewPass.getText().toString();
        String confirmPassword = edtEnterConfirmPass.getText().toString();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.invalidPass), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ChangePasswordActivity.this, HomeActivity.class);
        intent.putExtra("changepass","one");

        startActivity(intent);
    }
}