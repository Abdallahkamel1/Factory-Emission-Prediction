package com.example.factory_emission.operator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.factory_emission.BaseActivity;
import com.example.factory_emission.JsonParser;
import com.example.factory_emission.LoginActivity;
import com.example.factory_emission.R;
import com.example.factory_emission.SettingsActivity;
import com.example.factory_emission.admin.ManageFactoriesActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddFactoryEmissionActivity extends BaseActivity {
    ImageView imgBack, imgHome, imgProfile, imgSettings;
    TextView txtHome, txtProfile, txtSettings;
    AppCompatButton btnSave;
    TextInputLayout txtCO2, txtSO2, txtNO, txtPM, txtProduction;
    JsonParser jsonParser = new JsonParser();
    ProgressBar progress;
    String strCO2, strSO2, strNO, strPM, strFactoryID, strProduction;
    String strFactoryName, strIndustryType, strFuelType, strCity, strLongitude, strLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_factory_emission);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        imgProfile = findViewById(R.id.imgProfile);
        imgSettings = findViewById(R.id.imgSettings);
        txtHome = findViewById(R.id.txtHome);
        txtProfile = findViewById(R.id.txtProfile);
        txtSettings = findViewById(R.id.txtSettings);
        btnSave = findViewById(R.id.btnEmission);
        txtCO2 = findViewById(R.id.txtCo2);
        txtSO2 = findViewById(R.id.txtSo2);
        txtNO = findViewById(R.id.txtNo2);
        txtPM = findViewById(R.id.txtPM);
        txtProduction = findViewById(R.id.txtProduction);
        progress = findViewById(R.id.progress);

        strFactoryID = LoginActivity.factory_operator.getFactoryID();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OperatorActivity.class);
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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numMatch = "^[0-9].+$";
                if(txtCO2.getEditText().getText().toString().trim().isEmpty()){
                    txtCO2.setError(getString(R.string.co2_required));
                    txtCO2.requestFocus();
                    return;
                }
                if(txtSO2.getEditText().getText().toString().trim().isEmpty()){
                    txtSO2.setError(getString(R.string.so2_required));
                    txtSO2.requestFocus();
                    return;
                }
                if(txtNO.getEditText().getText().toString().trim().isEmpty()){
                    txtNO.setError(getString(R.string.no_required));
                    txtNO.requestFocus();
                    return;
                }
                if(txtPM.getEditText().getText().toString().trim().isEmpty()){
                    txtPM.setError(getString(R.string.pm_required));
                    txtPM.requestFocus();
                    return;
                }
                if(txtProduction.getEditText().getText().toString().trim().isEmpty()){
                    txtProduction.setError(getString(R.string.production_required));
                    txtProduction.requestFocus();
                    return;
                }
                if(!txtProduction.getEditText().getText().toString().trim().matches(numMatch)){
                    txtProduction.setError(getString(R.string.production_error));
                    txtProduction.requestFocus();
                    return;
                }
                if(!txtCO2.getEditText().getText().toString().trim().matches(numMatch)){
                    txtCO2.setError(getString(R.string.emission_error));
                    txtCO2.requestFocus();
                    return;
                }
                if(!txtSO2.getEditText().getText().toString().trim().matches(numMatch)){
                    txtSO2.setError(getString(R.string.emission_error));
                    txtSO2.requestFocus();
                    return;
                }
                if(!txtNO.getEditText().getText().toString().trim().matches(numMatch)){
                    txtNO.setError(getString(R.string.emission_error));
                    txtNO.requestFocus();
                    return;
                }
                if(!txtPM.getEditText().getText().toString().trim().matches(numMatch)){
                    txtPM.setError(getString(R.string.emission_error));
                    txtPM.requestFocus();
                    return;
                }

                strCO2 = txtCO2.getEditText().getText().toString().trim();
                strSO2 = txtSO2.getEditText().getText().toString().trim();
                strPM = txtPM.getEditText().getText().toString().trim();
                strNO = txtNO.getEditText().getText().toString().trim();
                strProduction = txtProduction.getEditText().getText().toString().trim();
                new DoAddEmission().execute();
            }
        });
    }
    class DoAddEmission extends AsyncTask<String, String, String> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);//progress bar is visible
        }
        @Override
        public String doInBackground(String... args) {
            int success;
            try {
                List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
                parameters.add(new BasicNameValuePair("co2", strCO2));
                parameters.add(new BasicNameValuePair("so2", strSO2));
                parameters.add(new BasicNameValuePair("no", strNO));
                parameters.add(new BasicNameValuePair("pm", strPM));
                parameters.add(new BasicNameValuePair("production", strProduction));
                parameters.add(new BasicNameValuePair("id", strFactoryID));
                JSONObject parser = jsonParser.makeHttpRequest("https://legalcounsel14441.helioho.st/add_emission.php", "POST", parameters);
                success = parser.getInt("success");
                if(success == 1) {
                    String[] server_response = parser.getString("message").split("###");
                    strFactoryName = server_response[0];
                    strIndustryType = server_response[1];
                    strFuelType = server_response[2];
                    strCity = server_response[3];
                    strLatitude = server_response[4];
                    strLongitude = server_response[5];
                    Intent intent = new Intent(getApplicationContext(), RequestAnalyzeEmissionActivity.class);
                    intent.putExtra("co2", strCO2);
                    intent.putExtra("so2", strSO2);
                    intent.putExtra("no", strNO);
                    intent.putExtra("pm", strPM);
                    intent.putExtra("production", strProduction);
                    intent.putExtra("id", strFactoryID);
                    intent.putExtra("name", strFactoryName);
                    intent.putExtra("industry", strIndustryType);
                    intent.putExtra("fuel", strFuelType);
                    intent.putExtra("city", strCity);
                    intent.putExtra("lat", strLatitude);
                    intent.putExtra("lng", strLongitude);
                    startActivity(intent);
                    return  "Success";
                }
                else {
                    return parser.getString("message");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            progress.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), file_url, Toast.LENGTH_SHORT).show();
        }
    }
}