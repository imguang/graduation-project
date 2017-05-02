package com.imguang.demo.utils;

public class StringUtils {

	/**
	 * @param tem
	 * @return
	 * 防止空指针异常
	 */
	public static String trimString(String tem){
		if(tem == null || tem == ""){
			return tem;
		}
		return tem.trim();
	}
	
	
	/**
	 * string转为Int
	 * @param tem
	 * @return
	 */
	public static Integer parseInt(String tem){
		if(tem == null || "".equals(tem)){
			return null;
		}
		return Integer.valueOf(tem);
	}
}
