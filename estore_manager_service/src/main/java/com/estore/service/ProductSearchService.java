package com.estore.service;

import java.util.List;

import com.estore.bean.Product;
import com.estore.bean.ProductModel;
import com.estore.bean.ProductSearch;
import com.estore.utils.JsonMsg;

public interface ProductSearchService {

	JsonMsg indexSearch(String keyWord) throws Exception;
	
	JsonMsg productSearch(ProductSearch productSearch) throws Exception;
	
	/**
	 * @throws Exception 
	 * 
	* @Title: deleteAll  
	* @Description: TODO 删除所有搜索服务器索引数据  
	* @return void    返回类型  
	* @throws
	 */
	void deleteAll() throws Exception;

	/**
	 * @throws Exception 
	 * 
	* @Title: deleteByPrimaryKey  
	* @Description: TODO 删除某条搜索服务器索引数据    
	* @param @param id   数据的id
	* @return void    返回类型  
	* @throws
	 */
    void deleteById(Integer id) throws Exception;

    
    int addSearchData(Product product) throws Exception;


    int updateSearchData(Product product) throws Exception;
	

}