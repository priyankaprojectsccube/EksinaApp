package com.example.eksinaapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.eksinaapp.model.PaymentResponse;
import com.example.eksinaapp.model.SaveCard;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.fragments.DashboardFragment;
import com.google.gson.JsonIOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class PaymentDeatilsActvity extends AppCompatActivity {
TextView amountYousend,amountDonation,amountFree,totalAmount,convertAmount,toatlaReceive;
String strAmount,strEdtAmount,name,email,cardNo,cvc,month,year,benId,strTransferMode,countryId;
Button btnpay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_deatils_actvity);

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
                payment(name,email,cardNo,cvc,month,year,strEdtAmount,benId,strTransferMode,countryId,strAmount);
            }
        });

        Intent intent=getIntent();

        strAmount=intent.getStringExtra("Totalamount");
      //  Log.d("total",strAmount);

        strEdtAmount=intent.getStringExtra("amount");

        name=intent.getStringExtra("name");

        email=intent.getStringExtra("email");

        cardNo=intent.getStringExtra("cardNo");

        cvc=intent.getStringExtra("cvc");

        month=intent.getStringExtra("month");

        year=intent.getStringExtra("year");

        benId=intent.getStringExtra("benId");

        strTransferMode=intent.getStringExtra("transferMode");
       // Log.d("transferMode",strTransferMode);

        countryId=intent.getStringExtra("countryId");
       //  Log.d("countryId",countryId);

        amountYousend.setText(strEdtAmount + " " + "EUR");

        totalAmount.setText(strEdtAmount + " " +"EUR");

        convertAmount.setText(strAmount);

        toatlaReceive.setText(strAmount);
    }


 private void payment(String strName,String strEmail,String strCardNo,String strCvc,String strMonth,String strYear,String strTotal,String beneficiaryid,String payment_method,String strcountryId,String benPay){
     int loginId;
     loginId=0;

     try {
         final ProgressDialog pd = ViewUtils.getProgressBar(PaymentDeatilsActvity.this,  getString(R.string.loading), getString(R.string.wait));
         ApiInterface apiService = ApiHandler.getApiService();
         final Call<PaymentResponse> loginCall = apiService.payment(Integer.parseInt(loginId+ SharedPrefManager.getLoginObject(PaymentDeatilsActvity.this).getUserId()),strName,strEmail,strCardNo,strCvc,strMonth,strYear,strTotal,beneficiaryid,payment_method,strcountryId,benPay);

         loginCall.enqueue(new Callback<PaymentResponse>() {
             @SuppressLint("WrongConstant")
             @Override
             public void onResponse(Call<PaymentResponse> call,
                                    Response<PaymentResponse> response) {
                 pd.hide();

                 try {

                     if (response.isSuccessful()){
                         PaymentResponse paymentResponse = response.body();
                         if (paymentResponse.getStatus().equals(200)) {
                             getSupportFragmentManager().beginTransaction()
                                     .replace(R.id.container,new DashboardFragment(),"fragment_tag")
                                     .addToBackStack(null)
                                     .commit();
                             Toast.makeText(PaymentDeatilsActvity.this, paymentResponse.getMessage(), Toast.LENGTH_LONG).show();

                         } else if (paymentResponse.getStatus().equals(400)){
                             Toast.makeText(PaymentDeatilsActvity.this, paymentResponse.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
                 }catch (Exception e) {
                     System.out.println(e);
                 }
             }

             @Override
             public void onFailure(Call<PaymentResponse> call,
                                   Throwable t) {
                 pd.hide();
                 Toast.makeText(PaymentDeatilsActvity.this,"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
             }

         });

     } catch (JsonIOException e) {
         System.out.println("Thrown exception: " + e.getMessage());
     }
 }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}