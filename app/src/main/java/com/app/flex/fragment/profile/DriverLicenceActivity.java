package com.app.flex.fragment.profile;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.flex.R;

public class DriverLicenceActivity extends AppCompatActivity {
    private TextView take_photo_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_licence);

        take_photo_tv = findViewById(R.id.take_photo_tv);
        take_photo_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_take_photo = new Intent(DriverLicenceActivity.this, TakePhotoInfoActivity.class);
                startActivity(intent_take_photo);
            }
        });

    }
}
