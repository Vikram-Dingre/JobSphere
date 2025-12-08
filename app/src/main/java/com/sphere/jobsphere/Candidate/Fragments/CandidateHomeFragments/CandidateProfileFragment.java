package com.sphere.jobsphere.Candidate.Fragments.CandidateHomeFragments;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sphere.jobsphere.Candidate.Activities.CandidateEditProfileActivities.CandidateEditProfileActivity;
import com.sphere.jobsphere.Candidate.Activities.CandidateHomeActivity;
import com.sphere.jobsphere.Candidate.Activities.CandidateProfileInfoActivities.CandidateEducationActivity;
import com.sphere.jobsphere.Candidate.Activities.CandidateProfileInfoActivities.CandidatePersonalInfoActivity;
import com.sphere.jobsphere.Candidate.Activities.CandidateProfileInfoActivities.CandidateWorkExperienceActivity;
import com.sphere.jobsphere.Candidate.Activities.CandidateSavedJobsActivity;
import com.sphere.jobsphere.Candidate.Models.CandidateProfileSetupModels.CandidateProfile;
import com.sphere.jobsphere.Common.Activities.CommonLoginActivity;
import com.sphere.jobsphere.R;

public class CandidateProfileFragment extends Fragment {
    AppCompatButton acbCandidateProfileSavedJobs, acbCandidateProfileAppliedJobs;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUid;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    LinearLayout llCandidateProfilePersonalInfo, llCandidateProfileWorkExperience, llCandidateProfileEducation, llCandidateProfileLogOut;

    ImageView ivCandidateProfilePhoto, ivCandidateProfileEditProfile;
    TextView ivCandidateProfileUserName, ivCandidateProfileUserEmail;
    CandidateProfile profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidate_profile, container, false);

        currentUid = auth.getCurrentUser().getUid();

        acbCandidateProfileSavedJobs = view.findViewById(R.id.acbCandidateProfileSavedJobs);
        llCandidateProfilePersonalInfo = view.findViewById(R.id.llCandidateProfilePersonalInfo);
        llCandidateProfileWorkExperience = view.findViewById(R.id.llCandidateProfileWorkExperience);
        llCandidateProfileEducation = view.findViewById(R.id.llCandidateProfileEducation);
        llCandidateProfileLogOut = view.findViewById(R.id.llCandidateProfileLogOut);
        ivCandidateProfilePhoto = view.findViewById(R.id.ivCandidateProfilePhoto);
        ivCandidateProfileUserName = view.findViewById(R.id.ivCandidateProfileUserName);
        ivCandidateProfileUserEmail = view.findViewById(R.id.ivCandidateProfileUserEmail);
        ivCandidateProfileEditProfile = view.findViewById(R.id.ivCandidateProfileEditProfile);
        acbCandidateProfileAppliedJobs = view.findViewById(R.id.acbCandidateProfileAppliedJobs);


        pref = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
        editor = pref.edit();
        calculateBoxCounts();

        acbCandidateProfileAppliedJobs.setOnClickListener(v -> {
            Fragment candidateApplicationsFragment = new CandidateApplicationsFragment();
            CandidateHomeActivity activity = (CandidateHomeActivity) getActivity();
            activity.bnvCandidateHomeActivityBottomMenu.setSelectedItemId(R.id.candidate_home_bottom_menu_applications);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.flCandidateHomeActivityFrameContainer, candidateApplicationsFragment).commit();
        });

        ivCandidateProfileEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CandidateEditProfileActivity.class);
            startActivity(intent);

        });

        acbCandidateProfileSavedJobs.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CandidateSavedJobsActivity.class);
            startActivity(intent);
        });

        llCandidateProfilePersonalInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CandidatePersonalInfoActivity.class);
            startActivity(intent);

        });

        llCandidateProfileWorkExperience.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CandidateWorkExperienceActivity.class);
            startActivity(intent);

        });

        llCandidateProfileEducation.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CandidateEducationActivity.class);
            startActivity(intent);

        });

        llCandidateProfileLogOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            editor.putBoolean("isLoggedIn", false).apply();
            makeText(getActivity(), "Logged out Successfully.", LENGTH_SHORT).show();
            new Handler().postDelayed(() -> {
                startActivity(new Intent(getActivity(), CommonLoginActivity.class));
                getActivity().finish();
            }, 2000);
        });

        return view;
    }

    private void calculateBoxCounts() {

        db.collection("candidates")
                .document(currentUid)
                .addSnapshotListener((documentSnapshot, error) -> {

                    if (error != null || documentSnapshot == null) return;

                    profile = documentSnapshot.toObject(CandidateProfile.class);
                    profile.setUid(documentSnapshot.getId());

                    Glide.with(getActivity())
                            .load(profile.getPersonalInfo().getProfilePhotoUrl())
                            .into(ivCandidateProfilePhoto);

                    ivCandidateProfileUserName.setText(profile.getPersonalInfo().getFullName());
                    ivCandidateProfileUserEmail.setText(profile.getPersonalInfo().getEmail());

                });

        db.collection("candidateSavedJobs")
                .document(currentUid)
                .collection("savedJobs")
                .addSnapshotListener((snapshots, error) -> {

                    if (error != null || snapshots == null) return;

                    long count = snapshots.size();  // realtime count

                    acbCandidateProfileSavedJobs.setText(String.valueOf(count));
                });

        db.collection("CandidateApplications")
                .document(currentUid)
                .collection("applications")
                .addSnapshotListener((snapshots, error) -> {

                    if (error != null || snapshots == null) return;

                    long count = snapshots.size();  // realtime count

                    acbCandidateProfileAppliedJobs.setText(String.valueOf(count));
                });


        //        db.collection("candidateSavedJobs")
//                .document(currentUid)
//                .collection("savedJobs")
//                .count()
//                .get(AggregateSource.SERVER)
//                .addOnSuccessListener(snapshot -> {
//                    long count = snapshot.getCount();
//                    acbCandidateProfileSavedJobs.setText(count + "");
//                })
//                .addOnFailureListener(e -> Log.e("COUNT", "Error:", e));
    }
}