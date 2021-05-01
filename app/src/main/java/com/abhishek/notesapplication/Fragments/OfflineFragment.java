package com.abhishek.notesapplication.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.notesapplication.Adapters.NotesSQLITEAdapter;
import com.abhishek.notesapplication.DatabaseHelper;
import com.abhishek.notesapplication.Fragments.BaseFragment.BaseFragment;
import com.abhishek.notesapplication.R;

import java.util.ArrayList;

public class OfflineFragment extends BaseFragment {
    Context context;
    String authId;
    RecyclerView recyclerView;
    ImageView imageNotFound;

    public OfflineFragment(Context context, String authId) {
        this.context = context;
        this.authId = authId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        recyclerView = view.findViewById(R.id.recOffline);
        imageNotFound = view.findViewById(R.id.notFoundImage);
        setData(view);
        return view;
    }

    private void setData(View view) {
        DatabaseHelper helper = new DatabaseHelper(getContext());

        ArrayList<String> title, data;
        title = new ArrayList<>();
        data = new ArrayList<>();

        Cursor cursor = helper.readAllData();

        if(cursor.getCount() == 0){
            recyclerView.setVisibility(View.GONE);
            imageNotFound.setVisibility(View.VISIBLE);
        }else{
            while(cursor.moveToNext()){
                title.add(cursor.getString(0));
                data.add(cursor.getString(1));
            }

            NotesSQLITEAdapter adapter = new NotesSQLITEAdapter(getContext(), title, data);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

    }


}
