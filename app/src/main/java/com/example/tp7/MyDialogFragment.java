package com.example.tp7;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
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
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String nomEnseignant = nom.getText().toString();
                        String emailEnseignant = email.getText().toString();

                        Teacher newTeacher = new Teacher(nomEnseignant, emailEnseignant);
                        if (listener != null) {
                            listener.onTeacherAdded(newTeacher);
                        }
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Ne rien faire
                    }
                });

        return builder.create();
    }
}
