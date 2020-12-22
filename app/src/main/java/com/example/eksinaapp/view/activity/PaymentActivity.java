package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.CardResponse;
import com.example.eksinaapp.model.Datum;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.OnItemClick;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.adapter.CardAdapter;
import com.google.gson.JsonIOException;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class PaymentActivity extends AppCompatActivity implements OnItemClick {
Button btnDone;
ImageView imgBack;
TextView edtAddCard;
String strAmount,strEdtAmount,benId,strTransferMode,countryId,strcardId;
RecyclerView rlCard;
CardAdapter cardAdapter;
List<Datum> cardsList;
TextView txtNodata;
boolean isCheckedbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btnDone=findViewById(R.id.btnDone);

        imgBack=findViewById(R.id.imgBack);

        edtAddCard=findViewById(R.id.edtAddCard);

        rlCard=findViewById(R.id.rlCard);

        txtNodata=findViewById(R.id.txtNodata);

        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(PaymentActivity.this, LinearLayoutManager.VERTICAL, false);
        rlCard.setHasFixedSize(true);
        rlCard.setLayoutManager(linearLayoutManager);

        Intent intent=getIntent();
        strAmount=intent.getStringExtra("Totalamount");
        strEdtAmount=intent.getStringExtra("amount");
        benId=intent.getStringExtra("benId");
        strTransferMode=intent.getStringExtra("transferMode");
        countryId=intent.getStringExtra("countryId");
        Log.d("benId",benId);

        showCard();


        edtAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PaymentActivity.this,AddCardActivity.class);
                intent.putExtra("Totalamount",strAmount);
                intent.putExtra("amount",strEdtAmount);
                intent.putExtra("benId",benId);
                intent.putExtra("transferMode",strTransferMode);
                intent.putExtra("countryId",countryId);
                startActivity(intent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allowOrNot="No";
                for (int i=0;i<cardsList.size();i++){
                    if (cardsList.get(i).isChecked()){
                        allowOrNot="yes";
                    }
                }
                if (allowOrNot.equalsIgnoreCase("yes"))
                {
                    Intent intent=new Intent(PaymentActivity.this,SaveCardPaymentActivity.class);
                    intent.putExtra("Totalamount",strAmount);
                    intent.putExtra("amount",strEdtAmount);
                    intent.putExtra("benId",benId);
                    intent.putExtra("transferMode",strTransferMode);
                    intent.putExtra("countryId",countryId);
                    intent.putExtra("cardId",strcardId);
                    startActivity(intent);
                }else {
                    Toast.makeText(PaymentActivity.this,"Select Card First.", Toast.LENGTH_LONG).show();
                }

            }
        });
                }

    private void showCard() {
        int loginId;
        loginId = 0;
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(PaymentActivity.this,  getString(R.string.loading), getString(R.string.wait));
               pd.show();
            ApiInterface apiService = ApiHandler.getApiService();
            final Call<CardResponse> loginCall;
            loginCall = apiService.my_cards(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(PaymentActivity.this).getUserId()));

            loginCall.enqueue(new Callback<CardResponse>() {
                @SuppressLint({"WrongConstant", "SetTextI18n"})
                @Override
                public void onResponse(Call<CardResponse> call,
                                       Response<CardResponse> response) {
                    pd.hide();
                    CardResponse cards = response.body();
                    if (response.isSuccessful()) {
                        if (cards.getCards() != null) {
                            cardsList=response.body().getCards().getData();
                           // cardsList = Collections.singletonList((response.body().getCards()));
                            getd();
                        }else {
                          //  Toast.makeText(PaymentActivity.this,"No record found", Toast.LENGTH_LONG).show();
                            txtNodata.setVisibility(View.VISIBLE);
                            txtNodata.setText("No record found");
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


    private void getd() {
        cardAdapter = new CardAdapter(cardsList, PaymentActivity.this,this);
        Log.d("errors", String.valueOf(cardsList.size()));
        rlCard.setAdapter(cardAdapter);
    }

    int selectedCount = 0; // Global variable
    @Override
    public void onClick(String cardId) {
       strcardId=cardId;
    }
}