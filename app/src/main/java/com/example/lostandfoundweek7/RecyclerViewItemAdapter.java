package com.example.lostandfoundweek7;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerViewItemAdapter.MyViewHolder> {
    private ArrayList<ItemData> itemList;

    public RecyclerViewItemAdapter(ArrayList<ItemData> itemList)
    {
        this.itemList = itemList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView itemName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.recyclerViewItemName);
        }
    }
    @NonNull
    @Override
    public RecyclerViewItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewItemAdapter.MyViewHolder holder, int position) {
        String description = itemList.get(position).getDescription();
        holder.itemName.setText(description);
        holder.itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ItemDetailActivity.class);
                intent.putExtra("name",itemList.get(holder.getAdapterPosition()).getName());
                intent.putExtra("phone",itemList.get(holder.getAdapterPosition()).getPhone());
                intent.putExtra("description",itemList.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("date",itemList.get(holder.getAdapterPosition()).getDate());
                intent.putExtra("location",itemList.get(holder.getAdapterPosition()).getLocation());
                intent.putExtra("type",itemList.get(holder.getAdapterPosition()).getType());
                intent.putExtra("id",itemList.get(holder.getAdapterPosition()).getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
