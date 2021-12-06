package com.example.note200.database.repository;

import androidx.lifecycle.LiveData;

import com.example.note200.database.source.Note;

import java.util.List;

public interface NoteRepository {

    List<Note> getAllNotes();

    LiveData<List<Note>> getAllLiveData();

    Note getById(long id);

    void insert(Note note);

    void update(Note note);

    void delete(Note note);
}

