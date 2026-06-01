package com.example.vetdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout recordsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ImageView navHome = findViewById(R.id.nav_home);
        ImageView navMap = findViewById(R.id.nav_map);
        TextView fabAdd = findViewById(R.id.fab_add);
        TextView tabVaccines = findViewById(R.id.tab_vaccines);

        // Наш пустой контейнер для карточек
        recordsContainer = findViewById(R.id.ll_records_container);

        navHome.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, MainActivity.class)));
        navMap.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, MapActivity.class)));

        fabAdd.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, NewHistoryActivity.class)));

        tabVaccines.setOnClickListener(v -> {
            startActivity(new Intent(HistoryActivity.this, VaccinesActivity.class));
            finish();
        });
    }

    // Этот метод вызывается каждый раз при показе экрана
    @Override
    protected void onResume() {
        super.onResume();
        loadRecordsFromDatabase();
    }

    private void loadRecordsFromDatabase() {
        recordsContainer.removeAllViews();
        List<HistoryRecord> records = AppDatabase.getInstance(this).historyDao().getAllRecords();

        for (HistoryRecord record : records) {
            View cardView = getLayoutInflater().inflate(R.layout.item_history_card, recordsContainer, false);

            TextView tvDate = cardView.findViewById(R.id.tv_item_date);
            TextView tvSymptoms = cardView.findViewById(R.id.tv_item_symptoms);
            TextView tvDiagnosis = cardView.findViewById(R.id.tv_item_diagnosis);

            tvDate.setText("Дата: " + record.date);
            tvSymptoms.setText("Симптомы: " + record.symptoms);
            tvDiagnosis.setText("Диагноз/Заметка: " + record.diagnosis);

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(HistoryActivity.this, NewHistoryActivity.class);
                intent.putExtra("RECORD_ID", record.id);
                startActivity(intent);
            });

            cardView.setOnLongClickListener(v -> {
                new android.app.AlertDialog.Builder(HistoryActivity.this)
                        .setTitle("Удаление записи")
                        .setMessage("Вы уверены, что хотите удалить эту запись?")
                        .setPositiveButton("Да", (dialog, which) -> {
                            AppDatabase.getInstance(HistoryActivity.this).historyDao().delete(record);
                            loadRecordsFromDatabase();
                        })
                        .setNegativeButton("Нет", null)
                        .show();
                return true;
            });

            recordsContainer.addView(cardView);
        }
    }
}