package com.tradecraft.data;

import org.joda.time.DateTime;

public class Data {
    private DateTime dateTime;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;

    public Data(DateTime dateTime, double open, double high, double low, double close, double volume) {
        this.dateTime = dateTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }


    public DateTime getDateTime() {
        return dateTime;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getVolume() {
        return volume;
    }
}
