package com.example.tp7;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp7.databinding.ItemTeacherBinding;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {

    private final List<Teacher> teacherList;

    public TeacherAdapter(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTeacherBinding binding = ItemTeacherBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TeacherViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);


        holder.binding.teacherName.setText(teacher.getName());
        holder.binding.teacherEmail.setText(teacher.getEmail());


        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTeacher(holder.getAdapterPosition()); // Remove teacher at the current position
            }
        });
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }


    public void sortByName() {
        Collections.sort(teacherList, new Comparator<Teacher>() {
            @Override
            public int compare(Teacher t1, Teacher t2) {
                return t1.getName().compareToIgnoreCase(t2.getName());
            }
        });
        notifyDataSetChanged();
    }


    public void reverseByName() {
        Collections.sort(teacherList, new Comparator<Teacher>() {
            @Override
            public int compare(Teacher t1, Teacher t2) {
                return t2.getName().compareToIgnoreCase(t1.getName());
            }
        });
        notifyDataSetChanged();
    }


    public void addTeacher(Teacher teacher) {
        teacherList.add(teacher);
        notifyItemInserted(teacherList.size() - 1); // Notify adapter of the new item
    }

    // Remove a teacher from the list at the specified position
    public void removeTeacher(int position) {
        if (position >= 0 && position < teacherList.size()) {
            teacherList.remove(position);
            notifyItemRemoved(position); // Notify adapter of item removal
            notifyItemRangeChanged(position, teacherList.size()); // Update the remaining list
        }
    }

    // ViewHolder class to hold item_teacher.xml views
    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        ItemTeacherBinding binding;

        public TeacherViewHolder(ItemTeacherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
