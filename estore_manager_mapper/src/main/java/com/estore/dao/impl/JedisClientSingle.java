package com.estore.dao.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.estore.dao.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientSingle implements JedisClient{
	
	@Autowired
	private JedisPool jedisPool; 
	
	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.hget(hkey, key);
		jedis.close();
		return string;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}

	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	@Override
	public long hdel(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(hkey, key);
		jedis.close();
		return result;
	}
	
	public void test(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		//jedis.ltrim(key, start, end)
	}

	/**
	 * add list
	 */
	@Override
	public Long lpush(String listkey, String... key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.lpush(listkey, key);
		jedis.close();
		return result;
	}

	/**
	 * return all of list element
	 */
	@Override
	public List<String> getAllList(String listkey) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		List<String> result = jedis.lrange(listkey, 0, jedis.llen(listkey));
		jedis.close();
		return result;
	}

	@Override
	public List<String> lrange(String listkey, Long start, Long end) {
		Jedis jedis = jedisPool.getResource();
		List<String> result = jedis.lrange(listkey, start, end);
		jedis.close();
		return result;
	}

	@Override
	public Long llen(String listkey) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.llen(listkey);
		jedis.close();
		return result;
	}

	@Override
	public String ltrim(String listkey, Long start, Long end) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		String result = jedis.ltrim(listkey,start, end);
		jedis.close();
		return result;
	}

	
}
