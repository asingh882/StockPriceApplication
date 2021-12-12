package com.example.stock_price;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SymbolRecyclerView extends RecyclerView.Adapter<SymbolRecyclerView.SymbolviewHolder>{

    private Context mContext;
    public ArrayList<Stock> stockList;

    public SymbolRecyclerView(Context mContext, ArrayList<Stock> stocks) {
        this.mContext = mContext;
        this.stockList = stocks;
    }

    @NonNull
    @Override
    public SymbolviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stock_list, parent, false);
        return new SymbolviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SymbolRecyclerView.SymbolviewHolder holder, int position) {
        Stock stock = stockList.get(position);
        holder.nameOfStock.setText(stock.getName());
        holder.symbolOfStock.setText(stock.getSymbol());
        holder.originOfStock.setText(stock.getLocation());
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public static class SymbolviewHolder extends RecyclerView.ViewHolder {

        private final TextView nameOfStock;
        private final TextView originOfStock;
        private final TextView symbolOfStock;

        public SymbolviewHolder(@NonNull View itemView) {

            super(itemView);

            nameOfStock = itemView.findViewById(R.id.stockName);
            originOfStock = itemView.findViewById(R.id.originLocation);
            symbolOfStock = itemView.findViewById(R.id.stockSymbol);

        }
    }
}
