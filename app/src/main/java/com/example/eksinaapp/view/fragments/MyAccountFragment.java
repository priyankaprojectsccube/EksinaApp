package com.example.eksinaapp.view.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.eksinaapp.MainActivity;
import com.example.eksinaapp.R;
import com.example.eksinaapp.model.LocaleHelper;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.view.activity.ChangePasswordActivity;
import com.example.eksinaapp.view.activity.UserProfileActivity;

public class MyAccountFragment extends Fragment {
   TextView txtMyProfile,txtPayment,txtChnagePass,txtLogout,mTextView;
   //Spinner mLanguage;
    ArrayAdapter<String> mAdapter;
    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.fragment_my_account, container, false);
        txtMyProfile=rootView.findViewById(R.id.MyProfile);
        txtPayment=rootView.findViewById(R.id.PaymentMethod);
        txtChnagePass=rootView.findViewById(R.id.ChnagePassword);
        txtLogout=rootView.findViewById(R.id.Logout);



        mTextView = (TextView) rootView.findViewById(R.id.textView);

        mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_option));



        txtMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), UserProfileActivity.class);
                startActivity(intent);
            }
        });

        txtChnagePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showLogoutDialog(getActivity());
            }
        });

        return rootView;
    }

    public void showLogoutDialog(final Context mContext) {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SharedPrefManager(mContext).logout();
                        SharedPrefManager.LogOut(getActivity());
//                         Fragment fragment=new LoginFragment();
//                        replaceFragment(fragment);
//                      getActivity().finish();
                        Intent intent=new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("login","one");
                        startActivity(intent);
//                        LoginFragment fragment2 = new LoginFragment();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragment_container, fragment2);
//                        fragmentTransaction.commit();


                      /*  Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);*/
                       /* Fragment fragment=new LoginFragment();
                        replaceFragment(fragment);*/
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}