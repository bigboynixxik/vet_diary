package com.example.vetdiary;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NewHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_history);

        TextView btnBack = findViewById(R.id.btn_back);
        TextView btnSave = findViewById(R.id.btn_save);

        // Просто закрываем экран при нажатии Назад или Сохранить
        btnBack.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> finish());
    }
}