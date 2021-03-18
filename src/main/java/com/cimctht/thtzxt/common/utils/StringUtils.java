package com.cimctht.thtzxt.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Walter(翟笑天)
 * @date 2020/10/10
 */
public class StringUtils {

	/**
	 * @comment 将string 按分隔符分隔返回list
	 * @author Walter(翟笑天)
	 * @date 2020/10/10
	 */
	public static List<String> transString2ListString(String str,String reg) {
		List<String> list = new ArrayList<String>();
		String[] arr = str.split(reg);
		for(String s : arr) {
			list.add(s);
		}
		return list;
	}
	
	/**
	 * @comment 判断是否为null或者空字符串（""）
	 * @author Walter(翟笑天)
	 * @date 2020/10/10
	 */
	public static boolean isEmpty(String str) {
		if("".equals(str)) {
			return true;
		}else if(null==str) {
			return true;
		}
		return false;
	}
	
	/**
	 * @comment 将list<String>修改成?,?,?的形式
	 * @author Walter(翟笑天)
	 * @date 2020/10/10
	 */
	public static String list2String(List<String> list) {
		String s = "";
		for(String l : list) {
			if(s.equals("")) {
				s = l;
			}else {
				s = s + "," + l;
			}
		}
		return s;
	}
	
	/**
	 * @comment 将list<String>改成in后面的形式
	 * @author Walter(翟笑天)
	 * @date 2020/10/10
	 */
	public static String list2InString(List<String> list) {
		String s = "";
		for(String l : list) {
			if(s.equals("")) {
				s = "'"+l+"'";
			}else {
				s = s + ",'" + l + "'";
			}
		}
		return s;
	}
	
	
	/**
	 * @comment 将String[]改成in后面的形式
	 * @author Walter(翟笑天)
	 * @date 2020/10/10
	 */
	public static String arr2InString(String[] arr) {
		String s = "";
		for(String l : arr) {
			if(s.equals("")) {
				s = "'"+l+"'";
			}else {
				s = s + ",'" + l + "'";
			}
		}
		return s;
	}
	
	/**
	 * @comment 生成32位UUID
	 * @author Walter(翟笑天)
	 * @date 2020/10/10
	 */
	public static String generteUUID() {
		 return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
	
	/**
	 * @comment 左补位(同C#)
	 * @author Walter(翟笑天)
	 * @date 2020/10/10
	 */
	public static String padLeft(String s,Integer len, String c) {
		if(s.length()>=len) {
			return s;
		}
		Integer ct = len - s.length();
		for(int i = 1; i<=ct ; i++) {
			s = c + s;
		}
		return s;
	}
	
	/**
	 * @comment 右补位(同C#)
	 * @author Walter(翟笑天)
	 * @date 2020/10/10
	 */
	public static String padRight(String s,Integer len, String c) {
		if(s.length()>=len) {
			return s;
		}
		Integer ct = len - s.length();
		for(int i = 1; i<=ct ; i++) {
			s = s + c;
		}
		return s;
	}

	/**
	 * @comment 判断字符串不为空
	 * @author Walter(翟笑天)
	 * @date 2020/10/10
	 */
	public static boolean isNotEmpty(String str) {
		boolean flag = isEmpty(str);
		if(flag){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * @comment 将String改为 % string % 模式
	 * @author Walter(翟笑天)
	 * @date 2020/10/10
	 */
	public static String string2LikeParam(String s) {
		return "%" + s + "%";
	}

}
