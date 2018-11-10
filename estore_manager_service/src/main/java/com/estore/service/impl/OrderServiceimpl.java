package com.estore.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.bean.Cart;
import com.estore.bean.Order;
import com.estore.bean.OrderExample;
import com.estore.bean.Orderitem;
import com.estore.bean.Product;
import com.estore.bean.User;
import com.estore.dao.CartMapper;
import com.estore.dao.OrderMapper;
import com.estore.dao.OrderitemMapper;
import com.estore.dao.ProductMapper;
import com.estore.service.CartService;
import com.estore.service.OrderService;
import com.estore.service.ProductService;

/**
 * 
 * 在本层要处理 事务 获取和释放数据库资源
 * 
 * */

/**
 * 
 * @ClassName: OrderService 
 * @Description: TODO 用业务逻辑封装的订单及购物车操作类
 * @author: zw
 * @date: 2018年3月26日 下午1:59:51
 */
@Service("orderService")
public class OrderServiceimpl implements OrderService {
	
	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderitemMapper orderitemMapper;
	@Autowired
	CartMapper cartMapper;
	@Autowired
	CartService cartService;
	@Autowired
	ProductService productService;

	public String addOrder(List<Orderitem> Orderitems) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Order> getOrderByUser(User user) {
		// TODO Auto-generated method stub
		OrderExample orderExample = new OrderExample();
		orderExample.or().andUserIdEqualTo(user.getId());
		List<Order> selectByExample = orderMapper.selectByExample(orderExample);
		return selectByExample;
	}

	public List<Orderitem> getOrderitems(Order o) {
		// TODO Auto-generated method stub
		return null;
	}

	public void receipt(int order_id) {
		// TODO Auto-generated method stub

	}

	public void updateCartItem(Product p, Integer num, Integer uid) {
		// TODO Auto-generated method stub

	}

	public void addCartItem(Product p, Integer num, Integer uid) {
		// TODO Auto-generated method stub

	}

	public void removeCartItem(int user_id, Product p) {
		// TODO Auto-generated method stub

	}

	public Map<Product, Integer> getCart(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addOrder(List<Cart> items, HttpSession session, Order order) throws Exception {
		// TODO Auto-generated method stub
		User user = (User) session.getAttribute("user");
		Map<Product ,Integer > carts = (Map<Product, Integer>) session.getAttribute("carts");
		
		//写订单数据
		setPrice(items,session,order);
		orderMapper.updateByPrimaryKeySelective(order);

		//减库存,写orderItem到数据库
		for(Cart cart : items) {
			System.out.println("order id" + order.getId());
			productService.subPnum(cart);
			orderitemMapper.insertSelective(new Orderitem(order.getId(),cart.getProductId(),cart.getNum()));  
		}
//		System.out.println("items"+items);
//		System.out.println("carts"+carts);
		/*订单提交完成,删除无用数据*/
		//删除购物车项目
		for(Cart cart : items) {
			cart.setUserId(user.getId());
			//删除数据库
			cartService.removeCart(cart);
			
			//删除session中的购物车项目
			carts.remove(productService.getProduct(cart.getProductId()));
			
			/*
			 * 这种迭代删除会报异常    java.util.ConcurrentModificationException
								at java.util.HashMap$HashIterator.nextNode(HashMap.java:1437)
								at java.util.HashMap$KeyIterator.next(HashMap.java:1461)
				可能是因为: carts.keySet()直接返回是hashMap里维护的key集合,也就是说这个keySet hashMap还要用,  
				这时修改hashMap会传导到carts.keySet(), foreach 本身也是迭代器, 在集合迭代期间修改集合, 显然错误
			 * */
//			for(Product p : carts.keySet()) {
//				if(p.getId().equals(cart.getProductId())) {
//					carts.remove(p);
//				}
//			}
			
		}
		


	}

	/**
	 * 
	* @Title: setPrice  
	* @Description: TODO 计算订单总价
	* @param @param items
	* @param @param session
	* @param @param order    设定文件  
	* @return void    返回类型  
	* @throws
	 */
	private void setPrice(List<Cart> items, HttpSession session, Order order) {
		// TODO Auto-generated method stub
		
		double price = 0;
		for(Cart cart : items) {
			Product product = productService.getProduct(cart.getProductId());
			if(product == null) {
				logger.error("应该存在的商品不存在, 方法productService.getProduct(id) 返回null");
				return;
			}
			Double price2 = product.getPrice();
			price += price2 * cart.getNum();
			BigDecimal b = new BigDecimal(price);
			price = b.setScale(2, RoundingMode.HALF_UP).doubleValue();

		}
		
		//System.out.println(price+"--------------cart.size"+items.size());
		order.setPrice(price);
	}

	public void insertOrder(Order order) {
		order.setCreatedate(new Date());
		//写订单, 返回订单id保存到order对象中
		orderMapper.insert(order);

	}

	@Override
	public String removeOrder(Integer orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order getOrder(Integer orderId) {
		// TODO Auto-generated method stub
		
		return orderMapper.selectByPrimaryKey(orderId);
	}

	
	@Override
	public List<Order> getOrderInfo(User user) {
		// TODO Auto-generated method stub
		List<Order> selectByExample = orderMapper.selectAllInfoByUserId(user.getId());
		return selectByExample;
	}

	/* (non Javadoc) 
	 * @Title: addOrder
	 * @Description: TODO
	 * @param Orderitems
	 * @return 
	 * @see com.estore.service.OrderService#addOrder(java.util.List) 
	 */ 
	//
	//	@Override
	//	public String addOrder(List<Orderitem> Orderitems) {
	//		// TODO Auto-generated method stub
	//		String message = null;
	//		//注意:订单对象只有一个,
	//		Order o = Orderitems.get(0).getOrder();
	//
	//		Connection conn = null;
	//		try {
	//			conn = DataSourceUtils.getConnection();
	//			//开启事务, 阻止自动提交
	//			conn.setAutoCommit(false);
	//
	//			//减库存
	//			for(Orderitem oi : Orderitems) {
	//
	//				orderMapper.setPnum(oi.getProduct(),oi.getNum() * -1);
	//			}
	//			//写订单
	//			odao.addOrder(o);
	//
	//			//写订单item
	//			for(Orderitem oi : Orderitems) {
	//				odao.addOrderitem(oi);
	//			}
	//
	//			//一组操作完成, 提交
	//			conn.commit();
	//			//关闭事务
	//			conn.setAutoCommit(true);
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			message = "出错了";
	//			try {
	//				//出错回滚
	//				conn.rollback();
	//			} catch (SQLException e1) {
	//				// TODO Auto-generated catch block
	//				e1.printStackTrace();
	//			}
	//			e.printStackTrace();
	//		} finally {
	//
	//			closeConnection();
	//		}
	//
	//
	//
	//		return message;
	//
	//	}
	//
	//
	//
	//
	//	/* (non Javadoc) 
	//	 * @Title: getOrder
	//	 * @Description: TODO
	//	 * @param user
	//	 * @return 
	//	 * @see com.estore.service.OrderService#getOrder(com.estore.domain.User) 
	//	 */ 
	//	@Override
	//	public List<Order> getOrder(User user) {
	//		// TODO Auto-generated method stub
	//		List<Order> order = null;
	//		try {
	//			order = odao.getOrderByUser(user.getId());
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}finally {
	//
	//			closeConnection();
	//		}
	//
	//		return order;
	//	}
	//
	//
	//	/* (non Javadoc) 
	//	 * @Title: getOrderitems
	//	 * @Description: TODO
	//	 * @param o
	//	 * @return 
	//	 * @see com.estore.service.OrderService#getOrderitems(com.estore.domain.Order) 
	//	 */ 
	//	@Override
	//	public List<Orderitem> getOrderitems(Order o) {
	//		// TODO Auto-generated method stub
	//		List<Orderitem> Orderitems = null;
	//		try {
	//			Orderitems = odao.getOrderitems(o);
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}finally {
	//
	//			closeConnection();
	//		}
	//
	//		return Orderitems;
	//	}
	//
	//
	//	/* (non Javadoc) 
	//	 * @Title: receipt
	//	 * @Description: TODO
	//	 * @param order_id 
	//	 * @see com.estore.service.OrderService#receipt(int) 
	//	 */ 
	//	@Override
	//	public void receipt(int order_id) {
	//		// TODO Auto-generated method stub
	//		try {
	//			odao.receipt(order_id);
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}finally {
	//
	//			closeConnection();
	//		}
	//	}
	//
	//
	//	/* (non Javadoc) 
	//	 * @Title: updateCartItem
	//	 * @Description: TODO
	//	 * @param p
	//	 * @param num
	//	 * @param uid 
	//	 * @see com.estore.service.OrderService#updateCartItem(com.estore.domain.Product, java.lang.Integer, java.lang.Integer) 
	//	 */ 
	//	@Override
	//	public void updateCartItem(Product p, Integer num, Integer uid) {
	//		try {
	//			odao.updateCartItem(p, num, uid);
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}finally {
	//
	//			closeConnection();
	//		}
	//	}
	//
	//	/* (non Javadoc) 
	//	 * @Title: addCartItem
	//	 * @Description: TODO
	//	 * @param p
	//	 * @param num
	//	 * @param uid 
	//	 * @see com.estore.service.OrderService#addCartItem(com.estore.domain.Product, java.lang.Integer, java.lang.Integer) 
	//	 */ 
	//	@Override
	//	public void addCartItem(Product p, Integer num, Integer uid) {
	//		try {
	//			odao.addCartItem(p, num, uid);
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}finally {
	//
	//			closeConnection();
	//		}
	//	}
	//
	//	/* (non Javadoc) 
	//	 * @Title: removeCartItem
	//	 * @Description: TODO
	//	 * @param user_id
	//	 * @param p 
	//	 * @see com.estore.service.OrderService#removeCartItem(int, com.estore.domain.Product) 
	//	 */ 
	//	@Override
	//	public void removeCartItem(int user_id, Product p) {
	//		// TODO Auto-generated method stub
	//		try {
	//			odao.removeCartItem(user_id, p);
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}finally {
	//
	//			closeConnection();
	//		}
	//	}
	//	private void closeConnection() {
	//		// TODO Auto-generated method stub
	//		//释放连接
	//		try {
	//
	//			DataSourceUtils.closeConnection(DataSourceUtils.getConnection());
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//
	//	}
	//
	//
	//
	//
	//	/* (non Javadoc) 
	//	 * @Title: getCart
	//	 * @Description: TODO
	//	 * @param u
	//	 * @return 
	//	 * @see com.estore.service.OrderService#getCart(com.estore.domain.User) 
	//	 */ 
	//	@Override
	//	public Map<Product,Integer> getCart(User u) {
	//		// TODO Auto-generated method stub
	//		// DB cart
	//		Map<Integer,Integer> cart = null;
	//		// return for session cart
	//		Map<Product,Integer> sesCart = null;
	//		try {
	//			cart = odao.getCart(u);
	//			if(cart == null || cart.isEmpty()) {
	//				return null;
	//			}
	//			
	//			ProductService ps = new ProductServiceimpl();
	//			sesCart = new	HashMap<Product,Integer>();
	//			/*效率太差,合并成一条查询*/
	//			/*Product p = null;
	//			for(Integer pid : cart.keySet()) {
	//				p = ps.getProduct(pid);
	//				if(p != null) {
	//					sesCart.put(p, cart.get(pid));
	//				}
	//			}*/
	//			
	//			List<Product> products = orderMapper.getProductsbyIdSet(cart.keySet());
	//
	//		
	//			for(Product p : products) {
	//				
	//				sesCart.put(p, cart.get(p.getId()));
	//			}
	//
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}finally {
	//
	//			closeConnection();
	//		}
	//		return sesCart;
	//	}
	//


}
