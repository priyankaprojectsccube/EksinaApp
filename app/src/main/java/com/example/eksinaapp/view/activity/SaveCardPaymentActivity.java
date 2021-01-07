package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.ConvertCurrency;
import com.example.eksinaapp.model.PaymentResponse;
import com.example.eksinaapp.model.SaveCardPayment;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.fragments.DashboardFragment;
import com.google.gson.JsonIOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveCardPaymentActivity extends AppCompatActivity {
    TextView amountYousend,amountDonation,amountFree,totalAmount,convertAmount,toatlaReceive;
    String strAmount,strEdtAmount,cardNo,cvc,month,year,benId,strCardId,strTransferMode,countryId,strcurrency;
    Button btnpay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_card_payment);

        amountYousend=findViewById(R.id.amountYousend);

        amountDonation=findViewById(R.id.amountDonation);

        amountFree=findViewById(R.id.amountFree);

        totalAmount=findViewById(R.id.totalAmount);

        convertAmount=findViewById(R.id.convertAmount);

        toatlaReceive=findViewById(R.id.toatlaReceive);

        btnpay=findViewById(R.id.btnpay);

      btnpay.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              payment(strCardId,strEdtAmount,benId,strAmount,strTransferMode,countryId);
          }
      });

        Intent intent=getIntent();

        strAmount=intent.getStringExtra("Totalamount");

        strEdtAmount=intent.getStringExtra("amount");

        cardNo=intent.getStringExtra("cardNo");

        cvc=intent.getStringExtra("cvc");

        month=intent.getStringExtra("month");

        year=intent.getStringExtra("year");

        benId=intent.getStringExtra("benId");

        strCardId=intent.getStringExtra("cardId");

        strTransferMode=intent.getStringExtra("transferMode");

        countryId=intent.getStringExtra("countryId");

        strcurrency = intent.getStringExtra("currency");

//        amountYousend.setText(strEdtAmount + " " + "EUR");
//
//        totalAmount.setText(strEdtAmount + " " +"EUR");
//
//        convertAmount.setText(strAmount);
//
//        toatlaReceive.setText(strAmount);

        if(strcurrency != null && !strcurrency.isEmpty()) {
            if (strEdtAmount != null && !strEdtAmount.isEmpty()) {
                convertCurrency(strcurrency, strEdtAmount);
            }
        }
    }

    private void convertCurrency(String strcurrency, String strEdtAmount) {



        int loginId;
        loginId=0;
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(SaveCardPaymentActivity.this,  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();

            final Call<ConvertCurrency> loginCall;

            loginCall = apiService.convertCurrency(Integer.parseInt(loginId+ SharedPrefManager.getLoginObject(SaveCardPaymentActivity.this).getUserId()),strcurrency,strEdtAmount);

            loginCall.enqueue(new Callback<ConvertCurrency>() {
                @SuppressWarnings("NullableProblems")
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<ConvertCurrency> call,
                                       Response<ConvertCurrency> response) {
                    pd.hide();

                    ConvertCurrency convertCurrency = response.body();
                    String res = String.valueOf(response.body());
                    Log.d("response",res);
                    if (response.body().getStatus() == 200) {
                        Log.d("onsuccess","onsuccess");
//                        if (convertCurrency.getEroReciveAmount() != null) {


                        amountYousend.setText(response.body().getEroReciveAmount()  + " " +  "EUR");
                        amountDonation.setText("0 EUR");
                        amountFree.setText(response.body().getEroFees().toString() + " " +  "EUR");
                        totalAmount.setText(response.body().getEroTotalAmount().toString() + " " +  "EUR");
                        toatlaReceive.setText(response.body().getBenReciveAmount());

//                            yousendbelow.setText(response.body().getBenAmount());
//                            jtext.setText(response.body().getBenFees());
//                            recevies.setText(response.body().getBenReciveAmount());
//                        }else {
//                            totaltopay.setText("Failed to convert");
//                        }
                    }
                }

                @Override
                public void onFailure(Call<ConvertCurrency> call,
                                      Throwable t) {
                    pd.hide();
                    Log.d("error",t.toString());

                }
            });
        } catch (JsonIOException exception) {
            System.out.println("Thrown exception: " + exception.getMessage());
        }
    }

    private void payment(String card_id,String total_amount,String beneficiaryid,String benpay,String payment_method,String country_id){
        int loginId;
        loginId=0;
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(SaveCardPaymentActivity.this,  getString(R.string.loading), getString(R.string.wait));
            ApiInterface apiService = ApiHandler.getApiService();
            final Call<SaveCardPayment> loginCall = apiService.saved_card_payment(Integer.parseInt(loginId+ SharedPrefManager.getLoginObject(SaveCardPaymentActivity.this).getUserId()),card_id,total_amount,beneficiaryid,benpay,payment_method,country_id);

            loginCall.enqueue(new Callback<SaveCardPayment>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<SaveCardPayment> call,
                                       Response<SaveCardPayment> response) {
                    pd.hide();

                    try {

                        if (response.isSuccessful()){
                            SaveCardPayment paymentResponse = response.body();
                            if (paymentResponse.getStatus().equals(200)) {
                                /*getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container,new DashboardFragment(),"fragment_tag")
                                        .addToBackStack(null)
                                        .commit();*/
                                Intent intent=new Intent(SaveCardPaymentActivity.this,HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(SaveCardPaymentActivity.this, paymentResponse.getMessage(), Toast.LENGTH_LONG).show();
                            } else if (paymentResponse.getStatus().equals(400)){
                                Toast.makeText(SaveCardPaymentActivity.this, paymentResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }catch (Exception e) {
                        System.out.println(e);
                    }
                }

                @Override
                public void onFailure(Call<SaveCardPayment> call,
                                      Throwable t) {
                    pd.hide();
                    Toast.makeText(SaveCardPaymentActivity.this,"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }

            });

        } catch (JsonIOException e) {
            System.out.println("Thrown exception: " + e.getMessage());
        }
    }
}