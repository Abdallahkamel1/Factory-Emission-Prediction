package com.example.factory_emission;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends BaseActivity {

    AppCompatButton btnEnglish, btnArabic;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnEnglish = findViewById(R.id.btnEnglish);
        btnArabic = findViewById(R.id.btnArabic);
        imgBack = findViewById(R.id.imgBack);
        btnArabic.setOnClickListener(v -> changeLanguage("ar"));
        btnEnglish.setOnClickListener(v -> changeLanguage("en"));
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void changeLanguage(String langCode) {
        saveLanguage(langCode);
        LocaleHelper.setLocale(this, langCode);

        // Restart activity to apply changes
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    public void saveLanguage(String langCode) {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        prefs.edit().putString("My_Lang", langCode).apply();
    }
}