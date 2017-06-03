package com.bgi.rank.interceptor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.bgi.rank.util.Jackson;

import qxsso.customer.ws.ISSOWebService;

public class UserInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private ISSOWebService iSSOWebService;
	
	private List<String> excludeUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestUri = request.getRequestURI();
		for (String url : excludeUrls) {
			if (requestUri.endsWith(url)) {
				return true;
			}
		}
		String ticket = request.getAttribute("_st").toString();
		JSONObject json = new JSONObject();
		json.put("ticket", ticket);
		// 存放用户信息
		String info = iSSOWebService.getContactInfo(json.toJSONString());
		Map resMap = Jackson.getInstance().jsonToMap(info);
		request.setAttribute("user", resMap);
		
		String userInfo =iSSOWebService.getUserInfo(json.toJSONString());
		resMap = Jackson.getInstance().jsonToMap(userInfo);
		request.setAttribute("userInfo", resMap);
		
		if (ticket == null) {
			// 对应st的cookie不存在或者失效都跳转登录页面
			request.getRequestDispatcher("/loginPage?redirectUrl=" + requestUri).forward(request, response);
			return false;
		}
		return true;
	}

}
