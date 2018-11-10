package com.estore.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.estore.bean.PageElement;
import com.estore.bean.PageElementExample;


public interface IndexElementService {

	
	void addPageElement(PageElement p, MultipartFile file)throws Exception;

	List<PageElement> getPageElements(Integer num);
	
	List<PageElement> getPageElements();

	PageElement getPageElement(int id);

	String removePageElement(Integer id);

	String updatePageElement(PageElement p);
	
	List<PageElement> getPageElementsByCategory(int id ,Integer pageNum) ;

}