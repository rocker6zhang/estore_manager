package com.estore.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.estore.bean.Cart;
import com.estore.bean.Order;
import com.estore.bean.Orderitem;
import com.estore.bean.Product;
import com.estore.bean.User;


public interface OrderService {

	/**
	 * 将订单和订单项写到数据库, 先减库存,再写订单.  库存的正确性由dao维护,  对订单的操作涉及到多张表操作,需要事务管理
	 * 
	 * */

	String addOrder(List<Orderitem> Orderitems);

	/**
	 * 
	* @Title: getOrderByUser  
	* @Description: TODO 查询用户所有订单 
	* @param @param user
	* @return List<Order>    返回订单集合,不包括订单项目 
	* @throws
	 */
	List<Order> getOrderByUser(User user);
	
	/**
	 * 
	* @Title: getOrderInfo  
	* @Description: TODO 查询用户所有订单   
	* @param @param user
	* @param @return    设定文件  
	* @return List<Order>    返回订单集合,包括订单项目 ,以及商品信息
	* @throws
	 */
	List<Order> getOrderInfo(User user);

	List<Orderitem> getOrderitems(Order o);

	void receipt(int order_id);

	/**
	 * 更新用户cart内容,  如果用户登录,用户cart需写入数据库
	 * */
	void updateCartItem(Product p, Integer num, Integer uid);


	Map<Product, Integer> getCart(User u);
	public void insertOrder(Order order);

	
	String removeOrder(Integer orderId);

	void addOrder(List<Cart> items, HttpSession session, Order order) throws Exception;

	Order getOrder(Integer orderId);

}