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
import com.example.factory_emission.admin.ManageOperatorsActivity;
import com.example.factory_emission.models.Notes;
import com.example.factory_emission.operator.NotificationsActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Notes> mData;
    public Context context;
    JsonParser jParser = new JsonParser();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";
    String url_delete_note = "https://legalcounsel14441.helioho.st/deleteNote.php";

    public NoteAdapter(Context context, List<Notes> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notes data = mData.get(position);
        holder.txtName.setText("Emission Prediction: " + data.getEmissionValue());
        holder.txtDate.setText(data.getAlertDate());
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
                        DeleteNote(view.getContext(), data.getAlertID());
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
                        DeleteNote(view.getContext(), data.getAlertID());
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog card_note = new Dialog(context);
                card_note.setContentView(R.layout.card_note);
                ImageView btnClose = card_note.findViewById(R.id.btn_close);
                TextView card_txtPredict = card_note.findViewById(R.id.txtPrediction);
                TextView card_txtMsg = card_note.findViewById(R.id.txtMsg);

                card_note.show();
                card_txtPredict.setText(data.getEmissionValue());
                card_txtMsg.setText(data.getEmissionMsg());
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card_note.hide();
                    }
                });
            }
        });
        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog card_note = new Dialog(context);
                card_note.setContentView(R.layout.card_note);
                ImageView btnClose = card_note.findViewById(R.id.btn_close);
                TextView card_txtPredict = card_note.findViewById(R.id.txtPrediction);
                TextView card_txtMsg = card_note.findViewById(R.id.txtMsg);

                card_note.show();
                card_txtPredict.setText(data.getEmissionValue());
                card_txtMsg.setText(data.getEmissionMsg());
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card_note.hide();
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

        public TextView txtName, txtDate, txtDelete, txtView;
        public ImageView imgDelete, imgView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtView = itemView.findViewById(R.id.txtView);
            txtDelete = itemView.findViewById(R.id.txtDelete);
            txtDate = itemView.findViewById(R.id.txtDate);
            imgView = itemView.findViewById(R.id.imgView);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
    public void DeleteNote(Context context1, String id){
        class DeleteNOTES extends AsyncTask<String, String, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context1, "Deleting Notification ..", null,true,true);
            }
            protected String doInBackground(String... args) {
                int success;
                try {
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("noteID", id));
                    JSONObject json = jParser.makeHttpRequest(url_delete_note, "POST", params);
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        loading.dismiss();
                        Intent intent = new Intent(context1, NotificationsActivity.class);
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
        DeleteNOTES ui = new DeleteNOTES();
        ui.execute();
    }
}
