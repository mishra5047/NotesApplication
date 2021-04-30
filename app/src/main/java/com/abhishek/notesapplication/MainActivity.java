package com.abhishek.notesapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends Activity {

    TabLayout tabLayout;
    ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_add,
            R.drawable.ic_add,
    };
    /*
        2 tabs application
        1. Local Notes.
        2. Online Notes.
        3. A detail screen with option to edit / delete the note
        4. Add note screen
         */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.fragmentNotes);

        tabLayout.addTab(tabLayout.newTab().setText("Offline"));
        tabLayout.addTab(tabLayout.newTab().setText("Online"));

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tab_try, null);
        tabOne.setText("Device");
        tabLayout.getTabAt(0).setCustomView(tabOne);


        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tab_try, null);
        tabTwo.setText("Web");
        tabLayout.getTabAt(1).setCustomView(tabTwo);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}