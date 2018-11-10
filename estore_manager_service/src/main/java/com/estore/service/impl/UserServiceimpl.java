package com.estore.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.estore.bean.Cart;
import com.estore.bean.Product;
import com.estore.bean.User;
import com.estore.bean.UserExample;
import com.estore.dao.JedisClient;
import com.estore.dao.UserMapper;
import com.estore.service.CartService;
import com.estore.service.UserService;
import com.estore.utils.HttpClientUtil;
import com.estore.utils.JsonMsg;
import com.estore.utils.JsonUtils;
import com.estore.utils.MD5Utils;
import com.estore.utils.MailUtils;



/**
 * 
 * @ClassName: UserService 
 * @Description: TODO user 业务逻辑处理.<br/> 处理用户的注册/登录/信息加密(密码MD5)
 * @author: zw
 * @date: 2018年3月26日 下午1:49:20
 */
@Service("userService")
public class UserServiceimpl implements UserService  {

	
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	protected final String HEAD = "*** SSO ***";
	
	
	//mybatis 的 对象关系映射, 操作user表
	//ssm整合后有spring 注入
	@Autowired
	private UserMapper userMapper ;

	@Autowired
	private CartService cartService ;

	@Autowired
	private JedisClient jedisClient ;
	
	@Autowired
	private HttpSession httpSession ;

	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;

	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;




	@Value("${ACTIVE_URL}")
	String ACTIVE_URL;

	@Value("${WEB_NAME}")
	String WEB_NAME;

	/* (non Javadoc) 
	 * @Title: regist
	 * @Description: TODO 注册
	 * @param u 用户
	 * @return 
	 * @see com.estore.service.UserService#regist(com.estore.domain.User) 
	 */ 
	public JsonMsg regist(User u){ 
		UserExample ue = new UserExample();
		List<User> list = null;

		//判断用户名是否已经存在

		if(hasName(u.getUsername())){
			return JsonMsg.fail("username already exists ");
		}

		//判断 email address 是否已注册
		if(hasEmail(u.getEmail())) {
			return JsonMsg.fail("email already use!!!");
		}


		//补全参数
		//将用户密码用md5加密
		u.setPassword( MD5Utils.md5( u.getPassword() ) );

		//设置激活码
		u.setActivecode(UUID.randomUUID().toString());

		//设置激活状态
		u.setState(0);
		
		u.setUpdatetime(new Date());
		
		

		userMapper.insert(u);


		//send 激活 email...
		try {
			sendEmail(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			JsonMsg.fail("注册成功, 不过激活邮件发送失败,请在激活页面激活");
		}

		return JsonMsg.success("注册成功").addResult("activeCode", u.getActivecode());
	}


	/**
	 * 
	 * @Title: hasEmail  
	 * @Description: TODO 判断email是否已注册并且激活
	 * @param @param email
	 * @param @return    设定文件  
	 * @return boolean    如果已注册并且激活 返回true; 否则返回false
	 * @throws
	 */

	public boolean hasEmail(String email) {
		// TODO Auto-generated method stub
		UserExample userExample = new UserExample();
		userExample.or().andEmailEqualTo(email);
		boolean flag = false;

		//可能查到多个
		List<User> selectByExample = userMapper.selectByExample(userExample);
		for(User user : selectByExample) {

			// 是否存在 email 并且 已激活,只要没有激活就可以用,
			if(user != null && (user.getState() == null || user.getState() == 1)){
				flag = true;
			}
		}
		return flag;
	}



	/**
	 * @throws javax.mail.MessagingException 
	 * 
	 * @Title: sendEmail  
	 * @Description: TODO 发送激活邮件
	 * @param @param u
	 * @param @throws AddressException 如果邮件地址错误
	 * @param @throws MessagingException 如果发送失败
	 * @return void  
	 * @throws
	 */
	private void sendEmail(User u) throws Exception {
		//http://47.104.191.132:8080/store/activeUser
		//调用邮件工具出错, 使用API"/estore/sendEmail"发送邮件
		String message = "你正在注册"+WEB_NAME+"的会员,你的注册激活码是:"+"<a href='"+ACTIVE_URL+"?"
				+ "activeCode="+u.getActivecode()+"'>"+u.getActivecode()+"</a>, 请点击激活"+"  此邮件由系统发出,请勿回复.如果你没有注册, 请忽略这封邮件,谢谢!";
//		MailUtils.sendMail(u.getEmail(), "用户激活", message);
		
		Map<String, String> pam = new HashMap<>();
		pam.put("emailAddress", u.getEmail()); 
		pam.put("subject", "用户激活"); 
		pam.put("message", message); 
		String jsonData = HttpClientUtil.doGet("http://47.104.191.132/estore/sendEmail",pam);
		JsonMsg jsonToPojo = JsonUtils.jsonToPojo(jsonData, JsonMsg.class);
		if(jsonToPojo.getCode() == 200) {
			throw new Exception("邮件发送失败");
		}
		
	}



	/**
	 * 
	 * 
	 * */
	/* (non Javadoc) 
	 * @Title: active
	 * @Description: TODO 激活用户
	 * @param activeCode 激活码
	 * @return 
	 * @see com.estore.service.UserService#active(java.lang.String) 
	 */ 

	public String active(String activeCode) {
		UserExample ue = new UserExample();
		String wrongMsg = null;

		User u = null;
		ue.or().andActivecodeEqualTo(activeCode);
		List<User> l = userMapper.selectByExample(ue);

		if(l != null || l.size() == 1) {
			u = l.get(0);
		}


		//判断激活码是否正确
		if(u == null) {
			wrongMsg = "激活码错误"; 
			return wrongMsg;
		}


		/*
		 * 检查激活码是否多人重复激活
		//这里假设的场景是 同一个邮箱,  多次注册,但是都没有激活,  只允许一个激活
		// 是否存在 email 并且 已激活,
		 * 
		 * */
		UserExample userExample = new UserExample();
		userExample.or().andStateEqualTo(1).andEmailEqualTo(u.getEmail());
		List<User> selectByExample = userMapper.selectByExample(userExample);
		//不为null说明已注册或激活
		if(! selectByExample.isEmpty()){
			logger.debug(selectByExample);
			logger.debug(userExample.getOrderByClause());
			logger.debug(userExample.toString());
			//需要判断 次邮箱是被其他人注册了, 还是此用户重复激活
			if(selectByExample.size() > 1) {
				//不允许邮箱被激活超过1次,错误了
				logger.error(u.getEmail() + " 邮箱被激活超过1次.");
			}
			
			User user = selectByExample.get(0);
			
			if(user.getUsername().equals(u.getUsername())) {
				wrongMsg = "you already actived, don't repeat active!!!";
			}else {
				wrongMsg = "this email already use, please write new Email address from regist page!!!";
			}
			
			return wrongMsg;
		}


		//判断激活码是否过期
		long time = System.currentTimeMillis() - u.getUpdatetime().getTime();
		if(time > 1000*60*60*2) {
			wrongMsg = "激活码过期";
		}else {
			//激活用户
			u.setState(1);
			userMapper.updateByPrimaryKeySelective(u);
		}

		return wrongMsg;
	}

	/**
	 * @Title: sendActiveEmail  
	 * @Description: TODO 发送激活邮件 
	 * @param @param u 用户 @see User  
	 * @return String    为空表示正常.非空,其内容为注册错误报告,  
	 * @throws
	 */
	public JsonMsg sendActiveEmail(User u){
		
		String wrongMsg = "发送成功";

		//检查用户是否存在

		User user = getUser(u.getUsername(),u.getPassword());
		if(user == null) {
			wrongMsg = "用户名或密码错误";
			return JsonMsg.fail(wrongMsg);
		}
		
		//设置激活码
		user.setActivecode(UUID.randomUUID().toString());

		user.setUpdatetime(new Date());

		updateByid(user);

		try {
			sendEmail(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			wrongMsg = "发送失败";
			e.printStackTrace();
			
		}
		return JsonMsg.success(wrongMsg);
	}

	private void updateByid(User user) {
		// TODO Auto-generated method stub
		userMapper.updateByPrimaryKeySelective(user);
	}


	/* (non Javadoc) 
	 * @Title: login
	 * @Description: TODO 登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 
	 * @see com.estore.service.UserService#getUser(java.lang.String, java.lang.String) 
	 */ 
	public User getUser(String username, String password){
		//		System.out.println(username+password);
		UserExample ue = new UserExample();
		List<User> list = null;
		User u = null;


		ue.or().andUsernameEqualTo(username);

		list = userMapper.selectByExample(ue);
		//查询结果只有一条
		if(list == null || list.size() == 0) {
			return null;
		}

		u = list.get(0);
		if(!list.get(0).getPassword().equalsIgnoreCase(MD5Utils.md5( password))) {
			//密码错
			return null;
		}

		u.setPassword(null);
		return u;

	}


	/* (non Javadoc) 
	 * @Title: hasName
	 * @Description: TODO 验证用户名是否已使用(存在)
	 * @param name 用户名
	 * @return 
	 * @see com.estore.service.UserService#hasName(java.lang.String) 
	 */ 
	public boolean hasName(String name) {
		UserExample ue = new UserExample();
		boolean f = true;
		ue.or().andUsernameEqualTo(name);
		if(userMapper.selectByExample(ue).isEmpty()){
			//为空,name不存在
			f = false;
		}

		return f;
	}

	public JsonMsg login(User user) {
		//查询用户
		UserExample ue = new UserExample();
		List<User> list = null;
		User u = null;


		ue.or().andUsernameEqualTo(user.getUsername());

		list = userMapper.selectByExample(ue);
		//查询结果只有一条
		if(list == null || list.size() == 0) {
			logger.debug("账户名错误,数据库中没有账户:"+user.getUsername());
			return JsonMsg.fail("账户名或密码错误");
		}
		
		u = list.get(0);
		
		//校验密码
		if(! u.getPassword().equalsIgnoreCase(MD5Utils.md5( user.getPassword()))) {
			//密码错
			logger.debug("账户名或密码错误,用户密码="+user.getPassword()+"db password="+u.getPassword());
			return JsonMsg.fail("账户名或密码错误");
		}
		
		
		//检查账户是否激活
		if(u.getState() == null || u.getState() != 1) {
			//未激活
			logger.debug("user 未激活 username: "+user.getUsername());
			return JsonMsg.fail(202,"用户未激活").addResult("ACTIVE_URL", ACTIVE_URL);
		}

		
		/*将用户写入redis*/
		
		//生成token
		String token = UUID.randomUUID().toString();
		//保存用户之前，把用户对象中的密码清空。
		u.setPassword(null);
		//把用户信息写入redis
		jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(u));
		//设置session的过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);

		//添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效。
		//		这里不写cookie了, 返回token到客户端由客户端负责处理
//		CookieUtils.setCookie(, , "TT_TOKEN", token);

		
		logger.debug(HEAD+"sso user login 成功,USER_TOKEN="+token);

		return JsonMsg.success().addResult("USER_TOKEN", token);
	}
	
	@Override
	public JsonMsg logout(String token) {
		// TODO Auto-generated method stub
		long del = jedisClient.del(REDIS_USER_SESSION_KEY + ":" + token);
		
		logger.debug(HEAD+"用户注销 token="+token+"redis返回值  del="+del);
		if(del != 1) {
			
			return JsonMsg.fail().addResult("del", del);
		}
		return JsonMsg.success().addResult("del", del);
	}



	@Override
	public User loginOfSession(User user, HttpSession httpSession) {
		UserExample ue = new UserExample();
		List<User> list = null;
		User u = null;


		ue.or().andUsernameEqualTo(user.getUsername());

		list = userMapper.selectByExample(ue);
		//查询结果只有一条
		if(list == null || list.size() == 0) {
			return null;
		}

		u = list.get(0);
		if(! u.getPassword().equalsIgnoreCase(MD5Utils.md5( user.getPassword()))) {
			//			System.out.println("u.getPassword().equalsIgnoreCase(MD5Utils.md5( user.getPassword()))--->"+u.getPassword().equalsIgnoreCase(MD5Utils.md5( user.getPassword())));
			//			System.out.println("u.getPassword()--->"+u.getPassword());
			//			System.out.println("MD5Utils.md5( user.getPassword())--->"+MD5Utils.md5( user.getPassword()));

			//密码错
			return null;
		}

		//同步用户的session购物车内容和数据的购物车内容

		/*登录后处理*/
		syuCart(httpSession,u);

		u.setPassword(null);

		return u;
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


	@Override
	public JsonMsg getUserByToken(String token) {
		// TODO Auto-generated method stub

		//从redis中查询用户信息
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		//判断是否为空
		if (StringUtils.isBlank(json)) {
			logger.debug(HEAD+"redis 中不存在 key:"+REDIS_USER_SESSION_KEY + ":" + token);
			return JsonMsg.fail(200, "此session已经过期，请重新登录");
		}
		
		logger.debug(HEAD+"redis 存在 key:"+REDIS_USER_SESSION_KEY + ":" + token+",value:"+json);
		
		//更新过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		//返回用户信息
		return JsonMsg.success().addResult("user", json);

	}


	@Override
	public Boolean checkToken(String token) {
		//从redis中查询用户信息
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		//判断是否为空
		if (StringUtils.isBlank(json)) {
			logger.debug(HEAD+"redis 中不存在 key:"+REDIS_USER_SESSION_KEY + ":" + token);
			return false;
		}
		logger.debug(HEAD+"redis 存在 key:"+REDIS_USER_SESSION_KEY + ":" + token+",value:"+json);
		//更新过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
		//返回用户信息
		return true;

	}


	




































}
