package com.example.vetdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class VaccinesActivity extends AppCompatActivity {
    private LinearLayout recordsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccines);

        ImageView navHome = findViewById(R.id.nav_home);
        ImageView navMap = findViewById(R.id.nav_map);
        TextView fabAdd = findViewById(R.id.fab_add);
        TextView tabHistory = findViewById(R.id.tab_history);
        recordsContainer = findViewById(R.id.ll_vaccine_records_container);

        navHome.setOnClickListener(v -> startActivity(new Intent(VaccinesActivity.this, MainActivity.class)));
        navMap.setOnClickListener(v -> startActivity(new Intent(VaccinesActivity.this, MapActivity.class)));
        fabAdd.setOnClickListener(v -> startActivity(new Intent(VaccinesActivity.this, NewVaccineActivity.class)));

        tabHistory.setOnClickListener(v -> {
            startActivity(new Intent(VaccinesActivity.this, HistoryActivity.class));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecordsFromDatabase();
    }

    private void loadRecordsFromDatabase() {
        recordsContainer.removeAllViews();
        List<VaccineRecord> records = AppDatabase.getInstance(this).vaccineDao().getAllRecords();

        for (VaccineRecord record : records) {
            View cardView = getLayoutInflater().inflate(R.layout.item_history_card, recordsContainer, false);

            cardView.setBackgroundResource(R.drawable.bg_card_green);

            TextView tvDate = cardView.findViewById(R.id.tv_item_date);
            TextView tvMedicine = cardView.findViewById(R.id.tv_item_symptoms);
            TextView tvStatus = cardView.findViewById(R.id.tv_item_diagnosis);

            tvDate.setText("Дата: " + record.date);
            tvMedicine.setText("Препарат: " + record.medicine);
            tvStatus.setText("Статус: " + record.status);

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(VaccinesActivity.this, NewVaccineActivity.class);
                intent.putExtra("RECORD_ID", record.id);
                startActivity(intent);
            });

            cardView.setOnLongClickListener(v -> {
                new android.app.AlertDialog.Builder(VaccinesActivity.this)
                        .setTitle("Удаление")
                        .setMessage("Удалить эту прививку?")
                        .setPositiveButton("Да", (dialog, which) -> {
                            AppDatabase.getInstance(VaccinesActivity.this).vaccineDao().delete(record);
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