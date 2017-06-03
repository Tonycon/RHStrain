package com.bgi.rank.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	/**
	 * @Title: writeCookie 
	 * @Description: TODO(写cookie统一使用该方法) 
	 * @param @param key
	 * @param @param value
	 * @param @param response
	 * @return void    返回类型 
	 * @throws
	 */
	public static void writeCookie(String key, String value, HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(60*60*24*7);
		// 暂时注释,否则开发、测试无法登陆上	
		cookie.setDomain(".genebook.com.cn");
		cookie.setPath("/");
		response.addCookie(cookie) ;
	}
	
	/**
	 * @Title: removeCookie 
	 * @Description: TODO(根据名称移除cookie) 
	 * @param @param key
	 * @param @param request
	 * @param @param response
	 * @return void    返回类型 
	 * @throws
	 */
	public static void removeCookie(String key, HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			String name = null;
			for (Cookie cookie : cookies) {
				name = cookie.getName();
				if (name.equals(key)) {
					cookie.setMaxAge(0);
					response.addCookie(cookie); 
				}
			}
		}
	}
}
