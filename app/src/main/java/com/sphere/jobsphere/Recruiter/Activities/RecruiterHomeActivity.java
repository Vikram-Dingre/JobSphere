package com.sphere.jobsphere.Recruiter.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.sphere.jobsphere.Common.Activities.CommonLoginActivity;
import com.sphere.jobsphere.R;

public class RecruiterHomeActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    AppCompatButton logout;
    String currentUid;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recruiter_home);
        pref = getSharedPreferences("settings", MODE_PRIVATE);
        editor = pref.edit();

        currentUid = auth.getCurrentUser().getUid();
        logout = findViewById(R.id.logoutRecruiter);

        logout.setOnClickListener(v -> {
            auth.signOut();
            editor.putBoolean("isLoggedIn", false).apply();
            //editor.remove("isLoggedIn");
            startActivity(new Intent(this, CommonLoginActivity.class));
        });
    }
}

