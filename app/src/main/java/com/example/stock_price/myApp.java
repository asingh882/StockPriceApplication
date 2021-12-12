package com.example.stock_price;

import android.app.Application;

public class myApp extends Application {

    private NetworkingService networkingService = new NetworkingService();
    private JsonService jsonService = new JsonService();
    private DatabaseManager databaseManager = new DatabaseManager();

    public JsonService getJsonService() {
        return jsonService;
    }

    public NetworkingService getNetworkingService() {
        return networkingService;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
