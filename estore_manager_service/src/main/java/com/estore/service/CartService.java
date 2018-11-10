package com.estore.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.estore.bean.Cart;
import com.estore.bean.CartKey;
import com.estore.bean.Product;


public interface CartService {

	
	void addCart(Cart obj);

	List<Cart> getCarts(Integer num);
	
	List<Cart> getCarts();
	
	Map<Product,Integer> _getCartOfMap(Integer userId);

	Cart getCart(CartKey id);

	String removeCart(CartKey id);
	
	String removeCartByUserId(Integer id);

	String updateCart(Cart obj);

	void addCartOfSession(Cart cart, HttpSession session)throws Exception;

	void removeCartWithSession(HttpSession session, Integer id);

}