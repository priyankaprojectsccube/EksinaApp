package com.example.eksinaapp.view.fragments;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Login;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.activity.HomeActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonIOException;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

TextView txtForgetPassword,txtSignUp;
Fragment fragment;
Button btnSignIn;
ImageView imgBack;

EditText edtEnterEmail,edtEnterPass;
String strEmail,strPassword;
    private static final int RC_SIGN_IN = 1;
    CallbackManager callbackManager;
    String fbname,fbemail,gmailname,gmailemail;
    SignInButton logingoogle;
    GoogleSignInClient mGoogleSignInClient;
     LoginButton loginfacebook;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_login, container, false);
        callbackManager = CallbackManager.Factory.create();
        txtForgetPassword=rootView.findViewById(R.id.txtForgetPassword);
        imgBack=rootView.findViewById(R.id.imgBack);

        logingoogle=(SignInButton) rootView.findViewById(R.id.logingoogle);
        loginfacebook=(LoginButton) rootView.findViewById(R.id.loginfacebook);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        logingoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        edtEnterEmail=rootView.findViewById(R.id.edtEnterEmail);

        edtEnterPass=rootView.findViewById(R.id.edtEnterPass);


        loginfacebook.setLoginBehavior(LoginBehavior.WEB_ONLY);
        loginfacebook.setReadPermissions(Arrays.asList("first_name","last_name","email_id"));

        // If you are using in a fragment, call loginButton.setFragment(this);
        loginfacebook.setFragment(this);
        // Callback registration
        loginfacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                // Application code
                                try {
                                    fbemail = object.getString("email_id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fbname = object.getString("first_name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    fbname = object.getString("last_name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                social_login_facebook(fbname,fbname,fbemail);

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();



            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        btnSignIn=rootView.findViewById(R.id.btnSignIn);

        txtSignUp=rootView.findViewById(R.id.txtSignUp);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new SignUpFragment();
                replaceFragment(fragment);
               // finishActivity();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    strEmail=edtEnterEmail.getText().toString().trim();

                    strPassword=edtEnterPass.getText().toString().trim();

                    if (validateEmail() && validatePassword()){
                        logIn(strEmail,strPassword);
                    }
                }catch (Exception e){
                    System.out.println("Thrown exception: " + e.getMessage());
                    Log.d("Exception:" ,e.getMessage());
                }

            }
        });
        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment=new ForgetPasswordFragment();
                replaceFragment(fragment);
//                finishActivity();
            }
        });


        return rootView;
    }

    private void social_login_facebook(String firstName,String lastName,String emailId) {
        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(getActivity(),  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<Login> loginCall = apiService.login_facebook(firstName,lastName,emailId);

            loginCall.enqueue(new Callback<Login>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<Login> call,
                                       Response<Login> response) {

                    pd.hide();

                    try {
                        if (response.isSuccessful()){
                            Login userLogin = response.body();
                            if (userLogin.getStatus().equals(200)) {
                                SharedPrefManager.storeLoginObject(userLogin, getActivity());
                                userLogin.setUserId(userLogin.getUserId());
                                SharedPrefManager.setIsLogin(getActivity(),true);
                                Log.d("id",userLogin.getUserId());

                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                clearUser();
                                intent.putExtra("firstName",userLogin.getFirstName());
                                startActivity(intent);
//                                finishActivity();
                            } else {
                                Toast.makeText(getActivity(),userLogin.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception e) {
                        Log.d("exception",e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Login> call,
                                      Throwable t) {
                    pd.hide();
                    Toast.makeText(getActivity(),"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (JsonIOException e){
            System.out.println("Thrown exception: " + e.getMessage());
        }

    }


    private void logIn(final String userName, final String passWord) {

        if (userName.isEmpty()) {
            edtEnterEmail.setError(getString(R.string.enterEmail));
            edtEnterEmail.requestFocus();
            return;
        }

        if (passWord.isEmpty()) {
            edtEnterPass.setError(getString(R.string.enterPass));
            edtEnterPass.requestFocus();
            return;
        }

        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(getActivity(),  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<Login> loginCall = apiService.loginUser(userName,passWord);

            loginCall.enqueue(new Callback<Login>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<Login> call,
                                       Response<Login> response) {

                    pd.hide();

                    try {
                        if (response.isSuccessful()){
                            Login userLogin = response.body();
                            if (userLogin.getStatus().equals(200)) {
                                SharedPrefManager.storeLoginObject(userLogin, getActivity());
                                userLogin.setUserId(userLogin.getUserId());
                                SharedPrefManager.setIsLogin(getActivity(),true);
                                Log.d("id",userLogin.getUserId());
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                clearUser();
                                intent.putExtra("firstName",userLogin.getFirstName());
                                startActivity(intent);
                              //  finishActivity();
                            } else {
                                Toast.makeText(getActivity(),userLogin.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception e) {
                        Log.d("exception",e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Login> call,
                                      Throwable t) {
                    pd.hide();
                    Toast.makeText(getActivity(),"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (JsonIOException e){
            System.out.println("Thrown exception: " + e.getMessage());
        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.containerlogin, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onBackPressed()
    {
        SignUpFragment splashFragment=new SignUpFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerlogin, splashFragment);
        fragmentTransaction.commit();
    }
    private void clearUser(){
        edtEnterEmail.setText("");
        edtEnterPass.setText("");
    }

    private boolean validateEmail() {
        String email = edtEnterEmail.getText().toString().trim();
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

        }else {
            Toast.makeText(getActivity(), getString(R.string.invalidamail)
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String password = edtEnterPass.getText().toString().trim();
        if (password.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.enterPass)
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }


    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            gmailname = account.getGivenName();
            
            gmailemail= account.getEmail();



            social_login_gmail(gmailname,gmailname,gmailemail);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //    Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(getActivity(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }

    private void social_login_gmail(String firstName,String lastName,String emailId) {

        try {
            final ProgressDialog pd = ViewUtils.getProgressBar(getActivity(),  getString(R.string.loading), getString(R.string.wait));

            ApiInterface apiService = ApiHandler.getApiService();
            final Call<Login> loginCall = apiService.login_google(firstName,lastName,emailId);

            loginCall.enqueue(new Callback<Login>() {
                @SuppressLint("WrongConstant")
                @Override
                public void onResponse(Call<Login> call,
                                       Response<Login> response) {

                    pd.hide();

                    try {
                        if (response.isSuccessful()){
                            Login userLogin = response.body();
                            if (userLogin.getStatus().equals(200)) {
                                SharedPrefManager.storeLoginObject(userLogin, getActivity());
                                userLogin.setUserId(userLogin.getUserId());
                                SharedPrefManager.setIsLogin(getActivity(),true);
                                Log.d("id",userLogin.getUserId());

                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                clearUser();
                                intent.putExtra("firstName",userLogin.getFirstName());
                                startActivity(intent);
//                                finishActivity();
                            } else {
                                Toast.makeText(getActivity(),userLogin.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception e) {
                        Log.d("exception",e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Login> call,
                                      Throwable t) {
                    pd.hide();
                    Toast.makeText(getActivity(),"Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (JsonIOException e){
            System.out.println("Thrown exception: " + e.getMessage());
        }

    }
  /*  private void finishActivity() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }*/
}