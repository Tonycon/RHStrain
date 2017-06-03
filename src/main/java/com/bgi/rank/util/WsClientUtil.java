package com.bgi.rank.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;

public class WsClientUtil {
	public static void addSTHeader(Object service, HttpServletRequest request) {
		Client proxy = ClientProxy.getClient(service);
		String st = null;
		if (request == null) {
			Message message = PhaseInterceptorChain.getCurrentMessage();
			HttpServletRequest httprequest = (HttpServletRequest) message
					.get("HTTP.REQUEST");
			proxy = ClientProxy.getClient(service);
			st = httprequest.getHeader("_st");
		} else {
//			st = CookieUtil.getCookieValue(request, "_st");
		}
		Map headers = new HashMap();
		headers.put("_st", Arrays.asList(new String[] { st }));
		proxy.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);
	}

	public static void addSTToHeader(Object service, String st) {
		Client proxy = ClientProxy.getClient(service);
		Map headers = new HashMap();
		headers.put("_st", Arrays.asList(new String[] { st }));
		
		//乔新那边安全机制验证
		Properties ssoProperties = PropertiesUtils.getProperty("sso.properties");
		String pt = ssoProperties.getProperty("profile.server.pt");
		headers.put("_pt", Arrays.asList(new String[] { pt }));
		proxy.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);
	}
	
	/**
	 * 乔新那边消息头需要加的安全验证
	 * @param service
	 * @param st
	 */
	public static void addPtToHeader(Object service) {
		Client proxy = ClientProxy.getClient(service);
		Map headers = new HashMap();
		//乔新那边安全机制验证
		Properties ssoProperties = PropertiesUtils.getProperty("sso.properties");
		String pt = ssoProperties.getProperty("profile.server.pt");
		headers.put("_pt", Arrays.asList(new String[] { pt }));
		proxy.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);
	}

	public static String getSTOfSoap() {
		Message message = PhaseInterceptorChain.getCurrentMessage();
		HttpServletRequest httprequest = (HttpServletRequest) message
				.get("HTTP.REQUEST");
		return httprequest.getHeader("_st");
	}
	public static void addSTHeader(Object service) {
		Client proxy = ClientProxy.getClient(service);
		String st = null;
		Map headers = new HashMap();
		headers.put("Content-Encoding", "gzip");
		proxy.getResponseContext().put(Message.PROTOCOL_HEADERS, headers);
	}
}
