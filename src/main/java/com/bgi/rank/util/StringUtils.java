package com.bgi.rank.util;

import java.math.BigDecimal;
import java.util.UUID;

public class StringUtils {

	
	public static Float objectToFloat(Object src){
		if(src==null){
			return 0f;
		}
		return Float.parseFloat(String.valueOf(src));
	}
	
	
	public static double stringToDouble(String str){
		if(StringUtils.isNotEmpty(str)){
			return Double.parseDouble(str);
		}
		return 0;
	}
	
	
	public static boolean isEmpty(String str){
		if(str==null || "".equals(str)){
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	public static Float round(int scale,Float value){
		BigDecimal val=new BigDecimal(value);
		Float result=val.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
		return result;
	}
	
	public static String generatorId(){
		String id=UUID.randomUUID().toString().replace("-", "");
		return id;
	}
	
	public static void main(String[] args) {
		System.out.println(round(0, 28.5f));
	}
}
