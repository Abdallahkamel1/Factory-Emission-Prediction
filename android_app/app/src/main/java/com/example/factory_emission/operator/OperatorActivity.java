package com.example.factory_emission.operator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.factory_emission.BaseActivity;
import com.example.factory_emission.ForgetPasswordActivity;
import com.example.factory_emission.R;
import com.example.factory_emission.SettingsActivity;

public class OperatorActivity extends BaseActivity {

    ImageView imgLogout, imgProfile, imgSettings, imgHome, imgNotification;
    TextView txtProfile, txtHome, txtSettings, txtAddEmission, txtAnalyzeEmission, txtReport;
    ImageButton btnAddEmission, btnReport, btnAnalyzeEmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_operator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgLogout = findViewById(R.id.imgLogout);
        imgProfile = findViewById(R.id.imgProfile);
        txtProfile = findViewById(R.id.txtProfile);
        imgSettings = findViewById(R.id.imgSettings);
        txtSettings = findViewById(R.id.txtSettings);
        imgNotification = findViewById(R.id.imgNote);
        imgHome = findViewById(R.id.imgHome);
        txtHome = findViewById(R.id.txtHome);
        txtAddEmission = findViewById(R.id.txtAddEmission);
        btnAddEmission = findViewById(R.id.btnAddEmission);
        txtAnalyzeEmission = findViewById(R.id.txtAnalyzeEmission);
        btnAnalyzeEmission = findViewById(R.id.btnAnalyzeEmission);
        txtReport = findViewById(R.id.txtReport);
        btnReport = findViewById(R.id.btnReport);

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(i);
            }
        });
        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(i);
            }
        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OperatorActivity.class);
                startActivity(i);
            }
        });
        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OperatorActivity.class);
                startActivity(i);
            }
        });
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });
        txtSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }
        });
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NotificationsActivity.class);
                startActivity(i);
            }
        });
        btnAddEmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddFactoryEmissionActivity.class);
                startActivity(i);
            }
        });
        txtAddEmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddFactoryEmissionActivity.class);
                startActivity(i);
            }
        });
        btnAnalyzeEmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AnalyzeFactoryEmissionActivity.class);
                startActivity(i);
            }
        });
        txtAnalyzeEmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AnalyzeFactoryEmissionActivity.class);
                startActivity(i);
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FactoryEmissionReportActivity.class);
                startActivity(i);
            }
        });
        txtReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FactoryEmissionReportActivity.class);
                startActivity(i);
            }
        });

    }
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.logout));
        builder.setMessage(getString(R.string.confirm_logout));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform logout action
                logout();
            }
        });
        builder.setNegativeButton(getString(R.string.no), null);
        builder.show();
    }
    private void logout() {
        // Perform logout actions here, such as clearing session data, etc.
        finishAffinity();
    }
}