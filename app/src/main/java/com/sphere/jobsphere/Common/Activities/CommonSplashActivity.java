package com.sphere.jobsphere.Common.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.sphere.jobsphere.R;

public class CommonSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_common_splash);
        new Handler().postDelayed(()->{
            startActivity(new Intent(this, CommonLoginActivity.class));
            finish();
        },2000);
    }
}