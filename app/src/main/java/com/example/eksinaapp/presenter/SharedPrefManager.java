package com.example.eksinaapp.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Spinner;

import com.example.eksinaapp.model.Country;
import com.example.eksinaapp.model.CountryResponse;
import com.example.eksinaapp.model.Login;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private final String FCM_TOKEN = "fcmtoken";

    private Context mCtx;

    public SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    static String LOGIN_OBJECT = "login_object";
    static String USER_NAME = "user_name";
    static String USER_PPASSWORD = "user_password";
    private static String IS_LOGIN = "login";
    static String selectedCountry="country";
     Spinner yourSpinner;

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
    }

    public static boolean IsLogin(Context context) {
        return getSharedPreferences(context).getBoolean(IS_LOGIN , false);
    }

    public static void storeLoginObject(Login model, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString(LOGIN_OBJECT, json);
        editor.apply();
    }
    public static void setIsLogin(Context context, boolean newValue) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(IS_LOGIN , newValue);
        editor.commit();
    }

    public static Login getLoginObject(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        Gson gson = new Gson();
        String json = prefs.getString(LOGIN_OBJECT, "");
        return gson.fromJson(json, Login.class);
    }

    public static void storeCountry(CountryResponse model, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString(selectedCountry, json);
        editor.apply();
    }

    public static CountryResponse getCountry(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAME, 0);
        Gson gson = new Gson();
        String json = prefs.getString(selectedCountry, "");
        return gson.fromJson(json, CountryResponse.class);
    }
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }


    public static SharedPrefManager getmInstance() {
        return mInstance;
    }

    public static void setmInstance(SharedPrefManager mInstance) {
        SharedPrefManager.mInstance = mInstance;
    }

    public String getFCM_TOKEN() {
        return FCM_TOKEN;
    }

    public Context getmCtx() {
        return mCtx;
    }

    public void setmCtx(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static String getLoginObject() {
        return LOGIN_OBJECT;
    }

    public static void setLoginObject(String loginObject) {
        LOGIN_OBJECT = loginObject;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public static void setUserName(String userName) {
        USER_NAME = userName;
    }

    public static String getUserPpassword() {
        return USER_PPASSWORD;
    }

    public static void setUserPpassword(String userPpassword) {
        USER_PPASSWORD = userPpassword;
    }

    public static String getIsLogin() {
        return IS_LOGIN;
    }

    public static void setIsLogin(String isLogin) {
        IS_LOGIN = isLogin;
    }

    public static void LogOut(Context context)
    {
        getSharedPreferences(context).edit().clear().commit();
    }
}
