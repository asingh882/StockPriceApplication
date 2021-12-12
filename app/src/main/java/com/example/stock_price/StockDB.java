package com.example.stock_price;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 2, entities = {Stock.class})
public abstract class StockDB extends RoomDatabase {

    abstract public StocksDAO getStockDAO();

}
