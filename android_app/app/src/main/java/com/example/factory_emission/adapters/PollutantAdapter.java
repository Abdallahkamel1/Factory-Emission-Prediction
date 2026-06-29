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
import com.example.factory_emission.admin.AddPollutantActivity;
import com.example.factory_emission.admin.ManageFactoriesActivity;
import com.example.factory_emission.admin.ManagePollutantsActivity;
import com.example.factory_emission.admin.UpdatePollutantActivity;
import com.example.factory_emission.models.PollutantData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PollutantAdapter extends RecyclerView.Adapter<PollutantAdapter.ViewHolder> {

    private List<PollutantData> mData;
    public Context context;
    JsonParser jParser = new JsonParser();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";
    String url_delete_pollutant = "https://legalcounsel14441.helioho.st/deletePollutant.php";

    public PollutantAdapter(Context context, List<PollutantData> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pollutant_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PollutantData data = mData.get(position);
        holder.txtName.setText(data.getPollName());
        holder.txtSafe.setText(data.getPollSafe());
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
                        DeletePollutant(view.getContext(), data.getPollID());
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
                        DeletePollutant(view.getContext(), data.getPollID());
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
        holder.txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UpdatePollutantActivity.class);
                i.putExtra("id", data.getPollID());
                i.putExtra("name", data.getPollName());
                i.putExtra("safe", data.getPollSafe());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UpdatePollutantActivity.class);
                i.putExtra("id", data.getPollID());
                i.putExtra("name", data.getPollName());
                i.putExtra("safe", data.getPollSafe());
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

        public TextView txtName, txtSafe, txtUpdate, txtDelete;
        public ImageView imgDelete, imgUpdate;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtUpdate = itemView.findViewById(R.id.txtUpdate);
            txtDelete = itemView.findViewById(R.id.txtDelete);
            txtSafe = itemView.findViewById(R.id.txtSafe);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
    public void DeletePollutant(Context context1, String id){
        class DeletePOLL extends AsyncTask<String, String, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context1, "Deleting Pollutant ..", null,true,true);
            }
            protected String doInBackground(String... args) {
                int success;
                try {
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("pollID", id));
                    JSONObject json = jParser.makeHttpRequest(url_delete_pollutant, "POST", params);
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        loading.dismiss();
                        Intent intent = new Intent(context1, ManagePollutantsActivity.class);
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
        DeletePOLL ui = new DeletePOLL();
        ui.execute();
    }
}
