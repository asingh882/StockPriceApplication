package com.example.stock_price;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class NetworkingService {

    String symbolUrl = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=";
    String stockPriceUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=";
    String apiKey = "&apikey=DSOYH1PC9U81B9KN";
    String apiKey2 = "&apikey=UY13A0YB4LFLMU3W";
    String apiKey3 = "&apikey=PPL8K5EWRQZRN58P";

    static int keyIncrementor = 0;

    public static ExecutorService networkExecuterService = Executors.newFixedThreadPool(4);
    public static Handler networkingHandler = new Handler(Looper.getMainLooper());

    interface NetworkingListener {
        void dataListener(String jsonString);
    }

    public NetworkingListener listener;
    public void searchForStock(String stockChars)
    {
        String url = stockPriceUrl + stockChars;
        if(keyIncrementor == 0) {
            url += apiKey;
            keyIncrementor++;
        }
        else if(keyIncrementor == 1) {
            url += apiKey2;
            keyIncrementor++;
        }
        else {
            url += apiKey3;
            keyIncrementor = 0;
        }
        connect(url);
    }

    public void searchForSymbol(String symbolChars)
    {
        String url = symbolUrl + symbolChars;
        if(keyIncrementor == 0) {
            url += apiKey;
            keyIncrementor++;
        }
        else if(keyIncrementor == 1) {
            url += apiKey2;
            keyIncrementor++;
        }
        else {
            url += apiKey3;
            keyIncrementor = 0;
        }
        connect(url);
    }


    private void connect(String url) {
        networkExecuterService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try{
                    String jsonData = "";
                    URL urlObj = new URL(url);
                    httpURLConnection = (HttpURLConnection) urlObj.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Conent-Type", "application/json");

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int inputStreamData = 0;

                    while( (inputStreamData = reader.read()) != -1) {
                        char current = (char) inputStreamData;
                        jsonData += current;
                    }

                    final String finalData = jsonData;

                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.dataListener(finalData);
                        }
                    });

                } catch(MalformedURLException e) {
                    e.printStackTrace();
                } catch(IOException e) {
                    e.printStackTrace();
                } finally {
                    httpURLConnection.disconnect();
                }
            }
        });
    }

}
