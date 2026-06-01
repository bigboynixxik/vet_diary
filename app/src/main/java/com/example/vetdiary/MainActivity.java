package com.example.vetdiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.cert.X509Certificate;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trustAllCertificates();

        setContentView(R.layout.activity_main);

        ImageView navDiary = findViewById(R.id.nav_diary);
        ImageView navMap = findViewById(R.id.nav_map);

        navDiary.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HistoryActivity.class)));
        navMap.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapActivity.class)));

        fetchCurrentWeather();
    }

    private void fetchCurrentWeather() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                URL url = new URL("https://api.open-meteo.com/v1/forecast?latitude=56.0153&longitude=92.8932&current_weather=true");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject json = new JSONObject(response.toString());
                double temp = json.getJSONObject("current_weather").getDouble("temperature");
                int roundedTemp = (int) Math.round(temp);
                String tempString = (roundedTemp > 0 ? "+" : "") + roundedTemp + "°C";

                runOnUiThread(() -> {
                    TextView tvTemp = findViewById(R.id.tv_temperature);
                    if (tvTemp != null) {
                        tvTemp.setText(tempString);
                    }
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Ошибка API: " + e.getMessage(), Toast.LENGTH_LONG).show());
                e.printStackTrace();
            }
        });
    }

    public static void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}