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
import com.abhishek.notesapplication.R;

import java.util.ArrayList;

public class NotesSQLITEAdapter extends RecyclerView.Adapter<NotesSQLITEAdapter.ViewHolder> {
    static Context context;
    ArrayList notes_title, notes_details;
    static String authId;

    public NotesSQLITEAdapter(Context context, ArrayList notes_title, ArrayList notes_details, String authId) {
        this.context = context;
        this.notes_title = notes_title;
        this.notes_details = notes_details;
        this.authId = authId;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_note, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(notes_title.get(position).toString());
        holder.data.setText(notes_details.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return notes_title.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                loadFragment(new EditNotes(title.getText().toString(), data.getText().toString(), 0, authId));
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
