package com.abhishek.notesapplication.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.abhishek.notesapplication.DatabaseHelper;
import com.abhishek.notesapplication.Fragments.BaseFragment.BaseFragment;
import com.abhishek.notesapplication.ItemNotes;
import com.abhishek.notesapplication.LoginFragment;
import com.abhishek.notesapplication.Notes_Fragment;
import com.abhishek.notesapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditNotes extends BaseFragment {

    EditText editTitleNote;
    EditText editDataNote;
    String titleNote, dataNote, authId;
    TextView editNote, deleteNote;
    int type;
    DatabaseHelper myDb;
    DatabaseReference reference;
    //0 -> offline, 1 -> online

    public EditNotes(String titleNote, String dataNote, int type, String authId) {
        this.titleNote = titleNote;
        this.type = type;
        this.dataNote = dataNote;
        this.authId = authId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_notes, container, false);
        editTitleNote = view.findViewById(R.id.notesTitle);
        editDataNote = view.findViewById(R.id.notesData);
        editNote = view.findViewById(R.id.editNote);
        deleteNote = view.findViewById(R.id.deleteNote);
        myDb = new DatabaseHelper(getContext());
        reference = FirebaseDatabase.getInstance().getReference(authId);
        setData(view);

        return view;
    }

    void setData(View view){
        editTitleNote.setText(titleNote);
        editDataNote.setText(dataNote);
        deleteNote.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

            alertDialog.setTitle("Are you sure, you want to Delete this Note ?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //delete depending on online // offline note

                    if(type == 0){
                        myDb.deleteOneRow(titleNote);
                    }else {
                        //delete from firebase
                        Toast.makeText(getContext(), "deleting from firebase", Toast.LENGTH_SHORT).show();
                        reference.child(titleNote).removeValue();
                    }
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("choice", "choice");
                    startActivity(intent);
                }
            });

            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.create();
            alertDialog.show();
        });

        editNote.setOnClickListener(v -> {
            if(!checkEditText(editTitleNote) || !checkEditText(editDataNote)){
                return;
            }
            String title = editTitleNote.getText().toString();
            String data = editDataNote.getText().toString();
            Toast.makeText(getContext(), authId, Toast.LENGTH_SHORT).show();
            if(type == 0){
                myDb.updateNote(titleNote,title, data);
            }else if(type == 1){
                ItemNotes notes = new ItemNotes(title, data);
                reference.child(titleNote).removeValue();
                reference.child(title).setValue(notes);
            }
            Toast.makeText(getContext(), "Note Updated", Toast.LENGTH_SHORT).show();
            //loadFragment(new Notes_Fragment(authId));
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.putExtra("choice", "choice");
            startActivity(intent);

        });

    }
    boolean checkEditText(EditText editText){

        if(editText.getText().toString().isEmpty()){
            editText.setError("Can't be empty");
            return false;
        }

        return true;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
}
