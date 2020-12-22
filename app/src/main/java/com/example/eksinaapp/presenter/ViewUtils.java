package com.example.eksinaapp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class ViewUtils {

    public static ProgressDialog getProgressBar(Context context, String title, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    public void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
