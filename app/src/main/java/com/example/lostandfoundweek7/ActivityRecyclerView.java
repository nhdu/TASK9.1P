package com.example.lostandfoundweek7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ActivityRecyclerView extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_items);
        recyclerView = findViewById(R.id.recyclerViewItems);
        loadItemList();
        setAdapter();

    }

    private void loadItemList() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        ItemData.itemList.clear();
        sqLiteManager.populateItemListArray();
    }

    private void setAdapter() {
        RecyclerViewItemAdapter adapter = new RecyclerViewItemAdapter(ItemData.itemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItemList();
        setAdapter();
    }
}