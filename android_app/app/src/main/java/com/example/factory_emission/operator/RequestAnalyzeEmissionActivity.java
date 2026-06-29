package com.example.factory_emission.operator;

import android.content.Intent;
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

import com.example.factory_emission.BaseActivity;
import com.example.factory_emission.JsonParser;
import com.example.factory_emission.R;
import com.example.factory_emission.SettingsActivity;
import com.example.factory_emission.admin.AdminActivity;
import com.example.factory_emission.models.Admin;
import com.example.factory_emission.models.Operator;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.util.ArrayList;
import java.util.List;

public class RequestAnalyzeEmissionActivity extends BaseActivity {

    String strCo2, strSo2, strNo, strPm, strFactoryID, strPredict, strMsg;
    String strFactoryName, strIndustryType, strFuelType, strCity, strLongitude, strLatitude, strProduction;
    ProgressBar progress;
    JsonParser jsonParser = new JsonParser();
    AppCompatButton btnSave;
    TextInputLayout txtResult, txtPredict;
    ImageView imgBack, imgHome, imgProfile, imgSettings;
    TextView txtHome, txtProfile, txtSettings;
    private TFLiteHelper tfliteHelper;
    float[] mean  = {4.9375f, 6.967f, 2.587f,4.591f,3.4655f,23.6539750695f,42.579150209f,1.988f,2.033f,1198035.9626875f,130901.90162875f,1625.544335f,1299.986538125f, 349.86576749999995f,540810.90878375f,2019.909f};
    float[] std  = {3.1313884699921855f, 4.273278717799718f, 1.7071704660050795f,2.8028055587214746f,2.218740577444781f,3.6747412989184474f
            ,4.488466986053152f,1.4116855173869285f,1.3963921369013792f,579538.0338822411f,67324.989866995f,746.5305349745215f
            ,587.9788068680194f, 169.56841369504727f,243937.35394993046f,2.580061820964761f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_analyze_emission);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //// read the emission data
        strFactoryID = getIntent().getStringExtra("id");
        strCo2 = getIntent().getStringExtra("co2");
        strSo2 = getIntent().getStringExtra("so2");
        strNo = getIntent().getStringExtra("no");
        strPm = getIntent().getStringExtra("pm");
        strProduction = getIntent().getStringExtra("production");
        strFactoryName = getIntent().getStringExtra("name");
        strIndustryType = getIntent().getStringExtra("industry");
        strFuelType = getIntent().getStringExtra("fuel");
        strCity = getIntent().getStringExtra("city");
        strLatitude = getIntent().getStringExtra("lat");
        strLongitude = getIntent().getStringExtra("lng");

        imgBack = findViewById(R.id.imgBack);
        progress = findViewById(R.id.progress);
        txtResult = findViewById(R.id.txtResult);
        txtPredict = findViewById(R.id.txtPredict);
        btnSave = findViewById(R.id.btnSave);
        imgHome = findViewById(R.id.imgHome);
        imgProfile = findViewById(R.id.imgProfile);
        imgSettings = findViewById(R.id.imgSettings);
        txtHome = findViewById(R.id.txtHome);
        txtProfile = findViewById(R.id.txtProfile);
        txtSettings = findViewById(R.id.txtSettings);

        getIndustryCode(strIndustryType);
        getFuelCode(strFuelType);
        getCityCode(strCity);
        getRegionCode(strCity);

        // Initialize with the name of the file in your assets folder
       tfliteHelper = new TFLiteHelper(this, "emission_model.tflite");
            // Example: If your model expects 4 features (Age, Income, etc.)
            float[] inputFeatures = new float[16];
            inputFeatures[0] = 4.9375f;
            inputFeatures[1] = getIndustryCode(strIndustryType);
            inputFeatures[2] = getIndustryCode(strIndustryType);
            inputFeatures[3] = getCityCode(strCity);
            inputFeatures[4] = getRegionCode(strCity);
            inputFeatures[5] = Float.parseFloat(strLatitude);
            inputFeatures[6] = Float.parseFloat(strLongitude);
            inputFeatures[7] = getFuelCode(strFuelType);
            inputFeatures[8] = 2.033f;
            inputFeatures[9] = Float.parseFloat(strProduction);
            inputFeatures[10] = Float.parseFloat(strCo2);
            inputFeatures[11] = Float.parseFloat(strSo2);
            inputFeatures[12] = Float.parseFloat(strNo);
            inputFeatures[13] = Float.parseFloat(strPm);
            inputFeatures[14] = 540810.90878375f;
            inputFeatures[15] = 2019.909f;
       ///// call scalar function
        float[] scaledFeatures = scaleFeatures(inputFeatures, mean, std);
        float[][] result = tfliteHelper.doInference(scaledFeatures);
        // To get the category with the highest probability:
        int maxIndex = 0;
        for (int i = 0; i < result[0].length; i++) {
            if (result[0][i] > result[0][maxIndex]) {
                maxIndex = i;
            }
        }

// result[0][maxIndex] is your final prediction score!
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
    }
    public float getIndustryCode(String industry) {
        switch (industry.toLowerCase()) {
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
        switch (fuel.toLowerCase()) {
            case "diesel": return 0.0f;
            case "electricity": return 1.0f;
            case "heavy fuel oil": return 2.0f;
            case "mixed": return 3.0f;
            case "natural gas": return 4.0f;
            default: return -1.0f; // Unknown category
        }
    }
    public float getCityCode(String city) {
        switch (city.toLowerCase()) {
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
        switch (city.toLowerCase()) {
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
                parameters.add(new BasicNameValuePair("company", strFactoryName));
                parameters.add(new BasicNameValuePair("industry", strIndustryType));
                parameters.add(new BasicNameValuePair("zone", strIndustryType));
                parameters.add(new BasicNameValuePair("city", strCity));
                parameters.add(new BasicNameValuePair("region", strCity));
                parameters.add(new BasicNameValuePair("lat", strLatitude));
                parameters.add(new BasicNameValuePair("lng", strLongitude));
                parameters.add(new BasicNameValuePair("fuel", strFuelType));
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