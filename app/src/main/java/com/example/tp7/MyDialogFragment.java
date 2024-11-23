package com.example.tp7;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

public class MyDialogFragment extends DialogFragment {

    public interface OnTeacherAddedListener {
        void onTeacherAdded(Teacher teacher);
    }

    private OnTeacherAddedListener listener;

    public MyDialogFragment(OnTeacherAddedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        final View dialogView = getLayoutInflater().inflate(R.layout.layout_add_dialog, null, false);
        builder.setView(dialogView);

        final EditText nom = dialogView.findViewById(R.id.edit_text);
        final EditText email = dialogView.findViewById(R.id.email);

        builder.setTitle("Ajouter Nouveau Enseignant")
                .setMessage("Donner nom enseignant")
                .setPositiveButton("Valider", (dialog, whichButton) -> {
                    String nomEnseignant = nom.getText().toString();
                    String emailEnseignant = email.getText().toString();

                    // Creating Teacher object with name and email only (id will be auto-incremented)
                    Teacher newTeacher = new Teacher(nomEnseignant, emailEnseignant);

                    // Add teacher to database
                    if (listener != null) {
                        listener.onTeacherAdded(newTeacher);
                    }
                })
                .setNegativeButton("Annuler", (dialog, whichButton) -> {
                    // Nothing to do
                });

        return builder.create();
    }
}

