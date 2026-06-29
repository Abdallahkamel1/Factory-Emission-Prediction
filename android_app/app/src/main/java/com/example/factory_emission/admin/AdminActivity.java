package com.example.factory_emission.admin;

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
import com.example.factory_emission.LoginActivity;
import com.example.factory_emission.R;
import com.example.factory_emission.operator.EditProfileActivity;

public class AdminActivity extends BaseActivity {
    ImageView imgLogout, imgProfile;
    TextView txtProfile, txtManageFactory, txtManageOperator, txtManagePollutants, txtViewReport;
    ImageButton btnManageFactory, btnManageOperator, btnManagePollutants, btnViewReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgLogout = findViewById(R.id.imgLogout);
        imgProfile = findViewById(R.id.imgProfile);
        txtProfile = findViewById(R.id.txtProfile);
        txtManageFactory = findViewById(R.id.txtManageFactories);
        btnManageFactory = findViewById(R.id.btnManageFactory);
        txtManageOperator = findViewById(R.id.txtManageOperators);
        btnManageOperator = findViewById(R.id.btnManageOperators);
        txtManagePollutants = findViewById(R.id.txtManagePollutants);
        btnManagePollutants = findViewById(R.id.btnManagePollutants);
        txtViewReport = findViewById(R.id.txtEmissionReport);
        btnViewReports = findViewById(R.id.btnViewReports);

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditProfileAdminActivity.class);
                startActivity(i);
            }
        });
        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditProfileAdminActivity.class);
                startActivity(i);
            }
        });
        txtManageFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ManageFactoriesActivity.class);
                startActivity(i);
            }
        });
        btnManageFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ManageFactoriesActivity.class);
                startActivity(i);
            }
        });
        txtManageOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ManageOperatorsActivity.class);
                startActivity(i);
            }
        });
        btnManageOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ManageOperatorsActivity.class);
                startActivity(i);
            }
        });
        txtManagePollutants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ManagePollutantsActivity.class);
                startActivity(i);
            }
        });
        btnManagePollutants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ManagePollutantsActivity.class);
                startActivity(i);
            }
        });
        txtViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EmissionReportActivity.class);
                startActivity(i);
            }
        });
        btnViewReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EmissionReportActivity.class);
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