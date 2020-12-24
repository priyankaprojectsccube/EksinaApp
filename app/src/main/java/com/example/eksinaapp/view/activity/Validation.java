package com.example.eksinaapp.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.example.eksinaapp.R;
import com.google.android.material.textfield.TextInputLayout;

public class Validation {










    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean neverAskAgainSelected(Activity activity, String permission, String name) {
        final boolean prevShouldShowStatus = getRatinaleDisplayStatus(activity,permission,name);
        final boolean currShouldShowStatus = activity.shouldShowRequestPermissionRationale(permission);
        return prevShouldShowStatus != currShouldShowStatus;
    }


    public static boolean getRatinaleDisplayStatus(Context context,String permission,String name) {
        SharedPreferences genPrefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return genPrefs.getBoolean(permission, false);
    }


    public void displayNeverAskAgainDialog(String permission, final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(permission.equals("camera")) {
            builder.setMessage("Eksina needs permission to Camera for clicking picture(s).To enable tap on Settings and provide camera permission");
        }
        if(permission.equals("storage")) {
            builder.setMessage("Eksina needs permission to Storage for performing this action.To enable tap on Settings and provide storage permission");
        }

        builder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });

        builder.show();
    }





}

