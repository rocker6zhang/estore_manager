package com.estore.web.filter;



import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.estore.bean.VisitLog;
import com.estore.service.VisitLogService;
import com.estore.service.impl.VisitLogServiceImpl;








public class VisitLogFilter implements Filter {
	
	@Autowired
	VisitLogService visitLogService;
	private static Logger logger = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		logger = Logger.getLogger(VisitLogFilter.class);
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		System.out.println("======================visit filter in=======================");
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		//httpServletRequest.getRequestURI()
		String uri = httpServletRequest.getRequestURI();
		String contextPath = httpServletRequest.getContextPath();
		String path = uri.substring(contextPath.length());
		//过滤静态资源
		if(!path.contains("css") && !path.contains("upload")) {
			System.out.println("======================visitLogService in=======================");
			
			if(visitLogService == null) {
				visitLogService = new VisitLogServiceImpl();
						
			}
			try {
				visitLogService.addVisitLog(new VisitLog());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// 放行
		chain.doFilter(httpServletRequest, response);
	}

	public void destroy() {
		logger = null;

	}

}
