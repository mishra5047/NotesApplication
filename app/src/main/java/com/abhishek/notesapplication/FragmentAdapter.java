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
    String authId;

    public FragmentAdapter(@NonNull androidx.fragment.app.FragmentManager fm,Context context, int totalTabs, String authId) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
        this.authId = authId;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new OfflineFragment(context, authId);

            case 1 :
                return new OnlineFragment(context,authId);

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
