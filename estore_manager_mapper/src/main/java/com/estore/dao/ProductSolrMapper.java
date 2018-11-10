package com.estore.dao;

import java.util.List;


import com.estore.bean.Product;
import com.estore.bean.ProductModel;
import com.estore.bean.ProductSearch;

public interface ProductSolrMapper {
	

    void deleteAll() throws Exception;

    void deleteByPrimaryKey(Integer id) throws Exception;

    int add(Product product) throws Exception;

    List<ProductModel> selectByProductSearch(ProductSearch productSearch) throws Exception;

    int updateByPrimaryKey(Product product) throws Exception;
}