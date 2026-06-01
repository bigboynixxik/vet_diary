package com.example.vetdiary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insert(HistoryRecord record);

    @Query("SELECT * FROM history_records ORDER BY id DESC")
    List<HistoryRecord> getAllRecords();

    @Update
    void update(HistoryRecord record);

    @Query("SELECT * FROM history_records WHERE id = :recordId LIMIT 1")
    HistoryRecord getRecordById(int recordId);

    @Delete
    void delete(HistoryRecord record);
}