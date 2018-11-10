package com.estore.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.bean.Cart;
import com.estore.bean.CartExample;
import com.estore.bean.CartKey;
import com.estore.bean.Product;
import com.estore.bean.User;
import com.estore.dao.CartMapper;
import com.estore.service.CartService;
import com.estore.service.ProductService;

@Service("cartService")
public class CartServiceImpl implements CartService {


	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	ProductService productService;

	@Autowired
	CartMapper cartMapper;

	/**
	 * 先查询有没有这个购物城项目,  有则修改,  无则添加
	 */
	@Override
	public void addCart(Cart obj) {
		// TODO Auto-generated method stub
		logger.debug("add cart by : " + obj);
		
		Cart selectByPrimaryKey = cartMapper.selectByPrimaryKey(obj);
		if(selectByPrimaryKey == null) {
			//没有,增加
			cartMapper.insertSelective(obj);
		}else {
			//update
			obj.setNum(selectByPrimaryKey.getNum()+obj.getNum());
			cartMapper.updateByPrimaryKey(obj);
		}
		

	}

	@Override
	public List<Cart> getCarts(Integer userId) {
		// TODO Auto-generated method stub
		CartExample cartExample = new CartExample();
		cartExample.or().andUserIdEqualTo(userId);
		List<Cart> selectByExample = cartMapper.selectByExample(cartExample);
		return selectByExample;
	}

	@Override
	public List<Cart> getCarts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cart getCart(CartKey id) {
		// TODO Auto-generated method stub

		return cartMapper.selectByPrimaryKey(id);
	}

	@Override
	public String removeCart(CartKey id) {
		// TODO Auto-generated method stub
		cartMapper.deleteByPrimaryKey(id);
		return null;
	}

	@Override 
	public String updateCart(Cart cart) {
		// TODO Auto-generated method stub
		logger.debug("update  cart by : " + cart);
		
		cartMapper.updateByPrimaryKeySelective(cart);
		return null;
	}

	/**
	 * 保存用户的购物车
	 * 用户的cart以key value 的形式保存在session中,如果登录则同步到数据库
	 */
	@Override
	public void addCartOfSession(Cart cart, HttpSession session) throws Exception {
		// TODO Auto-generated method stub

		//拿到用户
		User u = (User) session.getAttribute("user");

		//以product作为map 的 key, 购物车数量作为value
		//也可以用product id 作为key, 购物车数量 是key, 但是这样购物车里就没有商品数据了, 客户访问购物车就不方便了
		Product p = productService.getProduct(cart.getProductId());
		Map<Product,Integer> carts = (Map<Product,Integer>) session.getAttribute("carts"); 

		if(carts == null) {
			//第一次使用购物车
			carts = new HashMap<Product,Integer>();
			session.setAttribute("carts", carts);
		}


		//购物车是否已有product, 如果没有 就新建一个
		Integer count = carts.get(p);
		if(count == null) {
			//商品第一次出现
			count = 0;

			// 如果用户已登录,添加到cart 数据库, 
			if(u != null) {
				cart.setUserId(u.getId());
				addCart(cart);
			}
		}else {
			
			//如果用户已登录,更新数据
			if(u != null) {
				cart.setUserId(u.getId());
				cart.setNum(count + cart.getNum());
				updateCart(cart);
			}
		}



		//更新数量,购物车里的数量不用考虑多用户库存不足问题, 
		//因为购物车里的商品可能很多天后才购买,  现在限制一个准确的库存,也没有太大意义
		count = count + cart.getNum();
		carts.put(p, count);


	}

	@Override
	public String removeCartByUserId(Integer id) {
		// TODO Auto-generated method stub
		CartExample cartExample = new CartExample();
		cartExample.or().andUserIdEqualTo(id);
		cartMapper.deleteByExample(cartExample);
		return null;
	}
	/*No qualifying bean of type 'com.estore.service.IndexElementService' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
	at org.springframework.beans.factory.support.DefaultLista*/

	@Override
	public void removeCartWithSession(HttpSession session, Integer id) {
		// TODO Auto-generated method stub
		//拿到用户
		User u = (User) session.getAttribute("user");
		if(u != null) {
			//用户已登录,同步删除数据库
			removeCart(new CartKey(u.getId(),id));
		}
		//删除session
		Map<Product,Integer> carts = (Map<Product,Integer>) session.getAttribute("carts"); 
		if(carts == null) {
			//System.out.println(carts);
			return;
		}
		Product p = null;
		for(Product product : carts.keySet()){
			if(product.getId() == id) {
				p = product;
				break;
			}
		}
		//删除session
		if(p != null) {
			
			carts.remove(p);
		}

	}

	@Override
	public Map<Product, Integer> _getCartOfMap(Integer userId) {
		// TODO Auto-generated method stub
		List<Cart> selectByUserId_join_product = cartMapper.selectByUserId_join_product(new CartKey(userId,null));
		HashMap<Product, Integer> hashMap = new HashMap<Product, Integer>();
		for (Cart cart : selectByUserId_join_product) {
			hashMap.put(cart.getProduct(), cart.getNum());
		}
		
		return hashMap;
	}
}
