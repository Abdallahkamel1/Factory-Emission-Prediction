package com.example.factory_emission.operator;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import com.example.factory_emission.adapters.PredictionAdapter;
import com.example.factory_emission.admin.EmissionReportActivity;
import com.example.factory_emission.models.Notes;
import com.example.factory_emission.models.Prediction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FactoryEmissionReportActivity extends BaseActivity {

    private static final String URL_PREDICTIONS = "https://legalcounsel14441.helioho.st/get_predictions.php";
    //a list to store all the products
    List<Prediction> predictions;
    RecyclerView recyclerView;
    ImageView imgBack, imgHome, imgProfile, imgSettings, imgSearch;
    TextView txtHome, txtProfile, txtSettings;
    TextView txtFrom, txtTo;
    String strFrom, strTo, strFactoryID;
    AppCompatButton btnView;
    private Calendar calendar;
    int selected_day = 0, selected_month=0, selected_year = 0;
    SimpleDateFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_factory_emission_report);
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
        btnView = findViewById(R.id.btnView);
        txtFrom = findViewById(R.id.txtFrom);
        txtTo = findViewById(R.id.txtTo);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        predictions = new ArrayList<>();

        //to display it in recyclerview
        loadPredictions();
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
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtFrom.getText().toString().trim().isEmpty()){
                    txtFrom.setError(getString(R.string.from_required));
                    txtFrom.requestFocus();
                    return;
                }
                if(txtTo.getText().toString().trim().isEmpty()){
                    txtTo.setError(getString(R.string.to_required));
                    txtTo.requestFocus();
                    return;
                }else {
                    strFrom = txtFrom.getText().toString().trim();
                    strTo = txtTo.getText().toString().trim();
                    loadPredictions2();
                }
            }
        });
        formatter = new SimpleDateFormat("yyyy-MM-dd");

        txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(FactoryEmissionReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        selected_day = i2;
                        selected_month = i1 + 1;
                        selected_year = i;
                        txtFrom.setText(selected_year + "-" + selected_month + "-" + selected_day);
                    }
                }, year, month, day);
                datePicker.setTitle("Start Date");
                datePicker.show();
            }
        });
        txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(FactoryEmissionReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        selected_day = i2;
                        selected_month = i1 + 1;
                        selected_year = i;
                        txtTo.setText(selected_year + "-" + selected_month + "-" + selected_day);
                    }
                }, year, month, day);
                datePicker.setTitle("End Date");
                datePicker.show();
            }
        });
    }

    private void loadPredictions() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PREDICTIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject predict = array.getJSONObject(i);
                                if(predict.getString("facID").equals(strFactoryID)) {
                                    predictions.add(new Prediction(
                                            predict.getString("id"),
                                            predict.getString("state"),
                                            predict.getString("predictDate"),
                                            predict.getString("co"),
                                            predict.getString("so"),
                                            predict.getString("no"),
                                            predict.getString("pm"),
                                            predict.getString("facID")
                                    ));
                                }
                            }
                            PredictionAdapter adapter = new PredictionAdapter(FactoryEmissionReportActivity.this, predictions);
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
    private void loadPredictions2() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PREDICTIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            predictions.clear();
                            JSONArray array = new JSONArray(response);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                            Date fromDate = sdf.parse(strFrom);
                            Date toDate = sdf.parse(strTo);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject predict = array.getJSONObject(i);
                                Date fullDate = sdf.parse(predict.getString("predictDate"));

                                if(predict.getString("facID").equals(strFactoryID)
                                        && (fullDate.after(fromDate)
                                        && fullDate.before(toDate))) {
                                    predictions.add(new Prediction(
                                            predict.getString("id"),
                                            predict.getString("state"),
                                            predict.getString("predictDate"),
                                            predict.getString("co"),
                                            predict.getString("so"),
                                            predict.getString("no"),
                                            predict.getString("pm"),
                                            predict.getString("facID")
                                    ));
                                }
                            }
                            PredictionAdapter adapter = new PredictionAdapter(FactoryEmissionReportActivity.this, predictions);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
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