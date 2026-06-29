package com.example.factory_emission.admin;

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
import com.example.factory_emission.R;
import com.example.factory_emission.SettingsActivity;
import com.example.factory_emission.adapters.FactoryAdapter;
import com.example.factory_emission.models.Factory;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageFactoriesActivity extends BaseActivity {

    private static final String URL_FACTORIES = "https://legalcounsel14441.helioho.st/get_factories.php";


    //a list to store all the products
    List<Factory> FactoryData;
    RecyclerView recyclerView;
    ImageView imgBack, imgAdd, imgHome, imgProfile, imgSettings, imgSearch;
    TextView txtAdd, txtHome, txtProfile, txtSettings;
    TextInputLayout txtSearch;
    String strSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_factories);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView);
        imgBack = findViewById(R.id.imgBack);
        imgAdd = findViewById(R.id.imgAdd);
        txtAdd = findViewById(R.id.txtAdd);
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

        FactoryData = new ArrayList<>();

        //to display it in recyclerview
        loadFactories();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(i);
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddFactoryActivity.class);
                startActivity(i);
            }
        });
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddFactoryActivity.class);
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
                    loadFactories2();
                }
            }
        });
    }

    private void loadFactories() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FACTORIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject tourist = array.getJSONObject(i);
                                FactoryData.add(new Factory(
                                        tourist.getString("id"),
                                        tourist.getString("name"),
                                        tourist.getString("city"),
                                        tourist.getString("industry"),
                                        tourist.getString("fuel"),
                                        tourist.getString("latitude"),
                                        tourist.getString("longitude")
                                ));
                            }
                            FactoryAdapter adapter = new FactoryAdapter(ManageFactoriesActivity.this, FactoryData);
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
    private void loadFactories2() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FACTORIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            FactoryData.clear();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject factories = array.getJSONObject(i);
                                if(factories.getString("name").toLowerCase().contains(strSearch) ||
                                        factories.getString("industry").toLowerCase().contains(strSearch) ||
                                        factories.getString("city").toLowerCase().contains(strSearch)){
                                            FactoryData.add(new Factory(
                                            factories.getString("id"),
                                            factories.getString("name"),
                                            factories.getString("city"),
                                            factories.getString("industry"),
                                            factories.getString("fuel"),
                                            factories.getString("latitude"),
                                            factories.getString("longitude")
                                    ));
                                }
                            }
                            FactoryAdapter adapter = new FactoryAdapter(ManageFactoriesActivity.this, FactoryData);
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