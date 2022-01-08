package com.example.note200.ui.details;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.note200.database.repository.NoteRepository;
import com.example.note200.database.repository.NoteRepositoryImpl;
import com.example.note200.database.source.Note;

import java.util.ArrayList;
import java.util.List;


public class DescriptionViewModel extends AndroidViewModel {
    private final NoteRepository repository;

    public MutableLiveData<Note> note = new MutableLiveData<>();


    public DescriptionViewModel(Application application) {
        super(application);
        repository = new NoteRepositoryImpl(application);
    }

    public void getData(long id) {
        Note currentNote = repository.getById(id);
        note.postValue(currentNote);
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }
}
