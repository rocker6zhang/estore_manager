package com.estore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.estore.bean.Product;
import com.estore.service.ProductService;
import com.estore.utils.JsonMsg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

//标注为控制器,  已经配制了自动扫描
@Controller
public class ProductController {

	@Autowired
	ProductService productService;


	@RequestMapping(value="/product",method=RequestMethod.POST)
	@ResponseBody
	public JsonMsg addProduct(Product product, @RequestParam("img") MultipartFile file) throws Exception {


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


		//productService.addProduct(product,file);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}

	@RequestMapping(value="/product",method=RequestMethod.DELETE)
	@ResponseBody
	public JsonMsg deleteProduct(Integer  id) {

		//productService.removeProduct(id);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}

	@RequestMapping(value="/product",method=RequestMethod.PUT)
	@ResponseBody
	public JsonMsg updateProduct(Product product) {


		//productService.updateProduct(product);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}

	@RequestMapping(value="/product",method=RequestMethod.GET)
	@ResponseBody
	public JsonMsg getProduct(Integer  id) {


		Product product = productService.getProduct(id);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pojo", product);
	}

	@RequestMapping(value="/product/all")
	@ResponseBody
	public JsonMsg getAllProduct() throws Exception {


		//List<Product> list = productService.getProducts();
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success("all of product is too lot");
		
	}

	@RequestMapping(value="/product/list")
	@ResponseBody
	public JsonMsg getProducts(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) throws Exception {



		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		// PageHelper会在 影响 数据库操作, 在mybatis配制文件中配制了
		PageHelper.startPage(pageNum, pageSize);
		List<Product> list = productService.getProducts();
		// pageInfo包装查询后的结果,封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page = new PageInfo(list, 5);

		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pageInfo", page);
	}
	
	@RequestMapping(value="/product/getProductByCategoryId")
	@ResponseBody
	public JsonMsg getProductByCategoryId(@RequestParam(value = "categoryId") Integer categoryId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) throws Exception {
		
		
		
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		// PageHelper会在 影响 数据库操作, 在mybatis配制文件中配制了
		PageHelper.startPage(pageNum, pageSize);
		List<Product> list = productService.getProductByCategoryId(categoryId,pageNum);;
		// pageInfo包装查询后的结果,封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page = new PageInfo(list, pageSize);
		
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pageInfo", page);
	}



	@RequestMapping(value="/product/addProductDescriptionImage")
	@ResponseBody
	public JsonMsg addProductDescriptionImage(Product product, @RequestParam("imgs") MultipartFile[] file) throws Exception {


		//检查参数
		for(MultipartFile f : file) {

			if(!f.isEmpty()) {

				// 获取上传文件名称,可能带有路径
				String filename = f.getOriginalFilename();
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
		}

		productService.addProductDescriptionImage(product,file);

		return JsonMsg.success();
	}




}