package com.app.flex.fragment.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.flex.DriverActivity;
import com.app.flex.HomeActivity;
import com.app.flex.R;

public class CongratulationActivity extends AppCompatActivity {
    Button done_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation);

        done_btn = findViewById(R.id.done_btn);
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent_congratulation = new Intent(CongratulationActivity.this, HomeActivity.class);
                Intent intent_congratulation = new Intent(CongratulationActivity.this, DriverActivity.class);
                startActivity(intent_congratulation);
                finish();
            }
        });
    }
}
