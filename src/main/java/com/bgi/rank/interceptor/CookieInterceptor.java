package com.bgi.rank.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.bgi.rank.util.CookieUtil;
import com.bgi.rank.util.Jackson;
import com.bgi.rank.util.StringUtils;

import qxsso.customer.ws.ISSOWebService;

public class CookieInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private ISSOWebService iSSOWebService;
	
	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// ios进入浏览器只能通过url方式写入st
		String st = request.getParameter("_st");
		if(StringUtils.isNotEmpty(st)) {
			CookieUtil.writeCookie("_st", st, response);
		}
		
		String ticket = st;
		if(ticket == null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				String name = null;
				// 如果没有设置过Cookie会返回null
				for (Cookie cookie : cookies) {
					name = cookie.getName();
					if ("_st".equals(name)) {
						ticket = cookie.getValue();
						CookieUtil.writeCookie("_st", ticket, response);
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(ticket)) {
			JSONObject json = new JSONObject();
			json.put("ticket", ticket);
			// 存放用户信息
			String info = iSSOWebService.getContactInfo(json.toJSONString());
			Map resMap = Jackson.getInstance().jsonToMap(info);
			request.setAttribute("user", resMap);
			String userInfo = iSSOWebService.getUserInfo(json.toJSONString());
			resMap = new HashMap();
			try {
				resMap = Jackson.getInstance().jsonToMap(userInfo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			request.setAttribute("userInfo", resMap);
			String groupName = resMap.get("groupName") != null ? resMap.get("groupName").toString() : "";

			if("100".equals(groupName)) {
				request.setAttribute("isHuadaUser", true);
			} else {
				request.setAttribute("isHuadaUser", false);
			}
		}
		
		request.setAttribute("_st", ticket);
		return true;
	}

}
