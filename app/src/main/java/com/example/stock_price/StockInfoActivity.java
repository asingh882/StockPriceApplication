package com.example.stock_price;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StockInfoActivity extends AppCompatActivity implements NetworkingService.NetworkingListener {


    NetworkingService networkingService;
    JsonService jsonService;
    Stock stock;

    DatabaseManager dbManager;
    StockDB db;

    int whereIsCalled;
    TextView name;
    TextView symbol;
    TextView price;
    TextView location;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);
        networkingService = ((myApp)getApplication()).getNetworkingService();
        jsonService = ((myApp)getApplication()).getJsonService();

        networkingService.listener = this;

        stock = new Stock((Stock) getIntent().getExtras().getParcelable("sentStock"));
        whereIsCalled = getIntent().getExtras().getInt("activityNum");

        networkingService.searchForStock(stock.getSymbol());

        db = DatabaseManager.getDBInstance(this);
        dbManager = ((myApp)((myApp) getApplication())).getDatabaseManager();

        name = (TextView) findViewById(R.id.nameOfStock);
        symbol = (TextView) findViewById(R.id.symbolOfStock);
        price = (TextView) findViewById(R.id.priceOfStock);
        location = (TextView) findViewById(R.id.locationOfStock);
        saveButton = (Button) findViewById(R.id.stockInfoButton);

        if(whereIsCalled == 2)
        {
            saveButton.setText(R.string.remove_button_text);
        }
    }

    @Override
    public void dataListener(String jsonString) {
        double p = jsonService.getStockFromJSON(jsonString);
        stock.setPrice(p);
        name.setText(stock.getName());
        symbol.setText(stock.getSymbol());
        price.setText(String.valueOf(stock.getPrice()));
        location.setText(stock.getLocation());
    }

    public void saveData(View view) {
        if(whereIsCalled == 2)
        {
            dbManager.deleteStock(stock);
            Toast.makeText(this, "Stock has been Removed from Watchlist", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, SavedListActivity.class);
            startActivity(intent);
        }
        else
        {
            dbManager.insertNewStock(stock);
            Toast.makeText(this, "This Stock has been added to Watchlist", Toast.LENGTH_LONG).show();
        }
    }
}
