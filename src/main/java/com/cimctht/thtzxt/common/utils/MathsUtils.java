package com.cimctht.thtzxt.common.utils;

import java.math.BigDecimal;

public class MathsUtils {

	/**
	 * @comment 将BigDecimal转换为百分百
	 * @author Walter(翟笑天)
	 * @date 2021/3/17
	 */
	public static String convertBigDecimal2HundredPercent(BigDecimal big) {
		String result = big.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString()+"%";
		return result;
	}
	
	/**
	 * @comment 将BigDecimal转换为百分百
	 * @author Walter(翟笑天)
	 * @date 2021/3/17
	 */
	public static String convertBigDecimal2HundredPercent(BigDecimal big,Integer decimal) {
		String result = big.multiply(new BigDecimal(100)).setScale(decimal, BigDecimal.ROUND_DOWN).toString()+"%";
		return result;
	}


	/**
	 * @comment 将Integer转换为BigDecimal
	 * @author Walter(翟笑天)
	 * @date 2021/3/17
	 */
	public static BigDecimal convertInteger2BigDecimal(Integer decimal) {
		BigDecimal b = new BigDecimal(decimal);
		return b;
	}

	/**
	 * @comment 将Long转换为BigDecimal
	 * @author Walter(翟笑天)
	 * @date 2021/3/17
	 */
	public static BigDecimal convertLong2BigDecimal(Long decimal) {
		BigDecimal b = new BigDecimal(decimal);
		return b;
	}
}
