package com.cimctht.thtzxt.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public class CollectionsUtils {

	/**
	 * @comment 字符串数组转字符串list
	 * @author Walter(翟笑天)
	 * @date 2020/10/10
	 */
	public static List<String> convertArrays2ListByString(String[] arr){
		List<String> list = new ArrayList<String>();
		for(String s : arr) {
			list.add(s);
		}
		return list;
	}
}
