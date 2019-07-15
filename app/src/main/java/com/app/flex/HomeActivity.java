package com.app.flex;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.flex.Utils.PrefUtils;
import com.app.flex.fragment.FinancialPlanningFragment;
import com.app.flex.fragment.HelpCenterFragment;
import com.app.flex.fragment.InsuranceFragment;
import com.app.flex.fragment.profile.CongratulationActivity;
import com.app.flex.fragment.DriverHomeFragment;
import com.app.flex.fragment.DriverProfileFragment;
import com.app.flex.fragment.HomeFragment;
import com.app.flex.fragment.PaymentFragment;
import com.app.flex.fragment.ProfileFragment;
import com.app.flex.fragment.TermConditionFragment;
import com.app.flex.fragment.profile.SSNNumberActivity;
import com.app.flex.model.User;
import com.google.android.libraries.places.api.Places;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class HomeActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	ImageView mMenuToggleIv;
	ImageView mCloseMenuIv;
	DrawerLayout drawer;

	TextView mTermConditionTv, mPrivacyPolicy;
	LinearLayout mPaymnetLi;
	LinearLayout mHomeLi;
	LinearLayout mProfileLi;
	LinearLayout mRateLi;
	LinearLayout mSendFeedbackLi;
	LinearLayout mAboutLi;
	LinearLayout mLogoutLi;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Places.initialize(getApplicationContext(), getResources().getString(R.string.google_key));
		PrefUtils.initPreferance(this);
		user = PrefUtils.getUserInfo();

		mMenuToggleIv = findViewById(R.id.menu_toggle_iv);
		mMenuToggleIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.openDrawer(GravityCompat.START);
			}
		});

		mCloseMenuIv = findViewById(R.id.close_menu_iv);
		mCloseMenuIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
			}
		});

		drawer = findViewById(R.id.drawer_layout);
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		/*if(user.getmRole().equals("1")){
			DriverHomeFragment someFragment = new DriverHomeFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.container, someFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}else {*/
			HomeFragment someFragment = new HomeFragment();
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.container, someFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		//}

		mTermConditionTv = findViewById(R.id.term_condition_tv);
		mTermConditionTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
				TermConditionFragment someFragment = new TermConditionFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, someFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
		TedPermission.with(this)
				.setPermissionListener(permissionlistener)
				.setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
				.setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
				.check();


		mPaymnetLi = findViewById(R.id.payment_li);
		mPaymnetLi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
				PaymentFragment someFragment = new PaymentFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, someFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});


		mHomeLi = findViewById(R.id.home_li);
		mHomeLi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
			/*	if(user.getmRole().equals("1")){
					DriverHomeFragment someFragment = new DriverHomeFragment();
					FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
					transaction.replace(R.id.container, someFragment);
					transaction.addToBackStack(null);
					transaction.commit();
				}else {*/
					HomeFragment someFragment = new HomeFragment();
					FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
					transaction.replace(R.id.container, someFragment);
					transaction.addToBackStack(null);
					transaction.commit();
				//}
			}
		});

		mProfileLi = findViewById(R.id.profile_li);
		mProfileLi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
				ProfileFragment someFragment = new ProfileFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, someFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});

		mRateLi = findViewById(R.id.rate_li);
		mRateLi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);

			}
		});

		mSendFeedbackLi = findViewById(R.id.send_feedback_li);
		mSendFeedbackLi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
				HelpCenterFragment someFragment = new HelpCenterFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, someFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});

		mAboutLi = findViewById(R.id.about_li);
		mAboutLi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
			}
		});

		mLogoutLi = findViewById(R.id.logout_li);
		mLogoutLi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
				Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
				startActivity(intent);
				finish();
				/*FinancialPlanningFragment someFragment = new FinancialPlanningFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, someFragment);
				transaction.addToBackStack(null);
				transaction.commit();*/
			}
		});

		mPrivacyPolicy = findViewById(R.id.privacy_policy_tv);
		mPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
				InsuranceFragment someFragment = new InsuranceFragment();
				FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.container, someFragment);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});

	}

	PermissionListener permissionlistener = new PermissionListener() {
		@Override
		public void onPermissionGranted() {

		}

		@Override
		public void onPermissionDenied(List<String> deniedPermissions) {

		}
	};

	@Override
	public void onBackPressed() {
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_home) {
			// Handle the camera action
		} else if (id == R.id.nav_gallery) {

		} else if (id == R.id.nav_slideshow) {

		} else if (id == R.id.nav_tools) {

		} else if (id == R.id.nav_share) {

		} else if (id == R.id.nav_send) {

		}

		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}
}
