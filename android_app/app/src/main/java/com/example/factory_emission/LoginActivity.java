package com.example.factory_emission;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.factory_emission.models.Admin;
import com.example.factory_emission.admin.AdminActivity;
import com.example.factory_emission.models.Operator;
import com.example.factory_emission.operator.OperatorActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {
    TextInputLayout txtEmail, txtPass;
    TextView txtForget, txtView;
    ImageView imgView;
    AppCompatButton btnLogin;
    ProgressBar progress;
    String  strEmail, strPass;
    JsonParser jsonParser = new JsonParser();
    public static Admin admin;
    public static Operator factory_operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        btnLogin = findViewById(R.id.btnLogin);
        txtForget = findViewById(R.id.txtForget);
        progress = findViewById(R.id.progress);
        txtView = findViewById(R.id.txtView);
        imgView = findViewById(R.id.imgView);

        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(i);
            }
        });
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ViewFactoriesActivity.class);
                startActivity(i);
            }
        });
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ViewFactoriesActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // first check for inputs
                if (txtEmail.getEditText().getText().toString().trim().isEmpty()) {
                    txtEmail.setError(getString(R.string.email_wrong));
                    txtEmail.requestFocus();
                    return;
                }
                if (txtPass.getEditText().getText().toString().trim().isEmpty()) {
                    txtPass.setError(getString(R.string.pass_wrong));
                    txtPass.requestFocus();
                    return;
                }

                strEmail = txtEmail.getEditText().getText().toString().trim();
                strPass = txtPass.getEditText().getText().toString().trim();

                new DoLogin().execute();
            }
        });
    }
    class DoLogin extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("pass", strPass));
                JSONObject parser = jsonParser.makeHttpRequest("https://legalcounsel14441.helioho.st/login.php", "POST", parameters);
                String[] server_response;
                success = parser.getInt("success");
                if (success == 1) {
                    server_response = parser.getString("message").split("###");
                    admin = new Admin(server_response[0],server_response[1],server_response[2],server_response[3]);
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivityForResult(intent, 100);
                    return  "Success" ;
                }else  if (success == 2) {
                    server_response = parser.getString("message").split("###");
                    factory_operator = new Operator(server_response[0],server_response[1],server_response[2],server_response[3],server_response[4],server_response[5]);
                    Intent intent = new Intent(getApplicationContext(), OperatorActivity.class);
                    startActivityForResult(intent, 100);
                    return "Success";
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
    public void saveLanguage(String langCode) {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        prefs.edit().putString("My_Lang", langCode).apply();
    }

}