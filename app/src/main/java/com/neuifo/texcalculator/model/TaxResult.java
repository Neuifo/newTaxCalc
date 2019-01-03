package com.neuifo.texcalculator.model;

public class TaxResult {


    /**
     * var tax:Double = 0///新的税
     * var amount:Double = 0///新的收入
     * var oldTax:Double = 0///老的税
     * var oldAmount:Double = 0///老的收入
     */
    public double tax;
    public double amount;
    public double oldTax;
    public double oldAmount;


    public TaxResult(double tax, double amount, double oldTax, double oldAmount) {
        this.tax = tax;
        this.amount = amount;
        this.oldTax = oldTax;
        this.oldAmount = oldAmount;
    }
}
