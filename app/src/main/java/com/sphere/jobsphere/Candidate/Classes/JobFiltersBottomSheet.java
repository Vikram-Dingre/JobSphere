package com.sphere.jobsphere.Candidate.Classes;

import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.animation.LayoutTransition;
import android.app.Dialog;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;
import com.sphere.jobsphere.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class JobFiltersBottomSheet extends BottomSheetDialogFragment {

    public boolean isFilterApplied = false;
    ImageView ivCandidateJobFiltersExpandJobTypes, ivCandidateJobFiltersExpandCategory, ivCandidateJobFiltersExpandSkills, ivCandidateJobFiltersExpandExperience, ivCandidateJobFiltersExpandLocation, ivCandidateJobFiltersExpandSalary, ivCandidateJobFiltersExpandFreshness;
    LinearLayout llCandidateFiltersJobType, llCandidateFiltersCategory, llCandidateFiltersSkills, llCandidateFiltersExperience, llCandidateFiltersLocation, llCandidateFiltersSalary, llCandidateFiltersFreshness;
    AppCompatButton acbCandidateJobFiltersApply, acbCandidateJobFiltersCancel;
    TextView ivCandidateJobFiltersClearFilters;
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
    ChipGroup chipGroupSelectedFilters;
    ChipGroup chipGroupJobType, chipGroupSkills, chipGroupLocation;
    RangeSlider salaryRangeSlider;
    RadioGroup rgCandidateFiltersExperience, rgCandidateFiltersFreshness;
    String homePageCategory = "";

    CandidateJobFilterState state;

    private OnFilterAppliedListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.candidate_jobs_filters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        findViews(view);


        state = CandidateJobFilterState.getInstance();

        //RESTORE ALL FILTERS
        restoreAllFilters();

        //CLICK LISTENERS
        allOnViewCreatedClickListeners();

        // add to filtered chips at top when clicking on that filter ui
        // SELECTING CHIPS ON OPENING BOTTOM SHEET
        addDefaultChipsToSelectedFilterChips();

        // RADIO BUTTON LISTENERS
        allOncViewCreatedRadioCheckedChangeListeners();

        if (!homePageCategory.isEmpty()) {
            llCandidateFiltersJobType.setVisibility(VISIBLE);
            ivCandidateJobFiltersExpandJobTypes.setImageResource(R.drawable.minus);
            int childCount = llCandidateFiltersJobType.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = llCandidateFiltersJobType.getChildAt(i);
                if (child instanceof ChipGroup) {
                    ChipGroup chipGroup = (ChipGroup) child;
                    int count = chipGroup.getChildCount();
                    for (int j = 0; j < count; j++) {
                        Chip chip = (Chip) chipGroup.getChildAt(j);

                        makeText(getContext(), "chip " + chip.getText(), LENGTH_SHORT).show();
                        if (chip.getText().toString().equalsIgnoreCase(homePageCategory)) {
                            chip.setChecked(true);
                            return;
                        }
                    }
                }
            }
        }

    }

    private void findViews(View view) {
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

        chipGroupSelectedFilters = view.findViewById(R.id.chipGroupSelectedFilters);
        chipGroupJobType = view.findViewById(R.id.chipGroupJobType);
        chipGroupLocation = view.findViewById(R.id.chipGroupLocation);
        chipGroupSkills = view.findViewById(R.id.chipGroupSkills);

        salaryRangeSlider = view.findViewById(R.id.salaryRangeSlider);

        rgCandidateFiltersExperience = view.findViewById(R.id.rgCandidateFiltersExperience);
        rgCandidateFiltersFreshness = view.findViewById(R.id.rgCandidateFiltersFreshness);

        ivCandidateJobFiltersClearFilters = view.findViewById(R.id.ivCandidateJobFiltersClearFilters);
    }

    private void addDefaultChipsToSelectedFilterChips() {
        for (int i = 0; i < chipGroupJobType.getChildCount(); i++) {
            View v = chipGroupJobType.getChildAt(i);
            if (v instanceof Chip) {
                Chip chip = (Chip) v;
                chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    addOrRemoveSelectedFilterChip(chip.getText().toString(), isChecked);
                });
            }
        }

        for (int i = 0; i < llCandidateFiltersCategory.getChildCount(); i++) {
            View v = llCandidateFiltersCategory.getChildAt(i);
            if (v instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) v;
                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    addOrRemoveSelectedFilterChip(checkBox.getText().toString(), isChecked);
                });
            }
        }

        for (int i = 0; i < chipGroupSkills.getChildCount(); i++) {
            View v = chipGroupSkills.getChildAt(i);
            if (v instanceof Chip) {
                Chip chip = (Chip) v;
                chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    addOrRemoveSelectedFilterChip(chip.getText().toString(), isChecked);
                });
            }
        }

        for (int i = 0; i < chipGroupLocation.getChildCount(); i++) {
            View v = chipGroupLocation.getChildAt(i);
            if (v instanceof Chip) {
                Chip chip = (Chip) v;
                chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    addOrRemoveSelectedFilterChip(chip.getText().toString(), isChecked);
                });
            }
        }
    }

    private void allOncViewCreatedRadioCheckedChangeListeners() {
        rgCandidateFiltersExperience.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);

                if (radioButton == null) return;

                String selectedText = radioButton.getText().toString();
                List<String> experienceButtons = new ArrayList<>();

                for (int j = 0; j < group.getChildCount(); j++) {
                    RadioButton radio = (RadioButton) group.getChildAt(j);
                    experienceButtons.add(radio.getText().toString());
                }

                int count = chipGroupSelectedFilters.getChildCount();

                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        View view = chipGroupSelectedFilters.getChildAt(i);
                        if (view instanceof Chip) {
                            Chip c = (Chip) view;
                            boolean isRadioButtonAlreadyExists = experienceButtons.contains(c.getText().toString());

                            if (isRadioButtonAlreadyExists) {

                                chipGroupSelectedFilters.removeView(c);
                                addOrRemoveSelectedFilterChip(selectedText, radioButton.isChecked());

//                            c.setText(selectedText);
//                            c.setOnCloseIconClickListener(v -> {
//                                unselectFilterFromSections(selectedText); // uncheck in section
//                                chipGroupSelectedFilters.removeView(c);
//                            });

                            } else {
                                addOrRemoveSelectedFilterChip(selectedText, radioButton.isChecked());
                            }
                        }
                    }
                } else {
                    addOrRemoveSelectedFilterChip(selectedText, radioButton.isChecked());
                }
            }
        });

        rgCandidateFiltersFreshness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);

                if (radioButton == null) return;

                String selectedText = radioButton.getText().toString();
                List<String> freshnessButtons = new ArrayList<>();

                for (int j = 0; j < group.getChildCount(); j++) {
                    RadioButton radio = (RadioButton) group.getChildAt(j);
                    freshnessButtons.add(radio.getText().toString());
                }

                int count = chipGroupSelectedFilters.getChildCount();

                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        View view = chipGroupSelectedFilters.getChildAt(i);
                        if (view instanceof Chip) {
                            Chip c = (Chip) view;
                            boolean isRadioButtonAlreadyExists = freshnessButtons.contains(c.getText().toString());

                            if (isRadioButtonAlreadyExists) {

                                chipGroupSelectedFilters.removeView(c);
                                addOrRemoveSelectedFilterChip(selectedText, radioButton.isChecked());

//                            c.setText(selectedText);
//                            c.setOnCloseIconClickListener(v -> {
//                                unselectFilterFromSections(selectedText); // uncheck in section
//                                chipGroupSelectedFilters.removeView(c);
//                            });

                            } else {
                                addOrRemoveSelectedFilterChip(selectedText, radioButton.isChecked());
                            }
                        }
                    }
                } else {
                    addOrRemoveSelectedFilterChip(selectedText, radioButton.isChecked());
                }
            }
        });

//        rgCandidateFiltersFreshness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton radioButton = group.findViewById(checkedId);
//                addOrRemoveSelectedFilterChip(radioButton.getText().toString(), radioButton.isChecked());
//            }
//        });
    }

    private void allOnViewCreatedClickListeners() {

        ivCandidateJobFiltersClearFilters.setOnClickListener(v -> {
            clearAllFilters();
        });

        acbCandidateJobFiltersCancel.setOnClickListener(v -> {
            dismiss();
        });

        acbCandidateJobFiltersApply.setOnClickListener(v -> {
            applyFilters(true);
        });

        ivCandidateJobFiltersExpandJobTypes.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(llCandidateFiltersJobType, new AutoTransition());
            if (isShowJobTypes) {
                llCandidateFiltersJobType.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandJobTypes.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersJobType.setVisibility(VISIBLE);
                ivCandidateJobFiltersExpandJobTypes.setImageResource(R.drawable.minus);
            }
            isShowJobTypes = !isShowJobTypes;
        });


        ivCandidateJobFiltersExpandCategory.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(llCandidateFiltersCategory, new AutoTransition());
            if (isShowCategory) {
                llCandidateFiltersCategory.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandCategory.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersCategory.setVisibility(VISIBLE);
                ivCandidateJobFiltersExpandCategory.setImageResource(R.drawable.minus);
            }
            isShowCategory = !isShowCategory;
        });

        ivCandidateJobFiltersExpandSkills.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(llCandidateFiltersSkills, new AutoTransition());
            if (isShowSkills) {
                llCandidateFiltersSkills.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandSkills.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersSkills.setVisibility(VISIBLE);
                ivCandidateJobFiltersExpandSkills.setImageResource(R.drawable.minus);
            }
            isShowSkills = !isShowSkills;

        });

        ivCandidateJobFiltersExpandExperience.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(llCandidateFiltersExperience, new AutoTransition());
            if (isShowExperience) {
                llCandidateFiltersExperience.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandExperience.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersExperience.setVisibility(VISIBLE);
                ivCandidateJobFiltersExpandExperience.setImageResource(R.drawable.minus);
            }
            isShowExperience = !isShowExperience;

        });

        ivCandidateJobFiltersExpandLocation.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(llCandidateFiltersLocation, new AutoTransition());
            if (isShowLocation) {
                llCandidateFiltersLocation.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandLocation.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersLocation.setVisibility(VISIBLE);
                ivCandidateJobFiltersExpandLocation.setImageResource(R.drawable.minus);
            }
            isShowLocation = !isShowLocation;

        });

        ivCandidateJobFiltersExpandSalary.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(llCandidateFiltersSalary, new AutoTransition());
            if (isSHowSalary) {
                llCandidateFiltersSalary.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandSalary.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersSalary.setVisibility(VISIBLE);
                ivCandidateJobFiltersExpandSalary.setImageResource(R.drawable.minus);
            }
            isSHowSalary = !isSHowSalary;
        });

        ivCandidateJobFiltersExpandFreshness.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(llCandidateFiltersFreshness, new AutoTransition());
            if (isShowFreshness) {
                llCandidateFiltersFreshness.setVisibility(View.GONE);
                ivCandidateJobFiltersExpandFreshness.setImageResource(R.drawable.plus);
            } else {
                llCandidateFiltersFreshness.setVisibility(VISIBLE);
                ivCandidateJobFiltersExpandFreshness.setImageResource(R.drawable.minus);
            }
            isShowFreshness = !isShowFreshness;
        });
    }

    public void restoreAllFilters() {
        // Restore categories
        for (int i = 0; i < llCandidateFiltersCategory.getChildCount(); i++) {
            View v = llCandidateFiltersCategory.getChildAt(i);
            if (v instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) v;
                checkBox.setChecked(state.selectedCategories.contains(checkBox.getText().toString()));
                if (checkBox.isChecked()) {
                    addOrRemoveSelectedFilterChip(checkBox.getText().toString(), checkBox.isChecked());
                    isShowCategory = true;
                    llCandidateFiltersCategory.setVisibility(VISIBLE);
                    ivCandidateJobFiltersExpandCategory.setImageResource(R.drawable.minus);
                }
            }
        }

        // Restore experience
        if (state.selectedExperience != null && !state.selectedExperience.isEmpty()) {

//            makeText(getContext(), ""+state.selectedExperience, LENGTH_SHORT).show();

            for (int i = 0; i < rgCandidateFiltersExperience.getChildCount(); i++) {
                View v = rgCandidateFiltersExperience.getChildAt(i);
                if (v instanceof RadioButton) {
                    RadioButton radioButton = (RadioButton) v;
                    if (radioButton.getText().toString().equals(state.selectedExperience)) {
                        radioButton.setChecked(true);
                        addOrRemoveSelectedFilterChip(radioButton.getText().toString(), true);
                        isShowExperience = true;
                        llCandidateFiltersExperience.setVisibility(VISIBLE);
                        ivCandidateJobFiltersExpandExperience.setImageResource(R.drawable.minus);
                        break;
                    }
                }
            }
        }

// Restore freshness
        if (state.freshness != null && !state.freshness.isEmpty()) {
            for (int i = 0; i < rgCandidateFiltersFreshness.getChildCount(); i++) {
                View v = rgCandidateFiltersFreshness.getChildAt(i);
                if (v instanceof RadioButton) {
                    RadioButton radioButton = (RadioButton) v;
                    if (radioButton.getText().toString().equals(state.freshness)) {
                        radioButton.setChecked(true);
                        addOrRemoveSelectedFilterChip(radioButton.getText().toString(), true);
                        isShowFreshness = true;
                        llCandidateFiltersFreshness.setVisibility(VISIBLE);
                        ivCandidateJobFiltersExpandFreshness.setImageResource(R.drawable.minus);
                        break;
                    }
                }
            }
        }


// Restore job types
        for (int i = 0; i < chipGroupJobType.getChildCount(); i++) {
            View v = chipGroupJobType.getChildAt(i);
            if (v instanceof Chip) {
                Chip chip = (Chip) v;
                chip.setChecked(state.selectedJobTypes.contains(chip.getText().toString()));
                if (chip.isChecked()) {
                    addOrRemoveSelectedFilterChip(chip.getText().toString(), chip.isChecked());
                    isShowJobTypes = true;
                    llCandidateFiltersJobType.setVisibility(VISIBLE);
                    ivCandidateJobFiltersExpandJobTypes.setImageResource(R.drawable.minus);
                }
            }
        }

// Restore skills
        for (int i = 0; i < chipGroupSkills.getChildCount(); i++) {
            View v = chipGroupSkills.getChildAt(i);
            if (v instanceof Chip) {
                Chip chip = (Chip) v;
                chip.setChecked(state.selectedSkills.contains(chip.getText().toString()));
                if (chip.isChecked()) {
                    addOrRemoveSelectedFilterChip(chip.getText().toString(), chip.isChecked());
                    isShowSkills = true;
                    llCandidateFiltersSkills.setVisibility(VISIBLE);
                    ivCandidateJobFiltersExpandSkills.setImageResource(R.drawable.minus);
                }
            }
        }


// Restore location
        for (int i = 0; i < chipGroupLocation.getChildCount(); i++) {
            View v = chipGroupLocation.getChildAt(i);
            if (v instanceof Chip) {
                Chip chip = (Chip) v;
                chip.setChecked(state.selectedLocation.contains(chip.getText().toString()));
                if (chip.isChecked()) {
                    addOrRemoveSelectedFilterChip(chip.getText().toString(), chip.isChecked());
                    isShowLocation = true;
                    llCandidateFiltersLocation.setVisibility(VISIBLE);
                    ivCandidateJobFiltersExpandLocation.setImageResource(R.drawable.minus);
                }
            }
        }

// Restore salary
        if (state.minSalary != 0 || state.maxSalary != 0) {
            salaryRangeSlider.setValues(Float.valueOf(1000.0F), Float.valueOf(5000.0F));
        }
    }

    public void clearAllFilters() {
        chipGroupJobType.clearCheck();
        chipGroupSkills.clearCheck();
        rgCandidateFiltersExperience.clearCheck();
        chipGroupLocation.clearCheck();
        rgCandidateFiltersFreshness.clearCheck();

        //Clear Category filters
        int count = llCandidateFiltersCategory.getChildCount();

        for (int i = 0; i < count; i++) {
            View view = llCandidateFiltersCategory.getChildAt(i);
            if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                checkBox.setChecked(false);
            }
        }
        applyFilters(false);
        isFilterApplied = false;

    }

    public void setOnFilterAppliedListener(OnFilterAppliedListener listener, String category) {
        this.listener = listener;
        this.homePageCategory = category;

    }

    private void unselectFilterFromSections(String filterName) {

        // Job type
        for (int i = 0; i < chipGroupJobType.getChildCount(); i++) {
            Chip c = (Chip) chipGroupJobType.getChildAt(i);
            if (c.getText().toString().equals(filterName)) {
                c.setChecked(false);
                return;
            }
        }

        // skills
        for (int i = 0; i < chipGroupSkills.getChildCount(); i++) {
            Chip c = (Chip) chipGroupSkills.getChildAt(i);
            if (c.getText().toString().equals(filterName)) {
                c.setChecked(false);
                return;
            }
        }

        // location
        for (int i = 0; i < chipGroupLocation.getChildCount(); i++) {
            Chip c = (Chip) chipGroupLocation.getChildAt(i);
            if (c.getText().toString().equals(filterName)) {
                c.setChecked(false);
                return;
            }
        }

        // Category
        for (int i = 0; i < llCandidateFiltersCategory.getChildCount(); i++) {
            View v = llCandidateFiltersCategory.getChildAt(i);
            if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                if (cb.getText().toString().equals(filterName)) {
                    cb.setChecked(false);
                    return;
                }
            }
        }

        // ✅ Unselect Experience (RadioGroup)
//            for (int i = 0; i < rgCandidateFiltersExperience.getChildCount(); i++) {
//                View v = rgCandidateFiltersExperience.getChildAt(i);
//                if (v instanceof RadioButton) {
//                    RadioButton radioButton = (RadioButton) v;
//                    if (radioButton.getText().toString().equals(filterName)) {
//                        radioButton.setChecked(false);
//                        break; // since only one can be selected
//                    }
//                }
//            }

        int selectedExpRadioId = rgCandidateFiltersExperience.getCheckedRadioButtonId();
        RadioButton expRadioButton = rgCandidateFiltersExperience.findViewById(selectedExpRadioId);
        if (expRadioButton != null) {
            rgCandidateFiltersExperience.clearCheck();
        }


        // ✅ Unselect Freshness (RadioGroup)
        int selectedFreshRadioId = rgCandidateFiltersFreshness.getCheckedRadioButtonId();
        RadioButton freshRadioButton = rgCandidateFiltersFreshness.findViewById(selectedFreshRadioId);
        if (freshRadioButton != null) {
            rgCandidateFiltersFreshness.clearCheck();
        }


//            for (int i = 0; i < rgCandidateFiltersFreshness.getChildCount(); i++) {
//                View v = rgCandidateFiltersFreshness.getChildAt(i);
//                if (v instanceof RadioButton) {
//                    RadioButton radioButton = (RadioButton) v;
//                    if (radioButton.getText().toString().equals(filterName)) {
//                        radioButton.setChecked(false);
//                        break; // since only one can be selected
//                    }
//                }
//            }
    }

    private void addOrRemoveSelectedFilterChip(String filterName, boolean isSelected) {

        if (isSelected) {

            // Avoid duplicates
            for (int i = 0; i < chipGroupSelectedFilters.getChildCount(); i++) {
                Chip c = (Chip) chipGroupSelectedFilters.getChildAt(i);
                if (c.getText().toString().equals(filterName)) return;
            }

            Chip chip = new Chip(getContext());
            chip.setText(filterName);
            chip.setCloseIconVisible(true);
            chip.setCheckable(false);
            chip.setClickable(false);
            chip.setChipBackgroundColorResource(R.color.lightPurple);
            chip.setTextColor(getResources().getColor(R.color.black));

            chip.setOnCloseIconClickListener(v -> {
                chipGroupSelectedFilters.removeView(chip);
                chipGroupSelectedFilters.setLayoutTransition(new LayoutTransition());
                unselectFilterFromSections(filterName); // uncheck in section
            });

            chipGroupSelectedFilters.setLayoutTransition(new LayoutTransition());
            chipGroupSelectedFilters.addView(chip, 0);

        } else {
            for (int i = 0; i < chipGroupSelectedFilters.getChildCount(); i++) {
                Chip c = (Chip) chipGroupSelectedFilters.getChildAt(i);
                if (c.getText().toString().equals(filterName)) {
                    chipGroupSelectedFilters.removeView(c);
                    break;
                }
            }
        }
    }

    // when user clicks "Apply Filters"
    public void applyFilters(boolean shouldDismissSheet) {

        selectedJobType = getSelectedFromSection(llCandidateFiltersJobType);
        selectedSkills = getSelectedFromSection(llCandidateFiltersSkills);
        selectedLocation = getSelectedFromSection(llCandidateFiltersLocation);
        selectedCategory = getSelectedFromSection(llCandidateFiltersCategory);

        selectedExperience = getSelectedFromRadioSections(rgCandidateFiltersExperience);
        selectedFreshness = getSelectedFromRadioSections(rgCandidateFiltersFreshness);

        selectedMinSalary = 0.0;
        selectedMaxSalary = 10000.0;

//        CandidateJobFilterState state = CandidateJobFilterState.getInstance();

        state.selectedCategories.clear();
        state.selectedCategories.addAll(selectedCategory);

        state.selectedJobTypes.clear();
        state.selectedJobTypes.addAll(selectedJobType);

        state.selectedSkills.clear();
        state.selectedSkills.addAll(selectedSkills);

//        makeText(getContext(), ""+selectedExperience, LENGTH_SHORT).show();

        state.selectedExperience = selectedExperience;
        state.selectedLocation = selectedLocation;
        state.minSalary = selectedMinSalary;
        state.maxSalary = selectedMaxSalary;
        state.freshness = selectedFreshness;

        if (shouldDismissSheet) {  // only mark true if user actually clicked "Apply"
            isFilterApplied = true;
        }

        CandidateJobFilterModel filters = new CandidateJobFilterModel(
                selectedJobType, selectedCategory, selectedSkills, selectedExperience, selectedLocation, selectedMinSalary, selectedMaxSalary, selectedFreshness
        );


        if (listener != null) {
            listener.onFilterApplied(filters); // send back to fragment
        }


        if (shouldDismissSheet) {
            dismiss();
        }
    }

    private String getSelectedFromRadioSections(RadioGroup radioGroup) {
        String selectedItemText = null;

        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = radioGroup.findViewById(selectedId);

        if (radioButton != null) {
            selectedItemText = radioButton.getText().toString();
        } else {
            selectedItemText = "";
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

//    private String getSelectedFromRadioSections(LinearLayout parentLayout) {
//        String selectedItemText = "";
//        int childCount = parentLayout.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = parentLayout.getChildAt(i);
//            if (child instanceof RadioGroup) {
//                RadioGroup radioGroup = (RadioGroup) child;
//                int selectedRadioId = radioGroup.getCheckedRadioButtonId();
//                RadioButton radioButton = radioGroup.findViewById(selectedRadioId);
//                if (radioButton != null) {
//                    selectedItemText = radioButton.getText().toString();
//                }
//                Toast.makeText(getContext(), ""+selectedRadioId, Toast.LENGTH_SHORT).show();
//
//            }
//        }
//        return selectedItemText;
//    }

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

    public interface OnFilterAppliedListener {
        void onFilterApplied(CandidateJobFilterModel filterModel);
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

