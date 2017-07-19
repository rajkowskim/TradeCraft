package com.tradecraft;

import com.tradecraft.data.Contract;
import com.tradecraft.data.DataPoint;
import com.tradecraft.data.DataLoader;
import com.tradecraft.engine.Engine;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TradeCraft {
    public static void main(String[] args) {
        List<Integer> profits = new ArrayList<Integer>();

        // sample sampleSize, stopLoss, stopWin, enterChance, cutoffHour
        int sampleSize = Integer.parseInt(args[0]);
        double stopLoss = Double.parseDouble(args[1]);
        double stopWin = Double.parseDouble(args[2]);
        double enterChance = Double.parseDouble(args[3]);
        int cutoffHour = Integer.parseInt(args[4]);

        Engine engine = new Engine(stopLoss, stopWin, enterChance, cutoffHour);
        Map<DateTime, List<DataPoint>> map = DataLoader.loadData("dax.csv");

        for (int i = 0; i < sampleSize; i++) {
            int oneLoopProfit = 0;
            for (DateTime x : map.keySet()) {
                List<DataPoint> dataPoints = map.get(x);
                Contract contract = engine.tryToEnter(dataPoints);

                if (contract != null) {
                    oneLoopProfit += contract.getProfit();
//                    System.out.println(contract);
                }
            }

            profits.add(oneLoopProfit);
        }

        displaySummary(profits);
    }

    private static double getAvg(List<Integer> profits) {
        return profits.stream().mapToDouble(a -> a).average().orElse(0.0);
    }

    private static double getStdDev(List<Integer> profits) {
        double sum = profits.stream().mapToDouble(a -> a).sum();
        double sumMinusAverage = sum - getAvg(profits);

        double var = sumMinusAverage * sumMinusAverage / (profits.size()-1);

        return Math.sqrt(var);
    }

    private static void displaySummary(List<Integer> profits) {
        System.out.println("=====Summary=====");
        System.out.println("Min: \t\t"+ Collections.min(profits));
        System.out.println("Max: \t\t"+ Collections.max(profits));
        System.out.println("Avg: \t\t"+ (int)getAvg(profits));
        System.out.println("Stddev: \t"+ (int) getStdDev(profits));
    }
}