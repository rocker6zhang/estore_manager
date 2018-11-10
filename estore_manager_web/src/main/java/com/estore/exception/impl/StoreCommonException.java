package com.estore.exception.impl;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;

import com.estore.exception.StoreException;

public class StoreCommonException extends Exception implements StoreException {
	
	private Integer code;
	
	private Boolean isJson;
	
	private String pageName;
	
	
	

	
	public StoreCommonException(Integer code, Boolean isJson, String message) {
		super(message);
		this.code = code;
		this.isJson = isJson;
	}
	
	public StoreCommonException() {
		super();
	}
	
	public StoreCommonException(Integer code, boolean b, String message, String pageName) {
		// TODO Auto-generated constructor stub
		super(message);
		this.code = code;
		this.isJson = b;
		this.pageName = pageName;
	}

	public static StoreCommonException withJson(Integer code, String message) {
		return new StoreCommonException(code, true, message);
	}
	
	public static StoreCommonException withPage(Integer code, String message, String pageName) {
		
		return new StoreCommonException(code, false, message, pageName);
	}

	
	public Boolean getIsJson() {
		return isJson;
	}
	
	public void setIsJson(Boolean isJson) {
		this.isJson = isJson;
	}
	
	public Integer getCode() {
		// TODO Auto-generated method stub
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}

	public Boolean isJson() {
		// TODO Auto-generated method stub
		return isJson;
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

	public String getPageName() {
		// TODO Auto-generated method stub
		return pageName;
	}
	
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}


}
