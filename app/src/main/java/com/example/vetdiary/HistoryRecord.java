package com.example.vetdiary;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "history_records")
public class HistoryRecord {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String healthFeatures;
    public String date;
    public String symptoms;
    public String diagnosis;
}