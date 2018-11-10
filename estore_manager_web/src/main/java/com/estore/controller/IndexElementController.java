package com.estore.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.estore.bean.PageElement;
import com.estore.bean.Product;
import com.estore.service.IndexElementService;
import com.estore.utils.JsonMsg;
import com.estore.utils.UploadUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

//标注为控制器,  已经配制了自动扫描
@Controller
@RequestMapping("/index")
public class IndexElementController {

	@Autowired
	IndexElementService indexElementService;


	@RequestMapping(value="/element",method=RequestMethod.POST)
	@ResponseBody
	public JsonMsg addElement(PageElement element, 
			@RequestParam("img") MultipartFile file) throws Exception {


		if(!file.isEmpty()) {

			// 获取上传文件名称,可能带有路径
			String filename = file.getOriginalFilename();
			if(filename == null || "".equals(filename)) {
				//没有图片文件
				return JsonMsg.fail("图片上传失败,可能是文件名异常");
			}

			//校验文件格式
			int index = filename.lastIndexOf('.');
			if(index == -1 || ".JPG .PNG .GIF".indexOf(filename.substring(index+1).toUpperCase()) == -1 ) {
				//不是图片
				return JsonMsg.fail("图片上传失败,图片后缀名必须为'.JPG .PNG .GIF'之一");
			}
		}

		//indexElementService.addPageElement(element,file);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}




	@RequestMapping(value="/element",method=RequestMethod.DELETE)
	@ResponseBody
	public JsonMsg deleteElement(Integer id) {

		System.out.println(id);
		if(id == null) {
			return JsonMsg.fail();
		}
		//indexElementService.removePageElement(id);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}

	@RequestMapping(value="/element",method=RequestMethod.PUT)
	@ResponseBody
	public JsonMsg updateElement(PageElement element ) {

		element.setUpdateTime(new Date());

		System.out.println(element);
		//indexElementService.updatePageElement(element);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();

	}

	@RequestMapping(value="/element",method=RequestMethod.GET)
	@ResponseBody
	public JsonMsg getElement(Integer  id) {


		PageElement element = indexElementService.getPageElement(id);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pojo", element);
	}

	@RequestMapping(value="/element/all")
	@ResponseBody
	public JsonMsg getAllElement() {


		//List<PageElement> list = indexElementService.getPageElements();
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success("all of data is too lot!!!");
	}

	@RequestMapping(value="/element/list")
	@ResponseBody
	public JsonMsg getAllElement(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize) {

		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		// PageHelper会在 影响 数据库操作, 在mybatis配制文件中配制了
		PageHelper.startPage(pageNum, pageSize);
		List<PageElement> list = indexElementService.getPageElements();
		// pageInfo包装查询后的结果,封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page = new PageInfo(list, 5);

		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pageInfo", page);
	}

	@RequestMapping(value="/element/getListByCategoryId")
	@ResponseBody
	public JsonMsg getElementsBycategory(@RequestParam(value = "categoryId") Integer categoryId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize) {

		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		// PageHelper会在 影响 数据库操作, 在mybatis配制文件中配制了    
		
		/*
		调试
		关闭分页后恢复正常
		mybatis执行 SELECT count(0) FROM page_element WHERE (category_id = ?)
		返回 null Total: 0
		这条sql语句是谁写的?可能是分页插件写的
		
		*/
//		### Error querying database.  Cause: java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
//		### The error may exist in file [D:\newWork\estore_manager\estore_manager_web\target\classes\mapper\PageElementMapper.xml]
//		### The error may involve com.estore.dao.PageElementMapper.selectByExample_COUNT
//		### The error occurred while handling results
//		### SQL: SELECT count(0) FROM page_element WHERE (category_id = ?)
//		### Cause: java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
//			at org.apache.ibatis.exceptions.ExceptionFactory.wrapException(ExceptionFactory.java:30)
		//
//		PageHelper.startPage(pageNum, pageSize);
		List<PageElement> list = indexElementService.getPageElementsByCategory(categoryId, pageNum);
		// pageInfo包装查询后的结果,封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page = new PageInfo(list, pageSize);

		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pageInfo", page);
		
		
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		// PageHelper会在 影响 数据库操作, 在mybatis配制文件中配制了
//		PageHelper.startPage(pageNum, pageSize);
//		List<Product> list = productService.getProductByCategoryId(categoryId);;
//		// pageInfo包装查询后的结果,封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
//		PageInfo page = new PageInfo(list, pageSize);
//		
//		//spring mvc 会自动将返回结果 json 化
//		return JsonMsg.success().addResult("pageInfo", page);
	}







}