package com.example.stock_price;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity //(primaryKeys = {"id", "symbol"})
public class Stock implements Parcelable {

    //@PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    @PrimaryKey
    @NonNull
    public String symbol;

    public String location;

    public double price;

    public Stock()
    {
        name = "";
        symbol = "";
        location = "";
        price = 0;
    }


    public Stock(Stock s){
        this.name = s.name;
        this.location = s.location;
        this.symbol = s.symbol;
        this.price = s.price;
    }

    public Stock(String name, String symbol, String location, double price)
    {
        this.name = name;
        this.symbol = symbol;
        this.location = location;
        this.price = price;
    }

    protected Stock(Parcel in) {
        name = in.readString();
        symbol = in.readString();
        location = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(symbol);
        dest.writeString(location);
        dest.writeDouble(price);
    }
}
