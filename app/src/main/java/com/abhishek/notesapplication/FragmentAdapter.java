package com.abhishek.notesapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.abhishek.notesapplication.Fragments.OfflineFragment;
import com.abhishek.notesapplication.Fragments.OnlineFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public FragmentAdapter(@NonNull FragmentManager fm, Context context, int totalTabs) {
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
