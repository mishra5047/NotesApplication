package com.abhishek.notesapplication.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.abhishek.notesapplication.Fragments.BaseFragment.BaseFragment;
import com.abhishek.notesapplication.LoginFragment;
import com.abhishek.notesapplication.Notes_Fragment;
import com.abhishek.notesapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String authId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        String res = "";
        if(getIntent().hasExtra("choice")){
            res = getIntent().getStringExtra("choice");
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("NotesData", 0); // 0 - for private mode
        authId = pref.getString("userId", "");
        loadFragment(new LoginFragment(res));
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        tellFragment();
    }

    private void tellFragment() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        //Toast.makeText(getApplicationContext(), "size is = " + fragmentList.size(), Toast.LENGTH_SHORT).show();
        for(Fragment f : fragmentList){
            if(f != null && f instanceof BaseFragment) {
                //Toast.makeText(getApplicationContext(), "name = " + f, Toast.LENGTH_SHORT).show();
                String name = f.toString();
                if(name.contains("Add")){
                    loadFragment(new Notes_Fragment(authId));
                }
                else if(name.contains("Offline") || name.contains("Online")){
                    //Toast.makeText(getApplicationContext(), "found", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                    alertDialog.setTitle("Are you sure, you want to Log Out ?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //delete depending on online // offline note
                            FirebaseAuth.getInstance().signOut();
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("NotesData", 0); // 0 - for private mode
                            SharedPreferences.Editor editor = pref.edit();
                            editor.remove("authId");
                            editor.apply();
                            editor.commit();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    });

                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("choice", "choice");
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    alertDialog.create();
                    alertDialog.show();

                }
                else{
                    ((BaseFragment) f).onBackPressed();
                }
            }
        }
    }
}
