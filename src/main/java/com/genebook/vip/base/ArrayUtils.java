package com.genebook.vip.base;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class ArrayUtils {
	
	public static boolean isEmpty(Object target){
		boolean flag=true;
		if(target==null){
			return flag;
		}
		if(target instanceof List){
			List list=(List)target;
			if(!list.isEmpty()){
				flag=false;
			}
		}else if(target instanceof Map){
			Map map=(Map)target;
			if(!map.isEmpty()){
				flag=false;
			}
		}else if(target.getClass().isArray()){
			if(Array.getLength(target)>0){
				flag=false;
			}
		}else if(target instanceof String){
			String str=target.toString();
			if(str!=null && !str.isEmpty()){
				flag=false;
			}
		}
		return flag;
	}
	
	public static boolean isNotEmpty(Object target){
		return !isEmpty(target);
	}
	
	public static String objectToString(Object dest){
		if(dest==null){
			return null;
		}
		return dest.toString();
	}
	
	public static String generatorId(){
		return  UUID.randomUUID().toString().replace("-","");
	}
	
	/**
	 * 数组中重复元素出现的次数
	 * @param args
	 * @return
	 */
	public static Map<Object, Integer> multipleElementsCounts(Object[] args){
		Map<Object, Integer> map = new HashMap<Object, Integer>();
		for (int i = 0; i < args.length; i++) {
			int count = 0;
			for (int j = 0; j < args.length; j++) {
				if(args[i] instanceof String){
					if (args[i].toString().trim().equals(args[j].toString().trim())) {
						count = count + 1;
					}
				} else if((args[i] instanceof Integer)){
					if(args[i]==args[j]){
						count = count + 1;
					}
					
				}
				
				map.put(args[i], count);

			}

		}
		return map;
		
	}
	
	//根据MAP中的value值进行排序
	public static Map<Object, Integer> sortMap(Map<Object, Integer> oldMap,final String order) {
		ArrayList<Map.Entry<Object, Integer>> list = new ArrayList<Map.Entry<Object, Integer>>(
				oldMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Object, Integer>>() {

			@Override
			public int compare(Entry<Object, Integer> arg0,
					Entry<Object, Integer> arg1) {
				if("desc".equals(order)){
					return arg1.getValue() - arg0.getValue();
				}else{
					return arg0.getValue()-arg1.getValue();
				}
				
			}
		});
		Map<Object, Integer> newMap = new LinkedHashMap<Object, Integer>();
		for (int i = 0; i < list.size(); i++) {
			newMap.put(list.get(i).getKey(), list.get(i).getValue());
		}
		return newMap;
	}

	// 删除重复元素，并保持顺序
	public static List<Object> removeDuplicateWithOrder(List<Object> list) {
		Set<Object> set = new HashSet<Object>();
		List<Object> newList = new ArrayList<Object>();
		for (Iterator<Object> iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		list.clear();
		list.addAll(newList);
		return list;
	}
	
	public static void main(String[] args) {
		List<String> list=new ArrayList<String>();
		list.add("123");
		
		Map<String,String> map=new HashMap<String, String>();
		int [] array=new int[0];
		System.out.println(ArrayUtils.isEmpty(array));
	}
}
