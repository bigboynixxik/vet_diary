package com.example.vetdiary;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NewHistoryActivity extends AppCompatActivity {

    private HistoryRecord currentRecord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_history);

        TextView btnBack = findViewById(R.id.btn_back);
        TextView btnSave = findViewById(R.id.btn_save);
        TextView tvTitle = findViewById(R.id.tv_title);

        EditText etHealthFeatures = findViewById(R.id.et_health_features);
        EditText etDate = findViewById(R.id.et_date);
        EditText etSymptoms = findViewById(R.id.et_symptoms);
        EditText etDiagnosis = findViewById(R.id.et_diagnosis);

        btnBack.setOnClickListener(v -> finish());

        // Проверяем, передали ли нам ID записи для редактирования
        int recordId = getIntent().getIntExtra("RECORD_ID", -1);

        if (recordId != -1) {
            tvTitle.setText("Редактирование");

            currentRecord = AppDatabase.getInstance(this).historyDao().getRecordById(recordId);

            if (currentRecord != null) {
                etHealthFeatures.setText(currentRecord.healthFeatures);
                etDate.setText(currentRecord.date);
                etSymptoms.setText(currentRecord.symptoms);
                etDiagnosis.setText(currentRecord.diagnosis);
            }
        }

        btnSave.setOnClickListener(v -> {
            if (currentRecord == null) {
                HistoryRecord newRecord = new HistoryRecord();
                newRecord.healthFeatures = etHealthFeatures.getText().toString();
                newRecord.date = etDate.getText().toString();
                newRecord.symptoms = etSymptoms.getText().toString();
                newRecord.diagnosis = etDiagnosis.getText().toString();

                AppDatabase.getInstance(this).historyDao().insert(newRecord);
                Toast.makeText(this, "Запись добавлена!", Toast.LENGTH_SHORT).show();
            } else {
                currentRecord.healthFeatures = etHealthFeatures.getText().toString();
                currentRecord.date = etDate.getText().toString();
                currentRecord.symptoms = etSymptoms.getText().toString();
                currentRecord.diagnosis = etDiagnosis.getText().toString();

                AppDatabase.getInstance(this).historyDao().update(currentRecord);
                Toast.makeText(this, "Запись обновлена!", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}