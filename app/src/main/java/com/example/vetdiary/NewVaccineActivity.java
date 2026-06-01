package com.example.vetdiary;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NewVaccineActivity extends AppCompatActivity {
    private VaccineRecord currentRecord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vaccine);

        TextView btnBack = findViewById(R.id.btn_back);
        TextView btnSave = findViewById(R.id.btn_save);
        TextView tvTitle = findViewById(R.id.tv_title);

        EditText etDate = findViewById(R.id.et_date);
        EditText etMedicine = findViewById(R.id.et_medicine);
        EditText etStatus = findViewById(R.id.et_status);

        btnBack.setOnClickListener(v -> finish());

        int recordId = getIntent().getIntExtra("RECORD_ID", -1);

        if (recordId != -1) {
            tvTitle.setText("Редактирование");
            currentRecord = AppDatabase.getInstance(this).vaccineDao().getRecordById(recordId);
            if (currentRecord != null) {
                etDate.setText(currentRecord.date);
                etMedicine.setText(currentRecord.medicine);
                etStatus.setText(currentRecord.status);
            }
        }

        btnSave.setOnClickListener(v -> {
            if (currentRecord == null) {
                VaccineRecord newRecord = new VaccineRecord();
                newRecord.date = etDate.getText().toString();
                newRecord.medicine = etMedicine.getText().toString();
                newRecord.status = etStatus.getText().toString();
                AppDatabase.getInstance(this).vaccineDao().insert(newRecord);
                Toast.makeText(this, "Прививка добавлена!", Toast.LENGTH_SHORT).show();
            } else {
                currentRecord.date = etDate.getText().toString();
                currentRecord.medicine = etMedicine.getText().toString();
                currentRecord.status = etStatus.getText().toString();
                AppDatabase.getInstance(this).vaccineDao().update(currentRecord);
                Toast.makeText(this, "Прививка обновлена!", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}