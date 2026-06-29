package com.example.factory_emission.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
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
import com.example.factory_emission.R;
import com.example.factory_emission.SettingsActivity;
import com.example.factory_emission.operator.EditProfileActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddOperatorActivity extends BaseActivity {

    ImageView imgBack, imgHome, imgProfile, imgSettings;
    TextView txtHome, txtProfile, txtSettings;
    AppCompatButton btnSave;
    TextInputLayout txtName, txtEmail, txtPhone, txtPass;
    JsonParser jsonParser = new JsonParser();
    ProgressBar progress;
    String strName, strEmail, strPhone, strPass, strFactoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_operator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //// read the factory ID ///////
        strFactoryID = getIntent().getStringExtra("id");
        ////////////////////////////////
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        imgProfile = findViewById(R.id.imgProfile);
        imgSettings = findViewById(R.id.imgSettings);
        txtHome = findViewById(R.id.txtHome);
        txtProfile = findViewById(R.id.txtProfile);
        txtSettings = findViewById(R.id.txtSettings);
        btnSave = findViewById(R.id.btnSave);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtPass = findViewById(R.id.txtPass);
        progress = findViewById(R.id.progress);

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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtMatch = "^[a-zA-Z0-9]+$";
                String phoneMatch = "^[0-9]+$";
                if(txtName.getEditText().getText().toString().trim().isEmpty()){
                    txtName.setError(getString(R.string.name_required));
                    txtName.requestFocus();
                    return;
                }
                if(txtEmail.getEditText().getText().toString().trim().isEmpty()){
                    txtEmail.setError(getString(R.string.email_wrong));
                    txtEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getEditText().getText().toString()).matches()){
                    txtEmail.setError(getString(R.string.email_invalid));
                    txtEmail.requestFocus();
                    return;
                }
                if(txtPhone.getEditText().getText().toString().trim().isEmpty()){
                    txtPhone.setError(getString(R.string.phone_required));
                    txtPhone.requestFocus();
                    return;
                }
                if(txtPhone.getEditText().getText().toString().trim().length() < 10){
                    txtPhone.setError(getString(R.string.phone_length));
                    txtPhone.requestFocus();
                    return;
                }
                if(!txtPhone.getEditText().getText().toString().trim().matches(phoneMatch)){
                    txtPhone.setError(getString(R.string.phone_numbers));
                    txtPhone.requestFocus();
                    return;
                }
                if(txtPass.getEditText().getText().toString().trim().isEmpty()){
                    txtPass.setError(getString(R.string.pass_wrong));
                    txtPass.requestFocus();
                    return;
                }
                if(txtPass.getEditText().getText().toString().trim().length() < 6 || txtPass.getEditText().getText().toString().trim().length() > 20){
                    txtPass.setError(getString(R.string.pass_length));
                    txtPass.requestFocus();
                    return;
                }
                if(!txtPass.getEditText().getText().toString().trim().matches(txtMatch)){
                    txtPass.setError(getString(R.string.pass_invalid));
                    txtPass.requestFocus();
                    return;
                }
                strName = txtName.getEditText().getText().toString().trim();
                strEmail = txtEmail.getEditText().getText().toString().trim();
                strPass = txtPass.getEditText().getText().toString().trim();
                strPhone = txtPhone.getEditText().getText().toString().trim();
                new DoAddOperator().execute();
            }
        });
    }
    class DoAddOperator extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("name", strName));
                parameters.add(new BasicNameValuePair("email", strEmail));
                parameters.add(new BasicNameValuePair("phone", strPhone));
                parameters.add(new BasicNameValuePair("pass", strPass));
                parameters.add(new BasicNameValuePair("id", strFactoryID));
                JSONObject parser = jsonParser.makeHttpRequest("https://legalcounsel14441.helioho.st/add_operator.php", "POST", parameters);
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