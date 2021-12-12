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

public class StockRecyclerView extends RecyclerView.Adapter<StockRecyclerView.StockviewHolder>{

    interface StockClickListener {
        public void stockClicked(Stock selectedStock);
    }

    private Context mContext;
    public ArrayList<Stock> stockList;
    StockClickListener listener;

    public StockRecyclerView(Context mContext, ArrayList<Stock> stockList)
    {
        this.mContext = mContext;
        this.stockList = stockList;
        this.listener = (StockClickListener) mContext;
    }

    @NonNull
    @Override
    public StockRecyclerView.StockviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stock_list, parent, false);
        return new StockviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockRecyclerView.StockviewHolder holder, int position) {
        Stock stock = stockList.get(position);
        holder.nameOfStock.setText(stock.getName());
        holder.symbolOfStock.setText(stock.getSymbol());
        holder.originOfStock.setText(stock.getLocation());
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    class StockviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView nameOfStock;
        private final TextView originOfStock;
        private final TextView symbolOfStock;

        public StockviewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfStock = itemView.findViewById(R.id.stockName);
            originOfStock = itemView.findViewById(R.id.originLocation);
            symbolOfStock = itemView.findViewById(R.id.stockSymbol);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Stock stock = stockList.get(getAdapterPosition());
            listener.stockClicked(stock);

        }
    }


}
