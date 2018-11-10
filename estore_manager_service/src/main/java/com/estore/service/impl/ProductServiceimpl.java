package com.estore.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.estore.bean.Cart;
import com.estore.bean.Product;
import com.estore.bean.ProductExample;
import com.estore.bean.ProductImg;
import com.estore.dao.JedisClient;
import com.estore.dao.ProductImgMapper;
import com.estore.dao.ProductMapper;
import com.estore.service.ProductSearchService;
import com.estore.service.ProductService;
import com.estore.service.RedisService;
import com.estore.service.productImgService;
import com.estore.utils.JsonMsg;
import com.estore.utils.JsonUtils;
import com.estore.utils.UploadUtils;


/**
 * 
 * @ClassName: Productervice 
 * @Description: TODO 用业务逻辑封装的商品操作类
 * @author: zw
 * @date: 2018年3月26日 下午1:58:39
 */

@Service("productService")
public class ProductServiceimpl implements ProductService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	//ROOT_PATH
	@Value("${FILE_UPLOAD_ROOT_PATH}")
	String ROOR_PATH;
	//mybatis 的 对象关系映射, 操作user表
	//ssm整合后有spring 注入
	@Autowired
	private ProductMapper productMapper ;
	
	@Autowired 
	private ProductImgMapper productImgMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private ProductSearchService productSearchService;
	
	
	@Value("${product_cache_hname}")
	private String product_cache_hname;



	
	public String addProduct(Product p , MultipartFile file) throws IllegalStateException, IOException {
	
		//保存商品图片
		p.setImgPath(saveFile(file));
		//保存商品
		productMapper.insertSelective(p);
		//添加商品搜索索引
		try {
			productSearchService.addSearchData(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("添加商品索引错误!");
			e.printStackTrace();
		}
		return null;
	}

	public List<Product> getProducts(Integer num) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<Product> getProducts() {
		// TODO Auto-generated method stub
		//查询全部
		List<Product> selectByExample = productMapper.selectByExample(null);
		return selectByExample;
	}

	public Product getProduct(int id) {
		
		//从缓存中取内容
		try {
			String result = jedisClient.hget(product_cache_hname, id + "");
			if (!StringUtils.isBlank(result)) {
				//把字符串转换成list
				Product product = JsonUtils.jsonToPojo(result, Product.class);
				return product;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Product selectByPrimaryKey = productMapper.selectByPrimaryKey(id);
		
		
		//向缓存中添加内容
		try {
			//把list转换成字符串
			String cacheString = JsonUtils.objectToJson(selectByPrimaryKey);
			jedisClient.hset(product_cache_hname, id + "", cacheString);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return selectByPrimaryKey;
	}

	public String removeProduct(Integer id) {
		// TODO Auto-generated method stub
		productMapper.deleteByPrimaryKey(id);
		
		//更新索引
		try {
			productSearchService.deleteById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("删除索引错误",e);
			e.printStackTrace();
		}
		return null;
	}

	public String updateProduct(Product p) {
		// TODO Auto-generated method stub
		productMapper.updateByPrimaryKeySelective(p);
		
		//更新索引
		try {
			productSearchService.updateSearchData(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("更新索引错误",e);
			e.printStackTrace();
		}
		return null;
	}


	private String saveFile(MultipartFile file) throws IllegalStateException, IOException {

		String filePath = null;
		
		//如果文件不为空，写入上传路径
        if(!file.isEmpty()) {
        	
        	// 获取上传文件名称,可能带有路径
        	String filename = file.getOriginalFilename();
			
			// 得到真实名称,去除路径
			filename = UploadUtils.subFileName(filename);

			// 得到随机名称
			String uuidname = UploadUtils.generateRandonFileName(filename);

			// 得到随机目录
			String uuidDir = UploadUtils
					.generateRandomDir(uuidname);

			//将随机目录和随机文件名组成相对路径,与当前ServletContext的真实路径/upload组合成完整路径
			//文件保存在 真实路径/upload 下方便浏览器获取
			File randomDir = new File(ROOR_PATH + "/upload" + uuidDir);

		
			// 判断随机目录是否存在，不存在，创建.
			if (!randomDir.exists()) {
				randomDir.mkdirs();
			}
           // System.out.println(filename);
            
			//最终存储的文件对象
			File dest = new File(randomDir, uuidname);
			
			//将文件的相对路径存入bean,  相对于图片库路径
			filePath = uuidDir + "/" + uuidname;
            //将上传文件保存到一个目标文件当中
            file.transferTo(dest);
            
         // 生成缩略图
//			PicUtils putils = new PicUtils(dest.getCanonicalPath());// 获取上传文件的绝对磁盘路径。
//
//			// 产生一个200*200的缩略图
//			putils.resize(200, 200);
        
            return filePath;
        } else {
        	return null;
        }
		
	}
	
	
	@Override
	public void addProductDescriptionImage(Product product, MultipartFile[] file) throws IllegalStateException, IOException {
		// TODO Auto-generated method stub
		for (MultipartFile multipartFile : file) {
			//存储image , 路径写入数据库
			String path = saveFile(multipartFile);
			productImgMapper.insertSelective(new ProductImg(path,product.getId()));
			
		}
	}

	@Override
	public List<Product> getProductByCategoryId(Integer categoryId,Integer pageNum) {
		/*这里缓存会干扰 PageHelper 分页插件工作, 原因不明
		 * 表现为第一次访问正常, 后面访问(读缓存)失去总页数信息
		 * */
		
		//从缓存中取内容
//		try {
//			String result = jedisClient.hget(product_cache_hname, "categoryId:" + categoryId + "pageNum:" + pageNum);
//			if (!StringUtils.isBlank(result)) {
//				//把字符串转换成list
//				List<Product> resultList = JsonUtils.jsonToList(result, Product.class);
//				return resultList;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		ProductExample productExample = new ProductExample();
		productExample.or().andCategoryIdEqualTo(categoryId);
		List<Product> selectByExample = productMapper.selectByExample(productExample);
		
		//向缓存中添加内容
//		try {
//			//把list转换成字符串
//			String cacheString = JsonUtils.objectToJson(selectByExample);
//			jedisClient.hset(product_cache_hname, "categoryId:" + categoryId + "pageNum:" + pageNum, cacheString);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return selectByExample;
	}

	@Override
	public void subPnum(Cart cart) throws Exception {
		// TODO Auto-generated method stub
		int subPnum = productMapper.subPnum(cart);
		logger.debug("productMapper.subPnum(cart) 返回int值 : " + subPnum);
		//更新缓存
		JsonMsg updateProductByKey = redisService.updateProductByKey(cart.getProductId()+"");
		if(! updateProductByKey.getExtend().get("del").equals("1")) {
			logger.error("缓存更新失败 redisService.updateProductByKey(cart.getProductId()+\"\");返回 : " + updateProductByKey);
		}
	}
	
}
