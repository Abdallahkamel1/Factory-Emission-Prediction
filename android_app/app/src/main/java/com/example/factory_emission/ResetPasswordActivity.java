package com.example.factory_emission;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResetPasswordActivity extends BaseActivity {
    ImageView imgBack;
    TextInputLayout txtCode, txtPass, txtConfirmPass;
    ProgressBar progress;
    AppCompatButton btnSubmit;
    String strEmail, strCode, strPass, strType;
    JsonParser jsonParser = new JsonParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        strCode = intent.getStringExtra("code");
        strEmail = intent.getStringExtra("email");
        strType = intent.getStringExtra("type");

        txtCode = findViewById(R.id.txtCode);
        txtPass = findViewById(R.id.txtPass);
        txtConfirmPass = findViewById(R.id.txtConfirmPass);
        btnSubmit = findViewById(R.id.btnSubmit);
        progress = findViewById(R.id.progress);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtMatch = "^[a-zA-Z0-9]+$";
                if (txtCode.getEditText().getText().toString().trim().isEmpty()) {
                    txtCode.setError(getString(R.string.secret_code));
                    txtCode.requestFocus();
                    return;
                }
                if(!txtCode.getEditText().getText().toString().equals(strCode)){
                    txtCode.setError(getString(R.string.code_mismatch));
                    txtCode.requestFocus();
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
                if(!txtPass.getEditText().getText().toString().trim().equals(txtConfirmPass.getEditText().getText().toString().trim())){
                    txtPass.setError(getString(R.string.pass_mismatch));
                    txtPass.requestFocus();
                    return;
                }
                strPass = txtPass.getEditText().getText().toString().trim();
                new ResetOperation().execute();
            }
        });
    }
    class ResetOperation extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("email", strEmail));
                parameters.add(new BasicNameValuePair("type", strType));
                parameters.add(new BasicNameValuePair("pass", strPass));
                JSONObject parser = jsonParser.makeHttpRequest("https://legalcounsel14441.helioho.st/resetPass.php",
                        "POST", parameters);
                success = parser.getInt("success");
                if (success == 1) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(intent, 100);
                    return  parser.getString("message") ;
                }else {
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