
package com.abhishek.notesapplication;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.abhishek.notesapplication.Fragments.AddNotesFragment;
import com.abhishek.notesapplication.Fragments.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Notes_Fragment extends androidx.fragment.app.Fragment {
    String authId;
    TabLayout tabLayout;
    ViewPager viewPager;


    FloatingActionButton floatingActionButton, logOut;
    /*
        2 tabs application
        1. Local Notes.
        2. Online Notes.
        3. A detail screen with option to edit / delete the note
        4. Add note screen
         */

    public Notes_Fragment(String authId) {
        this.authId = authId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        //Toast.makeText(getContext(), "id is = " + authId, Toast.LENGTH_SHORT).show();
        floatingActionButton = view.findViewById(R.id.addNotes);
        logOut = view.findViewById(R.id.logOut);

        logOut.setOnClickListener( v -> {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Are you sure, you want to Log Out ?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete depending on online // offline note
                FirebaseAuth.getInstance().signOut();
                SharedPreferences pref = getContext().getSharedPreferences("NotesData", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("authId");
                editor.apply();
                editor.commit();
                System.exit(0);
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

        tabLayout= view.findViewById(R.id.tabLayout);
        viewPager= view.findViewById(R.id.fragmentNotes);

        tabLayout.addTab(tabLayout.newTab().setText("Offline"));
        tabLayout.addTab(tabLayout.newTab().setText("Online"));

        TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tab_try, null);
        tabOne.setText("Device");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tab_try, null);
        tabTwo.setText("Web");
        tabLayout.getTabAt(1).setCustomView(tabTwo);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

        viewPager.setAdapter(new FragmentAdapter(getFragmentManager(), getContext(),2, authId));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AddNotesFragment(authId));
            }
        });

        return view;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}