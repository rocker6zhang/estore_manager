package com.estore.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.bean.Cart;
import com.estore.bean.Product;
import com.estore.bean.User;
import com.estore.service.CartService;
import com.estore.service.DataService;
import com.estore.service.UserService;
import com.estore.utils.CookieUtils;
import com.estore.utils.EstoreAPI;
import com.estore.utils.JsonMsg;
import com.estore.utils.OpenAPI;

@Service("dataService")
public class DataServiceImpl implements DataService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	UserService userService;
	
	@Autowired
	private CartService cartService ;

	
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	HttpServletRequest httpServletRequest;

	

	@Override
	public Map<String, Object> getUserPageInfo() throws Exception {
		// TODO Auto-generated method stub
		//用户
		User user = (User) httpSession.getAttribute("user");
		//购物车
		Map<Product,Integer> carts =  (Map<Product, Integer>) httpSession.getAttribute("carts");
		//user ip
		String requestURL = httpServletRequest.getRemoteAddr();
		
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("code", 666);
		if(user != null) {
			info.put("username", user.getUsername());
		}
		
		if(carts != null) {
			info.put("cartNum", carts.size());
		}else {
			info.put("cartNum", 0);
		}
		
		if(requestURL == null || requestURL.equals("47.104.191.132")) {
			info.put("city", "本地用户");
		}else {
			String city = OpenAPI.getCityWithIP(requestURL);
			if(city != null) {
				info.put("city", "city");
			}
		}
		logger.debug("user info for page :"+info.toString());
		return info;
	}


	@Override
	public Map<String, Object> getUserPageInfo(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	
	@Override
	public boolean isLogin() throws Exception {
		// TODO Auto-generated method stub
//		
		
		return getUserByToken() != null;
	}
	
	
	@Override
	public User getUserByToken() throws Exception {
		// TODO Auto-generated method stub
		
		/*check user has login*/
		User user = (User) httpSession.getAttribute("user");
		if(user != null) {
			//用户已登录,并保存到session里了
			//校验用户登录状态是否有效,
			if(checkToken((String) httpSession.getAttribute("USER_TOKEN"))) {
				//登录有效
				return user;
			}else {
				//登录无效
				removeUserInfo();
			}
		}
		
		//检查用户是否携带sso认证token
		String token = httpServletRequest.getParameter("USER_TOKEN");
		//检查cookie中是否有token
		if(StringUtils.isBlank(token)) {
			Cookie findCookieByName = CookieUtils.findCookieByName(httpServletRequest.getCookies(),
					"USER_TOKEN");
			if(findCookieByName != null) {
				token = findCookieByName.getValue();	
			}
		}
		if(! StringUtils.isBlank(token)) {
			logger.debug(" check user token for sso by:"+token);
			
			/*用户携带sso认证token, 根据token获取user ,*/
			JsonMsg userByToken = EstoreAPI.getUserByToken(token);
			if(userByToken == null || userByToken.getCode() != 100) {
				return null;
			}
			user = (User) userByToken.getExtend().get("user");
			if(user != null) {
				System.out.println("*********************in************************");
				httpSession.setAttribute("user", user);
				httpSession.setAttribute("isLogin", true);
				httpSession.setAttribute("USER_TOKEN", token);
				
				//同步购物车
				syuCart(httpSession, user);
				logger.debug(" check user token for sso return user:"+user);
			}
		}
	
		
		
		return user;
	}


	private boolean checkToken(String token) {
		// TODO Auto-generated method stub
		Boolean flag = null;
		//校验token 调用 sso 系统api 检查是否有效
//		flag = EstoreAPI.checkToken(token);
//		logger.debug("check token by "+token);
//		
//		if(flag == null) {
//			logger.debug("check token return null");
//			flag = false;
//		}
		
		//直接去调用userService 去 redis 检查,减少调用http消耗
		
		flag = userService.checkToken(token);
		if(flag == null) {
			logger.debug("check token  return null");
			flag = false;
		}
		return flag;
	}


	@Override
	public JsonMsg removeUserInfo() {
		// TODO Auto-generated method stub
		httpSession.removeAttribute("user");
		httpSession.removeAttribute("USER_TOKEN");
		httpSession.setAttribute("isLogin", false);
		return JsonMsg.success();
	}


	@Override
	public JsonMsg logout() {
		// TODO Auto-generated method stub
		//httpSession.removeAttribute("user");
		String USER_TOKEN = (String)httpSession.getAttribute("USER_TOKEN");
		removeUserInfo();
		return EstoreAPI.logout(USER_TOKEN);
	}
	
	/**
	 * 
	 * @Title: syuCart  
	 * @Description: TODO 同步session与数据库中的cart 
	 * @param @param httpSession
	 * @param @param u    设定文件  
	 * @return void    返回类型  
	 * @throws
	 */
	public void syuCart(HttpSession httpSession, User u) {
		// TODO Auto-generated method stub
		Map<Product,Integer> cart = (Map<Product,Integer>) httpSession.getAttribute("carts");

		if(cart == null) {
			//购物车为空,也需要同步,  把数据库同步到session
			cart = new HashMap<Product,Integer>();
			httpSession.setAttribute("carts", cart);
		}

		//db cart
		Map<Product,Integer> dbcart = cartService._getCartOfMap(u.getId());

		logger.debug("**********************database cart : "+dbcart);
		logger.debug("**********************session cart : "+cart);
		logger.debug("**********************user : "+u);
		
		//检查 dbcart是否为null, 
		if(dbcart == null || dbcart.isEmpty()) {
			// 数据库里没有cart item,  session cart 全部写入 db  
			for(Product p : cart.keySet()) {
				//add to db
				cartService.addCart(new Cart(u.getId(),p.getId(),cart.get(p)));

			}
			//不用再同步了
			return;
		}
		

		//遍历 session cart, 将session中的cart同步到数据库
		for(Product p : cart.keySet()) {
			if(dbcart.containsKey(p)) {
				//DB 已有, UPDATE
				cartService.updateCart(new Cart(u.getId(),p.getId(),cart.get(p) + dbcart.get(p)));
				logger.debug("update cart db : "+p);
			}else {
				//DB 没有,  add
				cartService.addCart(new Cart(u.getId(),p.getId(),cart.get(p)));
				logger.debug("add cart db : "+p);
			}
		}

		//遍历数据库cart,将数据库中的cart同步到session;
//		if(dbcart == null || dbcart.isEmpty()) {
//			//如果null,  不需要同步了
//			return;
//		}

		//遍历 DB cart
		for(Product p : dbcart.keySet()) {
			Integer count = cart.get(p);
			if(count != null) {
				//DB 已有, UPDATE
				cart.put(p, count + dbcart.get(p));
			}else {
				//DB 没有,  add
				cart.put(p, dbcart.get(p));

			}
		}

		dbcart = null;


	}


}
