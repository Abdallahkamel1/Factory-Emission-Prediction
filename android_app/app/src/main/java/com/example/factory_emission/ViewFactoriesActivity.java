package com.example.factory_emission;

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
import com.example.factory_emission.adapters.FactoryAdapter;
import com.example.factory_emission.adapters.PublicFactoryAdapter;
import com.example.factory_emission.admin.AddFactoryActivity;
import com.example.factory_emission.admin.AdminActivity;
import com.example.factory_emission.admin.EditProfileAdminActivity;
import com.example.factory_emission.admin.ManageFactoriesActivity;
import com.example.factory_emission.models.Factory;
import com.example.factory_emission.models.PublicFactory;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewFactoriesActivity extends BaseActivity {

    private static final String URL_FACTORIES = "https://legalcounsel14441.helioho.st/read_factories.php";


    //a list to store all the products
    List<PublicFactory> FactoryData;
    RecyclerView recyclerView;
    ImageView imgBack, imgSearch, imgInfo;
    TextInputLayout txtSearch;
    String strSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_factories);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        imgBack = findViewById(R.id.imgBack);
        imgInfo = findViewById(R.id.imgInfo);
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
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PollutionInfoActivity.class);
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
                                JSONObject fac = array.getJSONObject(i);
                                FactoryData.add(new PublicFactory(
                                        fac.getString("name"),
                                        fac.getString("factory"),
                                        fac.getString("city"),
                                        fac.getString("state"),
                                        fac.getString("year"),
                                        fac.getString("co"),
                                        fac.getString("so"),
                                        fac.getString("no"),
                                        fac.getString("pm"),
                                        fac.getString("industry"),
                                        fac.getString("fuel")
                                ));
                            }
                            PublicFactoryAdapter adapter = new PublicFactoryAdapter(ViewFactoriesActivity.this, FactoryData);
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
                                        factories.getString("city").toLowerCase().contains(strSearch) ||
                                        factories.getString("year").toLowerCase().equals(strSearch)){
                                    FactoryData.add(new PublicFactory(
                                            factories.getString("name"),
                                            factories.getString("factory"),
                                            factories.getString("city"),
                                            factories.getString("state"),
                                            factories.getString("year"),
                                            factories.getString("co"),
                                            factories.getString("so"),
                                            factories.getString("no"),
                                            factories.getString("pm"),
                                            factories.getString("industry"),
                                            factories.getString("fuel")
                                    ));
                                }
                            }
                            PublicFactoryAdapter adapter = new PublicFactoryAdapter(ViewFactoriesActivity.this, FactoryData);
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