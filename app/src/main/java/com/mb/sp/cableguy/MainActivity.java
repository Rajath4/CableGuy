package com.mb.sp.cableguy;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mb.sp.cableguy.Fragment.DashboardFragment;
import com.mb.sp.cableguy.Fragment.OthersFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    DashboardFragment dashboardFragment = new DashboardFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.flContent, dashboardFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:
                    OthersFragment othersFragment = new OthersFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.flContent, othersFragment);
                    fragmentTransaction1.commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
