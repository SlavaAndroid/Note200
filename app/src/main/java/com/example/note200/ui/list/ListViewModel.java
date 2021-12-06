package com.example.note200.ui.list;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.note200.database.repository.NoteRepository;
import com.example.note200.database.repository.NoteRepositoryImpl;
import com.example.note200.database.source.Note;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends ViewModel {

    private final NoteRepository repository;

    public final MutableLiveData<List<Note>> allNotes = new MutableLiveData<>(new ArrayList<>());

    public ListViewModel(Application application) {
        super();
        repository = new NoteRepositoryImpl(application);
    }

    public void getData() {
        List<Note> list = repository.getAllNotes();
        allNotes.postValue(list);
    }
}
