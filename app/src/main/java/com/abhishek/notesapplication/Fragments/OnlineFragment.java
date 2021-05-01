package com.abhishek.notesapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.notesapplication.Adapters.NotesFirebaseAdapter;
import com.abhishek.notesapplication.Fragments.BaseFragment.BaseFragment;
import com.abhishek.notesapplication.ItemNotes;
import com.abhishek.notesapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnlineFragment extends BaseFragment {
    Context context;
    String authId;
    RecyclerView recyclerView;
    ImageView imageNotFound;

    public OnlineFragment(Context context, String authId) {
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

    public void setData(View view){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("authId");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ItemNotes> arrayList = new ArrayList<>();
                if(snapshot.getChildrenCount() == 0){
                    //no notes found
                    recyclerView.setVisibility(View.GONE);
                    imageNotFound.setVisibility(View.VISIBLE);
                    return;
                }
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    arrayList.add(dataSnapshot.getValue(ItemNotes.class));
                }
                NotesFirebaseAdapter adapter = new NotesFirebaseAdapter(getContext(), arrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
