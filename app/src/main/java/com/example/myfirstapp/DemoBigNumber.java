package com.example.myfirstapp;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 演示大数值转换和操作，包括大整数和大浮点数。
 */
public class DemoBigNumber {

    //通过web3j获得的10个账户余额，放在一个字符串数组里面，暂时用模拟数据
    static String[] balanceStrArr = {
            "623699332234776605",
            "1623699332234776605",
            "2623699332234776605",
            "3623699332234776605",
            "4623699332234776605",
            "5623699332234776605",
            "6623699332234776605",
            "7623699332234776605",
            "8623699332234776605",
            "9623699332234776605"};

    public static void demoBigInteger01(){

        //初始化一个加总变量，初始值为0
        BigInteger totalBalance = BigInteger.ZERO;

        for(int i=0;i<balanceStrArr.length;i++) {
            //根据一个十进制数字的字符来构造
            BigInteger balance = new BigInteger(balanceStrArr[i]);
            //总数加上账户的余额
            totalBalance = totalBalance.add(balance);
        }

        System.out.println("十个账户总额为："+totalBalance.toString() + " Wei");

        //以太币最小单位是wei，转成为ether，除以10的18次方
        BigInteger ethFactor = BigInteger.valueOf(10).pow(18);
        totalBalance = totalBalance.divide(ethFactor);

        System.out.println("十个账户总额为："+totalBalance.toString() + " Ether");
    }

    public static void demoBigDecimal01(){

        //初始化一个加总变量，初始值为0
        BigInteger totalBalance = BigInteger.ZERO;

        for(int i=0;i<balanceStrArr.length;i++) {
            //根据一个十进制数字的字符来构造
            BigInteger balance = new BigInteger(balanceStrArr[i]);
            //总数加上账户的余额
            totalBalance = totalBalance.add(balance);
        }

        System.out.println("十个账户总额为："+totalBalance.toString() + " Wei");

        //转换成BigDecimal
        BigDecimal totalBalanceEth = new BigDecimal(totalBalance);
        //以太币最小单位是wei，转成为ether，除以10的18次方
        BigDecimal ethFactor = BigDecimal.valueOf(10).pow(18);
        totalBalanceEth = totalBalanceEth.divide(ethFactor);

        System.out.println("十个账户总额为："+totalBalanceEth.toString() + " Ether");
    }
}
