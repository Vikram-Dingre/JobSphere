package com.sphere.jobsphere.Common.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Activities.CandidateHomeActivity;
import com.sphere.jobsphere.R;

import java.util.HashMap;
import java.util.Map;

public class CommonRegisterActivity extends AppCompatActivity {

    AppCompatButton acbRegisterLogin, acbRegisterSignUp;
    CardView cvRegisterSeeker, cvRegisterRecruiter;
    TextInputEditText tieRegisterName, tieRegisterEmail, tieRegisterPassword;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_register);

        acbRegisterLogin = findViewById(R.id.acbRegisterLogin);
        acbRegisterSignUp = findViewById(R.id.acbRegisterSignUp);
        cvRegisterSeeker = findViewById(R.id.cvRegisterSeeker);
        cvRegisterRecruiter = findViewById(R.id.cvRegisterRecruiter);
        tieRegisterName = findViewById(R.id.tieRegisterName);
        tieRegisterEmail = findViewById(R.id.tieRegisterEmail);
        tieRegisterPassword = findViewById(R.id.tieRegisterPassword);

        acbRegisterLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, CommonLoginActivity.class));
            finish();
        });

        acbRegisterSignUp.setOnClickListener(v -> {


            String userName = tieRegisterName.getText().toString();
            String userEmail = tieRegisterEmail.getText().toString();
            String userPassword = tieRegisterPassword.getText().toString();

            if (!userEmail.isEmpty() && !userName.isEmpty() && !userPassword.isEmpty() && userRole != null) {

                auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        String uid = auth.getCurrentUser().getUid();

                        Map<String, Object> user = new HashMap<>();
                        user.put("userName", userName);
                        user.put("userEmail", userEmail);
                        user.put("userPassword", userPassword);
                        user.put("role", userRole);

                        db.collection("users")
                                .document(uid)
                                .set(user)
                                .addOnSuccessListener(ref -> {
                                    Toast.makeText(this, "Account Created Successfully.", Toast.LENGTH_SHORT).show();
                                    new Handler().postDelayed(() -> {
                                        startActivity(new Intent(this, CommonLoginActivity.class));
                                        finish();
                                    }, 1000);
                                })
                                .addOnFailureListener(e->{
                                    Toast.makeText(this, "Failure : Creating Account.", Toast.LENGTH_SHORT).show();
                                });
                        
                    } else {
                        Toast.makeText(this, "Error : Can't Create Account", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
            }

        });

        cvRegisterRecruiter.setOnClickListener(v -> {
            userRole = "recruiter";
            cvRegisterSeeker.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardUncheckedColor));
            cvRegisterRecruiter.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardCheckedColor));
        });

        cvRegisterSeeker.setOnClickListener(v -> {
            userRole = "seeker";
            cvRegisterRecruiter.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardUncheckedColor));
            cvRegisterSeeker.setCardBackgroundColor(ContextCompat.getColor(this, R.color.cardCheckedColor));
        });
    }
}

