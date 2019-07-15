package com.app.flex;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.flex.Utils.AppUtils;
import com.app.flex.model.ForgotPassword;
import com.app.flex.retrofit.ApiManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
	RelativeLayout mTopRl;
	TextView mBackTv;
	ProgressDialog pd;
	Button mForgotPasswordBtn;
	EditText mEmailEt;
	String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);

		mTopRl = findViewById(R.id.top_rl);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				(int) (AppUtils.getDeviceHeight(this) / 2.5f));
		mTopRl.setLayoutParams(params);
		pd = new ProgressDialog(this);


		mBackTv = findViewById(R.id.back_tv);
		mBackTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mEmailEt = findViewById(R.id.email_et);

		mForgotPasswordBtn = findViewById(R.id.forgot_password_btn);
		mForgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mEmailEt.getText().toString().isEmpty()) {
					Toast.makeText(ForgotPasswordActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
				} else if (!mEmailEt.getText().toString().matches(emailPattern)) {
					Toast.makeText(ForgotPasswordActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
				} else {
					forgotPasswordApi(mEmailEt.getText().toString());
				}
			}
		});
	}

	private void forgotPasswordApi(String email) {

		pd.show();
		pd.setMessage("Loading");

		ApiManager.getService().forgotPassword(email).enqueue(new Callback<ForgotPassword>() {

			@Override
			public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {
				pd.cancel();
				if (response != null && response.body() != null) {

					ForgotPassword result = response.body();
					Toast.makeText(ForgotPasswordActivity.this,result.getmResult(),Toast.LENGTH_SHORT).show();

					if(result.getmStatus().equals("success"))
					{
						finish();
					}
				}
			}
			@Override
			public void onFailure(Call<ForgotPassword> call, Throwable t) {
				pd.cancel();
				Toast.makeText(ForgotPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
