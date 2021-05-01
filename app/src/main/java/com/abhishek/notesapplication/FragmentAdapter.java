package com.abhishek.notesapplication;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.abhishek.notesapplication.Fragments.OfflineFragment;
import com.abhishek.notesapplication.Fragments.OnlineFragment;
import com.google.android.material.tabs.TabLayout;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;


    public FragmentAdapter(@NonNull androidx.fragment.app.FragmentManager fm,Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new OfflineFragment(context, "");

            case 1 :
                Toast.makeText(context, "Second CLicked", Toast.LENGTH_SHORT).show();
                return new OnlineFragment(context,"");

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
