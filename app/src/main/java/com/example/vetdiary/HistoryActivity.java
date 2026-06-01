package com.example.vetdiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ImageView navHome = findViewById(R.id.nav_home);
        ImageView navMap = findViewById(R.id.nav_map);
        TextView fabAdd = findViewById(R.id.fab_add);

        navHome.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, MainActivity.class)));
        navMap.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, MapActivity.class)));

        // Открытие формы новой записи
        fabAdd.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, NewHistoryActivity.class)));

        TextView tabVaccines = findViewById(R.id.tab_vaccines);

        tabVaccines.setOnClickListener(v -> {
            startActivity(new Intent(HistoryActivity.this, VaccinesActivity.class));
            finish(); // Закрываем экран истории, чтобы не засорять память
        });
    }
}