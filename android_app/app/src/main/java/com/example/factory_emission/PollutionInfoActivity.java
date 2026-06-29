package com.example.factory_emission;

import android.content.Intent;
import android.os.Bundle;

import com.example.factory_emission.adapters.TableAdapter;
import com.example.factory_emission.models.TableItem;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.factory_emission.databinding.ActivityPollutionInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class PollutionInfoActivity extends BaseActivity {
    RecyclerView recyclerView;
    ImageView imgBack;
    TableAdapter adapter;
    List<TableItem> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pollution_info);

        recyclerView = findViewById(R.id.recyclerTable);
        imgBack = findViewById(R.id.imgBack);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // predefined data
        dataList.add(new TableItem("Item", "Description", "Impacts"));
        dataList.add(new TableItem("Petrochemicals", "Production of chemicals, plastics, and fertilizers", "Industrial emissions and chemical pollutants"));
        dataList.add(new TableItem("Oil & Gas", "Extraction, refining, and processing of petroleum", "CO₂ and other combustion emissions"));
        dataList.add(new TableItem("Cement", "Manufacturing cement for construction", "High CO₂ emissions from kilns"));
        dataList.add(new TableItem("Mining", "Extraction of minerals and metals", "Dust and particulate pollution"));
        dataList.add(new TableItem("Steel & Metal", "Production of iron, steel, and metal products", "High energy consumption and NOx emissions"));
        dataList.add(new TableItem("Food Processing", "Manufacturing food and agricultural products", "Moderate industrial emissions"));
        dataList.add(new TableItem("Paper & Textile", "Production of paper goods and fabrics", "Chemical waste and air pollution"));
        dataList.add(new TableItem("CO₂ (Carbon Dioxide)", "Gas produced during fuel combustion and industrial processes", "Contributes to climate change"));
        dataList.add(new TableItem("SO₂ (Sulfur Dioxide)", "Gas produced by burning sulfur-containing fuels", "Causes acid rain and respiratory problems"));
        dataList.add(new TableItem("NOx (Nitrogen Oxides)", "Formed during high-temperature combustion in factories", "Contributes to smog and ozone formation"));
        dataList.add(new TableItem("PM (Particulate Matter)", "Tiny particles released from industrial processes", "Harmful to lungs and cardiovascular health"));

        adapter = new TableAdapter(dataList);
        recyclerView.setAdapter(adapter);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });


    }


}