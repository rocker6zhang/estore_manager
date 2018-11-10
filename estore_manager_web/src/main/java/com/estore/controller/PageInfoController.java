package com.estore.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estore.bean.ProductCategory;
import com.estore.service.DataService;
import com.estore.service.ProductCategoryService;
import com.estore.utils.JsonMsg;

//标注为控制器,  已经配制了自动扫描
@Controller
@RequestMapping("/pageData")
public class PageInfoController {

	@Autowired
	DataService dataService;
	
	@Autowired
	ProductCategoryService productCategoryService;
	
	


	/**
	 * @throws Exception 
	 * 
	* @Title: getDataByCategoryId  
	* @Description: TODO 根据用户iD查询与用户相关的页面信息.这里的用户指服务使用者,不论登录与否都可以使用此服务.<br/>
	* 用户信息有业务层决定.此方法首先在application作用域(后续考虑用redis代替)里面寻找用户info，找到则返回，否则方位service层获取 
	* @param @param userId 用户id
	* @param @return    设定文件  
	* @return JsonMsg    返回类型  用户信息
	* @throws
	 */
	@RequestMapping("/userPageInfo")
	@ResponseBody
	public JsonMsg getUserPageInfo() throws Exception {
		Map<String, Object> userPageInfo = dataService.getUserPageInfo();
		return JsonMsg.success().addResult("userInfo", userPageInfo);
	}




}