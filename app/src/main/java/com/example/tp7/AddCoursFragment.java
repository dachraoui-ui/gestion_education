package com.example.tp7;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class AddCoursFragment extends Fragment {

    private EditText courseName, courseHours;
    private RadioGroup typeRadioGroup;
    private Spinner teacherSpinner;
    private Button addCourseButton, listCoursesButton;

    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_cours, container, false);

        // Initialisation des vues
        courseName = view.findViewById(R.id.course_name);
        courseHours = view.findViewById(R.id.course_hours);
        typeRadioGroup = view.findViewById(R.id.course_type_group);
        teacherSpinner = view.findViewById(R.id.teacher_spinner);
        addCourseButton = view.findViewById(R.id.add_course_button);
        listCoursesButton = view.findViewById(R.id.list_courses_button);


        databaseHelper = new DatabaseHelper(getContext());


        loadTeachersIntoSpinner();


        addCourseButton.setOnClickListener(v -> {
            if (validateInputs()) {
                String name = courseName.getText().toString();
                float hours = Float.parseFloat(courseHours.getText().toString());
                String type = getSelectedCourseType();
                int teacherId = (int) teacherSpinner.getSelectedItemId();

                Course course = new Course(0, name, hours, type, teacherId);
                long result = databaseHelper.addCourse(course);

                if (result != -1) {
                    Toast.makeText(getContext(), "Cours ajouté avec succès !", Toast.LENGTH_SHORT).show();
                    clearInputs();
                } else {
                    Toast.makeText(getContext(), "Erreur lors de l'ajout du cours.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bouton Voir Liste des cours
        listCoursesButton.setOnClickListener(v -> {
            // Naviguer vers ListeCoursFragment
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ListeCoursFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void loadTeachersIntoSpinner() {
        List<Teacher> teachers = databaseHelper.getAllTeachers();
        List<String> teacherNames = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherNames.add(teacher.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, teacherNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacherSpinner.setAdapter(adapter);
    }

    private boolean validateInputs() {
        if (TextUtils.isEmpty(courseName.getText())) {
            Toast.makeText(getContext(), "Veuillez entrer le nom du cours.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(courseHours.getText())) {
            Toast.makeText(getContext(), "Veuillez entrer le nombre d'heures.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (typeRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Veuillez sélectionner un type de cours.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String getSelectedCourseType() {
        int selectedId = typeRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = typeRadioGroup.findViewById(selectedId);
        return selectedRadioButton.getText().toString();
    }

    private void clearInputs() {
        courseName.setText("");
        courseHours.setText("");
        typeRadioGroup.clearCheck();
        teacherSpinner.setSelection(0);
    }
}
