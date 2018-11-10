package com.estore.service;

import java.util.List;


import com.estore.bean.ProductCategory;


public interface ProductCategoryService {

	
	void addProductCategory(ProductCategory p);

	List<ProductCategory> getProductCategorys(Integer num);
	
	List<ProductCategory> getProductCategorys();

	ProductCategory getProductCategory(int id);

	String removeProductCategory(Integer id);

	String updateProductCategory(ProductCategory p);

}