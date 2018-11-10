package com.estore.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.estore.bean.PageElement;
import com.estore.bean.PageElementExample;
import com.estore.dao.JedisClient;
import com.estore.dao.PageElementMapper;
import com.estore.service.IndexElementService;
import com.estore.utils.FileUtil;
import com.estore.utils.JsonUtils;
import com.estore.utils.UploadUtils;
import org.springframework.stereotype.Service;

@Service("indexElementService")
public class IndexElementServiceimpl implements IndexElementService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Value("${FILE_UPLOAD_ROOR_PATH}")
	private String FILE_UPLOAD_ROOR_PATH;
	
	@Autowired
	private PageElementMapper pageElementMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	
	@Value("${index_element_cache_hname}")
	private String index_element_cache_hname;
				   
	
	

	public void addPageElement(PageElement p, MultipartFile file) throws IllegalStateException, IOException {
		// TODO Auto-generated method stub
		p.setCreateTime(new Date());
		p.setPicPath(saveFile(file));
		
		pageElementMapper.insertSelective(p);

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
			File randomDir = new File(FILE_UPLOAD_ROOR_PATH + "/image/upload" + uuidDir);

		
			// 判断随机目录是否存在，不存在，创建.
			if (!randomDir.exists()) {
				randomDir.mkdirs();
			}
           // System.out.println(filename);
            
			//最终存储的文件对象
			File dest = new File(randomDir, uuidname);
			
			//将文件的相对路径存入bean,  相对于图片库路径
			filePath = "/image/upload" + uuidDir + "/" + uuidname;
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

	public List<PageElement> getPageElements(Integer num) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PageElement> getPageElements() {
		// TODO Auto-generated method stub
		List<PageElement> selectByExample = pageElementMapper.selectByExample(null);
		return selectByExample;
	}

	public PageElement getPageElement(int id) {
		// TODO Auto-generated method stub
		PageElement selectByPrimaryKey = pageElementMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}

	public String removePageElement(Integer id) {
		// TODO Auto-generated method stub
		//delete image file
		Boolean flag = FileUtil.deleteFile(FILE_UPLOAD_ROOR_PATH + getPageElement(id).getPicPath());
		if(!flag) {
			logger.error("pageElement 的图片文件删除失败,文件path : " + FILE_UPLOAD_ROOR_PATH + getPageElement(id).getPicPath());
		}
		
		//delete datebase
		pageElementMapper.deleteByPrimaryKey(id);
		return null;
	}

	public String updatePageElement(PageElement p) {
		// TODO Auto-generated method stub
		pageElementMapper.updateByPrimaryKeySelective(p);
		return null;
	}

	public List<PageElement> getPageElementsByCategory(int id,Integer pageNum) {
		/*这里缓存会干扰 PageHelper 分页插件工作, 原因不明
		 * 表现为第一次访问正常, 后面访问(读缓存)失去总页数信息
		 * */
		//从缓存中取内容
//		try {
//			String result = jedisClient.hget(index_element_cache_hname, id + "" + pageNum);
//			if (!StringUtils.isBlank(result)) {
//				//把字符串转换成list
//				List<PageElement> resultList = JsonUtils.jsonToList(result, PageElement.class);
//				return resultList;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
				
		// TODO Auto-generated method stub
		PageElementExample pageElementExample =  new PageElementExample();
		pageElementExample.or().andCategoryIdEqualTo(id);
		List<PageElement> selectByExample = pageElementMapper.selectByExample(pageElementExample);
		
		//向缓存中添加内容
//		try {
//			//把list转换成字符串
//			String cacheString = JsonUtils.objectToJson(selectByExample);
//			jedisClient.hset(index_element_cache_hname, id + "" + pageNum, cacheString);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		return selectByExample;
	}

}
