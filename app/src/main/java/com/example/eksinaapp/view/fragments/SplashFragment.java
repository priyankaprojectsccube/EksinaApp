package com.example.eksinaapp.view.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eksinaapp.R;
import com.example.eksinaapp.model.Constants;
import com.example.eksinaapp.presenter.SharedPrefManager;
import com.example.eksinaapp.view.activity.HomeActivity;


public class SplashFragment extends Fragment {

    Fragment fragment;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Constants.currentFragment = "splashScreen";
        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.splashColor)); //status bar or the time bar at the top
        }

        View rootView = inflater.inflate(R.layout.fragment_splash, container, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                if (SharedPrefManager.IsLogin(getActivity())) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                    //finishActivity();
                } else {
                    fragment = new LoginFragment();
                    replaceFragment(fragment);
                    //  finishActivity();
                }
            }
        }, 100);
        return rootView;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
        /*getActivity().getSupportFragmentManager().popBackStack();*/

    }


}