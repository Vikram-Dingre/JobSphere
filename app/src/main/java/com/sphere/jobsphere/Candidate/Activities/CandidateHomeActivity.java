package com.sphere.jobsphere.Candidate.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.sphere.jobsphere.Common.Activities.CommonLoginActivity;
import com.sphere.jobsphere.R;

public class CandidateHomeActivity extends AppCompatActivity {


    FirebaseAuth auth = FirebaseAuth.getInstance();
    AppCompatButton logout;
    String currentUid;

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_home);

        pref = getSharedPreferences("settings",MODE_PRIVATE);
        editor = pref.edit();

        currentUid = auth.getCurrentUser().getUid();
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(v -> {
            auth.signOut();
            editor.putBoolean("isLoggedIn", false).apply();
            //editor.remove("isLoggedIn");
            startActivity(new Intent(this, CommonLoginActivity.class));
        });

    }
}

