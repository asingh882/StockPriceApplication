package com.example.stock_price;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {

    public ArrayList<Stock> getSymbolFromJSON(String json) {

        ArrayList<Stock> arrayList = new ArrayList<>(1);

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonStocks = jsonObject.getJSONArray("bestMatches");
            for(int i = 0; i < jsonStocks.length(); i++)
            {
                JSONObject stock = jsonStocks.getJSONObject(i);
                String symbol = stock.getString("1. symbol");
                String stockName = stock.getString("2. name");
                String region = stock.getString("4. region");

                if(stockName.length() > 20)
                {
                    stockName = stockName.substring(0, 18) + "...";
                }

                Stock s = new Stock(stockName, symbol, region, 0);
                arrayList.add(s);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    public double getStockFromJSON(String json) {
        double price = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject stockInfo = jsonObject.getJSONObject("Global Quote");
            price = stockInfo.getDouble("05. price");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return price;
    }
}
