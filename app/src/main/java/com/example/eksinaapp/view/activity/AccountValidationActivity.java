package com.example.eksinaapp.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.AccountValidationStep01;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.media.MediaBrowserServiceCompat.RESULT_OK;

public class AccountValidationActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
Button btnNext;
RadioGroup radioGroup;
RadioButton radioPassport,radioidentity,radioPermit,radioreceipt;
TextView choosieId,txtUploadId;
String strDoc;

    private static final String TAG = AccountValidationActivity.class.getSimpleName();
    private Uri uri;
    ImageView imgExpandMore,imgUploadId,imgTakeSelfie;
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    boolean isShow=false,isHide=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_validation);

        btnNext=findViewById(R.id.btnNext);

        imgExpandMore=findViewById(R.id.imgExpandMore);

        radioGroup=findViewById(R.id.radioGroup);

        radioPassport=findViewById(R.id.radioPassport);

        radioidentity=findViewById(R.id.radioidentity);

        radioPermit=findViewById(R.id.radioPermit);

        radioreceipt=findViewById(R.id.radioreceipt);

        txtUploadId=findViewById(R.id.txtUploadId);

        imgUploadId=findViewById(R.id.imgUploadId);

        imgTakeSelfie=findViewById(R.id.imgTakeSelfie);

        imgUploadId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });

        imgTakeSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });

        imgExpandMore.setImageResource(R.drawable.ic_baseline_expand_more_24);

        radioGroup.setVisibility(View.GONE);

        radioPassport.setVisibility(View.GONE);

        radioidentity.setVisibility(View.GONE);

        radioPermit.setVisibility(View.GONE);

        radioreceipt.setVisibility(View.GONE);


        imgExpandMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgExpandMore.setImageResource(R.drawable.ic_baseline_expand_more_24);
                radioGroup.setVisibility(View.VISIBLE);
                radioPassport.setVisibility(View.VISIBLE);
                radioidentity.setVisibility(View.VISIBLE);
                radioPermit.setVisibility(View.VISIBLE);
                radioreceipt.setVisibility(View.VISIBLE);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int option = radioGroup.getCheckedRadioButtonId();
                switch (option)
                {
                    case R.id.radioPassport:
                        if (radioPassport.isChecked()){
                            strDoc =radioPassport.getText().toString();
                        }
                    case R.id.radioPermit:
                        if (radioPermit.isChecked()){
                            strDoc =radioPermit.getText().toString();
                        }
                    case R.id.radioreceipt:
                        if (radioreceipt.isChecked()){
                            strDoc =radioreceipt.getText().toString();
                        }
                    case R.id.radioidentity:
                        if (radioidentity.isChecked()){
                            strDoc =radioidentity.getText().toString();
                        }
                }
            }
        });

        imgExpandMore.setImageResource(R.drawable.ic_baseline_expand_less_24);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(strDoc);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AccountValidationActivity.this);
    }

    @SuppressLint({"MissingSuperCall", "RestrictedApi"})
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.getData();

        }
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (uri != null) {
            uploadImage(strDoc);

        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }
    private void uploadImage(String document) {
        final int loginId=0;
        if (EasyPermissions.hasPermissions(AccountValidationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            String filePath = getRealPathFromURIPath(uri, AccountValidationActivity.this);
            final File file = new File(filePath);
            Log.d(TAG, "Filename " + file.getName());
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("doc_file", file.getName(), mFile);

            RequestBody mFile1 = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("photo_file", file.getName(), mFile1);

            final ProgressDialog pd = ViewUtils.getProgressBar(AccountValidationActivity.this, getString(R.string.loading), getString(R.string.wait));
            ApiInterface apiService = ApiHandler.getApiService();
            final Call<AccountValidationStep01> loginCall;
            loginCall = apiService.account_validation_step01(Integer.parseInt(loginId + SharedPrefManager.getLoginObject(AccountValidationActivity.this).getUserId()),document ,fileToUpload,fileToUpload1);
            loginCall.enqueue(new Callback<AccountValidationStep01>() {
                @Override
                public void onResponse(Call<AccountValidationStep01> call, Response<AccountValidationStep01> response) {
                    pd.hide();
                    Toast.makeText(AccountValidationActivity.this, "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(AccountValidationActivity.this,ESignatureActivity.class);
                    startActivity(intent);
                }
                @Override
                public void onFailure(Call<AccountValidationStep01> call, Throwable t) {
                    Log.d("error", t.toString());
                    pd.hide();
                    Toast.makeText(AccountValidationActivity.this, "Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

}