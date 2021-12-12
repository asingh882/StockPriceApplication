package com.example.stock_price;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StocksDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewStock(Stock s);

    @Delete
    void deleteStock(Stock s);

    @Query("SELECT * FROM Stock")
    List<Stock> getAllStocks();

}
