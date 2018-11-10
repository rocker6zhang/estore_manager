package com.estore.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.estore.dao.JedisClient;
import com.estore.service.RedisService;
import com.estore.utils.JsonMsg;

@Service("redisService")
public class RedisServiceImpl implements RedisService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	JedisClient jedisClient;
	
	@Value("${index_element_cache_hname}")
	private String index_element_cache_hname;
	
	@Value("${product_cache_hname}")
	private String product_cache_hname;
	
	@Value("${product_category_cache_hname}")
	private String product_category_cache_hname;

	
	@Value("${user_info_cache_hname}")
	private String user_info_cache_hname;

	@Override
	public JsonMsg updateIndexElementAll() {
		// TODO Auto-generated method stub
		long del = jedisClient.del(index_element_cache_hname);
		logger.debug("update IndexElement for redis cache by " + index_element_cache_hname + ",update result : " + del);
		return JsonMsg.success().addResult("del", del);
	}

	@Override
	public JsonMsg updateProductByKey(String updateKey) {
		
		long del = jedisClient.hdel(product_cache_hname,updateKey);
		logger.debug("update product for redis cache by updateKey: " + product_cache_hname + "(" + updateKey + "),update result : " + del);
		return JsonMsg.success().addResult("del", del);
	}



	@Override
	public JsonMsg updateProductAll() {
		long del = jedisClient.del(product_cache_hname);
		logger.debug("remove all of product for redis cache, update result : " + del);
		return JsonMsg.success().addResult("del", del);
	}
	
	
	@Override
	public JsonMsg updateProductCategory(String categoryId) {
		// TODO Auto-generated method stub
		long del = jedisClient.hdel(product_category_cache_hname, categoryId);
		logger.debug("update product for redis cache by categoryId:" + product_category_cache_hname + "(" + categoryId + ")update result : " + del);
		return JsonMsg.success().addResult("del", del);
	}

	@Override
	public JsonMsg updateProductCategoryAll() {
		long del = jedisClient.del(product_category_cache_hname);
		logger.debug("remove all of ProductCategory for redis cache, update result : " + del);
		return JsonMsg.success().addResult("del", del);
	}

}
