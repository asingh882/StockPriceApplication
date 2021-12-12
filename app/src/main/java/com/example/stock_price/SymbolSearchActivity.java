package com.example.stock_price;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class SymbolSearchActivity extends AppCompatActivity implements NetworkingService.NetworkingListener{

    ArrayList<Stock> stocks = new ArrayList<>();
    SymbolRecyclerView symbolAdapter;
    RecyclerView symbolRecyclerView;
    NetworkingService networkingManager;
    JsonService jsonService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symbol_search);


        networkingManager = ((myApp)getApplication()).getNetworkingService();
        jsonService = ((myApp)getApplication()).getJsonService();

        networkingManager.listener = this;

        symbolRecyclerView = findViewById(R.id.symbolSearchList);
        symbolRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        symbolAdapter = new SymbolRecyclerView(this, stocks);
        symbolRecyclerView.setAdapter(symbolAdapter);
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

        searchView.setQueryHint("Search For a Symbol");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Query", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() > 0)
                    networkingManager.searchForSymbol(newText);
                else {
                    stocks = new ArrayList<>(0);
                    symbolAdapter.stockList = stocks;
                    symbolAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        return true;
    }


    @Override
    public void dataListener(String jsonString) {

        stocks = jsonService.getSymbolFromJSON(jsonString);
        symbolAdapter = new SymbolRecyclerView(this, stocks);
        symbolRecyclerView.setAdapter(symbolAdapter);
        symbolAdapter.notifyDataSetChanged();
    }
}