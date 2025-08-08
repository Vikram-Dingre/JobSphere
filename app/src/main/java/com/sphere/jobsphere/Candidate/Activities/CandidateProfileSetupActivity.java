package com.sphere.jobsphere.Candidate.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sphere.jobsphere.R;

public class CandidateProfileSetupActivity extends AppCompatActivity {
AppCompatButton btn;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile_setup);

        pref = getSharedPreferences("settings", MODE_PRIVATE);
       editor = pref.edit();
        
        btn = findViewById(R.id.btn);
        
        btn.setOnClickListener(v -> {
            editor.putBoolean("isProfileSetupCompleted",true).apply();
            Toast.makeText(this, "Profile Setup Completed Successfully.", Toast.LENGTH_SHORT).show();
        });

    }
}

