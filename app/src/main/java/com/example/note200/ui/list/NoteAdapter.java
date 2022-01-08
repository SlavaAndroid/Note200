package com.example.note200.ui.list;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note200.database.source.Note;
import com.example.note200.databinding.ListItemNoteBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> implements Filterable {
    private List<Note> searchNotes;
    private List<Note> notes = new ArrayList<>();
    public NoteClickListener listener;


    public void setNotes(List<Note> list) {
        this.notes = list;
        this.searchNotes = notes;
        notifyDataSetChanged();
    }

    public void setListener(NoteClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemNoteBinding binding = ListItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bind(searchNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return searchNotes.size();
    }


    public class NoteHolder extends RecyclerView.ViewHolder {
        ListItemNoteBinding binding;

        public NoteHolder (ListItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(Note note) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(note.getCurrentDate());

            Date date = calendar.getTime();

            String pattern = "dd MMM HH:mm";
            SimpleDateFormat simpleDateFormat = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                simpleDateFormat = new SimpleDateFormat(pattern, new Locale("ukr", "UKR"));
            }
            assert simpleDateFormat != null;
            String dateString = simpleDateFormat.format(date);

            binding.noteTitle.setText(note.getTitle());
            binding.noteDate.setText(dateString);
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                searchNotes = (List<Note>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Note> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = notes;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<Note> getFilteredResults(String constraint) {
        List<Note> results = new ArrayList<>();

        for (Note item : notes) {
            if (item.getTitle().toLowerCase().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }
}
