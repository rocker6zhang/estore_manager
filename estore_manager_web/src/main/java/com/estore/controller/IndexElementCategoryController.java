package com.estore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estore.bean.PageElementCategory;
import com.estore.service.IndexElementCategoryService;
import com.estore.utils.JsonMsg;

//标注为控制器,  已经配制了自动扫描
@Controller
@RequestMapping("/index")
public class IndexElementCategoryController {

	@Autowired
	IndexElementCategoryService indexElementCategoryService;


	@RequestMapping(value="/category",method=RequestMethod.POST)
	@ResponseBody
	public JsonMsg addCategory(PageElementCategory category) {

		
		//indexElementCategoryService.addPageElementCategory(category);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}
	
	@RequestMapping(value="/category",method=RequestMethod.DELETE)
	@ResponseBody
	public JsonMsg deleteCategory(Integer  id) {
		
		if(id == null) {
			return JsonMsg.fail("没有收到参数");
		}
		
		//indexElementCategoryService.removePageElementCategory(id);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}
	
	@RequestMapping(value="/category",method=RequestMethod.PUT)
	@ResponseBody
	public JsonMsg updateCategory(PageElementCategory category) {

		
		
		//indexElementCategoryService.updatePageElementCategory(category);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}
	
	@RequestMapping(value="/category",method=RequestMethod.GET)
	@ResponseBody
	public JsonMsg getCategory(Integer  id) {

		System.out.println("indexElementCategoryService"+indexElementCategoryService == null);
		System.out.println("id"+id == null);
		
		if(id == null) {
			return JsonMsg.fail("没有收到参数");
		}
		
		PageElementCategory category = indexElementCategoryService.getPageElementCategory(id);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pojo", category);
	}
	
	@RequestMapping(value="/category/all")
	@ResponseBody
	public JsonMsg getAllCategory() {

		
		List<PageElementCategory> list = indexElementCategoryService.getPageElementCategorys();
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("list", list);
	}
	




}