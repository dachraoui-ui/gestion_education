package com.example.tp7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class homeFragment extends Fragment {

    private TeacherAdapter adapter;
    private List<Teacher> teachers;

    public static homeFragment newInstance(List<Teacher> teachers) {
        homeFragment fragment = new homeFragment();
        fragment.teachers = teachers != null ? teachers : new ArrayList<>();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Permet au fragment de gérer le menu d'options
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.mRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new TeacherAdapter(teachers);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_enseig, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.a_z) {
            adapter.sortByName();
            return true;
        } else if (item.getItemId() == R.id.z_a) {
            adapter.reverseByName();
            return true;
        } else if (item.getItemId() == R.id.add) {
            showAddingDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddingDialog() {
        MyDialogFragment dialog = new MyDialogFragment(new MyDialogFragment.OnTeacherAddedListener() {
            @Override
            public void onTeacherAdded(Teacher teacher) {
                adapter.addTeacher(teacher);
            }
        });
        dialog.show(getParentFragmentManager(), "my_dialog");
    }
}
