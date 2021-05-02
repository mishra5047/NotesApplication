package com.abhishek.notesapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.notesapplication.Fragments.EditNotes;
import com.abhishek.notesapplication.ItemNotes;
import com.abhishek.notesapplication.R;

import java.util.ArrayList;

public class NotesFirebaseAdapter extends RecyclerView.Adapter<NotesFirebaseAdapter.ViewHolder>{
    static Context context;
    ArrayList<ItemNotes> notes;
    static String authId;

    public NotesFirebaseAdapter(Context context, ArrayList<ItemNotes> notes, String authId) {
        this.context = context;
        this.notes = notes;
        this.authId = authId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemNotes note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.data.setText(note.getData());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                loadFragment(new EditNotes(title.getText().toString(), data.getText().toString(), 1,authId));
            });
            title = itemView.findViewById(R.id.titleNotes);
            data = itemView.findViewById(R.id.dataNotes);
        }
    }
    static private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            ((FragmentActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
