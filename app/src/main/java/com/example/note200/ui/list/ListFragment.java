package com.example.note200.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.note200.R;
import com.example.note200.database.source.Note;
import com.example.note200.databinding.FragmentListBinding;
import com.example.note200.ui.details.DescriptionFragment;

public class ListFragment extends Fragment {
    private NoteAdapter adapter;
    private FragmentListBinding binding;
    private ListViewModel viewModel;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_list, container, false);
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new NoteAdapter();
        adapter.setListener(note -> {
            openNote(note);
        });

        binding.notesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.notesRecyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        viewModel.allNotes.observe(getViewLifecycleOwner(), notes -> {
            adapter.setNotes(notes);

        });
        viewModel.getData();
    }

    private void openNote(Note note) {
        long currentId = note.getId();
//        if(currentId != 0) {
//            Fragment descriptionFragment = DescriptionFragment.newInstance(currentId);
//
//        }
    }
}
