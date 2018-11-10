package com.estore.service;

import java.util.List;


import com.estore.bean.Product;


public interface Service {

	
	void addProduct(Product obj);

	List<Product> getProducts(Integer num);
	
	List<Product> getProducts();

	Product getProduct(int id);

	String removeProduct(Integer id);

	String updateProduct(Product obj);

}