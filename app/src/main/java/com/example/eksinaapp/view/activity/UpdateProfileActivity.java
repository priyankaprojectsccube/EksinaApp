package com.example.eksinaapp.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.AccountValidationStep02;
import com.example.eksinaapp.model.Register;
import com.example.eksinaapp.model.UpdateProfile;
import com.example.eksinaapp.model.UserProfile;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;
import com.example.eksinaapp.view.fragments.LoginFragment;
import com.google.gson.JsonIOException;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class UpdateProfileActivity extends AppCompatActivity /*implements EasyPermissions.PermissionCallbacks*/{
    ImageView imgedit;

    CircleImageView imgUpdatePic;

    private static final String TAG = ESignatureActivity.class.getSimpleName();

    private  static final int IMAGE = 100;

    private Uri uri;

    Bitmap bitmap;

    EditText txtFirstName,txtLastName,txtEmail,txtMobile;

    Button btnUpdate;

    String strFirstName,strLastName,strEmail,strMobile,strDob,filePath;

    TextView txtDob;
    ProgressDialog pd;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat dformate = new SimpleDateFormat("yyyy-MM-dd");
    Validation validation = new Validation();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        imgedit=findViewById(R.id.imgedit);

        imgUpdatePic=findViewById(R.id.imgUpdatePic);

     pd = ViewUtils.getProgressBar(UpdateProfileActivity.this,  getString(R.string.loading), getString(R.string.wait));
        txtFirstName=findViewById(R.id.txtFirstName);

        txtLastName=findViewById(R.id.txtLastName);

        txtEmail=findViewById(R.id.txtEmail);

        txtMobile=findViewById(R.id.txtMobile);

        btnUpdate=findViewById(R.id.btnUpdate);

        txtDob=findViewById(R.id.txtDob);

        txtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatepicker();
            }
        });

        imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(intent, IMAGE);

                if (ActivityCompat.checkSelfPermission(UpdateProfileActivity.this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (validation.neverAskAgainSelected(UpdateProfileActivity.this, WRITE_EXTERNAL_STORAGE, "STORAGE")) {
                            validation.displayNeverAskAgainDialog("storage", UpdateProfileActivity.this);
                        } else {
                            ActivityCompat.requestPermissions(UpdateProfileActivity.this,
                                    new String[]{WRITE_EXTERNAL_STORAGE},
                                    1);
                        }
                    }

                } else {


                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, IMAGE);
                }
            }


        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strFirstName=txtFirstName.getText().toString();
                strLastName=txtLastName.getText().toString();
                strEmail=txtEmail.getText().toString();
                strMobile=txtMobile.getText().toString();
                strDob=txtDob.getText().toString();
                Log.d("dobt",strFirstName+" "+strLastName+" "+strEmail+" "+strMobile+" "+strDob);
//                try {
                    if (validateFirstName() && validateLastname() && validateEmail() && validateMobileno()){
                       uploadImage(strFirstName,strLastName,strEmail,strMobile,strDob);
                    }
//                }catch (NullPointerException e){
//                    System.out.println(e);
//                    Log.d("exceptionn", String.valueOf(e));
//                    Intent intent=new Intent(UpdateProfileActivity.this,UserProfileActivity.class);
//                    startActivity(intent);
//                    finish();
//                }


            }
        });
              showProfile();
    }

   private String convertToString()
   {
       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
       byte[] imgByte = byteArrayOutputStream.toByteArray();
       return Base64.encodeToString(imgByte,Base64.DEFAULT);
   }

    @SuppressLint({"MissingSuperCall", "RestrictedApi"})
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== IMAGE && resultCode==RESULT_OK && data!=null)
        {
//            uri = data.getData();
//             filePath = getPathFromURI(uri);
//             Log.d("filepath",filePath);
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
//                imgUpdatePic.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
              filePath = cursor.getString(columnIndex);
                cursor.close();

            imgUpdatePic.setImageBitmap(BitmapFactory.decodeFile(filePath));

        }

    }

    private void uploadImage(String firstName,String lastName,String email,String mobileNumber,String dob){
          // int loginId=0;
      //  String image = convertToString();
       // String imageName = imgTitle.getText().toString();
              ; //getRealPathFromURIPath(uri, UpdateProfileActivity.this);
        MultipartBody.Part fileToUpload = null;
        File file;
        //Log.d("getfilepath",filePath);
        if(filePath != null ){
            try {
                file = new File(filePath);
                Log.d("Filename", file.getName());
                if (file.exists()) {
                    RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                    fileToUpload = MultipartBody.Part.createFormData("user_image", file.getName(), mFile);
                }
            }
         catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        }else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            fileToUpload = MultipartBody.Part.createFormData("user_image", "", attachmentEmpty);
        }



        RequestBody firstnamerb = RequestBody.create(MediaType.parse("text/plain"), firstName);
        RequestBody lastnamerb = RequestBody.create(MediaType.parse("text/plain"), lastName);
        RequestBody emailrb = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody numberrb = RequestBody.create(MediaType.parse("text/plain"), mobileNumber);
        RequestBody dobrb = RequestBody.create(MediaType.parse("text/plain"), dob);

        int loginId=0;
      //  final ProgressDialog pd = ViewUtils.getProgressBar(UpdateProfileActivity.this,  getString(R.string.loading), getString(R.string.wait));
          pd.show();
        ApiInterface apiService = ApiHandler.getApiService();
        final Call<UpdateProfile> loginCall;
        loginCall = apiService.updateProfile(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(UpdateProfileActivity.this).getUserId()),firstnamerb,lastnamerb,emailrb,numberrb,fileToUpload,dobrb);
        loginCall.enqueue(new Callback<UpdateProfile>(){
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<UpdateProfile> call,
                                   Response<UpdateProfile> response) {
                pd.dismiss();
                try {
                    if (response.isSuccessful()){
                        UpdateProfile user = response.body();
                        if (user.getStatus() == 200) {
                            Log.d("insuccess","200");
                            Toast.makeText(UpdateProfileActivity.this, user.getMessage(), Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(UpdateProfileActivity.this,UserProfileActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (user.getStatus() ==400){
                            Log.d("insuccess","400");
                            Toast.makeText(UpdateProfileActivity.this, user.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }catch (Exception e) {
                    Log.d("exception","error");
                    Toast.makeText(UpdateProfileActivity.this,"error"+e,Toast.LENGTH_LONG);

                }
            }
            @Override
            public void onFailure(Call<UpdateProfile> call, Throwable t) {
                Log.d("Server Response",""+t.toString());
                  pd.dismiss();
            }
        });

    }

    private String getPathFromURI(Uri uri) {

        String fileName="unknown";//default fileName
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content")==0)
        {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst())
            {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                filePathUri = Uri.parse(cursor.getString(column_index));
                fileName = filePathUri.getLastPathSegment().toString();
            }
        }
        else if (uri.getScheme().compareTo("file")==0)
        {
            fileName = filePathUri.getLastPathSegment().toString();
        }
        else
        {
            fileName = fileName+"_"+filePathUri.getLastPathSegment();
        }
        return fileName;

    }

    private String getRealPathFromURIPath(Uri contentURI, @NotNull Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void showProfile() {
        try {
            int loginId=0;
            // ProgressDialog pd = ViewUtils.getProgressBar(UpdateProfileActivity.this,  getString(R.string.loading), getString(R.string.wait));
              pd.show();
            ApiInterface apiService = ApiHandler.getApiService();
            final Call<UserProfile> loginCall;
            loginCall = apiService.showProfile(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(UpdateProfileActivity.this).getUserId()));

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


                                strFirstName = response.body().getFirstName();
                                txtFirstName.setText(strFirstName);

                                strLastName = response.body().getLastName();
                                txtLastName.setText(strLastName);

                                strMobile = response.body().getMobileNo();
                                txtMobile.setText(strMobile);

                                strEmail = response.body().getEmailId();
                                txtEmail.setText(strEmail);

                                strDob=response.body().getDob();
                                txtDob.setText(strDob);

                                if(response.body().getUserImage() != null && !response.body().getUserImage().isEmpty())
                                {
                                    Picasso.get()
                                            .load(userProfile.getUserImage())
                                            .placeholder(R.drawable.images)
                                            .error(R.drawable.images)
                                            .into(imgUpdatePic);
                                }else {
                                    // Toast.makeText(UserProfileActivity.this, "Empty Image URL", Toast.LENGTH_LONG).show();
                                    Picasso.get().load(R.drawable.images).into(imgUpdatePic);
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
                    pd.hide();

                }
            });
        } catch (JsonIOException exception) {
            System.out.println("Thrown exception: " + exception.getMessage());
        }
    }



    private void openDatepicker() {

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
// Launch Date Picker Dialog

        android.app.DatePickerDialog dpd = new android.app.DatePickerDialog(

                this,

                //  R.style.DialogThemeRed,

                new android.app.DatePickerDialog.OnDateSetListener() {

                    @Override

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        //Set current selected date to Calendar

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        //Set date to textview

                        txtDob.setText(dformate.format(c.getTime()));

                    }
                }, mYear, mMonth, mDay);



        //Set Max Date for disable future dates
        dpd.getDatePicker().setMaxDate(new Date().getTime());
        dpd.show();


    }

    private boolean validateFirstName() {
        String firstName = txtFirstName.getText().toString().trim();
        if (firstName.isEmpty()) {
            Toast.makeText(UpdateProfileActivity.this, "Please Enter first name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateLastname() {
        String lastName = txtLastName.getText().toString().trim();
        if (lastName.isEmpty()) {
            Toast.makeText(UpdateProfileActivity.this, "Please Enter last name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String email = txtEmail.getText().toString().trim();
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

        }else {
            Toast.makeText(UpdateProfileActivity.this, getString(R.string.invalidamail)
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateMobileno() {
        String mobileNo = txtMobile.getText().toString().trim();
        if (mobileNo.isEmpty()) {
            Toast.makeText(UpdateProfileActivity.this, getString(R.string.enterMobileNumber), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}