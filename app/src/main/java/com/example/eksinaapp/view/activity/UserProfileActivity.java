package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.ShowBeneficiery;
import com.example.eksinaapp.model.UserProfile;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.google.gson.JsonIOException;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {
TextView txtFirstName,txtLastName,txtEmail,txtMobile,txtDob;
CircleImageView profileImage;
ImageView imgEdit;
String strFirstName,strLastName,strEmail,strMobile,strDob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        txtFirstName=findViewById(R.id.txtFirstName);
        txtLastName=findViewById(R.id.txtLastName);
        txtEmail=findViewById(R.id.txtEmail);
        txtMobile=findViewById(R.id.txtMobile);
        profileImage=findViewById(R.id.imgUpdatePic);
        imgEdit=findViewById(R.id.imgEdit);
        txtDob=findViewById(R.id.txtDob);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfileActivity.this,UpdateProfileActivity.class);
                startActivity(intent);
            }
        });

        showProfile();
    }

    private void showProfile() {
        try {
            int loginId=0;
            final ProgressDialog pd = ViewUtils.getProgressBar(UserProfileActivity.this,  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<UserProfile> loginCall;
            loginCall = apiService.showProfile(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(UserProfileActivity.this).getUserId()));

            loginCall.enqueue(new Callback<UserProfile>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<UserProfile> call,
                                       Response<UserProfile> response) {
                    pd.hide();
                    try {
                        if (response.body()!=null) {
                            UserProfile userProfile = response.body();
                            if (userProfile.getStatus()!=null) {


                                strFirstName = response.body().getFirstName();
                                txtFirstName.setText(strFirstName);

                                strLastName = response.body().getLastName();
                                txtLastName.setText(strLastName);

                                strMobile = response.body().getMobileNo();
                                txtMobile.setText(strMobile);

                                strEmail = response.body().getEmailId();
                                txtEmail.setText(strEmail);

                                strDob=response.body().getDob();
                                txtDob.setText(strDob);

                                if(response.body().getUserImage() != null && !response.body().getUserImage().isEmpty())
                                {
                                    Picasso.get()
                                            .load(userProfile.getUserImage())
                                            .placeholder(R.drawable.images)
                                            .error(R.drawable.images)
                                            .into(profileImage);
                                }else {
                                    // Toast.makeText(UserProfileActivity.this, "Empty Image URL", Toast.LENGTH_LONG).show();
                                    Picasso.get().load(R.drawable.images).into(profileImage);
                                }

                            }
                        }
                    } catch (NullPointerException e) {
                        System.out.println(e);
                    }


                }

                @Override
                public void onFailure(Call<UserProfile> call,
                                      Throwable t) {
                    pd.hide();

                }
            });
        } catch (JsonIOException exception) {
            System.out.println("Thrown exception: " + exception.getMessage());
        }
    }
}