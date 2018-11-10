package com.estore.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.estore.exception.impl.StoreCommonException;
import com.estore.utils.JsonMsg;
import com.estore.utils.JsonUtils;

/**
 * 
 * @ClassName: TotalExceptionResolver 
 * @Description: TODO 前端同一异常处理器
 * @author: zw
 * @date: 2018年8月14日 上午10:24:25
 */
public class TotalExceptionResolver implements HandlerExceptionResolver {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	//前端控制器DispatcherServlet在进行HandlerMapping、调用HandlerAdapter执行Handler过程中，如果遇到异常就会执行此方法
	//handler最终要执行的Handler，它的真实身份是HandlerMethod
	//Exception ex就是接收到异常信息
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// TODO Auto-generated method stub
		ModelAndView doException = null;
		try {
			
			//输出异常
			
			logger.error(ex.getMessage() ,ex);
			logger.error("request.getRequestURI() == "+request.getRequestURI());
			
			//处理异常
			doException = doException(request, response, handler, ex);
		}catch(Exception e) {
			//放弃治疗
			e.printStackTrace();
		}

		if(doException == null) {
			return new ModelAndView("error/error");
		}
		
		return doException;

	}

	private ModelAndView doException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//统一异常处理代码
		StoreException storeException = null;
		//如果ex是系统 自定义的异常，直接取出异常信息
		if(ex instanceof StoreException){
			storeException = (StoreCommonException) ex;
		}else{
			//针对非CustomException异常，对这类重新构造成一个CustomException，异常信息为“未知错误”
			//大部分响应是json格式, 对于运行时异常返回json格式响应
			storeException = StoreCommonException.withJson(500, "系统出错了,请联系管理员");
		}

		logger.debug("isJson : " + storeException.isJson());
		if(storeException.isJson() != null && storeException.isJson() == true) {
			//返回json格式响应.
			JsonMsg fail = JsonMsg.fail(storeException.getCode(), storeException.getMessage());
			request.setAttribute("exceptionInfo", JsonUtils.objectToJson(fail));
			//在jsp页面设置, 重复设置会被覆盖
			//response.setHeader("Content-Type","application/json;charset=UTF-8");
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("jsonMessage");
			logger.debug("json异常处理: 返回 modelAndView ViewName " + modelAndView.getViewName());
			return modelAndView;
		}else {
			//返回html页面格式响应
			String pageName = storeException.getPageName();

			if(StringUtils.isBlank(storeException.getPageName())) {
				//默认页面
				pageName = "error/error";
			}
			//传递参数
			request.setAttribute("message", storeException.getMessage());
			request.setAttribute("code", storeException.getCode());

			//返回响应
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName(pageName);
			logger.debug("page异常处理: 返回 modelAndView ViewName " + modelAndView.getViewName());
			//			try {
			//				request.getRequestDispatcher("/WEB-INF/views/error/error.jsp").forward(request, response);
			//			} catch (ServletException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			} catch (IOException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			}
			return modelAndView;
		}
	}



}
