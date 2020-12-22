package com.example.eksinaapp.view.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Country;
import com.example.eksinaapp.model.CountryResponse;
import com.example.eksinaapp.model.Register;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.activity.EditBenefiriesActivity;
import com.google.gson.JsonIOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpFragment extends Fragment {
private Button btnSignUp;
private TextView txtSignInnow,txtPrivacy,txtTerms;
private ImageView imgBack;
private Fragment fragment;
private EditText edtFirtName,edtLastName,edtMobile,edtEmail,edtPass,edtConfirmPass;
TextView edtDob;
private String strFirstName,strLastName,strMobile,strEmail,strPass,strDob,strCountry,strConfirmPass;
//private Spinner select_city;
    DatePickerDialog picker;

  //  final Calendar myCalendar = Calendar.getInstance();
    Calendar c = Calendar.getInstance();
    SimpleDateFormat dformate = new SimpleDateFormat("dd MMM yyyy");
    List<Country> countryList;
    CheckBox checkbox;
    int country = 0;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_sign_up, container, false);

        btnSignUp=rootView.findViewById(R.id.btnSignUp);

        txtSignInnow=rootView.findViewById(R.id.txtSignin);

        imgBack=rootView.findViewById(R.id.imgBack);

        edtFirtName=rootView.findViewById(R.id.edtEnterName);

        edtLastName=rootView.findViewById(R.id.edtEnterLastName);

        edtMobile=rootView.findViewById(R.id.edtEnterMobile);

        edtEmail=rootView.findViewById(R.id.edtEnterEmail);

        edtPass=rootView.findViewById(R.id.edtEnterPass);

        edtConfirmPass=rootView.findViewById(R.id.edtEnterConfirmPass);

        edtDob=rootView.findViewById(R.id.edtDob);

        //select_city=rootView.findViewById(R.id.select_city);

        checkbox=rootView.findViewById(R.id.checkBox1);

        txtPrivacy=rootView.findViewById(R.id.txtPrivacy);

        txtTerms=rootView.findViewById(R.id.txtTerms);

      //  openDatepicker();

        edtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openDatepicker();
            }
        });
        txtTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://cube9projects.co.in/eksina/site/privacy_policy"));
                startActivity(browserIntent);
            }
        });

        txtPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://cube9projects.co.in/eksina/site/privacy_policy"));
                startActivity(browserIntent);
            }
        });

        btnSignUp.setEnabled(false);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked() && checkbox.isChecked()){
                    btnSignUp.setEnabled(true);

                }
                else {
                    btnSignUp.setEnabled(false);

                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });





        strCountry= String.valueOf(country);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (validateFirstName() && validateLastname() && validateMobileno() && validateEmail() && validateDOB() &&
                            /*valprivate void openDatepicker() {

    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
// Launch Date Picker Dialog

android.app.DatePickerDialog dpd = new android.app.DatePickerDialog(

getActivity(),

R.style.DialogThemeRed,

new android.app.DatePickerDialog.OnDateSetListener() {

@Override

public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                    //Set current selected date to Calendar

                    c.set(Calendar.YEAR, year);
                    c.set(Calendar.MONTH, monthOfYear);
                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    //Set date to textview
                    yourTextview.setText(dformate.format(c.getTime()));

                }
            }, mYear, mMonth, mDay);

    //Set Max Date for disable future dates
    dpd.getDatePicker().setMaxDate(new Date().getTime());
    dpd.show();


}idateCurrency() &&*/ validatePassword() && validateConfirmPassword() && validateCheckPassword()) {
                        strFirstName = edtFirtName.getText().toString().trim();
                        strLastName = edtLastName.getText().toString().trim();
                        strEmail = edtEmail.getText().toString().trim();
                        strMobile = edtMobile.getText().toString().trim();
                        strPass = edtPass.getText().toString().trim();
                        strConfirmPass = edtConfirmPass.getText().toString().trim();
                        strDob=edtDob.getText().toString();
                       // strCountry=select_city.getSelectedItem().toString().trim();

                        signUp(strFirstName, strLastName, strEmail, strMobile, strPass,strDob);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        txtSignInnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fragment=new LoginFragment();
               replaceFragment(fragment);
            }
        });
        //loadCity();
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
        SplashFragment splashFragment=new SplashFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, splashFragment);
        fragmentTransaction.commit();
    }

    private void signUp(final String FirstName, String LastName, String EmailID, String mobNo,String user_password,String dob) {
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(getActivity(),  getString(R.string.loading), getString(R.string.wait));
            ApiInterface apiService = ApiHandler.getApiService();
            final Call<Register> loginCall = apiService.reigsterUser(FirstName, LastName, EmailID, mobNo, user_password,dob);
            loginCall.enqueue(new Callback<Register>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<Register> call,
                                       Response<Register> response) {
                    pd.hide();
                    try {
                        if (response.isSuccessful()){
                            Register user = response.body();
                            if (user.getStatus().equals(200)) {
                                Toast.makeText(getActivity(), user.getMessage(), Toast.LENGTH_LONG).show();
                                fragment = new LoginFragment();
                                replaceFragment(fragment);
                                clearUser();
                            } else if (user.getStatus().equals(400)){
                                Toast.makeText(getActivity(), user.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        }catch (Exception e) {
                        System.out.println(e);
                    }
                }
                @Override
                public void onFailure(Call<Register> call,
                                      Throwable t) {
                    pd.hide();
                     Toast.makeText(getActivity(),"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }

            });

        } catch (JsonIOException e) {
            System.out.println("Thrown exception: " + e.getMessage());
        }
    }


    private void clearUser(){
        edtFirtName.setText("");
        edtLastName.setText("");
        edtEmail.setText("");
        edtMobile.setText("");
        edtPass.setText("");
        edtConfirmPass.setText("");
        edtDob.setText("");
    }

    private boolean validateFirstName() {
        String firstName = edtFirtName.getText().toString().trim();
        if (firstName.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.enterFirst), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateLastname() {
        String lastName = edtLastName.getText().toString().trim();
        if (lastName.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.enterLastName), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateMobileno() {
        String mobileNo = edtMobile.getText().toString().trim();
        if (mobileNo.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.enterMobileNumber), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateDOB() {
       /* String dob = edtDob.getText().toString().trim();*/
        if (edtDob.getText().toString().equals("Enter your date of birth")) {
            Toast.makeText(getActivity(), "Enter date of birth", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

   /* private boolean validateCurrency() {
        if (select_city.getSelectedItemPosition() > 0) {
            String itemvalue = String.valueOf(select_city.getSelectedItem());
        } else {
            Toast.makeText(getActivity(), "Select country", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }*/

    private boolean validateEmail() {
        String email = edtEmail.getText().toString().trim();
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

        }else {
            Toast.makeText(getActivity(), getString(R.string.invalidamail)
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String password = edtPass.getText().toString().trim();
        if (password.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.enterPass)
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword() {
        String confirmPassword = edtConfirmPass.getText().toString();
        if (confirmPassword.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.enterConf), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validateCheckPassword() {
        String password = edtPass.getText().toString();
        String confirmPassword = edtConfirmPass.getText().toString();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), getString(R.string.invalidPass), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void openDatepicker() {

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
// Launch Date Picker Dialog

        android.app.DatePickerDialog dpd = new android.app.DatePickerDialog(

                getActivity(),

              //  R.style.DialogThemeRed,

                new android.app.DatePickerDialog.OnDateSetListener() {

                    @Override

                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                        //Set current selected date to Calendar

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        //Set date to textview
                        edtDob.setText(dformate.format(c.getTime()));

                    }
                }, mYear, mMonth, mDay);



        //Set Max Date for disable future dates
        dpd.getDatePicker().setMaxDate(new Date().getTime());
        dpd.show();


    }
   /* private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edtDob.setText(sdf.format(myCalendar.getTime()));
        strDob=edtDob.getText().toString();
        Log.d("fromDate",strDob);
    }*/

}