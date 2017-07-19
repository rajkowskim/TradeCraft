package com.tradecraft.data;

import org.joda.time.DateTime;

public class Contract {

    private DateTime buyTime;
    private DateTime sellTime;
    private double buyPrice;
    private double sellPrice;
    private boolean isLong;

    public Contract(DataPoint dataPoint) {
        this.buyTime = dataPoint.getDateTime();
        this.buyPrice = dataPoint.getOpen();
        // TODO: logic?
        this.isLong = Math.random() > 0.5;
    }

    public DateTime getBuyTime() {
        return buyTime;
    }

    public DateTime getSellTime() {
        return sellTime;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public boolean isLong() {
        return isLong;
    }

    public void setSellTime(DateTime sellTime) {
        this.sellTime = sellTime;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void closeContract(DataPoint dataPoint) {
        setSellTime(dataPoint.getDateTime());
        setSellPrice(dataPoint.getOpen());
    }

    public int getProfit() {
        if(isLong) {
            return (int)(sellPrice - buyPrice);
        } else {
            return (int)(buyPrice - sellPrice);
        }
    }

    @Override
    public String toString() {
        return "Contract " +
                "\t["+buyTime+"]" +
                "\t["+sellTime+"]" +
                "\t\t["+(isLong ? "long" : "short")+"]" +
                "\t\t[buyPrice=" + (int)buyPrice + "]" +
                "\t\t[sellPrice=" + (int)sellPrice + "]" +
                "\t\t[profit=" + getProfit() +"]";
    }
}
