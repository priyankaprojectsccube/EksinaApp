package com.example.eksinaapp.view.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.BeneficiaryResponse;
import com.example.eksinaapp.model.RecentTransfer;
import com.example.eksinaapp.model.Transaction;
import com.example.eksinaapp.model.TransactionResponse;
import com.example.eksinaapp.model.UserProfile;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.activity.AccountValidationActivity;
import com.example.eksinaapp.view.activity.UserProfileActivity;
import com.example.eksinaapp.view.activity.YourTransferActivity;
import com.example.eksinaapp.view.adapter.RecentTransferAdapter;
import com.example.eksinaapp.view.adapter.SavedBeneficiariesAdapter;
import com.google.gson.JsonIOException;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyTransactionFragment extends Fragment {

    RecyclerView recyclerView;
    Button btnSendMoney;
    Fragment fragment;
    TextView txtFromDate,txtToDate;
    String strFromDate,strToDate;
DatePickerDialog datePickerDialog1,datePickerDialog2;
    final Calendar myCalendar = Calendar.getInstance();
    RecentTransferAdapter adapter;
    ImageView imgFromDate,imgToDate;
    List<Transaction> transactionsList;
    TextView txtNodata;
    UserProfile userProfile=new UserProfile();
    int nxtyear= 0, nxtmonthOfYear= 0, nxtdayOfMonth= 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_my_transaction, container, false);

        recyclerView=view.findViewById(R.id.rlTransfer);

        txtFromDate=view.findViewById(R.id.txtSelectFromDate);

        txtToDate=view.findViewById(R.id.txtSelectToDate);

        btnSendMoney=view.findViewById(R.id.btnSendMoney);

        imgFromDate=view.findViewById(R.id.imgSelectFromDate);

        imgToDate=view.findViewById(R.id.imgSelectToDate);

        txtNodata=view.findViewById(R.id.txtNodata);

        updateLabel();

        updateToTime();


        showDetails("","");

        Log.d("startdate",strFromDate);

        Log.d("enddate",strToDate);

//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
////                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                updateLabel();
//
//            }
//        };

        imgFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DatePickerDialog(getActivity(), date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                // date picker dialog
                datePickerDialog1 = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                // set day of month , month and year value in the edit text
                                txtFromDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                nxtyear = year;
                                nxtmonthOfYear = monthOfYear + 1;
                                nxtdayOfMonth = dayOfMonth;
                                Log.d("calvaluefir",nxtyear+" "+nxtmonthOfYear+" "+nxtdayOfMonth);
                               // txtFromDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//updateLabel();
//                                String myFormat = "yyyy-MM-dd"; //In which you need put here
//                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                                txtFromDate.setText(sdf.format(myCalendar.getTime()));
                                strFromDate=txtFromDate.getText().toString();
                                Log.d("fromDate",strFromDate);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); //disable previous dates
                datePickerDialog1.show();
            }

        });
        imgToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("calvalue",nxtyear+" "+nxtmonthOfYear+" "+nxtdayOfMonth);
                if(nxtyear != 0 && nxtmonthOfYear != 0 && nxtdayOfMonth != 0)
                {
                 //   new DatePickerDialog(getActivity(), Todate, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    final Calendar c = Calendar.getInstance();
                    int mYear = nxtyear; // current year
                    int mMonth = nxtmonthOfYear-1; // current month
                    int mDay = nxtdayOfMonth; // current day

                    datePickerDialog2 = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                    // set day of month , month and year value in the edit text
                                    txtToDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                    // txtFromDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//updateLabel();
//                                String myFormat = "yyyy-MM-dd"; //In which you need put here
//                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                                txtFromDate.setText(sdf.format(myCalendar.getTime()));

                                    strToDate=txtToDate.getText().toString();
showDetails(strFromDate,strToDate);
                                    Log.d("toDate",strToDate);
                                }
                            }, mYear, mMonth, mDay);

                    c.set(mYear, mMonth, mDay);
                    datePickerDialog2.getDatePicker().setMinDate(c.getTimeInMillis()); //disable previous dates
                    datePickerDialog2.show();
                }
                else{
                    Toast.makeText(getActivity(),"Please Select Start Date",Toast.LENGTH_SHORT).show();
                }

            }
        });

//        final DatePickerDialog.OnDateSetListener Todate = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateToTime();
//                showDetails(strFromDate,strToDate);
//            }
//        };



        btnSendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showProfile();
            }
        });

        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    private void updateToTime() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtToDate.setText(sdf.format(myCalendar.getTime()));
        strToDate=txtToDate.getText().toString();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtFromDate.setText(sdf.format(myCalendar.getTime()));
        strFromDate=txtFromDate.getText().toString();
        Log.d("fromDate",strFromDate);
    }

    private void showDetails(String startDate,String endDate) {
        int loginId;
        loginId = 0;
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(getActivity(),  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<TransactionResponse> loginCall;
            loginCall = apiService.my_transactions(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(getActivity()).getUserId()),startDate,endDate);

            loginCall.enqueue(new Callback<TransactionResponse>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<TransactionResponse> call,
                                       Response<TransactionResponse> response) {
                    pd.hide();
                    TransactionResponse transaction = response.body();
                    if (response.isSuccessful()) {
                        if (transaction.getTransaction() != null) {
                            transactionsList = response.body().getTransaction();
                            adapter = new RecentTransferAdapter(transactionsList, getActivity());
                            recyclerView.setAdapter(adapter);
                            txtNodata.setVisibility(View.GONE);
                        }else {
                           // Toast.makeText(getActivity(),transaction.getMessage(), Toast.LENGTH_LONG).show();
                            txtNodata.setVisibility(View.VISIBLE);
                            txtNodata.setText("No record found");
                        }
                    }

                }

                @Override
                public void onFailure(Call<TransactionResponse> call,
                                      Throwable t) {
                    pd.hide();
                    Log.d("error",t.toString());

                }
            });
        } catch (JsonIOException exception) {
            System.out.println("Thrown exception: " + exception.getMessage());
        }
    }
    private void showProfile() {
        try {
            int loginId=0;

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<UserProfile> loginCall;
            loginCall = apiService.showProfile(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(getActivity()).getUserId()));

            loginCall.enqueue(new Callback<UserProfile>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<UserProfile> call,
                                       Response<UserProfile> response) {

                    try {
                        if (response.body()!=null) {
                            UserProfile userProfile = response.body();
                            if (userProfile.getStatus()!=null) {
                                    if (userProfile.getDocVarification().equals("pending") && userProfile.getTransCount()==0){
                                        Intent intent=new Intent(getActivity(), YourTransferActivity.class);
                                        startActivity(intent);
                                    }else if(userProfile.getDocVarification().equals("pending") && userProfile.getTransCount()>=1){
                                        Intent intent=new Intent(getActivity(), AccountValidationActivity.class);
                                        startActivity(intent);
                                }else if (userProfile.getDocVarification().equals("approved")){  //&& userProfile.getTransCount()>1
                                        Intent intent=new Intent(getActivity(), YourTransferActivity.class);
                                        startActivity(intent);
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
                }
            });
        } catch (JsonIOException exception) {
            System.out.println("Thrown exception: " + exception.getMessage());
        }
    }
}