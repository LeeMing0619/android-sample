package com.app.flex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.app.flex.Utils.PrefUtils;
import com.app.flex.model.User;
import com.google.gson.Gson;

public class SplashScreenActivity extends AppCompatActivity {
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spash_screen);


		PrefUtils.initPreferance(this);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

					Intent i = new Intent(SplashScreenActivity.this, SignInActivity.class);
					startActivity(i);
					finish();
			}
		}, 3000);
	}
}
