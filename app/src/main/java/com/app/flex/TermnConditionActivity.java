package com.app.flex;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.app.flex.Utils.PrefUtils;
import com.app.flex.model.User;

public class TermnConditionActivity extends AppCompatActivity
{
	Button mAcceptBtn;
	User user;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_term_n_condition);
		PrefUtils.initPreferance(this);
		user = PrefUtils.getUserInfo();

		mAcceptBtn=findViewById(R.id.accept_btn);
		mAcceptBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(user.getmRole().equals("2")){
					Intent i=new Intent(TermnConditionActivity.this,HomeActivity.class);
					startActivity(i);
					finish();
				}else {
					Intent i=new Intent(TermnConditionActivity.this,DocumentActivity.class);
					startActivity(i);
					finish();
				}

			}
		});

	}
}
