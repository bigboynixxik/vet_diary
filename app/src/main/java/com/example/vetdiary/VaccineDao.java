package com.example.vetdiary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VaccineDao {
    @Insert
    void insert(VaccineRecord record);

    @Query("SELECT * FROM vaccine_records ORDER BY id DESC")
    List<VaccineRecord> getAllRecords();

    @Query("SELECT * FROM vaccine_records WHERE id = :recordId LIMIT 1")
    VaccineRecord getRecordById(int recordId);

    @Update
    void update(VaccineRecord record);

    @Delete
    void delete(VaccineRecord record);
}