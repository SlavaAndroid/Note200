package com.example.note200.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.note200.database.source.AppDatabase;
import com.example.note200.database.source.Note;
import com.example.note200.database.source.NoteDao;

import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {

    private final NoteDao local;
    private final LiveData<List<Note>> allNotesLiveData;
    private final List<Note> allNotes;

    private NoteDao currentId;

    public NoteRepositoryImpl(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        local = db.noteDao();
        allNotes = local.getAll();
        allNotesLiveData = local.getAllLiveData();
    }

    @Override
    public List<Note> getAllNotes() {
        return allNotes;
    }

    @Override
    public LiveData<List<Note>> getAllLiveData() {
        return allNotesLiveData;
    }

    @Override
    public Note getById(long id) {
        return local.getById(id);
    }

    @Override
    public void insert(Note note) {
        local.insert(note);
    }

    @Override
    public void update(Note note) {
        local.insert(note);
    }

    @Override
    public void delete(Note note) {
        local.insert(note);
    }

}
