package com.bgi.rank.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * 设置日志输出文件路径
 */
public class Log4jListener implements ServletContextListener {
	
	Logger logger = Logger.getLogger(Log4jListener.class);
	public static final String log4jdirkey = "log4jdir";

	public void contextDestroyed(ServletContextEvent servletcontextevent) {
		System.getProperties().remove(log4jdirkey);
	}

	public void contextInitialized(ServletContextEvent servletcontextevent) {
		String log4jdir = servletcontextevent.getServletContext().getRealPath("/");
		System.out.println("log4jdir:" + log4jdir);
		System.setProperty(log4jdirkey, log4jdir);

		// System.out.println(new
		// File(org.apache.log4j.helpers.OptionConverter.substVars("${log4jdir}/logs
		// ", System.getProperties())).getAbsolutePath());
	}

}
