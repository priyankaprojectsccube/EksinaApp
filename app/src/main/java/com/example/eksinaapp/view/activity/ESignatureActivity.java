package com.example.eksinaapp.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.AccountValidationStep02;
import com.example.eksinaapp.presenter.ApiHandler;
import com.example.eksinaapp.presenter.ApiInterface;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.presenter.ViewUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.media.MediaBrowserServiceCompat.RESULT_OK;

public class ESignatureActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    Button btnSubmit;

    private static final String TAG = ESignatureActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private Uri uri;

    Bitmap bitmap;

    Fragment fragment;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_signature);

        btnSubmit=findViewById(R.id.btnNext);

        imageView=findViewById(R.id.selectImage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            uploadImage();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, ESignatureActivity.this);
    }

    @SuppressLint({"MissingSuperCall", "RestrictedApi"})
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.getData();
        }
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
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
            uploadImage();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }
    private void uploadImage() {
        int loginId=0;
        if (EasyPermissions.hasPermissions(ESignatureActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            String filePath = getRealPathFromURIPath(uri, ESignatureActivity.this);
            final File file = new File(filePath);
            Log.d(TAG, "Filename " + file.getName());
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("sign_file", file.getName(), mFile);

            final ProgressDialog pd = ViewUtils.getProgressBar(ESignatureActivity.this, getString(R.string.loading), getString(R.string.wait));
            ApiInterface apiService = ApiHandler.getApiService();
            final Call<AccountValidationStep02> loginCall;
            loginCall = apiService.account_validation_step02(Integer.parseInt(loginId+ SharedPrefManager.getLoginObject(ESignatureActivity.this).getUserId()),fileToUpload);
            loginCall.enqueue(new Callback<AccountValidationStep02>() {
                @Override
                public void onResponse(Call<AccountValidationStep02> call, Response<AccountValidationStep02> response) {
                    pd.hide();
                    Toast.makeText(ESignatureActivity.this, "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                    Toast.makeText(ESignatureActivity.this, "Success " + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Toast.makeText(ESignatureActivity.this,"OTP" +  response.body().getOtp(),Toast.LENGTH_LONG).show();
                      Intent intent=new Intent(ESignatureActivity.this,GenerateOTPActivity.class);
                      startActivity(intent);
                }

                @Override
                public void onFailure(Call<AccountValidationStep02> call, Throwable t) {
                    Log.d("error", t.toString());
                    pd.hide();
                    Toast.makeText(ESignatureActivity.this, "Something went wrong or no internet connection...Please try again!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

}