package com.example.vetdiary;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NewVaccineActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vaccine);

        TextView btnBack = findViewById(R.id.btn_back);
        TextView btnSave = findViewById(R.id.btn_save);

        btnBack.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> finish());
    }
}