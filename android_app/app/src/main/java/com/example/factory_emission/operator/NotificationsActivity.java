package com.example.factory_emission.operator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.factory_emission.BaseActivity;
import com.example.factory_emission.LoginActivity;
import com.example.factory_emission.R;
import com.example.factory_emission.SettingsActivity;
import com.example.factory_emission.adapters.NoteAdapter;
import com.example.factory_emission.models.Notes;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends BaseActivity {

    private static final String URL_ALERTS = "https://legalcounsel14441.helioho.st/get_alerts.php";


    //a list to store all the products
    List<Notes> notes;
    RecyclerView recyclerView;
    ImageView imgBack, imgHome, imgProfile, imgSettings, imgSearch;
    TextView txtHome, txtProfile, txtSettings;
    TextInputLayout txtSearch;
    String strSearch, strFactoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notifications);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        strFactoryID = LoginActivity.factory_operator.getFactoryID();

        recyclerView = findViewById(R.id.recyclerView);
        imgBack = findViewById(R.id.imgBack);
        imgHome = findViewById(R.id.imgHome);
        txtHome = findViewById(R.id.txtHome);
        imgProfile = findViewById(R.id.imgProfile);
        txtProfile = findViewById(R.id.txtProfile);
        imgSettings = findViewById(R.id.imgSettings);
        txtSettings = findViewById(R.id.txtSettings);
        imgSearch = findViewById(R.id.imgSearch);
        txtSearch = findViewById(R.id.txtSearch);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notes = new ArrayList<>();

        //to display it in recyclerview
        loadAlerts();
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
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtSearch.getEditText().getText().toString().trim().isEmpty()){
                    txtSearch.getEditText().setError(getString(R.string.search_txt));
                    txtSearch.requestFocus();
                    return;
                }else {
                    strSearch = txtSearch.getEditText().getText().toString().trim();
                    strSearch = strSearch.toLowerCase();
                    loadNotes2();
                }
            }
        });
    }

    private void loadAlerts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ALERTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject alert = array.getJSONObject(i);
                                if(alert.getString("facID").equals(strFactoryID)) {
                                    notes.add(new Notes(
                                            alert.getString("id"),
                                            alert.getString("facID"),
                                            alert.getString("alertDate"),
                                            alert.getString("predict"),
                                            alert.getString("msg")
                                    ));
                                }
                            }
                            NoteAdapter adapter = new NoteAdapter(NotificationsActivity.this, notes);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void loadNotes2() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ALERTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            notes.clear();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject alerts = array.getJSONObject(i);
                                if(alerts.getString("facID").equals(strFactoryID) &&
                                        (alerts.getString("alertDate").toLowerCase().contains(strSearch) ||
                                        alerts.getString("predict").toLowerCase().contains(strSearch))){
                                    notes.add(new Notes(
                                            alerts.getString("id"),
                                            alerts.getString("facID"),
                                            alerts.getString("alertDate"),
                                            alerts.getString("predict"),
                                            alerts.getString("msg")
                                    ));
                                }
                            }
                            NoteAdapter adapter = new NoteAdapter(NotificationsActivity.this, notes);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}