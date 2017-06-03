package com.bgi.rank.util;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class Jackson {
	
	private final static Jackson jackson = new Jackson();
	
	private ObjectMapper objectMapper;
	
	
	private Jackson(){
		objectMapper = new ObjectMapper();
		 // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性  
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES,true);
//		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		//设置默认时间格式 不要开启！
//		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));  
		
		//自动过滤MAP 中value为null的
//		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		//自动过滤对象掉为null的
//		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

	}
	
	public static Jackson getInstance() throws IOException {
		if(jackson == null) {
			return new Jackson();
		}
		return jackson;
	}
	
	/**
	 * java对象转换成json数据
	 * @param object java对象
	 * @return jsong格式字符串
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String beanToJson(Object object) throws IOException {
		StringWriter stringWrite = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWrite);
		this.objectMapper.writeValue(jsonGenerator, object);
		jsonGenerator.close();
		return stringWrite.toString();
	}
	
	/**
	 * java对象转换成json数据 忽略值为null的属性
	 * @param object java对象
	 * @return jsong格式字符串
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String beanToJsonIgnoreNull(Object object) throws IOException {
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		StringWriter stringWrite = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWrite);
		this.objectMapper.writeValue(jsonGenerator, object);
		jsonGenerator.close();
		return stringWrite.toString();
	}
	
	/**
	 * java对象转换成json数据  (排除指定的属性)
	 * @param object
	 * @param excludeField
	 * @return
	 * @throws IOException
	 */
	public synchronized String beanToJsonExclueField(Object object,String ...excludeField) throws IOException{
		StringWriter stringWriter=new StringWriter();
		JsonGenerator jsonGenerator=new JsonFactory().createGenerator(stringWriter);
		
		SimpleFilterProvider simpleFilterProvider=new SimpleFilterProvider();
		simpleFilterProvider.addFilter("excludeFilter", SimpleBeanPropertyFilter.serializeAllExcept(excludeField));
		this.objectMapper.setFilters(simpleFilterProvider);
		
		BidSerizlizerFactory beanSerializerFactory=BidSerizlizerFactory.instance;
		beanSerializerFactory.setFilterId("excludeFilter");;
		this.objectMapper.setSerializerFactory(beanSerializerFactory);
		
		this.objectMapper.writeValue(jsonGenerator, object);
		jsonGenerator.close();
		
		this.objectMapper.setSerializerFactory(BeanSerializerFactory.instance);
		this.objectMapper.setFilters(null);
		return stringWriter.toString();
	}
	
	/**
	 * java对象转换成json数据  (保留指定的属性)
	 * @param object
	 * @param excludeField
	 * @return
	 * @throws IOException
	 */
	public synchronized String beanToJsonRetainField(Object object,String ...retainField) throws IOException{
		StringWriter stringWriter=new StringWriter();
		JsonGenerator jsonGenerator=new JsonFactory().createGenerator(stringWriter);
		
		SimpleFilterProvider simpleFilterProvider=new SimpleFilterProvider();
		simpleFilterProvider.addFilter("includeFilter", SimpleBeanPropertyFilter.filterOutAllExcept(retainField));
		this.objectMapper.setFilters(simpleFilterProvider);
		
		BidSerizlizerFactory beanSerializerFactory=BidSerizlizerFactory.instance;
		beanSerializerFactory.setFilterId("includeFilter");;
		this.objectMapper.setSerializerFactory(beanSerializerFactory);
		
		this.objectMapper.writeValue(jsonGenerator, object);
		jsonGenerator.close();
		
		this.objectMapper.setSerializerFactory(BeanSerializerFactory.instance);
		this.objectMapper.setFilters(null);
		return stringWriter.toString();
	}
	
	/**
	 * json字符串转换成java对象
	 * @param <T> 泛型
	 * @param json json字符串
	 * @param cls Class对象
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public <T> T jsonToBean(String json,Class<T> cls) throws IOException {
		return this.objectMapper.readValue(json, cls);
	}
	
	public <T> T jsonToMap(String json) throws IOException{
		return (T) this.objectMapper.readValue(json, Map.class);
	}
	
	/**
	 * json字符串转换成java对象
	 * @param <T> 泛型
	 * @param json json字符串
	 * @param cls Class对象
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public <T> List<T> jsonToList(String json,Class<T> cls) throws IOException {
//		List<T> list = objectMapper.readValue(json, new TypeReference<List<VipSportNew>>(){});
//		return list;
		JavaType javaType = getCollectionType(ArrayList.class, cls); 
		List<T> list = objectMapper.readValue(json, javaType);
		return list;
	}
	
	/**
	 * 获取泛型的Collection Type  
	 * @param collectionClass 泛型的Collection 
	 * @param elementClasses 元素类 
	 * @return JavaType Java类型  
	 */
	public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
	      return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	}   
	
	/**
	 * 获取map型json的json value
	 * @param json
	 * @param key
	 * @return
	 */
	public String getValue(String json , String key) {
		if(StringUtils.isEmpty(json)) {
			return null;
		}
		JSONObject temp = (JSONObject) JSON.parse(json);
		return temp.get(key).toString();
	}
	
	/**
	 * 把实体类对象转换成MAP
	 * @param obj
	 * @return  MAP<String,Object>
	 */
	public static Map<String,Object> ConvertObjToMap(Object obj){
		  Map<String,Object> reMap = new HashMap<String,Object>();
		  if (obj == null) 
		   return null;
		  Field[] fields = obj.getClass().getDeclaredFields();
		  try {
		   for(int i=0;i<fields.length;i++){
		    try {
		     Field f = obj.getClass().getDeclaredField(fields[i].getName());
		     f.setAccessible(true);
		           Object o = f.get(obj);
		           reMap.put(fields[i].getName(), o);
		    } catch (NoSuchFieldException e) {
		     e.printStackTrace();
		    } catch (IllegalArgumentException e) {
		     e.printStackTrace();
		    } catch (IllegalAccessException e) {
		     e.printStackTrace();
		    }
		   }
		  } catch (SecurityException e) {
		   e.printStackTrace();
		  } 
		  return reMap;
		 }
		 
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.println(5<<3);  
		System.out.println(2>>3);
		System.out.println(JSON.toJSON(new Date()));
		
		
		
	}
}

