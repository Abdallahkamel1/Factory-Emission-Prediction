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
import com.example.factory_emission.adapters.OperatorAdapter;
import com.example.factory_emission.models.Factory;
import com.example.factory_emission.models.OperatorData;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageOperatorsActivity extends BaseActivity {
    private static final String URL_OPERATORS = "https://legalcounsel14441.helioho.st/get_operators.php";


    //a list to store all the products
    List<OperatorData> Operators;
    RecyclerView recyclerView;
    ImageView imgBack, imgAdd, imgHome, imgProfile, imgSettings, imgSearch;
    TextView txtAdd, txtHome, txtProfile, txtSettings;
    TextInputLayout txtSearch;
    String strSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_operators);
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

        Operators = new ArrayList<>();

        //to display it in recyclerview
        loadOperators();
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
                Intent i = new Intent(getApplicationContext(), ManageFactoriesActivity.class);
                startActivity(i);
            }
        });
        txtAdd.setOnClickListener(new View.OnClickListener() {
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
                    loadOperators2();
                }
            }
        });
    }

    private void loadOperators() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_OPERATORS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject tourist = array.getJSONObject(i);
                                Operators.add(new OperatorData(
                                        tourist.getString("id"),
                                        tourist.getString("name"),
                                        tourist.getString("email"),
                                        tourist.getString("phone"),
                                        tourist.getString("factoryName")
                                ));
                            }
                            OperatorAdapter adapter = new OperatorAdapter(ManageOperatorsActivity.this, Operators);
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
    private void loadOperators2() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_OPERATORS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Operators.clear();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject operators = array.getJSONObject(i);
                                if(operators.getString("name").toLowerCase().contains(strSearch) ||
                                        operators.getString("factoryName").toLowerCase().contains(strSearch)){
                                    Operators.add(new OperatorData(
                                            operators.getString("id"),
                                            operators.getString("name"),
                                            operators.getString("email"),
                                            operators.getString("phone"),
                                            operators.getString("factoryName")
                                    ));
                                }
                            }
                            OperatorAdapter adapter = new OperatorAdapter(ManageOperatorsActivity.this, Operators);
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