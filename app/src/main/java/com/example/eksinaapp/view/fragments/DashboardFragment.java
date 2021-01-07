package com.example.eksinaapp.view.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.eksinaapp.R;
import com.example.eksinaapp.model.CardResponse;
import com.example.eksinaapp.model.Datum;
import com.example.eksinaapp.model.RecenetTransaction;
import com.example.eksinaapp.model.RecentTransactionResponse;
import com.example.eksinaapp.model.UserProfile;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;

import com.example.eksinaapp.view.adapter.CardDetailsAdapter;
import com.example.eksinaapp.view.adapter.RecentTransactionAdapter;
import com.google.gson.JsonIOException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerView,recyclerviewCards;
    List<RecenetTransaction> recenetTransactionList;
    RecentTransactionAdapter recentTransactionAdapter;
    TextView txtNodata,txtNodataCards,txtCreateAccount;
    String strTxtName;
    CardDetailsAdapter cardAdapter;
    List<Datum> cardsList;

    public DashboardFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView=view.findViewById(R.id.rlTransfer);

        recyclerviewCards=view.findViewById(R.id.recyclerviewCards);

        txtNodata=view.findViewById(R.id.txtNodata);

        txtNodataCards=view.findViewById(R.id.txtNodataCards);

        txtCreateAccount=view.findViewById(R.id.txtCreateAccount);

        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerviewCards.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager1);

        recyclerView.setHasFixedSize(true);
        showTransaction();
        showCard();
        showProfile();
        return view;
    }

    private void showTransaction() {
        int loginId;
        loginId = 0;
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(getActivity(),  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<RecentTransactionResponse> loginCall;
            loginCall = apiService.my_all_transactions(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(getActivity()).getUserId()));

            loginCall.enqueue(new Callback<RecentTransactionResponse>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<RecentTransactionResponse> call,
                                       Response<RecentTransactionResponse> response) {
                    Log.d("test", String.valueOf(response.code()));
                    pd.hide();
                    RecentTransactionResponse transaction = response.body();
                    if (response.isSuccessful()) {
                        if (transaction.getStatus()==200){
                            Log.d("response", String.valueOf(transaction.getStatus()));
                            if (transaction.getTransaction() != null) {
                                recenetTransactionList=response.body().getTransaction();
                                // cardsList = Collections.singletonList((response.body().getCards()));
                                Log.d("transaction",recenetTransactionList.toString());
                                recentTransactionAdapter= new RecentTransactionAdapter(recenetTransactionList, getActivity());
                                recyclerView.setAdapter(recentTransactionAdapter);
                            }else {
                                //  Toast.makeText(PaymentActivity.this,"No record found", Toast.LENGTH_LONG).show();
                                txtNodata.setVisibility(View.VISIBLE);
                                txtNodata.setText("No record found");
                            }
                        }

                    }


                }

                @Override
                public void onFailure(Call<RecentTransactionResponse> call,
                                      Throwable t) {
                    pd.hide();
                    Log.d("error",t.toString());

                }
            });
        } catch (JsonIOException exception) {
            System.out.println("Thrown exception: " + exception.getMessage());
        }
    }

    private void showCard() {
        int loginId;
        loginId = 0;
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(getActivity(),  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<CardResponse> loginCall;
            loginCall = apiService.my_cards(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(getActivity()).getUserId()));

            loginCall.enqueue(new Callback<CardResponse>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<CardResponse> call,
                                       Response<CardResponse> response) {
                    pd.hide();
                    CardResponse cards = response.body();
                    if (response.isSuccessful()) {
                        if (cards.getCards() != null) {
                            cardsList =response.body().getCards().getData();
                            cardAdapter = new CardDetailsAdapter(cardsList, getActivity());
                            recyclerviewCards.setAdapter(cardAdapter);
                        }else {

                            txtNodataCards.setVisibility(View.VISIBLE);
                            txtNodataCards.setText("No record found");
                        }
                    }

                }

                @Override
                public void onFailure(Call<CardResponse> call,
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
            final ProgressDialog pd = ViewUtils.getProgressBar(getActivity(),  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<UserProfile> loginCall;
            loginCall = apiService.showProfile(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(getActivity()).getUserId()));

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


                                strTxtName = response.body().getFirstName();
                                txtCreateAccount.setText( " Hi! " + strTxtName);




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