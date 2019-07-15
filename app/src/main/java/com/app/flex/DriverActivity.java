package com.app.flex;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.flex.fragment.DriverHomeFragment;
import com.app.flex.fragment.DriverProfileFragment;
import com.app.flex.fragment.HelpCenterFragment;
import com.app.flex.fragment.PaymentFragment;

public class DriverActivity extends AppCompatActivity {
    DrawerLayout drawer_driver;
    ImageView mMenuToggleIv, mMenuCloseIv;
    TextView mSearchRidesTv, mRideHistoryTv, mProfileTv, mSettingsTv, mHelpCenterTv, mSignOutTv;
    LinearLayout mPaymentLi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        drawer_driver = findViewById(R.id.drawer_layout_driver);
        mSearchRidesTv = findViewById(R.id.search_rides_tv);
        mRideHistoryTv = findViewById(R.id.ride_history_tv);
        mProfileTv = findViewById(R.id.profile_tv);
        mPaymentLi = findViewById(R.id.payments_li);
        mSettingsTv = findViewById(R.id.settings_tv);
        mHelpCenterTv = findViewById(R.id.help_center_tv);
        mSignOutTv = findViewById(R.id.sign_out_tv);

        mMenuToggleIv = findViewById(R.id.menu_toggle_iv);
        mMenuToggleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_driver.openDrawer(GravityCompat.START);
            }
        });

        mMenuCloseIv= findViewById(R.id.close_menu_driver);
        mMenuCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_driver.closeDrawer(GravityCompat.START);
            }
        });

        DriverHomeFragment someFragment = new DriverHomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        mSearchRidesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_driver.closeDrawer(GravityCompat.START);
                DriverHomeFragment someFragment = new DriverHomeFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mProfileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_driver.closeDrawer(GravityCompat.START);
                DriverProfileFragment someFragment = new DriverProfileFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mPaymentLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_driver.closeDrawer(GravityCompat.START);
                PaymentFragment someFragment = new PaymentFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mHelpCenterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_driver.closeDrawer(GravityCompat.START);
                HelpCenterFragment someFragment = new HelpCenterFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mSignOutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer_driver.isDrawerOpen(GravityCompat.START)) {
            drawer_driver.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
