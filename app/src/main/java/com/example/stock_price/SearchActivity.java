package com.example.stock_price;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements NetworkingService.NetworkingListener, StockRecyclerView.StockClickListener{

    ArrayList<Stock> stocks = new ArrayList<Stock>();
    StockRecyclerView stockAdapter;
    RecyclerView stockRecyclerView;
    NetworkingService networkingManager;
    JsonService jsonService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        networkingManager = ((myApp)getApplication()).getNetworkingService();
        jsonService = ((myApp)getApplication()).getJsonService();

        networkingManager.listener =  this;

        stockRecyclerView = findViewById(R.id.searchList);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        stockAdapter = new StockRecyclerView(this, stocks);
        stockRecyclerView.setAdapter(stockAdapter);
        setTitle("Search for new Stock");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);

        MenuItem searchViewMenuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) searchViewMenuItem.getActionView();
        String searchFor = searchView.getQuery().toString();
        if(!searchFor.isEmpty())
        {
            searchView.setIconified(false);
            searchView.setQuery(searchFor, false);
        }

        searchView.setQueryHint("Search For a Stock");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Query", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() > 1)
                    networkingManager.searchForSymbol(newText);
                else
                {
                    stocks = new ArrayList<>(0);
                    stockAdapter.stockList = stocks;
                    stockAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void stockClicked(Stock selectedStock) {
        Intent intent = new Intent(this, StockInfoActivity.class);
        intent.putExtra("sentStock", selectedStock);
        intent.putExtra("activityNum", 1);
        startActivity(intent);
    }

    @Override
    public void dataListener(String jsonString){
        stocks = jsonService.getSymbolFromJSON(jsonString);
        stockAdapter = new StockRecyclerView(this, stocks);
        stockRecyclerView.setAdapter(stockAdapter);
        stockAdapter.notifyDataSetChanged();
    }
}
