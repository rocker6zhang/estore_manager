package com.estore.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.estore.bean.Cart;
import com.estore.bean.Product;


public interface ProductService {

	String addProduct(Product p, MultipartFile file)throws Exception;

	List<Product> getProducts(Integer num)throws Exception;
	
	List<Product> getProducts()throws Exception;

	Product getProduct(int id);

	String removeProduct(Integer id);

	String updateProduct(Product p);


	void addProductDescriptionImage(Product product, MultipartFile[] file)throws Exception;

	List<Product> getProductByCategoryId(Integer categoryId, Integer pageNum);
	
	/**
	 * 
	* @Title: subPnum  
	* @Description: TODO 减库存, 将cart item 里的商品id所对应的商品的数量减去cart item 的数量
	* @param @param cart
	* @throws
	 */
	void subPnum(Cart cart) throws Exception;

}