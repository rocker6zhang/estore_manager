package com.estore.service;

import java.util.List;


import com.estore.bean.Product;


public interface productImgService {

	
	void addProduct(Product p);

	List<Product> getProducts(Integer num);
	
	List<Product> getProducts();

	Product getProduct(int id);

	String removeProduct(Integer id);

	String updateProduct(Product p);

}