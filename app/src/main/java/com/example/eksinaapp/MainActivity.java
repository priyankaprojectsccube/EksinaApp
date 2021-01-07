package com.example.eksinaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.eksinaapp.view.fragments.LoginFragment;
import com.example.eksinaapp.view.fragments.MyBenfiriesFragment;
import com.example.eksinaapp.view.fragments.SplashFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showSplash();

        if (getIntent().hasExtra("login")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containermain, new LoginFragment())
                    .commit();
        }
        }
    private void showSplash() {
        Intent intent = new Intent(MainActivity.this,SplashFragment.class);
        startActivity(intent);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.containermain, new SplashFragment())
//                .addToBackStack(null)
//                .commit();
    }

  //  @SuppressWarnings("SingleStatementInBlock")
//    @Override
//    public void onBackPressed() {
//        int fragments = getSupportFragmentManager().getBackStackEntryCount();
//        if (fragments == 1) {
//            finish();
//        } else {
//            if (getFragmentManager().getBackStackEntryCount() > 1) {
//                getFragmentManager().popBackStack();
//            } else {
//                super.onBackPressed();
//            }
//        }
//    }
  @Override
  public void onBackPressed() {
      super.onBackPressed();

      finishAffinity();

  }

}