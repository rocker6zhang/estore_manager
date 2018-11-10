package com.estore.exception;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * @ClassName: StoreException 
 * @Description: TODO store 项目所有异常的父接口, 此接口规范异常代码,异常消息,和异常处理方式. 异常处理方式分为两种:<br/>
 * 						1. 返回json格式的响应, 在响应中给出异常代码和异常消息<br/>
 * 						2. 返回错误页面, 提供异常代码和异常消息给jsp页面,供其显示<br/>
 * 						当isJson为使用何种处理方式的判断标准.当isJson为true时使用方式1,
 * @author: zw
 * @date: 2018年8月14日 上午9:18:52
 */
public interface StoreException {
	/**
	 * 
	* @Title: getCode  
	* @Description: TODO 返回异常代码 
	* @return Integer    返回类型  
	* @throws
	 */
	Integer getCode();
	
	/**
	 * 
	* @Title: isJson  
	* @Description: TODO 返回是否已json格式的响应处理异常  
	* @return Boolean    是返回true,否则返回false  
	* @throws
	 */
	Boolean isJson();
	
	/**
	 * 
	* @Title: getMessage  
	* @Description: TODO 返回错误信息  
	* @return String    返回类型  
	* @throws
	 */
	String getMessage();
	
	/**
	 * 
	* @Title: getPageName  
	* @Description: TODO 返回异常处理文件name  
	* @param @return    设定文件  
	* @return String    文件名称  
	* @throws
	 */
	String getPageName();
	
}
