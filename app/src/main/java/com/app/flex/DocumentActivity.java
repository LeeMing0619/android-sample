package com.app.flex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.app.flex.Utils.AppUtils;
import com.app.flex.Utils.PrefUtils;
import com.app.flex.fragment.profile.DriverLicenceActivity;
import com.app.flex.model.User;

public class DocumentActivity extends AppCompatActivity
{
	RelativeLayout mTopRl;
	Button driver_licence_btn;
	User user;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_document);

		mTopRl=findViewById(R.id.top_rl);

		PrefUtils.initPreferance(this);
		user = PrefUtils.getUserInfo();

		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				(int)(AppUtils.getDeviceHeight(this)/2.5f));
		mTopRl.setLayoutParams(params);

		driver_licence_btn = findViewById(R.id.driver_licence_btn);
		Button btnSubmit=findViewById(R.id.submit_btn);

		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(user.getmRole().equals("1")){
					Intent i = new Intent(DocumentActivity.this, DriverActivity.class);
					startActivity(i);
					finish();
				}else {
					Intent i = new Intent(DocumentActivity.this, HomeActivity.class);
					startActivity(i);
					finish();
				}
				/*Intent i=new Intent(DocumentActivity.this,HomeActivity.class);
				startActivity(i);
				finish();*/
			}
		});

		driver_licence_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent_home = new Intent(DocumentActivity.this, DriverLicenceActivity.class);
				//intent_home.putExtra("driver_licence","driver_licence");
				startActivity(intent_home);
			}
		});
	}
}
