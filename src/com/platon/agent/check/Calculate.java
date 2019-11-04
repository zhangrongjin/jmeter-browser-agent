package com.platon.agent.check;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

public class Calculate {
	public static void main(String[] args) {
		maxOrminTxFee();
//		System.out.println(Convert.fromVon(new BigDecimal("1962704417314427000000000000").add(Convert.toVon(new BigDecimal("0.02625"), Unit.LAT)), Unit.LAT));
//		System.out.println(calculateTxFee(new BigInteger("48788"), new BigInteger("500000000000"),Unit.LAT));	
	}
	
	
	
	/**
	 * 1、转账 手续费 gasprice 滑块 通过获取推荐值W显示上下边界值（下W-W*50%，上W+W*500%），进入默认定位在推荐值W上，滑块不设固定点，滑块可以按照Gasprice Gvon小数点2位变更。
	 */
	public static void maxOrminTxFee() {
		BigDecimal defaultPrice = new BigDecimal(500000000000L);
		BigDecimal gasUsed = new BigDecimal(210000);
		BigDecimal maxPirce =  defaultPrice.add(defaultPrice.multiply(BigDecimal.valueOf(5)));
		BigDecimal minPirce =  defaultPrice.subtract(defaultPrice.multiply(BigDecimal.valueOf(0.5)));
				
		System.out.println("maxfee = " + Convert.fromVon(maxPirce.multiply(gasUsed), Unit.LAT)) ;
		System.out.println("minfee = " + Convert.fromVon(minPirce.multiply(gasUsed), Unit.LAT)) ;
		
		return ;
	}
	
	
	public static BigDecimal calculateTxFee(BigInteger gasUsed, BigInteger gasPrice, Unit unit) {
		BigInteger valueFee = gasUsed.multiply(gasPrice);
		return Convert.fromVon(new BigDecimal(valueFee), Unit.LAT);
	}
	
}
