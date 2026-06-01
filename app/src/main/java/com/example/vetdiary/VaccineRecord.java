package com.example.vetdiary;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vaccine_records")
public class VaccineRecord {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String date;
    public String medicine;
    public String status;
}