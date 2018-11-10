package com.estore.service;

import javax.servlet.http.HttpSession;

import com.estore.bean.User;
import com.estore.utils.JsonMsg;

public interface UserService {

	/**
	 * 
	* @Title: regist  
	* @Description: TODO 注册
	* @param @param u 用户
	* @return JsonMsg  注册信息, 如果发送异常返回null 
	* @throws
	 */
	JsonMsg regist(User u);

	/**
	 * 
	* @Title: active  
	* @Description: TODO 使用激活码激活用户 
	* @param @param activeCode :激活码
	* @param @return    设定文件  
	* @return String    为空表示正常.非空,其内容为激活错误报告, 
	* @throws
	 */
	String active(String activeCode);

	

	/**
	* @Title: login  
	* @Description: TODO 根据用户名和密码获取用户对象 
	* @param @param username 
	* @param @param password 
	* @return user对象, 如果没有找到用户返回null
	* @throws
	 */
	User getUser(String username, String password);
	
	/**
	 * 
	* @Title: login  
	* @Description: TODO (这里用一句话描述这个方法的作用)  
	* @param @param user
	* @param @return    设定文件  
	* @return JsonMsg    返回类型  
	* @throws
	 */
	JsonMsg login(User user);
	
	/**
	 * 
	* @Title: logout  
	* @Description: TODO 注销用户 
	* @param @return    设定文件  
	* @return JsonMsg    返回类型  
	* @throws
	 */
	JsonMsg logout(String token);
	
	
	
	
	
	
	

	/**
	* @Title: hasName  
	* @Description: TODO 验证用户名 是否可用 
	* @param @param name
	* @param @return    设定文件  
	* @return boolean    true : name已存在, false name不存在 
	* @throws
	 */
	boolean hasName(String name);
	
	/**
	 * 
	* @Title: hasEmail  
	* @Description: TODO 判断email是否已注册并且激活
	* @param @param email
	* @return boolean    如果已注册并且激活 返回true; 否则返回false
	* @throws
	 */
	public boolean hasEmail(String email);

	User loginOfSession(User u, HttpSession httpSession);

	JsonMsg getUserByToken(String token);

	Boolean checkToken(String value);
	
	void syuCart(HttpSession httpSession, User u);
	
	JsonMsg sendActiveEmail(User user);

}