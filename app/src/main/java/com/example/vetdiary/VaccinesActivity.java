package com.example.vetdiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class VaccinesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccines);

        ImageView navHome = findViewById(R.id.nav_home);
        ImageView navMap = findViewById(R.id.nav_map);
        TextView fabAdd = findViewById(R.id.fab_add);

        navHome.setOnClickListener(v -> startActivity(new Intent(VaccinesActivity.this, MainActivity.class)));
        navMap.setOnClickListener(v -> startActivity(new Intent(VaccinesActivity.this, MapActivity.class)));

        // Открытие формы новой прививки
        fabAdd.setOnClickListener(v -> startActivity(new Intent(VaccinesActivity.this, NewVaccineActivity.class)));

        TextView tabHistory = findViewById(R.id.tab_history);

        tabHistory.setOnClickListener(v -> {
            startActivity(new Intent(VaccinesActivity.this, HistoryActivity.class));
            finish();
        });
    }
}