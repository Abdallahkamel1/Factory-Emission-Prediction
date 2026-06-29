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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FactoryAdapter extends RecyclerView.Adapter<FactoryAdapter.ViewHolder> {

    private List<Factory> mData;
    public Context context;
    JsonParser jParser = new JsonParser();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";
    String url_delete_factory = "https://legalcounsel14441.helioho.st/deleteFactory.php";

    public FactoryAdapter(Context context, List<Factory> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.factory_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Factory data = mData.get(position);
        holder.txtName.setText(data.getFactoryName());
        holder.txtIndustry.setText(data.getIndustryType());
        holder.txtCity.setText(data.getFactoryCity());
        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog card_factory = new Dialog(context);
                card_factory.setContentView(R.layout.card_factory);
                ImageView btnClose = card_factory.findViewById(R.id.btn_close);
                TextView card_txtName = card_factory.findViewById(R.id.txtName);
                TextView card_txtIndustry = card_factory.findViewById(R.id.txtIndustry);
                TextView card_txtCity = card_factory.findViewById(R.id.txtCity);
                TextView card_txtFuel = card_factory.findViewById(R.id.txtFuel);
                TextView card_btnLocation = card_factory.findViewById(R.id.btnLocation);

                card_factory.show();
                card_txtName.setText(data.getFactoryName());
                card_txtIndustry.setText(data.getIndustryType());
                card_txtCity.setText(data.getFactoryCity());
                card_txtFuel.setText(data.getFuelType());
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card_factory.hide();
                    }
                });
                card_btnLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), FactoryLocationActivity.class);
                        i.putExtra("name", data.getFactoryName());
                        i.putExtra("lat", data.getFactoryLatitude());
                        i.putExtra("lng", data.getFactoryLongitude());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
            }
        });
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
                        DeleteFactory(view.getContext(), data.getFactoryID());
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
                        DeleteFactory(view.getContext(), data.getFactoryID());
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
        holder.txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UpdateFactoryActivity.class);
                i.putExtra("id", data.getFactoryID());
                i.putExtra("name", data.getFactoryName());
                i.putExtra("industry", data.getIndustryType());
                i.putExtra("city", data.getFactoryCity());
                i.putExtra("fuel", data.getFuelType());
                i.putExtra("lat", data.getFactoryLatitude());
                i.putExtra("lng", data.getFactoryLongitude());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UpdateFactoryActivity.class);
                i.putExtra("id", data.getFactoryID());
                i.putExtra("name", data.getFactoryName());
                i.putExtra("industry", data.getIndustryType());
                i.putExtra("city", data.getFactoryCity());
                i.putExtra("fuel", data.getFuelType());
                i.putExtra("lat", data.getFactoryLatitude());
                i.putExtra("lng", data.getFactoryLongitude());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddOperatorActivity.class);
                i.putExtra("id", data.getFactoryID());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddOperatorActivity.class);
                i.putExtra("id", data.getFactoryID());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName, txtIndustry, txtCity, txtUpdate, txtDelete, txtAdd;
        public ImageView imgAdd, imgDelete, imgUpdate;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtUpdate = itemView.findViewById(R.id.txtUpdate);
            txtDelete = itemView.findViewById(R.id.txtDelete);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtIndustry = itemView.findViewById(R.id.txtIndustry);
            txtAdd = itemView.findViewById(R.id.txtAdd);
            imgAdd = itemView.findViewById(R.id.imgAdd);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
    public void DeleteFactory(Context context1, String id){
        class DeleteFCTRY extends AsyncTask<String, String, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context1, "Deleting Factory ..", null,true,true);
            }
            protected String doInBackground(String... args) {
                int success;
                try {
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("factoryID", id));
                    JSONObject json = jParser.makeHttpRequest(url_delete_factory, "POST", params);
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        loading.dismiss();
                        Intent intent = new Intent(context1, ManageFactoriesActivity.class);
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
        DeleteFCTRY ui = new DeleteFCTRY();
        ui.execute();
    }
}
