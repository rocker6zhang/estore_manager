package com.estore.service.impl;

import java.util.List;

import org.apache.ibatis.io.ResolverUtil.Test;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.bean.Product;
import com.estore.bean.ProductModel;
import com.estore.bean.ProductSearch;
import com.estore.dao.ProductSolrMapper;
import com.estore.service.ProductSearchService;
import com.estore.utils.JsonMsg;

@Service("productSearchServiceImpl")
public class ProductSearchServiceImpl implements ProductSearchService {
	
	@Autowired
	ProductSolrMapper productSolrMapper;
	

	@Override
	public JsonMsg indexSearch(String keyWord) throws Exception {
		// TODO Auto-generated method stub
		//暂时只有product搜索
		ProductSearch productSearch = new ProductSearch();
		productSearch.setQueryString(keyWord);
		return productSearch(productSearch);
	}

	/**
	 * @throws Exception 
	 * 
	* @Title: productSearch  
	* @Description: TODO 商品搜索服务,  
	* @param @param productSearch 搜索条件
	* @return JsonMsg    返回类型  返回搜索结果
	* @throws
	 */
	@Override
	public JsonMsg productSearch(ProductSearch productSearch) throws Exception {
		// TODO Auto-generated method stub
		List<ProductModel> selectByProductSearch = productSolrMapper.selectByProductSearch(productSearch);
		return JsonMsg.success().addResult("searchResult", selectByProductSearch);
	}


	@Override
	public void deleteAll() throws Exception {
		// TODO Auto-generated method stub
		productSolrMapper.deleteAll();
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		productSolrMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int addSearchData(Product product) throws Exception {
		// TODO Auto-generated method stub
		productSolrMapper.add(product);
		return 0;
	}

	@Override
	public int updateSearchData(Product product) throws Exception {
		// TODO Auto-generated method stub
		productSolrMapper.updateByPrimaryKey(product);
		return 0;
	}

}
