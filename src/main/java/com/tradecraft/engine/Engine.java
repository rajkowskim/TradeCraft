package com.tradecraft.engine;

import com.tradecraft.data.Contract;
import com.tradecraft.data.DataPoint;

import java.util.List;

public class Engine {

    private double stopLoss;
    private double stopWin;
    private double enterChance;
    private int cutOffHour;

    public Engine(double stopLoss, double stopWin, double enterChance, int cutOffHour) {
        this.stopLoss = stopLoss;
        this.stopWin = stopWin;
        this.enterChance = enterChance;
        this.cutOffHour = cutOffHour;
    }

    public Contract tryToEnter(List<DataPoint> dataPoints) {
        Contract contract = null;
        boolean isOpen = false;

        for (DataPoint dataPoint : dataPoints) {
//            System.out.println(dataPoint.getDateTime()+ " ["+ dataPoint.getOpen()+"]");
            // try to open contract
            if (isOpen) {
                if (checkStopLoss(contract, dataPoint) || checkWin(contract, dataPoint) || dataPoint.getDateTime().getHourOfDay() > cutOffHour) {
                    contract.closeContract(dataPoint);
                    break;
                }
            } else if (enter()) {
                contract = new Contract(dataPoint);
                isOpen = true;
            }
        }


        return contract;
    }

    private boolean checkStopLoss(Contract contract, DataPoint dataPoint) {
        if (contract.isLong()) {
            return (contract.getBuyPrice() - dataPoint.getOpen()) > stopLoss;
        } else {
            return (dataPoint.getOpen() - contract.getBuyPrice()) > stopLoss;
        }
    }

    private boolean checkWin(Contract contract, DataPoint dataPoint) {
        if (contract.isLong()) {
            return (dataPoint.getOpen() - contract.getBuyPrice()) > stopWin;
        } else {
            return (contract.getBuyPrice() - dataPoint.getOpen()) > stopWin;
        }
    }

    private boolean enter() {
        return Math.random() > enterChance;
    }
}
