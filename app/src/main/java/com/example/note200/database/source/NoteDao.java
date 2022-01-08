package com.example.note200.database.source;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note ORDER BY `current_date` DESC")
    List<Note> getAll();

    @Query("SELECT * FROM note ORDER BY `current_date` DESC")
    LiveData<List<Note>> getAllLiveData();

    @Query("SELECT * FROM note WHERE id = :id")
    Note getById(long id);

    @Query("SELECT * FROM note ORDER BY `is_favorite` DESC")
    List<Note> getNoteByFavorite();

    @Insert(onConflict = REPLACE)
    void insert(Note note);

    @Update(onConflict = REPLACE)
    void update(Note note);

    @Delete
    void delete(Note note);
}
