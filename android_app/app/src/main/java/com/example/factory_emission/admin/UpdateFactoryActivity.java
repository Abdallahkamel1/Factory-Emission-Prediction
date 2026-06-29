package com.example.factory_emission.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.example.factory_emission.R;
import com.example.factory_emission.SettingsActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateFactoryActivity extends BaseActivity {
    ImageView imgBack, imgHome, imgProfile, imgSettings;
    TextView txtHome, txtProfile, txtSettings;
    AppCompatButton btnSave, btnLocation;
    TextInputLayout txtName;
    public static TextInputLayout txtLatitude, txtLongitude;
    Spinner txtCity, txtIndustry, txtFuel;
    JsonParser jsonParser = new JsonParser();
    ProgressBar progress;
    String strName, strCity, strIndustry, strFuel, strLat, strLng, strID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_factory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //// read the factory data ////////////
        strID = getIntent().getStringExtra("id");
        strName = getIntent().getStringExtra("name");
        strCity = getIntent().getStringExtra("city");
        strIndustry = getIntent().getStringExtra("industry");
        strFuel = getIntent().getStringExtra("fuel");
        strLat = getIntent().getStringExtra("lat");
        strLng = getIntent().getStringExtra("lng");
        ////////////////////////////////////////////////
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        imgProfile = findViewById(R.id.imgProfile);
        imgSettings = findViewById(R.id.imgSettings);
        txtHome = findViewById(R.id.txtHome);
        txtProfile = findViewById(R.id.txtProfile);
        txtSettings = findViewById(R.id.txtSettings);
        btnSave = findViewById(R.id.btnSave);
        btnLocation = findViewById(R.id.btnLocation);
        txtName = findViewById(R.id.txtName);
        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);
        txtCity = findViewById(R.id.txtCity);
        txtIndustry = findViewById(R.id.txtIndustry);
        txtFuel = findViewById(R.id.txtFuel);
        progress = findViewById(R.id.progress);

        txtName.getEditText().setText(strName);
        txtLatitude.getEditText().setText(strLat);
        txtLongitude.getEditText().setText(strLng);
        for (int j = 0; j < txtCity.getCount(); j++) {
            if (txtCity.getItemAtPosition(j).equals(strCity)) {
                txtCity.setSelection(j);
                break;
            }
        }
        for (int j = 0; j < txtIndustry.getCount(); j++) {
            if (txtIndustry.getItemAtPosition(j).equals(strIndustry)) {
                txtIndustry.setSelection(j);
                break;
            }
        }
        for (int j = 0; j < txtFuel.getCount(); j++) {
            if (txtFuel.getItemAtPosition(j).equals(strFuel)) {
                txtFuel.setSelection(j);
                break;
            }
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ManageFactoriesActivity.class);
                startActivity(i);
            }
        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(i);
            }
        });
        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(i);
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
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ChooseFactoryLocationActivity.class);
                i.putExtra("come", "2");
                i.putExtra("lat", strLat);
                i.putExtra("lng", strLng);
                startActivity(i);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // first check for inputs
                if(txtName.getEditText().getText().toString().trim().isEmpty()){
                    txtName.setError(getString(R.string.name_required));
                    txtName.requestFocus();
                    return;
                }
                strName = txtName.getEditText().getText().toString().trim();
                strCity = txtCity.getSelectedItem().toString().trim();
                strIndustry = txtIndustry.getSelectedItem().toString().trim();
                strFuel = txtFuel.getSelectedItem().toString().trim();
                strLat = txtLatitude.getEditText().getText().toString().trim();
                strLng = txtLongitude.getEditText().getText().toString().trim();
                new DoUpdateFactory().execute();
            }
        });
    }
    class DoUpdateFactory extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("id", strID));
                parameters.add(new BasicNameValuePair("name", strName));
                parameters.add(new BasicNameValuePair("city", strCity));
                parameters.add(new BasicNameValuePair("industry", strIndustry));
                parameters.add(new BasicNameValuePair("fuel", strFuel));
                parameters.add(new BasicNameValuePair("lat", strLat));
                parameters.add(new BasicNameValuePair("lng", strLng));
                JSONObject parser = jsonParser.makeHttpRequest("https://legalcounsel14441.helioho.st/update_factory.php", "POST", parameters);
                success = parser.getInt("success");
                if(success == 1) {
                    Intent intent = new Intent(getApplicationContext(), ManageFactoriesActivity.class);
                    startActivity(intent);
                    return  parser.getString("message") ;
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