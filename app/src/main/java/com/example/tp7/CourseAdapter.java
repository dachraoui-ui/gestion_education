package com.example.tp7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> courseList;
    private Context context;
    private OnCourseActionListener listener;

    public interface OnCourseActionListener {
        void onEdit(Course course, int position);
        void onDelete(Course course, int position);
    }

    public CourseAdapter(List<Course> courseList, Context context, OnCourseActionListener listener) {
        this.courseList = courseList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);

        holder.tvCourseName.setText(course.getName());
        holder.etHours.setText(String.valueOf(course.getHours()));
        // Configure spinner for teachers
        ArrayAdapter<String> teacherAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, course.getTeacherNames());
        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spTeacher.setAdapter(teacherAdapter);
        holder.spTeacher.setSelection(course.getTeacherIndex());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(course, position));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(course, position));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName;
        EditText etHours;
        Spinner spTeacher;
        Button btnEdit, btnDelete;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tv_course_name);
            etHours = itemView.findViewById(R.id.et_hours);
            spTeacher = itemView.findViewById(R.id.sp_teacher);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
