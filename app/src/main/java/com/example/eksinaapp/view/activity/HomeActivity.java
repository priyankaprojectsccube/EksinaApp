package com.example.eksinaapp.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.eksinaapp.R;
import com.example.eksinaapp.view.fragments.DashboardFragment;
import com.example.eksinaapp.view.fragments.MyAccountFragment;
import com.example.eksinaapp.view.fragments.MyBenfiriesFragment;
import com.example.eksinaapp.view.fragments.MyTransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView navigation;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigation = findViewById(R.id.navigation);
        if (getIntent().hasExtra("addactivity")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyBenfiriesFragment()).commit();

            navigation.setSelectedItemId(R.id.navigation_benificiaries);
        }else if(getIntent().hasExtra("YourTransferActivity")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyTransactionFragment()).commit();

            navigation.setSelectedItemId(R.id.navigation_transaction);
        }else if(getIntent().hasExtra("changepass")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyAccountFragment()).commit();

            navigation.setSelectedItemId(R.id.navigation_account);
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
//            navigation.setOnNavigationItemSelectedListener(this);
        }


        //getting bottom navigation view and attaching the listener
//          navigation = findViewById(R.id.navigation);
         navigation.setOnNavigationItemSelectedListener(this);


    }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).commit();
                  //  fragment = new DashboardFragment();
                    break;
                case R.id.navigation_transaction:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyTransactionFragment()).commit();
                  //  fragment = new MyTransactionFragment();
                    break;
                case R.id.navigation_benificiaries:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyBenfiriesFragment()).commit();
                   // fragment = new MyBenfiriesFragment();
                    break;
                case R.id.navigation_account:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyAccountFragment()).commit();
                    //fragment = new MyAccountFragment();
                    break;
            }

         //   return loadFragment(fragment);
            return true;
        }

        private boolean loadFragment(Fragment fragment) {
            //switching fragment
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            }
            return false;
        }
//    @Override
//    public void onBackPressed() {
//       /* View overlay = findViewById(R.id.navigation);
//
//        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_FULLSCREEN);*/
//       // navigation.setVisibility(View.GONE);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(false);
//        builder.setMessage(getString(R.string.exitDialog));
//        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//              finish();
//                //if user pressed "yes", then he is allowed to exit from application
//               /* Intent a = new Intent(Intent.ACTION_MAIN);
//                a.addCategory(Intent.CATEGORY_HOME);
//                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(a);*/
//            }
//        });
//        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //if user select "No", just cancel this dialog and continue with app
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
@Override
public void onBackPressed() {
    super.onBackPressed();
    finishAffinity();
}

    }
