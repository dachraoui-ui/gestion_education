package com.example.tp7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListeCoursFragment extends Fragment {

    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private List<Course> courseList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste_cours, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        DatabaseHelper db = new DatabaseHelper(getContext());
        courseList = db.getAllCourses();

        adapter = new CourseAdapter(courseList, getContext(), new CourseAdapter.OnCourseActionListener() {
            @Override
            public void onEdit(Course course, int position) {

                course.setHours(Float.parseFloat(course.getHours()));
                db.updateCourse(course);
                Toast.makeText(getContext(), "Course updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDelete(Course course, int position) {
                // Handle delete functionality
                db.deleteCourse(course.getId());
                courseList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(getContext(), "Course deleted", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
        return view;
    }
}
