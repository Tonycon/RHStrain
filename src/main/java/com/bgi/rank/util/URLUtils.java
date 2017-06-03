package com.bgi.rank.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.util.CollectionUtils;

public abstract class URLUtils {

	/**
	 * 编码url
	 * @param url
	 * @return
	 */
	public static String encodeURL(String url) {
		String result = null;
		if(StringUtils.isEmpty(url)) {
			return result;
		}
		try {
			result = URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 解码url
	 * @param url
	 * @return
	 */
	public static String decodeUrl(String url) {
		String result = null;
		if(StringUtils.isEmpty(url)) {
			return result;
		}
		try {
			result = URLDecoder.decode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 判断url是否被Encode过的
	 * @param url
	 * @return
	 */
	public static boolean isEncoded(String url) {
		if(StringUtils.isEmpty(url)) {
			return false;
		}
		String str = decodeUrl(url);
		return !url.equals(str);
	}

	/**
	 * 获取参数
	 * @param url
	 * @return
	 */
	public static Map<String , String> getParamMap(String url){
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		try {
			URL ul = new URL(url);
			String query = ul.getQuery();
			if(StringUtils.isEmpty(query)) {
				return null;
			}else {
				Map<String, String> ht = new LinkedHashMap<String, String>();
			    StringTokenizer st = new StringTokenizer(query, "&");
			    while (st.hasMoreTokens()) {
			        String pair = st.nextToken();
			        int pos = pair.indexOf('=');
			        if (pos == -1) {
			            ht.put(pair, "");
			        } else {
			            ht.put(pair.substring(0, pos),
			                   pair.substring(pos + 1));
			        }
			    }
			    return ht;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据参数名获取参数值
	 * @param url
	 * @param paramName
	 * @return
	 */
	public static String getParam(String url , String paramName) {
		Map<String , String> paramMap = getParamMap(url);
		if(CollectionUtils.isEmpty(paramMap)) {
			return null;
		}
		return paramMap.get(paramName);
	}
	/**
	 * 根据参数名移除参数值
	 * @param url
	 * @param paramName
	 */
	public static String removeParam(String url , String paramName) {
		if(!containsParam(url, paramName)) {
			return url;
		}
		Map<String , String> paramMap = getParamMap(url);
		paramMap.remove(paramName);
		String path = getFullPath(url);
		return appendParam(path, paramMap);
	}
	/**
	 * 是否包含参数名
	 * @param url
	 * @param paramName
	 * @return
	 */
	public static boolean containsParam(String url , String paramName) {
		Map<String , String> paramMap = getParamMap(url);
		if(CollectionUtils.isEmpty(paramMap)) {
			return false;
		}
		return paramMap.containsKey(paramName);
	}
	
	public static String appendOrReplaceParam(String url , String paramName , String paramValue) {
		Map<String , String> paramMap = getParamMap(url);
		if(CollectionUtils.isEmpty(paramMap)) {
			Map<String,String> param = new HashMap<String, String>(1);
			param.put(paramName, paramValue);
			return appendParam(url, param);
		}else {
			paramMap.put(paramName, paramValue);
			String path = getFullPath(url);
			return appendParam(path, paramMap);
		}
	}
	
	public static String appendOrReplaceParam(String url , Map<String , String> params) {
		Map<String , String> paramMap = getParamMap(url);
		if(CollectionUtils.isEmpty(paramMap)) {
			return appendParam(url, params);
		}else {
			paramMap.putAll(params);
			String path = getFullPath(url);
			return appendParam(path, paramMap);
		}
	}
	
	/**
	 * 添加参数
	 * @param url
	 * @param params
	 * @return
	 */
	public static String appendParam(String url , Map<String , String> params) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		if(CollectionUtils.isEmpty(params)) {
			return url;
		}
		StringBuffer buffer = new StringBuffer(url);
		if(CollectionUtils.isEmpty(getParamMap(url))) {
			buffer.append("?");
		}else {
			buffer.append("&");
		}
		for(String key : params.keySet()) {
			buffer.append(key).append("=").append(params.get(key)).append("&");
		}
		String result = buffer.toString();
		result = result.substring(0, result.length()-1);
		return result;
	}
	/**
	 * 获取主机名
	 * @param url
	 * @return
	 */
	public static String getHost(String url) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		try {
			URL ul = new URL(url);
			return ul.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取协议名
	 * @param url
	 * @return
	 */
	public static String getProtocol(String url) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		try {
			URL ul = new URL(url);
			return ul.getProtocol();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取锚点
	 * @param url
	 * @return
	 */
	public static String getRef(String url) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		try {
			URL ul = new URL(url);
			return ul.getRef();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取路径
	 * @param url
	 * @return
	 */
	public static String getPath(String url) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		try {
			URL ul = new URL(url);
			return ul.getPath();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取端口号
	 * @param url
	 * @return
	 */
	public static String getPort(String url) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		try {
			URL ul = new URL(url);
			int port = ul.getPort();
			if(port==-1) {
				return null;
			}
			return String.valueOf(port);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获取完整路径
	 * @param url
	 * @return
	 */
	public static String getFullPath(String url) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		String protocol = getProtocol(url);
		String host = getHost(url);
		String port = getPort(url);
		String path = getPath(url);
		StringBuffer buffer = new StringBuffer();
		buffer.append(protocol).append("://").append(host);
		if(!StringUtils.isEmpty(port)) {
			buffer.append(":").append(port);
		}
		buffer.append(path);
		return buffer.toString();
	}
	
	public static String getBasePath(String url){
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		String protocol = getProtocol(url);
		String host = getHost(url);
		String port = getPort(url);
		StringBuffer buffer = new StringBuffer();
		buffer.append(protocol).append("://").append(host);
		if(!StringUtils.isEmpty(port)) {
			buffer.append(":").append(port);
		}
		return buffer.toString();
	}
	
	public static String getQuery(String url) {
		if(StringUtils.isEmpty(url)) {
			return null;
		}
		try {
			URL ul = new URL(url);
			return ul.getQuery();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getIp(String domain) {
		if(StringUtils.isEmpty(domain)) {
			return null;
		}
		InetAddress[] addresses = null;
		try {
			addresses = InetAddress.getAllByName(domain);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if(addresses==null) {
			return null;
		}
		String[] ips = new String[addresses.length];
		for(int i = 0 ; i<addresses.length ; i++) {
			ips[i] = addresses[i].getHostAddress();
		}
		return ips;
	}
	
	public static boolean isUrl(String resourceLocation) {
		if (resourceLocation == null) {
			return false;
		}
		try {
			new URL(resourceLocation);
			return true;
		}
		catch (MalformedURLException ex) {
			return false;
		}
	}
	
	/*public static String encodeParams(String url){
		Map<String , String> params = getParamMap(url);
		if(CollectionUtils.isEmpty(params)){
			return url;
		}
		for(String paramName : params.keySet()){
			String paramValue = params.get(paramName);
			if(StringUtils.isContainChinese(paramValue)){
				paramValue = encodeURL(paramValue);
				url = appendOrReplaceParam(url, paramName, paramValue);
			}
		}
		return url;
	}*/
}
