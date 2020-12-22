package com.example.eksinaapp.view.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.ForgetPassword;
import com.example.eksinaapp.model.Login;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.activity.HomeActivity;
import com.google.gson.JsonIOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordFragment extends Fragment {

private Button btnSubmit;
private Fragment fragment;
ImageView imgBack;
EditText edtEnterEmail;
String strEmail;
    public ForgetPasswordFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_forget_password, container, false);

        btnSubmit=rootView.findViewById(R.id.btnSubmit);

        imgBack=rootView.findViewById(R.id.imgBack);

        edtEnterEmail=rootView.findViewById(R.id.edtEnterEmail);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               strEmail=edtEnterEmail.getText().toString();
               forgetPassword(strEmail);
            }
        });
        return rootView;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onBackPressed()
    {
        LoginFragment loginFragment=new LoginFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, loginFragment);
        fragmentTransaction.commit();
    }

    private void forgetPassword(final String email) {

        if (email.isEmpty()) {
            edtEnterEmail.setError(getString(R.string.enterEmail));
            edtEnterEmail.requestFocus();
            return;
        }

        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(getActivity(),  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<ForgetPassword> loginCall = apiService.forgetPassword(email);

            loginCall.enqueue(new Callback<ForgetPassword>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<ForgetPassword> call,
                                       Response<ForgetPassword> response) {

                    pd.hide();

                    try {
                        if (response.isSuccessful()){
                            ForgetPassword forgetPassword = response.body();

                            if (forgetPassword.getStatus().equals(200)) {
                                Toast.makeText(getActivity(),forgetPassword.getMessage(), Toast.LENGTH_SHORT).show();

                                fragment=new LoginFragment();
                                replaceFragment(fragment);
                                clearUser();
                            } else {
                                Toast.makeText(getActivity(),forgetPassword.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception e) {
                        Log.d("exception",e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ForgetPassword> call,
                                      Throwable t) {
                    pd.hide();
                    Toast.makeText(getActivity(),"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (JsonIOException e){
            System.out.println("Thrown exception: " + e.getMessage());
        }

    }

    private void clearUser() {
        edtEnterEmail.setText(" ");
    }
    }