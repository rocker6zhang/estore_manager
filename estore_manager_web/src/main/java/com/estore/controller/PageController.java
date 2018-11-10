package com.estore.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.estore.exception.impl.StoreCommonException;
import com.estore.service.LogFileService;
import com.estore.utils.JsonMsg;


//标注为控制器,  已经配制了自动扫描
@Controller
public class PageController {

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Value("${ROOT_PASSWORD}")
	String ROOT_PASSWORD;
	
	@Value("${ERROR_LOG_PATH}")
	String ERROR_LOG_PATH;
	
	@Value("${LOG_PATH}")
	String LOG_PATH;
	
	@Autowired
	private LogFileService logFileService;

	
	/**
	 * 
	* @Title: index  
	* @Description: TODO 将项目根路径映射到 page/index.html,  使用客户端跳转
	* @param @param httpServletRequest
	* @param @param httpServletResponse
	* @param @throws ServletException
	* @param @throws IOException    设定文件  
	* @return void    返回类型  
	* @throws
	 */

	@RequestMapping(value="/index.html")

	public void index(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws ServletException, IOException {
//		httpServletRequest.getRequestDispatcher("page/index.html").forward(httpServletRequest,httpServletResponse);
		httpServletResponse.sendRedirect("page/index.html");
	}
	
	@RequestMapping(value="/")
	
	public void index2(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException {
		httpServletResponse.sendRedirect("page/index.html");
	}
	
	
	/**
	 * 
	* @Title: proposal  
	* @Description: TODO 查看用户反馈页面映射  
	* @param @param httpServletRequest
	* @param @param password
	* @return String    
	* @throws  
	 */
	@RequestMapping(value="/page/showProposal")
	public String proposal(HttpServletRequest httpServletRequest, @RequestParam("password") String password) {
		logger.debug("ROOT_PASSWORD = "+ROOT_PASSWORD);
		if(password != null && password.equals(ROOT_PASSWORD)) {
			return "ProposalList";
		}else {
			httpServletRequest.setAttribute("jumpURL",httpServletRequest.getContextPath()+"/index.html");
			httpServletRequest.setAttribute("title","错误");
			httpServletRequest.setAttribute("message","密码错误");
			return "showMessage";
		}
		
		
	}
	
	


	@RequestMapping(value="/page/showVisitLog")
	
	public String visitLog(HttpServletRequest httpServletRequest,@RequestParam("password") String password) {
		logger.debug("root Password--"+ROOT_PASSWORD);
		logger.info("user visit by password : "+password);
		
		if(password != null && password.equals(ROOT_PASSWORD)) {
			return "visitLogList";
		}else {
			httpServletRequest.setAttribute("title","错误");
			httpServletRequest.setAttribute("message","密码错误");
			return "showMessageNotJump";
		}
		
		
	}
	
	
	
	@RequestMapping(value="/page/showUserVisit")
	
	public String showUserVisit(String ip,HttpServletRequest httpServletRequest) {
		if(ip != null) {
			httpServletRequest.setAttribute("ip", ip);
			return "showUserVisit";
		}else {
			httpServletRequest.setAttribute("jumpURL",httpServletRequest.getContextPath()+"/page/showVisitLog");
			httpServletRequest.setAttribute("title","错误");
			httpServletRequest.setAttribute("message","参数 ip 不能为空");
			return "showMessage";
		}
		
		
	}

//	
	@RequestMapping(value="/page/getFile")
	
	public ModelAndView getFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam(value="fileName")String fileName) throws StoreCommonException {
		httpServletResponse.setContentType("text/html; charset = UTF-8"); 
		PrintWriter writer;
		try {
			writer = httpServletResponse.getWriter();
			
			
			File file = new File(fileName);
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file),1024*100);
			String buf = null;
			while((buf = bufferedReader.readLine()) != null) {
				writer.println(buf);
			}
			
			bufferedReader.close();
			
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw StoreCommonException.withPage(500, "日志读取失败 : "+e.getMessage(), "error/error");
		}
		
		
		
		return new ModelAndView();
	}
	
	@RequestMapping(value="/page/showFile")
	
	public String showFile(HttpServletRequest httpServletRequest,@RequestParam(value="fileName")String fileName) throws StoreCommonException {
		System.out.println("in"
				+ "-----------------");
		File file = new File(fileName);
		if(file.isDirectory()) {
			//返回文件夹内文件列表
			List<String> list = logFileService.getAppLogFileList(fileName);
			
			httpServletRequest.setAttribute("FileList", list);
			
			return "showFileList";
		}
		//返回文件
		return "forward:getFile";
		
	}

	/**
	 * 
	* @Title: appLogFileList  
	* @Description: TODO 返回根目录列表  
	* @param @param httpServletRequest
	* @param @return
	* @param @throws StoreCommonException    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping(value="/page/appLogFileList")
	
	public String appLogFileList(HttpServletRequest httpServletRequest) throws StoreCommonException {
		
		return "FileRootList";
		
	}
	
	
	/**
	 * 
	* @Title: showFileList  
	* @Description: TODO 返回根目录文件列表, 处于安全性考虑, 可访问的目录路径写死
	* @param @param httpServletRequest
	* @param @param index
	* @param @return
	* @param @throws StoreCommonException    设定文件  
	* @return String    返回类型  
	* @throws
	 */
	@RequestMapping(value="/page/showFileList")
	
	public String showFileList(HttpServletRequest httpServletRequest, @RequestParam(value="index",defaultValue = "1")Integer index) throws StoreCommonException {
		
		String rootPath = "";;
		
		switch (index) {
		case 1:
			//win7 store_ssm 项目日志路径
			rootPath = "E:\\logs\\";
			break;
		case 2:
			//win7 store_ssm tomcat日志路径
			rootPath = "/home/wwwroot/ftptest/staticServer/log/";
			break;
		case 3:
			rootPath = "/usr/local/tomcat8_2/logs";
			break;
		case 4:
			rootPath = "/usr/local/tomcat8/logs";
			break;

		default:
			rootPath = "E:\\logs\\";
			break;
		}
		
		List<String> appLogFileList = logFileService.getAppLogFileList(rootPath);
		httpServletRequest.setAttribute("FileList", appLogFileList);
		System.out.println(appLogFileList);
		return "showFileList";
		
	}
	
	
//	@RequestMapping(value="/page/showLog")
//	
//	public ModelAndView showLog(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam(value="logName" ,defaultValue = "error")String logName) throws StoreCommonException {
//		PrintWriter writer;
//		try {
//			writer = httpServletResponse.getWriter();
//			
//			
//			File file = new File(LOG_PATH + logName);
//			
//			BufferedReader bufferedReader = new BufferedReader(new FileReader(file),1024*100);
//			String buf = null;
//			while((buf = bufferedReader.readLine()) != null) {
//				writer.println(buf);
//			}
//			
//			bufferedReader.close();
//			
//			writer.close();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			throw StoreCommonException.withPage(500, "日志读取失败 : "+e.getMessage(), "error/error");
//		}
//		
//		
//		
//		return new ModelAndView();
//	}
	
//	
//	@RequestMapping(value="/page/tomcatLogFileList")
//	@ResponseBody
//	public JsonMsg tomcatLogFileList(@RequestParam(value="tomcatName" ,defaultValue = "")String tomcatName) throws StoreCommonException {
//		
//		JsonMsg appLogFileList = logFileService.getTomcatLogFileList(tomcatName);
//		if(appLogFileList != null) {
//			appLogFileList = JsonMsg.fail();
//		}
//		
//		appLogFileList.addResult("title", "tomcat "+tomcatName+" log");
//		
//		return appLogFileList;
//		
//	}
//	
//	@RequestMapping(value="/page/getAppLog")
//	@ResponseBody
//	public JsonMsg getAppLog(@RequestParam(value="fileName")String fileName) throws StoreCommonException {
//		
//		JsonMsg appLogFileList = logFileService.getAppLogFile(fileName);
//		if(appLogFileList != null) {
//			appLogFileList = JsonMsg.fail();
//		}
//		
//		appLogFileList.addResult("title", "application log");
//		
//		return appLogFileList;
//		
//	}
//	
//	@RequestMapping(value="/page/getTomcatLog")
//	@ResponseBody
//	public JsonMsg getTomcatLog(@RequestParam(value="fileName")String fileName) throws StoreCommonException {
//		
//		JsonMsg appLogFileList = logFileService.getTomcatLogFile(fileName);
//		if(appLogFileList != null) {
//			appLogFileList = JsonMsg.fail();
//		}	
//		
//		appLogFileList.addResult("title", "application log");
//		
//		return appLogFileList;
//		
//	}
//	
//	
//	
//	@RequestMapping(value="/page/showErrorLog")
//	
//	public ModelAndView showErrorLog(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws StoreCommonException {
//		PrintWriter writer;
//		try {
//			//放置乱码
//			httpServletResponse.setContentType("text/html; charset = UTF-8"); 
//			writer = httpServletResponse.getWriter();
//			
//			File file = new File(ERROR_LOG_PATH);
//			
//			BufferedReader bufferedReader = new BufferedReader(new FileReader(file),1024*100);
//			String buf = null;
//			while((buf = bufferedReader.readLine()) != null) {
//				writer.println(buf);
//			}
//			
//			bufferedReader.close();
//			
//			writer.close();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			throw StoreCommonException.withPage(500, "日志读取失败 : "+e.getMessage(), "error/error");
//		}
//		
//		
//		
//		return new ModelAndView();
//	}
//	

}