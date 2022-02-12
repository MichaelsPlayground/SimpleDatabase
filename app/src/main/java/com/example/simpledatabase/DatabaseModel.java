package com.example.simpledatabase;

import java.io.Serializable;

public class DatabaseModel implements Serializable {

    private String stockIsin = "";
    private String stockName = "";

    // constructor
    public DatabaseModel(String stockIsin, String stockName) {
        this.stockIsin = stockIsin;
        this.stockName = stockName;
    }

    public String getStockIsin() {
        return stockIsin;
    }

    public void setStockIsin(String stockIsin) {
        this.stockIsin = stockIsin;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

}
