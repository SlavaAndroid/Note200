package com.example.note200.ui.list;

import static com.example.note200.ui.details.DescriptionFragment.EMPTY_ID;


import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.note200.R;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requireActivity().setTitle("Заметки");

        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new NoteAdapter();
        adapter.setListener(note -> {
            openNote((int) note.getId());
        });


        binding.notesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.notesRecyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        viewModel.allNotes.observe(getViewLifecycleOwner(), notes -> {
            adapter.setNotes(notes);
        });

        binding.addNoteButton.setOnClickListener(v -> openNote(EMPTY_ID));

        viewModel.getDataByDate();

        setHasOptionsMenu(true);
    }

    private void openNote(int id) {
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(id);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, descriptionFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        descriptionFragment.setListener(() -> {
            viewModel.getDataByDate();
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.list_fragment, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);
                return true;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "";
                searchView.setQuery(query, false);
            }
        });
    }

}
