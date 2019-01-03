package com.neuifo.texcalculator.model;

public class MyAmount {

    /**
     * 基本薪
     */
    public double amount;
    /**
     * 扣除4金
     */
    public double insurance;
    /**
     * 其他减项
     */
    public double other;


    public MyAmount(double amount, double insurance, double other) {
        this.amount = amount;
        this.insurance = insurance;
        this.other = other;
    }
}
