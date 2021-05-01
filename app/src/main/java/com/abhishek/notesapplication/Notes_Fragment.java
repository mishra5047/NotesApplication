package com.abhishek.notesapplication;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.notesapplication.Fragments.AddNotesFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class Notes_Fragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    FloatingActionButton floatingActionButton;
    /*
        2 tabs application
        1. Local Notes.
        2. Online Notes.
        3. A detail screen with option to edit / delete the note
        4. Add note screen
         */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        floatingActionButton = view.findViewById(R.id.addNotes);

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

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AddNotesFragment());
            }
        });

        return view;
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
}