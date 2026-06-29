package com.example.factory_emission.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.factory_emission.JsonParser;
import com.example.factory_emission.R;
import com.example.factory_emission.admin.AddOperatorActivity;
import com.example.factory_emission.admin.FactoryLocationActivity;
import com.example.factory_emission.admin.ManageFactoriesActivity;
import com.example.factory_emission.admin.UpdateFactoryActivity;
import com.example.factory_emission.models.Factory;
import com.example.factory_emission.models.PublicFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PublicFactoryAdapter extends RecyclerView.Adapter<PublicFactoryAdapter.ViewHolder> {

    private List<PublicFactory> mData;
    public Context context;
    JsonParser jParser = new JsonParser();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";

    public PublicFactoryAdapter(Context context, List<PublicFactory> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.public_factory_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PublicFactory data = mData.get(position);
        holder.txtName.setText(data.getCompanyName() + "(" + data.getFactoryName() + ")");
        holder.txtEmission.setText("Emission Level: " + data.getFactoryState());
        holder.txtIndustry.setText(data.getFactoryIndustry());
        holder.txtCity.setText(data.getFactoryCity());
        holder.txtYear.setText(data.getStateYear());
        holder.txtFuel.setText(data.getFactoryFuel());
        //holder.txtCO.setText("CO Level \n" + data.getStateCO());
        //holder.txtSO.setText("SO Level \n" + data.getStateSO());
        //holder.txtNO.setText("NO Level \n" + data.getStateNO());
        //holder.txtPM.setText("PM Level \n" + data.getStatePM());
        holder.txtCO.setText("CO Level \n" + Math.round(Double.parseDouble(data.getStateCO()) / 1000 * 100.0) / 100.0);
        holder.txtSO.setText("SO Level \n" + Math.round(Double.parseDouble(data.getStateSO()) / 1000 * 100.0) / 100.0);
        holder.txtNO.setText("NO Level \n" + Math.round(Double.parseDouble(data.getStateNO()) / 1000 * 100.0) / 100.0);
        holder.txtPM.setText("PM Level \n" + Math.round(Double.parseDouble(data.getStatePM()) / 1000 * 100.0) / 100.0);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName, txtEmission, txtCity, txtYear, txtFuel, txtIndustry, txtCO, txtSO, txtNO, txtPM;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmission = itemView.findViewById(R.id.txtEmission);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtFuel = itemView.findViewById(R.id.txtFuel);
            txtIndustry = itemView.findViewById(R.id.txtIndustry);
            txtCO = itemView.findViewById(R.id.txtCO);
            txtSO = itemView.findViewById(R.id.txtSO);
            txtNO = itemView.findViewById(R.id.txtNO);
            txtPM = itemView.findViewById(R.id.txtPM);
        }
    }
}
