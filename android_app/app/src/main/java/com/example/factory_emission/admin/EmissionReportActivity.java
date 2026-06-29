package com.example.factory_emission.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.factory_emission.BaseActivity;
import com.example.factory_emission.R;
import com.example.factory_emission.SettingsActivity;
import com.example.factory_emission.operator.EditProfileActivity;
import com.example.factory_emission.operator.OperatorActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EmissionReportActivity extends BaseActivity {

    TextInputLayout txtName;
    AppCompatButton btnView, btnRefresh;
    Spinner txtPollutant;
    TextView txtLevel, txtSettings, txtHome, txtProfile, txtFrom, txtTo, txtTitle;
    String strName, strFrom, strTo, strLevel, strPollutant;
    ImageView imgBack, imgSettings, imgHome, imgProfile;
    private Calendar calendar;
    int selected_day = 0, selected_month=0, selected_year = 0;
    SimpleDateFormat formatter;
    LineDataSet dataSet;
    float total = 0.0f;
    float avg = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_emission_report);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtName = findViewById(R.id.txtName);
        txtFrom = findViewById(R.id.txtFrom);
        txtTo = findViewById(R.id.txtTo);
        btnView = findViewById(R.id.btnView);
        btnRefresh = findViewById(R.id.btnRefresh);
        txtPollutant = findViewById(R.id.txtPollutant);
        txtLevel = findViewById(R.id.txtLevel);
        txtSettings = findViewById(R.id.txtSettings);
        txtHome = findViewById(R.id.txtHome);
        txtProfile = findViewById(R.id.txtProfile);
        txtTitle = findViewById(R.id.txtTitle);
        imgSettings = findViewById(R.id.imgSettings);
        imgHome = findViewById(R.id.imgHome);
        imgProfile = findViewById(R.id.imgProfile);
        imgBack = findViewById(R.id.imgBack);

        formatter = new SimpleDateFormat("yyyy-MM-dd");

        txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(EmissionReportActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                datePicker = new DatePickerDialog(EmissionReportActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtName.getEditText().getText().toString().trim().isEmpty()){
                    txtName.setError(getString(R.string.factory_name));
                    txtName.requestFocus();
                    return;
                }
                if(txtFrom.getText().toString().trim().isEmpty()){
                    txtFrom.setError(getString(R.string.from_required));
                    txtFrom.requestFocus();
                    return;
                }
                if(txtTo.getText().toString().trim().isEmpty()){
                    txtTo.setError(getString(R.string.to_required));
                    txtTo.requestFocus();
                    return;
                }
                strName = txtName.getEditText().getText().toString().trim();
                strFrom = txtFrom.getText().toString().trim();
                strTo = txtTo.getText().toString().trim();
                strPollutant = txtPollutant.getSelectedItem().toString();
                txtTitle.setText(strPollutant + "Emission Distribution");
                new GetChartData().execute();
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EmissionReportActivity.class);
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
    }

    class GetChartData extends AsyncTask<String, String, String> {

        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        @Override
        protected String doInBackground(String... strings) {
            try {
                String condition = "name=" + strName + "&from=" + strFrom + "&to=" + strTo + "&type=" + strPollutant;
                URL url = new URL("https://legalcounsel14441.helioho.st/read_co_emission.php?" + condition);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject json = new JSONObject(result.toString());
                JSONArray dataArray = json.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject obj = dataArray.getJSONObject(i);
                    float y = 0.0f;
                    if(strPollutant.equals("CO")){
                        y = Float.parseFloat(obj.getString("CO2Level"));
                    }else if(strPollutant.equals("SO")){
                        y = Float.parseFloat(obj.getString("SO2Level"));
                    }else if(strPollutant.equals("NO")){
                        y = Float.parseFloat(obj.getString("NOLevel"));
                    }else if(strPollutant.equals("PM")){
                        y = Float.parseFloat(obj.getString("PMLevel"));
                    }
                    total += y;
                    String date = obj.getString("emissionDate");

                    entries.add(new Entry(i, y));   // X = index
                    labels.add(date);               // store date
                }
                avg = total/dataArray.length();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            drawChart(entries, labels);
            txtLevel.setText(avg + "tons/year");
        }
    }

    private void drawChart(ArrayList<Entry> entries, ArrayList<String> labels) {

        LineChart lineChart = findViewById(R.id.lineChart);
        if(strPollutant.equals("CO")) {
            dataSet = new LineDataSet(entries, "CO2 Levels");
        }else if(strPollutant.equals("SO")) {
            dataSet = new LineDataSet(entries, "SO2 Levels");
        }else if(strPollutant.equals("NO")) {
            dataSet = new LineDataSet(entries, "NO Levels");
        }else if(strPollutant.equals("PM")) {
            dataSet = new LineDataSet(entries, "PM Levels");
        }
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // ✅ Set X-Axis labels (dates)
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(2f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setLabelCount(5, true);
        dataSet.setLineWidth(4f);

        lineChart.invalidate();
    }
}