package com.example.stock_price;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements StockRecyclerView.StockClickListener{


    ArrayList<Stock> stockList = new ArrayList<Stock>();
    StockRecyclerView stockAdapter;
    RecyclerView stockRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stockRecyclerView = findViewById(R.id.popularStocks);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        stockList.add(new Stock(getString(R.string.sample_stock1_name), getString(R.string.sample_stock1_symbol), getString(R.string.sample_stock1_location), 0));
        stockList.add(new Stock(getString(R.string.sample_stock2_name), getString(R.string.sample_stock2_symbol), getString(R.string.sample_stock2_location), 0));
        stockList.add(new Stock(getString(R.string.sample_stock3_name), getString(R.string.sample_stock3_symbol), getString(R.string.sample_stock3_location), 0));
        stockList.add(new Stock(getString(R.string.sample_stock4_name), getString(R.string.sample_stock4_symbol), getString(R.string.sample_stock4_location), 0));

        stockAdapter = new StockRecyclerView(this, stockList);
        stockRecyclerView.setAdapter(stockAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         switch (item.getItemId()){
             case R.id.searchSymbol:
                 Intent intent = new Intent(this, SymbolSearchActivity.class);
                 startActivity(intent);
                 break;
             case R.id.saved:
                 Intent newIntent = new Intent(this, SavedListActivity.class);
                 startActivity(newIntent);
                 break;
             case R.id.exitApp:
                 finishAndRemoveTask();
                 break;
         }
         return true;
    }

    public void searchPage(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }

    @Override
    public void stockClicked(Stock selectedStock) {
        Intent intent = new Intent(this, StockInfoActivity.class);
        intent.putExtra("sentStock", selectedStock);
        startActivity(intent);
    }
}