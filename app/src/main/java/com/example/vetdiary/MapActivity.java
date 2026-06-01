package com.example.vetdiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ImageView navHome = findViewById(R.id.nav_home);
        ImageView navDiary = findViewById(R.id.nav_diary);

        navHome.setOnClickListener(v -> startActivity(new Intent(MapActivity.this, MainActivity.class)));
        navDiary.setOnClickListener(v -> startActivity(new Intent(MapActivity.this, HistoryActivity.class)));
    }
}