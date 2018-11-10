package com.estore.service.impl;

import java.util.Date;
import java.util.List;

import javax.xml.ws.WebServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.bean.PageElementCategory;
import com.estore.dao.PageElementCategoryMapper;
import com.estore.service.IndexElementCategoryService;

@Service("indexElementCategoryService")
public class IndexElementCategoryServiceimpl implements IndexElementCategoryService {
	
	@Autowired
	PageElementCategoryMapper PageElementCategoryMapper;

	@Override
	public void addPageElementCategory(PageElementCategory p) {
		// TODO Auto-generated method stub
		p.setCreateTime(new Date());
		PageElementCategoryMapper.insertSelective(p);

		
	}

	@Override
	public List<PageElementCategory> getPageElementCategorys(Integer num) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public List<PageElementCategory> getPageElementCategorys() {
		// TODO Auto-generated method stub
		List<PageElementCategory> selectByExample = PageElementCategoryMapper.selectByExample(null);
		return selectByExample;
	}

	@Override
	public PageElementCategory getPageElementCategory(int id) {
		// TODO Auto-generated method stub
		PageElementCategory selectByPrimaryKey = PageElementCategoryMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}

	@Override
	public String removePageElementCategory(Integer id) {
		// TODO Auto-generated method stub
		PageElementCategoryMapper.deleteByPrimaryKey(id);
		return null;
	}

	@Override
	public String updatePageElementCategory(PageElementCategory p) {
		// TODO Auto-generated method stub
		PageElementCategoryMapper.updateByPrimaryKeySelective(p);
		return null;
	}

}
