package com.tradecraft;

import com.tradecraft.data.Contract;
import com.tradecraft.data.DataPoint;
import com.tradecraft.data.DataLoader;
import com.tradecraft.engine.Engine;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

public class TradeCraft {
    public static void main(String[] args) {
        int profit = 0;

        // sample sampleSize, stopLoss, stopWin, enterChance, cutoffHour
        int sampleSize = Integer.parseInt(args[0]);
        double stopLoss = Double.parseDouble(args[1]);
        double stopWin = Double.parseDouble(args[2]);
        double enterChance = Double.parseDouble(args[3]);
        int cutoffHour = Integer.parseInt(args[4]);

        Engine engine = new Engine(stopLoss, stopWin, enterChance, cutoffHour);
        Map<DateTime, List<DataPoint>> map = DataLoader.loadData("dax.csv");

        for (int i = 0; i < sampleSize; i++) {
            for (DateTime x : map.keySet()) {
                List<DataPoint> dataPoints = map.get(x);
                Contract contract = engine.tryToEnter(dataPoints);

                if (contract != null) {
                    profit = profit + contract.getProfit();
//                    System.out.println(contract);
                }
            }
        }

        System.out.println("Profit [pips]: " + profit / sampleSize);
    }
}