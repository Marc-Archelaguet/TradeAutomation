package com.marchelaguet.tradeautomation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String asset; // El símbol, per exemple EURUSD, AAPL, etc.
    private String direction; // "LONG" (Compra) o "SHORT" (Venda)

    private LocalDateTime openTime;  // Timestamp d'obertura
    private LocalDateTime closeTime; // Timestamp de tancament

    private double openPrice;
    private double closePrice;

    private double riskReward; // El "RR" que ell menciona
    private double netPnl; // El guany o pèrdua final d'aquella operació

    // Constructor buit obligatori per a Spring Boot
    public Trade() {
    }

    // Constructor amb dades
    public Trade(String asset, String direction, LocalDateTime openTime, LocalDateTime closeTime,
                 double openPrice, double closePrice, double riskReward, double netPnl) {
        this.asset = asset;
        this.direction = direction;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.riskReward = riskReward;
        this.netPnl = netPnl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getRiskReward() {
        return riskReward;
    }

    public void setRiskReward(double riskReward) {
        this.riskReward = riskReward;
    }

    public double getNetPnl() {
        return netPnl;
    }

    public void setNetPnl(double netPnl) {
        this.netPnl = netPnl;
    }
}