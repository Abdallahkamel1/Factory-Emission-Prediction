package com.example.factory_emission.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
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
import com.example.factory_emission.operator.EditProfileActivity;
import com.example.factory_emission.operator.OperatorActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditProfileAdminActivity extends BaseActivity {

    TextInputLayout txtName, txtEmail, txtPass;
    AppCompatButton btnSave;
    String strName, strID, strEmail, strPass;
    JsonParser jsonParser = new JsonParser();
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        btnSave = findViewById(R.id.btnSave);
        progress = findViewById(R.id.progress);

        strID = LoginActivity.admin.getAdminID();
        strName = LoginActivity.admin.getAdminName();
        strEmail = LoginActivity.admin.getAdminEmail();
        strPass = LoginActivity.admin.getAdminPassword();

        txtName.getEditText().setText(strName);
        txtEmail.getEditText().setText(strEmail);
        txtPass.getEditText().setText(strPass);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // first check for inputs
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
                new DoEditProfile().execute();
            }
        });
    }
    class DoEditProfile extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("id", strID));
                parameters.add(new BasicNameValuePair("pass", strPass));
                JSONObject parser = jsonParser.makeHttpRequest("https://legalcounsel14441.helioho.st/edit_admin.php", "POST", parameters);
                success = parser.getInt("success");
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
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
            LoginActivity.admin.setAdminName(strName);
            LoginActivity.admin.setAdminEmail(strEmail);
            LoginActivity.admin.setAdminPassword(strPass);
        }
    }
}