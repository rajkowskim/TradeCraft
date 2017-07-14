package com.tradecraft;

import com.tradecraft.data.Data;
import com.tradecraft.data.DataLoader;

import java.util.List;

public class TradeCraft {
    public static void main(String[] args) {
        List<Data> data = DataLoader.loadData("daxDayOne");

        for (Data d: data) {
            System.out.println(d.getHigh());
        }
    }
}