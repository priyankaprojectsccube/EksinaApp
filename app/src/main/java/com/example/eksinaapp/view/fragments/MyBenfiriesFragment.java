package com.example.eksinaapp.view.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Beneficiary;
import com.example.eksinaapp.model.BeneficiaryResponse;
import com.example.eksinaapp.model.RecentTransfer;
import com.example.eksinaapp.model.SavedBeneficiaries;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.activity.AddBenefiariesActivity;
import com.example.eksinaapp.view.activity.Addactivity;
import com.example.eksinaapp.view.adapter.RecentTransferAdapter;
import com.example.eksinaapp.view.adapter.SavedBeneficiariesAdapter;
import com.google.gson.JsonIOException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyBenfiriesFragment extends Fragment {
    RecyclerView recyclerView;
    Button btnAdd;
    TextView txtNodata;
    SavedBeneficiariesAdapter savedBeneficiariesAdapter;
    List<Beneficiary> beneficiaryList;
    public MyBenfiriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_my_benfiries, container, false);

       recyclerView=view.findViewById(R.id.recyclerview);
       btnAdd=view.findViewById(R.id.btnAdd);
       txtNodata=view.findViewById(R.id.txtNodata);


       btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getActivity(), Addactivity.class);
               startActivity(intent);
           }
       });
          recyclerView.setHasFixedSize(true);
          recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showBenefiries();
        return view;
    }

    private void showBenefiries() {
        int loginId;
        loginId = 0;
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(getActivity(),  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<BeneficiaryResponse> loginCall;
            loginCall = apiService.showbenificiery(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(getActivity()).getUserId()));

            loginCall.enqueue(new Callback<BeneficiaryResponse>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<BeneficiaryResponse> call,
                                       Response<BeneficiaryResponse> response) {
                    pd.hide();
                    BeneficiaryResponse beneficiaryResponse = response.body();
                    if (response.isSuccessful()) {
                        if (beneficiaryResponse.getBeneficiaries() != null) {
                            beneficiaryList = response.body().getBeneficiaries();
                            savedBeneficiariesAdapter = new SavedBeneficiariesAdapter(beneficiaryList, getActivity());
                            recyclerView.setAdapter(savedBeneficiariesAdapter);
                            txtNodata.setVisibility(View.GONE);
                        }else {
                           // Toast.makeText(getActivity(),beneficiaryResponse.getMessage(), Toast.LENGTH_LONG).show();
                            txtNodata.setVisibility(View.VISIBLE);
                            txtNodata.setText("No record found");
                        }
                    }

                }

                @Override
                public void onFailure(Call<BeneficiaryResponse> call,
                                      Throwable t) {
                    pd.hide();
                    Log.d("error",t.toString());

                }
            });
        } catch (JsonIOException exception) {
            System.out.println("Thrown exception: " + exception.getMessage());
        }
    }
}