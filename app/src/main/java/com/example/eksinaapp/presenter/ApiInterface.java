package com.example.eksinaapp.presenter;

import com.example.eksinaapp.model.AccountValidationStep01;
import com.example.eksinaapp.model.AccountValidationStep02;
import com.example.eksinaapp.model.AccountValidationStep03;
import com.example.eksinaapp.model.AddBenificiery;
import com.example.eksinaapp.model.BeneficiaryResponse;
import com.example.eksinaapp.model.CardResponse;
import com.example.eksinaapp.model.Cards;
import com.example.eksinaapp.model.ChangePassword;
import com.example.eksinaapp.model.ConvertCurrency;
import com.example.eksinaapp.model.CountryResponse;
import com.example.eksinaapp.model.DeleteBenefiries;
import com.example.eksinaapp.model.EditBenefiries;
import com.example.eksinaapp.model.ForgetPassword;
import com.example.eksinaapp.model.Login;
import com.example.eksinaapp.model.PaymentResponse;
import com.example.eksinaapp.model.RecentTransactionResponse;
import com.example.eksinaapp.model.Register;
import com.example.eksinaapp.model.SaveCard;
import com.example.eksinaapp.model.SaveCardPayment;
import com.example.eksinaapp.model.ShowBeneficiery;
import com.example.eksinaapp.model.Transaction;
import com.example.eksinaapp.model.TransactionResponse;
import com.example.eksinaapp.model.UpdateProfile;
import com.example.eksinaapp.model.UserProfile;
import com.example.eksinaapp.model.WalletResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("register")
    @FormUrlEncoded
    Call<Register> reigsterUser(@Field("first_name") String first_name,
                                @Field("last_name") String last_name,
                                @Field("email") String email,
                                @Field("mobile_no") String mobile_no,
                                @Field("password") String password,
                                @Field("dob") String dob
    );
    @POST("login")
    @FormUrlEncoded
    Call<Login> loginUser(@Field("username") String username,
                          @Field("password") String password

    );
    @POST("login_google")
    @FormUrlEncoded
    Call<Login> login_google(@Field("first_name") String first_name,
                             @Field("last_name") String last_name,
                             @Field("email_id") String email_id


    );

    @POST("login_facebook")
    @FormUrlEncoded
    Call<Login> login_facebook(@Field("first_name") String first_name,
                              @Field("last_name") String last_name,
                              @Field("email_id") String email_id


    );
    @GET("countries")
    Call<CountryResponse> getCountry();


    @GET("wallet")
    Call<WalletResponse> walletType();

    @POST("add_beneficieries")
    @FormUrlEncoded
    Call<AddBenificiery>
    addbenificiery(                     @Field("user_id") int user_id,
                                        @Field("country_id") int country_id,
                                        @Field("city_name") String city_name,
                                        @Field("first_name") String first_name,
                                        @Field("last_name") String last_name,
                                        @Field("nick_name") String nick_name,
                                        @Field("mobile") String mobile,
                                        @Field("bank_name") String bank_name,
                                        @Field("bank_acc_no") String bank_acc_no,
                                        @Field("ifsc_code") String ifsc_code,
                                        @Field("wallet_id") String wallet_id,
                                        @Field("purpose_for") String purpose_for,
                                        @Field("wallet_id_fk") int wallet_id_fk
                                        );



    @POST("beneficieries")
    @FormUrlEncoded
    Call<BeneficiaryResponse> showbenificiery(@Field("user_id") int user_id
                                           );
     @POST("beneficieries")
     @FormUrlEncoded
     Call<BeneficiaryResponse> showWalletBenefiries(@Field("user_id") int user_id,
                                                    @Field("pay_method") String pay_method
                                                    );
    @POST("beneficiery_detail")
    @FormUrlEncoded
    Call<ShowBeneficiery> benefiriesDetails(@Field("ben_id") int ben_id
    );

    @POST("delete_beneficiery")
    @FormUrlEncoded
    Call<DeleteBenefiries> deleteBenefiries(@Field("ben_id") int ben_id
    );

    @POST("my_profile")
    @FormUrlEncoded
    Call<UserProfile> showProfile(@Field("user_id") int user_id
    );

    @POST("change_password")
    @FormUrlEncoded
    Call<ChangePassword> changePassword(@Field("user_id") int user_id,
                                        @Field("old_password") String old_password,
                                        @Field("new_password") String new_password
    );

    @POST("edit_beneficieries")
    @FormUrlEncoded
    Call<EditBenefiries> editbenefiries(@Field("user_id") int user_id,
                                        @Field("ben_id") int ben_id,
                                        @Field("country_id") int country_id,
                                        @Field("city_name") String city_name,
                                        @Field("first_name") String first_name,
                                        @Field("last_name") String last_name,
                                        @Field("nick_name") String nick_name,
                                        @Field("mobile") String mobile,
                                        @Field("purpose_for") String purpose_for,
                                        @Field("wallet_id") String wallet_id,
                                        @Field("bank_name") String bankname,
                                        @Field("bank_acc_no") String bank_acc_no,
                                        @Field("ifsc_code") String ifsc_code,
                                        @Field("wallet_id_fk") int wallet_id_fk
                                        );

    @POST("account_validation_step01")
    @Multipart
    Call<AccountValidationStep01> account_validation_step01(@Part("user_id") int user_id,
                                                            @Part("doc_name") String doc_name,
                                                            @Part MultipartBody.Part doc_file,
                                                            @Part MultipartBody.Part photo_file
                                                            );
    @POST("account_validation_step02")
    @Multipart
    Call<AccountValidationStep02> account_validation_step02(@Part("user_id") int user_id,
                                                            @Part MultipartBody.Part sign_file);

    @POST("account_validation_step03")
    @FormUrlEncoded
    Call<AccountValidationStep03> account_validation_step03(@Field("user_id") int user_id,
                                                            @Field("otp") String otp
                                                            );

    @POST("convert_currency")
    @FormUrlEncoded
    Call<ConvertCurrency> convertCurrency(@Field("user_id") int user_id,
                                          @Field("con_curr") String con_curr,
                                          @Field("ero_amount") String ero_amount
                                          );

    //      @Field("user_image") String image,

    @POST("update_my_profile")
    @Multipart
    Call<UpdateProfile> updateProfile(@Part("user_id") int user_id,
                                      @Part("first_name") RequestBody first_name,
                                      @Part("last_name") RequestBody last_name,
                                      @Part("email_id") RequestBody email_id,
                                      @Part("mobile_no") RequestBody mobile_no,
                                      @Part MultipartBody.Part user_image,
                                      @Part("dob") RequestBody dob
                                      );

    @POST("my_transactions")
    @FormUrlEncoded
    Call<TransactionResponse> my_transactions(@Field("user_id") int user_id,
                                              @Field("start_date") String start_date,
                                              @Field("end_date") String end_date
                                      );

    @POST("request_forgot_password")
    @FormUrlEncoded
    Call<ForgetPassword> forgetPassword(@Field("forget_email") String forget_email);


    @POST("add_card")
    @FormUrlEncoded
    Call<SaveCard> saveCard(@Field("card_no") String card_no,
                            @Field("exp_month") String exp_month,
                            @Field("exp_year") String exp_year,
                            @Field("cvc") String cvc,
                            @Field("name") String name,
                            @Field("email") String email,
                            @Field("user_id") int user_id
                            );

    @POST("my_all_transactions")
    @FormUrlEncoded
    Call<RecentTransactionResponse> my_all_transactions(@Field("user_id") int user_id);

    @POST("payment")
    @FormUrlEncoded
    Call<PaymentResponse> payment(@Field("user_id") int user_id,
                                  @Field("name") String name,
                                  @Field("email") String email,
                                  @Field("card_no") String card_no,
                                  @Field("cvc") String cvc,
                                  @Field("exp_month") String exp_month,
                                  @Field("exp_year") String exp_year,
                                  @Field("total_amount") String total_amount,
                                  @Field("beneficiaryid") String beneficiaryid,
                                  @Field("payment_method") String payment_method,
                                  @Field("country_id") String countryId,
                                  @Field("benpay") String benpay
                                  );



    @POST("my_cards")
    @FormUrlEncoded
    Call<CardResponse> my_cards(@Field("user_id") int user_id);

    @POST("saved_card_payment")
    @FormUrlEncoded
    Call<SaveCardPayment> saved_card_payment(@Field("user_id") int user_id,
                                             @Field("card_id") String card_id,
                                             @Field("total_amount") String total_amount,
                                             @Field("beneficiaryid") String beneficiaryid,
                                             @Field("benpay") String benpay,
                                             @Field("payment_method") String payment_method,
                                             @Field("country_id") String country_id);

}
