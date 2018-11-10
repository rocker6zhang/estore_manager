package com.estore.service;

import java.util.List;


import com.estore.bean.PageElementCategory;


public interface IndexElementCategoryService {

	
	void addPageElementCategory(PageElementCategory p);

	List<PageElementCategory> getPageElementCategorys(Integer num);
	
	List<PageElementCategory> getPageElementCategorys();

	PageElementCategory getPageElementCategory(int id);

	String removePageElementCategory(Integer id);

	String updatePageElementCategory(PageElementCategory p);

}