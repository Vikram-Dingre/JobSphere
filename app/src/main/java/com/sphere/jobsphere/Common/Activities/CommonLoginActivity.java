package com.sphere.jobsphere.Common.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.sphere.jobsphere.R;

public class CommonLoginActivity extends AppCompatActivity {

    AppCompatButton acbLoginLogin, acbLoginSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_login);

        acbLoginLogin = findViewById(R.id.acbLoginLogin);
        acbLoginSignUp = findViewById(R.id.acbLoginSignUp);

        acbLoginSignUp.setOnClickListener(v->{
            startActivity(new Intent(this, CommonRegisterActivity.class));
        });

        acbLoginLogin.setOnClickListener(v -> {
            Toast.makeText(this, "Login Feature In-Progress...", Toast.LENGTH_SHORT).show();
        });

    }
}