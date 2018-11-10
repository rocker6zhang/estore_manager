package com.estore.web.listener;



import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class h
 *
 */
@WebListener
public class InitListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public InitListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	
    	ServletContext sc = sce.getServletContext();
    	//读取my配制文件,保存到application作用域中
    	//my中保存了email address and password ,  项目根路径root, 项目域名+根路径home url
    	ResourceBundle r = ResourceBundle.getBundle("my");
    	Enumeration<String> keys = r.getKeys();
    	while(keys.hasMoreElements()) {
    		String key = keys.nextElement();
    		sc.setAttribute(key,r.getString(key));
    	}
    	
    	
    	System.out.println("info:--------  application context 作用域 配制参数加载完成");
    	
    	
         // TODO Auto-generated method stub
    }
	
}
