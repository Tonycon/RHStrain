package com.bgi.rank.util;

import com.whalin.MemCached.MemCachedClient;

public class MemcacheUtil {
	
	public final static long MEMCACHE_LONG_EXPIRE_TIME = Long.parseLong(PropertiesUtils.getValue("memcache.properties", "memcache.long.expire.time"));
	public final static long MEMCACHE_SHORT_EXPIRE_TIME = Long.parseLong(PropertiesUtils.getValue("memcache.properties", "memcache.short.expire.time"));
	
	public static MemCachedClient getMemCachedClient() {
		return SpringContextHolder.getBean("memcachedClient");
	}
}
