package com.example.note200.ui.list;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note200.database.source.Note;
import com.example.note200.databinding.ListItemNoteBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    public NoteClickListener listener;

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void setListener(NoteClickListener listener) {
        this.listener = listener;
    }

    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemNoteBinding binding = ListItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public class NoteHolder extends RecyclerView.ViewHolder {
        ListItemNoteBinding binding;

        public NoteHolder (ListItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(Note note) {

            String pattern = "MMM dd, yyyy, HH:mm";
            SimpleDateFormat simpleDateFormat = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                simpleDateFormat = new SimpleDateFormat(pattern, new Locale("ukr", "UKR"));
            }
            assert simpleDateFormat != null;
            String date = simpleDateFormat.format(new Date());

            binding.noteTitle.setText(note.getTitle());
            binding.noteDate.setText(note.getCurrentDate());
            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onNoteClick(note);
                }
            });
        }
    }

    public interface NoteClickListener {
        void onNoteClick(Note note);
    }
}
