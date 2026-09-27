package com.marchelaguet.tradeautomation.model;

import com.opencsv.bean.CsvBindByName;
import java.time.LocalDateTime;

public class OrderExecution {

    // El "@CsvBindByName" es la magia de OpenCSV.
    // Busca en la primera fila del archivo la palabra exacta y la guarda aquí.

    @CsvBindByName(column = "Symbol")
    private String symbol;

    @CsvBindByName(column = "Seite")
    private String seite; // "Buy" o "Sell"

    // Guardamos la fecha como texto primero porque los CSV alemanes
    // suelen tener formatos de fecha raros (ej. 26.09.2026 22:40:16)
    @CsvBindByName(column = "Zeit")
    private String zeitText;

    @CsvBindByName(column = "tatsächlicher Erfüllungspreis")
    private String actualPriceText; // El precio al que realmente se compró/vendió

    @CsvBindByName(column = "Profit")
    private String profitText;

    // El número de ticket de la orden, crucial para emparejar la compra con su venta
    @CsvBindByName(column = "Order-Nr.")
    private String orderNr;

    // Constructor vacío requerido por OpenCSV
    public OrderExecution() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSeite() {
        return seite;
    }

    public void setSeite(String seite) {
        this.seite = seite;
    }

    public String getZeitText() {
        return zeitText;
    }

    public void setZeitText(String zeitText) {
        this.zeitText = zeitText;
    }

    public String getActualPriceText() {
        return actualPriceText;
    }

    public void setActualPriceText(String actualPriceText) {
        this.actualPriceText = actualPriceText;
    }

    public String getProfitText() {
        return profitText;
    }

    public void setProfitText(String profitText) {
        this.profitText = profitText;
    }

    public String getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(String orderNr) {
        this.orderNr = orderNr;
    }
}
