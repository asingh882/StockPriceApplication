package com.example.stock_price;

import android.content.Context;
import android.os.Looper;
import android.os.Handler;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DatabaseManager {

    static StockDB db;
    ExecutorService databaseExecuter = Executors.newFixedThreadPool(4);
    Handler db_handler = new Handler(Looper.getMainLooper());

    interface DatabaseListener {
        void StockListener(List<Stock> stocks);
    }

    public DatabaseListener listener;

    private static void BuildDBInstance(Context context) {

        db = Room.databaseBuilder(context, StockDB.class, "Stock_db").fallbackToDestructiveMigration().build();
    }

    public static StockDB getDBInstance(Context context) {
        if(db == null) {
            BuildDBInstance(context);
        }
        return db;
    }

    public void insertNewStock(Stock s) {
        databaseExecuter.execute((new Runnable() {
            @Override
            public void run() {
                db.getStockDAO().insertNewStock(s);
            }
        }));
    }

    public void deleteStock(Stock s) {
        databaseExecuter.execute((new Runnable() {
            @Override
            public void run() {
                db.getStockDAO().deleteStock(s);
            }
        }));
    }

    public void getAllStocks() {
        databaseExecuter.execute(new Runnable() {
            @Override
            public void run() {
                List<Stock> list  = db.getStockDAO().getAllStocks();
                db_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.StockListener(list);
                    }
                });
            }
        });
    }

}
