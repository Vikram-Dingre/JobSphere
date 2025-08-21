package com.sphere.jobsphere.Candidate.Classes;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.sphere.jobsphere.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class JobFiltersBottomSheet extends BottomSheetDialogFragment {

    ImageView ivCandidateJobFiltersExpandJobTypes, ivCandidateJobFiltersExpandCategory, ivCandidateJobFiltersExpandSkills, ivCandidateJobFiltersExpandExperience, ivCandidateJobFiltersExpandLocation, ivCandidateJobFiltersExpandSalary, ivCandidateJobFiltersExpandFreshness;
    LinearLayout llCandidateFiltersJobType, llCandidateFiltersCategory, llCandidateFiltersSkills, llCandidateFiltersExperience, llCandidateFiltersLocation, llCandidateFiltersSalary, llCandidateFiltersFreshness;
    AppCompatButton acbCandidateJobFiltersApply, acbCandidateJobFiltersCancel;

    boolean isShowJobTypes = false;
    boolean isShowCategory = false;
    boolean isShowSkills = false;
    boolean isShowExperience = false;
    boolean isShowLocation = false;
    boolean isSHowSalary = false;
    boolean isShowFreshness = false;

    List<String> selectedJobType;
    List<String> selectedCategory;
    List<String> selectedSkills;
    String selectedExperience;
    List<String> selectedLocation;
    Double selectedMinSalary;
    Double selectedMaxSalary;
    String selectedFreshness;

    private OnFilterAppliedListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.candidate_jobs_filters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ivCandidateJobFiltersExpandJobTypes = view.findViewById(R.id.ivCandidateJobFiltersExpandJobTypes);
        ivCandidateJobFiltersExpandCategory = view.findViewById(R.id.ivCandidateJobFiltersExpandCategory);
        ivCandidateJobFiltersExpandSkills = view.findViewById(R.id.ivCandidateJobFiltersExpandSkills);
        ivCandidateJobFiltersExpandExperience = view.findViewById(R.id.ivCandidateJobFiltersExpandExperience);
        ivCandidateJobFiltersExpandLocation = view.findViewById(R.id.ivCandidateJobFiltersExpandLocation);
        ivCandidateJobFiltersExpandSalary = view.findViewById(R.id.ivCandidateJobFiltersExpandSalary);
        ivCandidateJobFiltersExpandFreshness = view.findViewById(R.id.ivCandidateJobFiltersExpandFreshness);

        llCandidateFiltersJobType = view.findViewById(R.id.llCandidateFiltersJobType);
        llCandidateFiltersCategory = view.findViewById(R.id.llCandidateFiltersCategory);
        llCandidateFiltersSkills = view.findViewById(R.id.llCandidateFiltersSkills);
        llCandidateFiltersExperience = view.findViewById(R.id.llCandidateFiltersExperience);
        llCandidateFiltersLocation = view.findViewById(R.id.llCandidateFiltersLocation);
        llCandidateFiltersSalary = view.findViewById(R.id.llCandidateFiltersSalary);
        llCandidateFiltersFreshness = view.findViewById(R.id.llCandidateFiltersFreshness);

        acbCandidateJobFiltersApply = view.findViewById(R.id.acbCandidateJobFiltersApply);
        acbCandidateJobFiltersCancel = view.findViewById(R.id.acbCandidateJobFiltersCancel);

        acbCandidateJobFiltersCancel.setOnClickListener(v -> {
            dismiss();
        });

        acbCandidateJobFiltersApply.setOnClickListener(v -> {
            applyFilters();
        });

        ivCandidateJobFiltersExpandJobTypes.setOnClickListener(v -> {
            if (isShowJobTypes) {
                llCandidateFiltersJobType.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandJobTypes.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersJobType.setVisibility(View.VISIBLE);
                ivCandidateJobFiltersExpandJobTypes.setImageResource(R.drawable.minus);
            }
            isShowJobTypes = !isShowJobTypes;

        });

        ivCandidateJobFiltersExpandCategory.setOnClickListener(v -> {
            if (isShowCategory) {
                llCandidateFiltersCategory.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandCategory.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersCategory.setVisibility(View.VISIBLE);
                ivCandidateJobFiltersExpandCategory.setImageResource(R.drawable.minus);
            }
            isShowCategory = !isShowCategory;
        });

        ivCandidateJobFiltersExpandSkills.setOnClickListener(v -> {
            if (isShowSkills) {
                llCandidateFiltersSkills.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandSkills.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersSkills.setVisibility(View.VISIBLE);
                ivCandidateJobFiltersExpandSkills.setImageResource(R.drawable.minus);
            }
            isShowSkills = !isShowSkills;

        });

        ivCandidateJobFiltersExpandExperience.setOnClickListener(v -> {
            if (isShowExperience) {
                llCandidateFiltersExperience.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandExperience.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersExperience.setVisibility(View.VISIBLE);
                ivCandidateJobFiltersExpandExperience.setImageResource(R.drawable.minus);
            }
            isShowExperience = !isShowExperience;

        });

        ivCandidateJobFiltersExpandLocation.setOnClickListener(v -> {
            if (isShowLocation) {
                llCandidateFiltersLocation.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandLocation.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersLocation.setVisibility(View.VISIBLE);
                ivCandidateJobFiltersExpandLocation.setImageResource(R.drawable.minus);
            }
            isShowLocation = !isShowLocation;

        });

        ivCandidateJobFiltersExpandSalary.setOnClickListener(v -> {
            if (isSHowSalary) {
                llCandidateFiltersSalary.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandSalary.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersSalary.setVisibility(View.VISIBLE);
                ivCandidateJobFiltersExpandSalary.setImageResource(R.drawable.minus);
            }
            isSHowSalary = !isSHowSalary;
        });

        ivCandidateJobFiltersExpandFreshness.setOnClickListener(v -> {
            if (isShowFreshness) {
                llCandidateFiltersFreshness.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandFreshness.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersFreshness.setVisibility(View.VISIBLE);
                ivCandidateJobFiltersExpandFreshness.setImageResource(R.drawable.minus);
            }
            isShowFreshness = !isShowFreshness;
        });

    }

    public interface OnFilterAppliedListener {
        void onFilterApplied(CandidateJobFilterModel filterModel);
    }

    public void setOnFilterAppliedListener(OnFilterAppliedListener listener) {
        this.listener = listener;
    }

    // when user clicks "Apply Filters"
    private void applyFilters() {

        selectedJobType = getSelectedFromSection(llCandidateFiltersJobType);
        selectedSkills = getSelectedFromSection(llCandidateFiltersSkills);
        selectedLocation = getSelectedFromSection(llCandidateFiltersLocation);
        selectedCategory = getSelectedFromSection(llCandidateFiltersCategory);

        selectedExperience = getSelectedFromRadioSections(llCandidateFiltersExperience);
        selectedFreshness = getSelectedFromRadioSections(llCandidateFiltersFreshness);

        selectedMinSalary = 0.0;
        selectedMaxSalary = 10000.0;

        CandidateJobFilterModel filters = new CandidateJobFilterModel(
                selectedJobType, selectedCategory, selectedSkills, selectedExperience, selectedLocation, selectedMinSalary, selectedMaxSalary, selectedFreshness
        );

        if (listener != null) {
            listener.onFilterApplied(filters); // send back to fragment
        }
        dismiss();
    }

    private String getSelectedFromRadioSections(LinearLayout parentLayout) {
        String selectedItemText = "";
        int childCount = parentLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parentLayout.getChildAt(i);
            if (child instanceof RadioGroup) {
                RadioGroup radioGroup = (RadioGroup) child;
                int selectedRadioId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = radioGroup.findViewById(selectedRadioId);
                if (radioButton != null) {
                    selectedItemText = radioButton.getText().toString();
                }
            }
        }
        return selectedItemText;
    }

    private List<String> getSelectedFromSection(LinearLayout parentLayout) {
        List<String> selectedItems = new ArrayList<>();

        int childCount = parentLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parentLayout.getChildAt(i);

            if (child instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) child;
                if (checkBox.isChecked()) {
                    selectedItems.add(checkBox.getText().toString());
                }
            } else if (child instanceof ChipGroup) {
                ChipGroup chipGroup = (ChipGroup) child;
                List<Integer> selectedChipIds = chipGroup.getCheckedChipIds();
                for (Integer item : selectedChipIds) {
                    Chip chip = chipGroup.findViewById(item);
                    String chipText = chip.getText().toString();
                    selectedItems.add(chipText);
                }
            }
        }
        return selectedItems;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;

            View bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

            if (bottomSheet != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);

                // Make it expanded by default
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                // Disable hiding by swiping down
                behavior.setHideable(false);

                // Disable dragging/swiping
                behavior.setDraggable(false);

                // Optional: make full height
                bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                bottomSheet.requestLayout();
            }
        });

        return dialog;
    }


//job Type ✅
//Category ✅
//Skills ✅
//Experience ✅
//Location ✅
//Salary Range ✅
//Posted Date ✅
//action buttons ✅


}

