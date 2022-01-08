package com.example.note200.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.note200.R;
import com.example.note200.database.source.Note;
import com.example.note200.databinding.FragmentItemBinding;
import com.example.note200.ui.list.ListViewModel;
import com.example.note200.ui.main.MainActivity;

import java.util.Objects;

public class DescriptionFragment extends Fragment {
    public static final int EMPTY_ID = -1;
    private static final String ARG_NOTE_ID = "note_id";

    private FragmentItemBinding binding;
    private DescriptionViewModel viewModel;
    private long id;
    private Note currentNote = new Note();

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NoteListener listener;


    public static DescriptionFragment newInstance(long noteId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE_ID, noteId);
        DescriptionFragment fragment = new DescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(DescriptionViewModel.class);

        if (getArguments() != null) {
            id = getArguments().getLong(ARG_NOTE_ID, EMPTY_ID);
            if (id == EMPTY_ID) {
                currentNote = new Note();
            } else {
                viewModel.getData(id);
            }
        }
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).setTitle("Заметки");
        requireActivity().setTitle("");

        binding = FragmentItemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextTitle = view.findViewById(R.id.description_edit_title);
        editTextDescription = view.findViewById(R.id.description_edit_text);

        viewModel.note.observe(getViewLifecycleOwner(), note -> {
            if (note != null) {
                currentNote = note;
                binding.descriptionEditTitle.setText(note.getTitle());
                binding.descriptionEditText.setText(note.getDescription());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.description_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save_note:
                saveNote();
                return true;
            case R.id.delete_note:
//                dlg2.show(getChildFragmentManager(), "dlg2");
                DialogDelete1 dlg1 = DialogDelete1.newInstance();
                dlg1.show(getChildFragmentManager(), "dlg1");
                dlg1.setListener(new DialogDelete1.DeleteYesButtonListener() {
                    @Override
                    public void delete() {
                        deleteNote();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setListener(NoteListener listener) {
        this.listener = listener;
    }

    private void deleteNote() {
        viewModel.delete(currentNote);
        if (listener != null)
            listener.update();

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String[] desc = description.split(" ");

        if (TextUtils.isEmpty(title)) {
            title = desc[0];
        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(getActivity(), "Add your description", Toast.LENGTH_LONG).show();
        }

        currentNote.setTitle(title);
        currentNote.setDescription(description);
        currentNote.setCurrentDate(System.currentTimeMillis());

        viewModel.insert(currentNote);

        if (listener != null)
            listener.update();

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

    public interface NoteListener {
        void update();
    }
}
