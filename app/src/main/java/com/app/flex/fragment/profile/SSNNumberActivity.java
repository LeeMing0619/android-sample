package com.app.flex.fragment.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.flex.R;

public class SSNNumberActivity extends AppCompatActivity {
    Button mNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssnnumber);

        mNextBtn =findViewById(R.id.next_btn);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SSNNumberActivity.this, CongratulationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
