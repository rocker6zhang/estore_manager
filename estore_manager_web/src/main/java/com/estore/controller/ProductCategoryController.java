package com.estore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estore.bean.ProductCategory;
import com.estore.service.ProductCategoryService;
import com.estore.utils.JsonMsg;

//标注为控制器,  已经配制了自动扫描
@Controller
@RequestMapping("/product")
public class ProductCategoryController {

	@Autowired
	ProductCategoryService productCategoryService;


	@RequestMapping(value="/category",method=RequestMethod.POST)
	@ResponseBody
	public JsonMsg addCategory(ProductCategory category) {

		
		//productCategoryService.addProductCategory(category);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}
	
	@RequestMapping(value="/category",method=RequestMethod.DELETE)
	@ResponseBody
	public JsonMsg deleteCategory(Integer  id) {
		
		//productCategoryService.removeProductCategory(id);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}
	
	@RequestMapping(value="/category",method=RequestMethod.PUT)
	@ResponseBody
	public JsonMsg updateCategory(ProductCategory category) {

		
		//productCategoryService.updateProductCategory(category);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}
	
	@RequestMapping(value="/category",method=RequestMethod.GET)
	@ResponseBody
	public JsonMsg getCategory(Integer  id) {

		
		ProductCategory category = productCategoryService.getProductCategory(id);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pojo", category);
	}
	
	@RequestMapping(value="/category/all")
	@ResponseBody
	public JsonMsg getAllCategory() {

		
		List<ProductCategory> list = productCategoryService.getProductCategorys();
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("list", list);
	}
	




}