package com.example.stock_price;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.List;

public class SavedListActivity extends AppCompatActivity implements DatabaseManager.DatabaseListener, StockRecyclerView.StockClickListener {

    ArrayList<Stock> listFromDB;
    RecyclerView stockRecyclerView;
    StockRecyclerView stockAdapter;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list);

        stockRecyclerView = findViewById(R.id.savedListRecycler);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbManager = ((myApp)getApplication()).getDatabaseManager();
        dbManager.listener = this;

        dbManager.getDBInstance(this);
        dbManager.getAllStocks();

    }


    @Override
    public void StockListener(List<Stock> stocks) {
        listFromDB = new ArrayList<>(stocks);
        stockAdapter = new StockRecyclerView(this, listFromDB);
        stockRecyclerView.setAdapter(stockAdapter);

    }

    @Override
    public void stockClicked(Stock selectedStock) {
        Intent intent = new Intent(this, StockInfoActivity.class);
        intent.putExtra("sentStock", selectedStock);
        intent.putExtra("activityNum", 2);
        startActivity(intent);
    }
}