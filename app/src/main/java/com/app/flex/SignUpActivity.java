package com.app.flex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.flex.Utils.AppUtils;
import com.app.flex.Utils.PrefUtils;
import com.app.flex.model.Result;
import com.app.flex.retrofit.ApiManager;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity
{

	RelativeLayout mTopRl;
	EditText mPasswordEt;
	EditText mConfirmPasswordEt;
	EditText mEmailEt;
	TextView mLoginTv;

	RadioButton mDriverChk;
	RadioButton mRiderChk;
	RadioButton mP2PChk;

	Button mSignUpBtn;
	String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	String role= "";
	ProgressDialog pd;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_signup);

		PrefUtils.initPreferance(this);
		pd = new ProgressDialog(SignUpActivity.this);
		mTopRl=findViewById(R.id.top_rl);

		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				(int)(AppUtils.getDeviceHeight(this)/2.5f));
		mTopRl.setLayoutParams(params);

		mEmailEt = findViewById(R.id.email_et);
		mPasswordEt=findViewById(R.id.password_et);
		mConfirmPasswordEt=findViewById(R.id.confirm_password_et);

		mDriverChk = findViewById(R.id.driver_chk);
		mRiderChk = findViewById(R.id.rider_chk);
		mP2PChk= findViewById(R.id.p2p_chk);

		mConfirmPasswordEt.setTransformationMethod(new PasswordTransformationMethod());
		mPasswordEt.setTransformationMethod(new PasswordTransformationMethod());


		mLoginTv=findViewById(R.id.login_tv);
		mLoginTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(SignUpActivity.this,SignInActivity.class);
				startActivity(i);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				finish();
			}
		});


		mSignUpBtn=findViewById(R.id.signup_btn);
		mSignUpBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(mDriverChk.isChecked()){
					role = "1";
				}else if(mRiderChk.isChecked()){
					role = "2";
				}else if(mP2PChk.isChecked()){
					role = "3";
				}
				/*Intent i=new Intent(SignUpActivity.this,CreateProfileActivity.class);
				startActivity(i);*/

				if(mEmailEt.getText().toString().isEmpty()){
					Toast.makeText(SignUpActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
				}else if(!mEmailEt.getText().toString().matches(emailPattern)){
					Toast.makeText(SignUpActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
				}else if(mPasswordEt.getText().toString().isEmpty()){
					Toast.makeText(SignUpActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
				}else if(mConfirmPasswordEt.getText().toString().isEmpty()){
					Toast.makeText(SignUpActivity.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
				}else if(!mPasswordEt.getText().toString().equals(mConfirmPasswordEt.getText().toString())){
					Toast.makeText(SignUpActivity.this, "Password and Confirm Password must be same.", Toast.LENGTH_SHORT).show();
				}else {
					doRegister(mEmailEt.getText().toString(),mPasswordEt.getText().toString(),mPasswordEt.getText().toString(),role,"android","");
				}

			}
		});
	}

	private void doRegister(String email, String password, String confirmPassword, String role,String deviceType,String deviceToken) {
		pd.show();
		pd.setMessage("Loading");

		ApiManager.getService().register(email, password, confirmPassword, role, deviceType,deviceToken).enqueue(new Callback<Result>() {
			@Override
			public void onResponse(Call<Result> call, Response<Result> response) {
				//try {

				if(response != null && response.body() != null){

					Result result = response.body();

					if(result.getStatus().equalsIgnoreCase("success"))
					{
						PrefUtils.addUserInfo(result.getUser());
						Intent i=new Intent(SignUpActivity.this,CreateProfileActivity.class);
						startActivity(i);
						finish();
					}

				}else {
					Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
				}

				pd.cancel();

			}

			@Override
			public void onFailure(Call<Result> call, Throwable t) {
				pd.cancel();
				Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
