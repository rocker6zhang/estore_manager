package com.estore.dao;

import java.util.List;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

public interface JedisClient {

	String get(String key);
	String set(String key, String value);
	String hget(String hkey, String key);
	long hset(String hkey, String key, String value);
	long incr(String key);
	long expire(String key, int second);
	long ttl(String key);
	long del(String key);
	long hdel(String hkey, String key);
	Long lpush(String listkey, String... key);
	List<String> getAllList(String listkey);
	List<String> lrange(String listkey, Long start, Long end);
	Long llen(String listkey);
	String ltrim(String listkey, Long start, Long end);
	
	
}
