package com.example.vetdiary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.cert.X509Certificate;

public class MapActivity extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trustAllCertificates();

        Configuration.getInstance().setUserAgentValue("VetDiaryApp");
        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("osmdroid", MODE_PRIVATE));

        setContentView(R.layout.activity_map);

        ImageView navHome = findViewById(R.id.nav_home);
        ImageView navDiary = findViewById(R.id.nav_diary);

        navHome.setOnClickListener(v -> startActivity(new Intent(MapActivity.this, MainActivity.class)));
        navDiary.setOnClickListener(v -> startActivity(new Intent(MapActivity.this, HistoryActivity.class)));

        map = findViewById(R.id.map_view);
        map.setMultiTouchControls(true);
        map.getController().setZoom(13.0);

        GeoPoint startPoint = new GeoPoint(56.0153, 92.8932);
        map.getController().setCenter(startPoint);

        addClinicMarker(56.0200, 92.8900, "КрасВетМедика", "ул. Взлетная, 2");
        addClinicMarker(56.0100, 92.8700, "Белый пудель", "ул. Ленина, 10");
        addClinicMarker(56.0180, 92.9000, "Доброе дело", "ул. Партизана Железняка, 15");
    }

    private void addClinicMarker(double lat, double lon, String title, String snippet) {
        Marker marker = new Marker(map);
        marker.setPosition(new GeoPoint(lat, lon));
        marker.setTitle(title);
        marker.setSnippet(snippet);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(marker);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (map != null) map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (map != null) map.onPause();
    }
}