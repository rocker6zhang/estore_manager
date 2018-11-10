package com.estore.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.estore.bean.Product;
import com.estore.bean.ProductCategory;
import com.estore.dao.JedisClient;
import com.estore.dao.ProductCategoryMapper;
import com.estore.service.ProductCategoryService;
import com.estore.utils.JsonUtils;
@Service("productCategoryService")
public class ProductCategoryServiceimpl implements ProductCategoryService {
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	ProductCategoryMapper productCategoryMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	
	@Value("${product_category_cache_hname}")
	private String product_category_cache_hname;


	
	@Override
	public void addProductCategory(ProductCategory p) {
		// TODO Auto-generated method stub
		p.setCreateTime(new Date());
		productCategoryMapper.insertSelective(p);

	}

	@Override
	public List<ProductCategory> getProductCategorys(Integer num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductCategory> getProductCategorys() {
		// TODO Auto-generated method stub
		List<ProductCategory> selectByExample = productCategoryMapper.selectByExample(null);
		System.out.println(selectByExample);
		return selectByExample;
	}

	@Override
	public ProductCategory getProductCategory(int id) {
		// TODO Auto-generated method stub
		
		//从缓存中取内容
		try {
			String result = jedisClient.hget(product_category_cache_hname, id + "");
			if (!StringUtils.isBlank(result)) {
				//把字符串转换成pojo
				ProductCategory productCategory = JsonUtils.jsonToPojo(result, ProductCategory.class);
				logger.debug("redis cache 命中 : " + id +" from " + product_category_cache_hname);
				return productCategory;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		
		ProductCategory selectByPrimaryKey = productCategoryMapper.selectByPrimaryKey(id);
		
		
		//向缓存中添加内容
		try {
			//把list转换成字符串
			String cacheString = JsonUtils.objectToJson(selectByPrimaryKey);
			jedisClient.hset(product_category_cache_hname, id + "", cacheString);
			logger.debug("add redis cache key: " + id +" from " + product_category_cache_hname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return selectByPrimaryKey;
	}

	@Override
	public String removeProductCategory(Integer id) {
		// TODO Auto-generated method stub
		productCategoryMapper.deleteByPrimaryKey(id);
		return null;
	}

	@Override
	public String updateProductCategory(ProductCategory p) {
		// TODO Auto-generated method stub
		p.setUpdateTime(new Date());
		productCategoryMapper.updateByPrimaryKeySelective(p);
		return null;
	}

}
