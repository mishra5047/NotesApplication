package com.abhishek.notesapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abhishek.notesapplication.DatabaseHelper;
import com.abhishek.notesapplication.Fragments.BaseFragment.BaseFragment;
import com.abhishek.notesapplication.ItemNotes;
import com.abhishek.notesapplication.Notes_Fragment;
import com.abhishek.notesapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class AddNotesFragment extends BaseFragment {
    MaterialEditText noteTitle, noteDescription;
    RadioGroup radioGroup;
    TextView addButton;

    String authId;

    public AddNotesFragment(String authId) {
        this.authId = authId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_notes_fragment, container,false);

        noteTitle = view.findViewById(R.id.notesTitle);
        noteDescription = view.findViewById(R.id.notesData);
        radioGroup = view.findViewById(R.id.radioSave);
        addButton = view.findViewById(R.id.addNote);

        setViews(view);
        return view;
    }

    void setViews(View view){
        addButton.setOnClickListener(v -> {

            if(!checkEditText(noteTitle) || !checkEditText(noteDescription) ){

            }else{
                String noteName = noteTitle.getText().toString();
                String noteDetail = noteDescription.getText().toString();

                int selectedId= radioGroup.getCheckedRadioButtonId();
                RadioButton button =(RadioButton)view.findViewById(selectedId);
                if(button == null){
                    Toast.makeText(getContext(), "Select a category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(button.getText().toString().contains("Local")){
                //    Toast.makeText(getContext(), selectedId + "Local", Toast.LENGTH_SHORT).show();
                    addToSqLite(noteName, noteDetail);
                }else{
                //    Toast.makeText(getContext(), selectedId + "Online", Toast.LENGTH_SHORT).show();
                    addToFirebase(noteName, noteDetail);
                }
                Toast.makeText(getContext(), "Note Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("choice", "choice");
                startActivity(intent);
                //loadFragment(new Notes_Fragment(authId));
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    boolean checkEditText(EditText editText){

        if(editText.getText().toString().isEmpty()){
            editText.setError("Can't be empty");
            return false;
        }

        return true;
    }

    void addToSqLite(String title, String data){
        final DatabaseHelper myDb = new DatabaseHelper(getContext());
        myDb.addItem(title, data);
    }

    void addToFirebase(String title, String data){
        ItemNotes notes = new ItemNotes(title, data);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(authId);
        reference.child(title).setValue(notes);
    }
}
