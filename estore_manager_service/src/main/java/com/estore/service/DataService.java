package com.estore.service;

import java.util.Map;

import com.estore.bean.User;
import com.estore.utils.JsonMsg;


/**
 * 
 * @ClassName: DataService 
 * @Description: TODO 提供客户端需要的一般数据服务(不好归类的杂项, 通用的数据等)
 * @author: zw
 * @date: 2018年8月1日 下午5:47:06
 */
public interface DataService {

	/**
	 * 
	* @Title: getUserPageInfo  
	* @Description: TODO 依据用户id返回用户的页面相关信息  
	* @param @param UserId
	* @param @return
	* @param @throws Exception    设定文件  
	* @return Map<String,Object>    返回类型  
	* @throws
	 */
	Map<String,Object> getUserPageInfo(Integer UserId)throws Exception;
	
	/**
	 * 
	* @Title: getUserPageInfo  
	* @Description: TODO 依据session中的用户返回用户的页面相关信息   
	* @param @return
	* @param @throws Exception    设定文件  
	* @return Map<String,Object>    返回类型  
	* @throws
	 */
	
	Map<String,Object> getUserPageInfo()throws Exception;
	
	/**
	 * 
	* @Title: isLogin  
	* @Description: TODO 检查用户是否登录 
	* @param @throws Exception    设定文件  
	* @return boolean   已登录返回true,否则返回false 
	* @throws
	 */
	boolean isLogin()throws Exception;

	User getUserByToken() throws Exception;

	/**
	 * 
	* @Title: removeUserInfo  
	* @Description: TODO 删除用户在当前application中的登录信息  
	* @param @return    设定文件  
	* @return JsonMsg    返回类型  
	* @throws
	 */
	JsonMsg removeUserInfo();

	/**
	 * 
	* @Title: logout  
	* @Description: TODO 调用sso系统api 注销用户 
	* @param @return    设定文件  
	* @return JsonMsg    返回类型  
	* @throws
	 */
	JsonMsg logout();
}