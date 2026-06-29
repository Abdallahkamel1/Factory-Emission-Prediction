package com.example.factory_emission.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.factory_emission.R;
import com.example.factory_emission.models.TableItem;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {

    List<TableItem> list;

    public TableAdapter(List<TableItem> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView col1, col2, col3;

        public ViewHolder(View view) {
            super(view);

            col1 = view.findViewById(R.id.col1);
            col2 = view.findViewById(R.id.col2);
            col3 = view.findViewById(R.id.col3);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TableItem item = list.get(position);

        holder.col1.setText(item.col1);
        holder.col2.setText(item.col2);
        holder.col3.setText(item.col3);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
