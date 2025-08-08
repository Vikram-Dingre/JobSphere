package com.sphere.jobsphere.Common.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.sphere.jobsphere.R;

public class CommonRegisterActivity extends AppCompatActivity {

    AppCompatButton acbRegisterLogin, acbRegisterSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_register);

        acbRegisterLogin = findViewById(R.id.acbRegisterLogin);
        acbRegisterSignUp = findViewById(R.id.acbRegisterSignUp);

        acbRegisterLogin.setOnClickListener(v->{
            startActivity(new Intent(this, CommonLoginActivity.class));
        });

        acbRegisterSignUp.setOnClickListener(v->{
            Toast.makeText(this, "Registration will open sooner...", Toast.LENGTH_SHORT).show();
        });

    }
}