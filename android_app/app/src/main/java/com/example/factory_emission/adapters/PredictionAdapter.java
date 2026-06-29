package com.example.factory_emission.adapters;

import android.app.AlertDialog;
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
import com.example.factory_emission.admin.ManageOperatorsActivity;
import com.example.factory_emission.models.Prediction;
import com.example.factory_emission.operator.FactoryEmissionReportActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PredictionAdapter extends RecyclerView.Adapter<PredictionAdapter.ViewHolder> {

    private List<Prediction> mData;
    public Context context;
    JsonParser jParser = new JsonParser();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";
    String url_delete_prediction = "https://legalcounsel14441.helioho.st/deletePrediction.php";

    public PredictionAdapter(Context context, List<Prediction> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Prediction data = mData.get(position);
        holder.txtName.setText(data.getStrPredictState());
        holder.txtDate.setText(data.getStrPredictDate());
        holder.txtCO.setText(data.getStrCOLevel());
        holder.txtSO.setText(data.getStrSOLevel());
        holder.txtNO.setText(data.getStrNOLevel());
        holder.txtPM.setText(data.getStrPMLevel());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.confirm_delete);
                builder.setMessage(R.string.confirm_delete2);
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Remove the item from the RecyclerView
                        DeletePrediction(view.getContext(), data.getStrPredictID());
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.confirm_delete);
                builder.setMessage(R.string.confirm_delete2);
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Remove the item from the RecyclerView
                        DeletePrediction(view.getContext(), data.getStrPredictID());
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName, txtDate, txtDelete, txtCO, txtSO, txtNO, txtPM;
        public ImageView imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtCO = itemView.findViewById(R.id.txtCo2);
            txtDelete = itemView.findViewById(R.id.txtDelete);
            txtDate = itemView.findViewById(R.id.txtDate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            txtSO = itemView.findViewById(R.id.txtSo2);
            txtNO = itemView.findViewById(R.id.txtNO);
            txtPM = itemView.findViewById(R.id.txtPM);
        }
    }
    public void DeletePrediction(Context context1, String id){
        class DeletePRD extends AsyncTask<String, String, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context1, "Deleting Prediction ..", null,true,true);
            }
            protected String doInBackground(String... args) {
                int success;
                try {
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("predictID", id));
                    JSONObject json = jParser.makeHttpRequest(url_delete_prediction, "POST", params);
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        loading.dismiss();
                        Intent intent = new Intent(context1, FactoryEmissionReportActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        return json.getString(TAG_MESSAGE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        DeletePRD ui = new DeletePRD();
        ui.execute();
    }
}
