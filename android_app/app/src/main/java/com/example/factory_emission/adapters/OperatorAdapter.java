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
import com.example.factory_emission.admin.ManageFactoriesActivity;
import com.example.factory_emission.admin.ManageOperatorsActivity;
import com.example.factory_emission.models.OperatorData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OperatorAdapter extends RecyclerView.Adapter<OperatorAdapter.ViewHolder> {

    private List<OperatorData> mData;
    public Context context;
    JsonParser jParser = new JsonParser();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";
    String url_delete_operator = "https://legalcounsel14441.helioho.st/deleteOperator.php";

    public OperatorAdapter(Context context, List<OperatorData> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.operator_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OperatorData data = mData.get(position);
        holder.txtName.setText(data.getOperatorName());
        holder.txtFactory.setText(data.getOperatorFactory());
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
                        DeleteOperator(view.getContext(), data.getOperatorID());
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
                        DeleteOperator(view.getContext(), data.getOperatorID());
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog card_operator = new Dialog(context);
                card_operator.setContentView(R.layout.card_operator);
                ImageView btnClose = card_operator.findViewById(R.id.btn_close);
                TextView card_txtName = card_operator.findViewById(R.id.txtName);
                TextView card_txtEmail = card_operator.findViewById(R.id.txtEmail);
                TextView card_txtPhone = card_operator.findViewById(R.id.txtPhone);
                TextView card_txtFactory = card_operator.findViewById(R.id.txtFactory);

                card_operator.show();
                card_txtName.setText(data.getOperatorName());
                card_txtEmail.setText(data.getOperatorEmail());
                card_txtPhone.setText(data.getOperatorPhone());
                card_txtFactory.setText(data.getOperatorFactory());
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card_operator.hide();
                    }
                });
            }
        });
        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog card_operator = new Dialog(context);
                card_operator.setContentView(R.layout.card_operator);
                ImageView btnClose = card_operator.findViewById(R.id.btn_close);
                TextView card_txtName = card_operator.findViewById(R.id.txtName);
                TextView card_txtEmail = card_operator.findViewById(R.id.txtEmail);
                TextView card_txtPhone = card_operator.findViewById(R.id.txtPhone);
                TextView card_txtFactory = card_operator.findViewById(R.id.txtFactory);

                card_operator.show();
                card_txtName.setText(data.getOperatorName());
                card_txtEmail.setText(data.getOperatorEmail());
                card_txtPhone.setText(data.getOperatorPhone());
                card_txtFactory.setText(data.getOperatorFactory());
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card_operator.hide();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName, txtFactory, txtDelete, txtView;
        public ImageView imgAdd, imgDelete, imgView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtView = itemView.findViewById(R.id.txtView);
            txtDelete = itemView.findViewById(R.id.txtDelete);
            txtFactory = itemView.findViewById(R.id.txtFactory);
            imgView = itemView.findViewById(R.id.imgView);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
    public void DeleteOperator(Context context1, String id){
        class DeleteOPR extends AsyncTask<String, String, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context1, "Deleting Operator ..", null,true,true);
            }
            protected String doInBackground(String... args) {
                int success;
                try {
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("operatorID", id));
                    JSONObject json = jParser.makeHttpRequest(url_delete_operator, "POST", params);
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        loading.dismiss();
                        Intent intent = new Intent(context1, ManageOperatorsActivity.class);
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
        DeleteOPR ui = new DeleteOPR();
        ui.execute();
    }
}
