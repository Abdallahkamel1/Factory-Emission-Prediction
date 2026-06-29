package com.example.factory_emission.operator;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.example.factory_emission.LoginActivity;
import com.example.factory_emission.R;
import com.example.factory_emission.SettingsActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeFactoryEmissionActivity extends BaseActivity {
    String strCo2, strSo2, strNo, strPm, strFactoryID, strPredict, strMsg, strUserID, strFactoryName;
    String strIndustryType, strFuelType, strCity, strLongitude, strLatitude, strProduction;
    ProgressBar progress;
    JsonParser jsonParser = new JsonParser();
    AppCompatButton btnSave, btnEmission;
    TextInputLayout txtResult, txtPredict;
    ImageView imgBack, imgHome, imgProfile, imgSettings;
    TextView txtHome, txtProfile, txtSettings;
    TextInputLayout txtCO2, txtSO2, txtNO, txtPM, txtProduction;
    String[] data;
    private TFLiteHelper tfliteHelper;
    float[] mean  = {4.9375f, 6.967f, 2.587f,4.591f,3.4655f,23.6539750695f,42.579150209f,1.988f,2.033f,1198035.9626875f,130901.90162875f,1625.544335f,1299.986538125f, 349.86576749999995f,540810.90878375f,2019.909f};
    float[] std  = {3.1313884699921855f, 4.273278717799718f, 1.7071704660050795f,2.8028055587214746f,2.218740577444781f,3.6747412989184474f
            ,4.488466986053152f,1.4116855173869285f,1.3963921369013792f,579538.0338822411f,67324.989866995f,746.5305349745215f
            ,587.9788068680194f, 169.56841369504727f,243937.35394993046f,2.580061820964761f};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analyze_factory_emission);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //// read the emission data
        strFactoryID = LoginActivity.factory_operator.getFactoryID();
        strUserID = LoginActivity.factory_operator.getOperatorID();

        imgBack = findViewById(R.id.imgBack);
        progress = findViewById(R.id.progress);
        txtResult = findViewById(R.id.txtResult);
        txtPredict = findViewById(R.id.txtPredict);
        btnSave = findViewById(R.id.btnSave);
        btnEmission = findViewById(R.id.btnEmission);
        imgHome = findViewById(R.id.imgHome);
        imgProfile = findViewById(R.id.imgProfile);
        imgSettings = findViewById(R.id.imgSettings);
        txtHome = findViewById(R.id.txtHome);
        txtProfile = findViewById(R.id.txtProfile);
        txtSettings = findViewById(R.id.txtSettings);
        txtCO2 = findViewById(R.id.txtCo2);
        txtSO2 = findViewById(R.id.txtSo2);
        txtNO = findViewById(R.id.txtNo2);
        txtPM = findViewById(R.id.txtPM);
        txtProduction = findViewById(R.id.txtProduction);
//// read the factory data
        new GetFactoryData().execute();

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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DoAddPrediction().execute();
            }
        });
        btnEmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numMatch = "^[0-9].+$";
                if(txtCO2.getEditText().getText().toString().trim().isEmpty()){
                    txtCO2.setError(getString(R.string.co2_required));
                    txtCO2.requestFocus();
                    return;
                }
                if(txtSO2.getEditText().getText().toString().trim().isEmpty()){
                    txtSO2.setError(getString(R.string.so2_required));
                    txtSO2.requestFocus();
                    return;
                }
                if(txtNO.getEditText().getText().toString().trim().isEmpty()){
                    txtNO.setError(getString(R.string.no_required));
                    txtNO.requestFocus();
                    return;
                }
                if(txtPM.getEditText().getText().toString().trim().isEmpty()){
                    txtPM.setError(getString(R.string.pm_required));
                    txtPM.requestFocus();
                    return;
                }
                if(txtProduction.getEditText().getText().toString().trim().isEmpty()){
                    txtProduction.setError(getString(R.string.production_required));
                    txtProduction.requestFocus();
                    return;
                }
                if(!txtProduction.getEditText().getText().toString().trim().matches(numMatch)){
                    txtProduction.setError(getString(R.string.production_error));
                    txtProduction.requestFocus();
                    return;
                }
                if(!txtCO2.getEditText().getText().toString().trim().matches(numMatch)){
                    txtCO2.setError(getString(R.string.emission_error));
                    txtCO2.requestFocus();
                    return;
                }
                if(!txtSO2.getEditText().getText().toString().trim().matches(numMatch)){
                    txtSO2.setError(getString(R.string.emission_error));
                    txtSO2.requestFocus();
                    return;
                }
                if(!txtNO.getEditText().getText().toString().trim().matches(numMatch)){
                    txtNO.setError(getString(R.string.emission_error));
                    txtNO.requestFocus();
                    return;
                }
                if(!txtPM.getEditText().getText().toString().trim().matches(numMatch)){
                    txtPM.setError(getString(R.string.emission_error));
                    txtPM.requestFocus();
                    return;
                }

                strCo2 = txtCO2.getEditText().getText().toString().trim();
                strSo2 = txtSO2.getEditText().getText().toString().trim();
                strPm = txtPM.getEditText().getText().toString().trim();
                strNo = txtNO.getEditText().getText().toString().trim();
                strProduction = txtProduction.getEditText().getText().toString().trim();

                getIndustryCode(strIndustryType);
                getFuelCode(strFuelType);
                getCityCode(strCity);
                getRegionCode(strCity);

                // Initialize with the name of the file in your assets folder
                tfliteHelper = new TFLiteHelper(getApplicationContext(), "emission_model.tflite");
                // Example: If your model expects 4 features (Age, Income, etc.)
                float[] inputFeatures = new float[16];
                inputFeatures[0] = 4.9375f;
                inputFeatures[1] = getIndustryCode(data[1]);
                inputFeatures[2] = getIndustryCode(data[1]);
                inputFeatures[3] = getCityCode(data[3]);
                inputFeatures[4] = getRegionCode(data[3]);
                inputFeatures[5] = Float.parseFloat(data[4]);
                inputFeatures[6] = Float.parseFloat(data[5]);
                inputFeatures[7] = getFuelCode(data[2]);
                inputFeatures[8] = 2.033f;
                inputFeatures[9] = Float.parseFloat(strProduction);
                inputFeatures[10] = Float.parseFloat(strCo2);
                inputFeatures[11] = Float.parseFloat(strSo2);
                inputFeatures[12] = Float.parseFloat(strNo);
                inputFeatures[13] = Float.parseFloat(strPm);
                inputFeatures[14] = 540810.90878375f;
                inputFeatures[15] = 2019.909f;

                float[] scaledFeatures = scaleFeatures(inputFeatures, mean, std);
                float[][] result = tfliteHelper.doInference(scaledFeatures);

                // To get the category with the highest probability:
                int maxIndex = 0;
                for (int i = 0; i < result[0].length; i++) {
                    if (result[0][i] > result[0][maxIndex]) {
                        maxIndex = i;
                    }
                }
                //double[] result = EmissionPredictor.score(inputFeatures);
                // Display the result
                if(maxIndex == 0){
                    strPredict = "High";
                    strMsg= "⚠ High emissions detected — reduce operations immediately.";
                    txtPredict.getEditText().setText("Emission Prediction: High");
                    txtResult.getEditText().setText("Message to operator:\n" +
                            "⚠ High emissions detected — reduce operations immediately.");
                    new AddNotification().execute();
                }else if(maxIndex == 1){
                    strPredict = "Low";
                    strMsg= "Emission levels are optimal. Maintain current operating conditions.";
                    txtPredict.getEditText().setText("Emission Prediction: Low");
                    txtResult.getEditText().setText("Message to operator:\n" +
                            "Emission levels are optimal. Maintain current operating conditions.");
                }else if(maxIndex == 2){
                    strPredict = "Moderate";
                    strMsg= "Emission levels are moderate. Maintain steady operations and monitor closely.";
                    txtPredict.getEditText().setText("Emission Prediction: Moderate");
                    txtResult.getEditText().setText("Message to operator:\n" +
                            "Emission levels are moderate. Maintain steady operations and monitor closely.");
                }else if(maxIndex == 3){
                    strPredict = "Very High";
                    strMsg= "⚠ EMERGENCY: Very High emissions detected — stop operations immediately.";
                    txtPredict.getEditText().setText("Emission Prediction: Very High");
                    txtResult.getEditText().setText("Message to operator:\n" +
                            "⚠ EMERGENCY: Very High emissions detected — stop operations immediately.");
                    new AddNotification().execute();
                }
                btnSave.setEnabled(true);
            }
        });
    }
    public float getIndustryCode(String industry) {
        switch (data[1].toLowerCase()) {
            case "aluminum": return 0.0f;
            case "cement": return 1.0f;
            case "electronics": return 2.0f;
            case "fertilizer": return 3.0f;
            case "food processing": return 4.0f;
            case "glass": return 5.0f;
            case "mining": return 6.0f;
            case "paper": return 7.0f;
            case "petrochemical": return 8.0f;
            case "pharmaceutical": return 9.0f;
            case "plastics": return 10.0f;
            case "power generation": return 11.0f;
            case "steel": return 12.0f;
            case "textile": return 13.0f;
            case "water desalination": return 14.0f;
            default: return -1.0f; // Unknown category
        }
    }
    public float getFuelCode(String fuel) {
        switch (data[2].toLowerCase()) {
            case "diesel": return 0.0f;
            case "electricity": return 1.0f;
            case "heavy fuel oil": return 2.0f;
            case "mixed": return 3.0f;
            case "natural gas": return 4.0f;
            default: return -1.0f; // Unknown category
        }
    }
    public float getCityCode(String city) {
        switch (data[3].toLowerCase()) {
            case "abha": return 0.0f;
            case "dammam": return 1.0f;
            case "hail": return 2.0f;
            case "jazan": return 3.0f;
            case "jeddah": return 4.0f;
            case "jubail": return 5.0f;
            case "rabigh": return 6.0f;
            case "riyadh": return 7.0f;
            case "tabuk": return 8.0f;
            case "yanbu": return 9.0f;
            default: return -1.0f; // Unknown category
        }
    }
    public float getRegionCode(String city) {
        switch (data[3].toLowerCase()) {
            case "abha": return 0.0f;
            case "dammam": return 1.0f;
            case "hail": return 2.0f;
            case "jazan": return 3.0f;
            case "jeddah": return 5.0f;
            case "jubail": return 1.0f;
            case "rabigh": return 5.0f;
            case "riyadh": return 6.0f;
            case "tabuk": return 7.0f;
            case "yanbu": return 4.0f;
            default: return -1.0f; // Unknown category
        }
    }
    public static float[] scaleFeatures(float[] values, float[] means, float[] stds) {
        float[] scaled = new float[values.length];

        for (int i = 0; i < values.length; i++) {
            if (stds[i] == 0) {
                scaled[i] = 0;
            } else {
                scaled[i] = (values[i] - means[i]) / stds[i];
            }
        }

        return scaled;
    }
    class DoAddPrediction extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("companyID", strFactoryID));
                parameters.add(new BasicNameValuePair("company", data[0]));
                parameters.add(new BasicNameValuePair("industry", data[1]));
                parameters.add(new BasicNameValuePair("zone", data[1]));
                parameters.add(new BasicNameValuePair("city", data[3]));
                parameters.add(new BasicNameValuePair("region", data[3]));
                parameters.add(new BasicNameValuePair("lat", data[4]));
                parameters.add(new BasicNameValuePair("lng", data[5]));
                parameters.add(new BasicNameValuePair("fuel", data[2]));
                parameters.add(new BasicNameValuePair("emission_control", "2.033"));
                parameters.add(new BasicNameValuePair("co2", strCo2));
                parameters.add(new BasicNameValuePair("so2", strSo2));
                parameters.add(new BasicNameValuePair("no", strNo));
                parameters.add(new BasicNameValuePair("pm", strPm));
                parameters.add(new BasicNameValuePair("production", strProduction));
                parameters.add(new BasicNameValuePair("energy", "540810.9"));
                parameters.add(new BasicNameValuePair("year", "2026"));
                parameters.add(new BasicNameValuePair("predict", strPredict));
                parameters.add(new BasicNameValuePair("msg", strMsg));
                parameters.add(new BasicNameValuePair("id", strFactoryID));
                JSONObject parser = jsonParser.makeHttpRequest("https://legalcounsel14441.helioho.st/add_prediction.php", "POST", parameters);
                success = parser.getInt("success");
                if(success == 1) {
                    Intent i = new Intent(getApplicationContext(), OperatorActivity.class);
                    startActivity(i);
                    return  "Success";
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
    class GetFactoryData extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("id", strFactoryID));
                JSONObject parser = jsonParser.makeHttpRequest("https://legalcounsel14441.helioho.st/read_factory.php", "POST", parameters);
                success = parser.getInt("success");
                if(success == 1) {
                    data = parser.getString("message").split("###");
                    Log.d("API_RESPONSE", parser.toString());
                    return  "Success";
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
    class AddNotification extends AsyncTask<String, String, String> {
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
                parameters.add(new BasicNameValuePair("predict", strPredict));
                parameters.add(new BasicNameValuePair("msg", strMsg));
                parameters.add(new BasicNameValuePair("id", strFactoryID));
                JSONObject parser = jsonParser.makeHttpRequest("https://legalcounsel14441.helioho.st/add_alert.php", "POST", parameters);
                success = parser.getInt("success");
                if(success == 1) {
                    return  "Notification Saved";
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