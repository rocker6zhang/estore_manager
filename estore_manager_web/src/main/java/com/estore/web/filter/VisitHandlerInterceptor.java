package com.estore.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.estore.bean.VisitLog;
import com.estore.service.VisitLogService;
import com.estore.service.impl.VisitLogServiceImpl;

public class VisitHandlerInterceptor implements HandlerInterceptor{
	
	@Autowired
	VisitLogService visitLogService;

	/**
	 * controller执行前调用此方法
	 * 返回true表示继续执行，返回false中止执行
	 * 这里可以加入登录校验、权限拦截等
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		
//	System.out.println("======================visit interceptor in=======================");
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		//httpServletRequest.getRequestURI()
		String uri = httpServletRequest.getRequestURI();
		String contextPath = httpServletRequest.getContextPath();
		String path = uri.substring(contextPath.length());
//		System.out.println("path:---"+path);
//		System.out.println("uri:---"+uri);
//		System.out.println("contextPath:---"+contextPath);
//		path:---/index/visitLog/list
//		uri:---/estore_ssm/index/visitLog/list
//		contextPath:---/estore_ssm
		//过滤静态资源
		if(!path.contains("/image") && !path.contains("/static")) {
//			System.out.println("======================interceptor visitLogService in=======================");
			
			visitLogService.addVisitLog(new VisitLog());
		}
		
		
		return true;
	}
	/**
	 * controller执行后但未返回视图前调用此方法
	 * 这里可在返回用户前对模型数据进行加工处理，比如这里加入公用信息以便页面显示
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}
	/**
	 * controller执行后且视图返回后调用此方法
	 * 这里可得到执行controller时的异常信息
	 * 这里可记录操作日志，资源清理等
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
