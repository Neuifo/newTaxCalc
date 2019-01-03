package com.neuifo.texcalculator;

import com.neuifo.texcalculator.model.MyAmount;

import java.text.DecimalFormat;

public class SalaryUtils {


    public static final String MONTH_SALARY = "MONTH_SALARY";
    public static final String FOUR_ACCUMULATION = "FOUR_ACCUMULATION";
    public static final String ALLOWANCE = "ALLOWANCE";

    /**
     * 四金
     *
     * @param basicSalary
     * @return
     */
    public static String getFourSalary(double basicSalary) {

        DecimalFormat df = new DecimalFormat("######0");
        /**
         *  if a != 0{
         *             let tmpG = a > 21400 ? 21400 : a
         *             let tmpQ = a > 21396 ? 21396 : a
         *             let rat = tmpG * 0.07 + tmpQ * 0.105
         *             txtInsurance.text = "\(Int(rat))"
         *         }
         */

        double tmpG = basicSalary > 21400 ? 21400 : basicSalary;
        double tmpQ = basicSalary > 21396 ? 21396 : basicSalary;
        return df.format(tmpG * 0.07 + tmpQ * 0.105);
    }


    public static String formatDouble(double data) {
        return new DecimalFormat("######0").format(data);
    }


    /**
     * /// 获取速扣金额
     * ///
     * /// - Parameters:
     * ///   - amount: 个人基本工资
     * ///   - mounth: 月份
     * /// - Returns: 速扣金额
     * fileprivate func getLeftOverAmount(amount:MyAmount,mounth:Int) ->(rate:Double,sub:Double){
     * let tmp = Double(mounth) * (amount.amount - amount.insurance - amount.other - 5000)
     * if tmp < 36000{return (0.03,0)}
     * else if tmp < 144000{return (0.1,2520)}
     * else if tmp < 300000{return (0.2,16920)}
     * else if tmp < 420000{return (0.25,31920)}
     * else if tmp < 660000{return (0.30,52920)}
     * else if tmp < 960000{return (0.35,85920)}
     * else {return (0.45,181920)}
     * }
     */

    public static double[] getLeftOverAmount(MyAmount amount, int mounth) {
        double tmp = new Double(mounth) * (amount.amount - amount.insurance - amount.other - 5000);
        if (tmp < 36000) {
            return new double[]{0.03, 0};
        } else if (tmp < 144000) {
            return new double[]{0.1, 2520};
        } else if (tmp < 300000) {
            return new double[]{0.2, 16920};
        } else if (tmp < 420000) {
            return new double[]{0.25, 31920};
        } else if (tmp < 660000) {
            return new double[]{0.30, 52920};
        } else if (tmp < 960000) {
            return new double[]{0.35, 85920};
        } else {
            return new double[]{0.45, 181920};
        }
    }


    /***
     * /// 获取老的税
     * ///
     * /// - Parameters:
     * ///   - amount: 个人基本工资
     * ///   - mounth: 月份
     * /// - Returns: 老的速扣金额
     * fileprivate func getOldLeftOverAmount(amount:MyAmount) ->Double{
     *     let tmp = amount.amount - amount.insurance - 5000
     *     if tmp < 3000{return (amount.amount - 3000) * 0.03}
     *     else if tmp < 12000{return tmp * 0.1 - 210}
     *     else if tmp < 25000{return tmp * 0.2  - 1410}
     *     else if tmp < 35000{return tmp * 0.25 - 2660}
     *     else if tmp < 55000{return tmp * 0.30 - 4410}
     *     else if tmp < 80000{return tmp * 0.35 - 7160}
     *     else {return tmp * 0.45 - 15160}
     * }
     */


    public static double getOldLeftOverAmount(MyAmount amount) {
        double tmp = amount.amount - amount.insurance - 5000;
        if (tmp < 3000) {
            return (amount.amount - 3000) * 0.03;
        } else if (tmp < 12000) {
            return tmp * 0.1 - 210;
        } else if (tmp < 25000) {
            return tmp * 0.2 - 1410;
        } else if (tmp < 35000) {
            return tmp * 0.25 - 2660;
        } else if (tmp < 55000) {
            return tmp * 0.30 - 4410;
        } else if (tmp < 80000) {
            return tmp * 0.35 - 7160;
        } else {
            return tmp * 0.45 - 15160;
        }
    }
}
