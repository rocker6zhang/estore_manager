package com.estore.service;


import java.util.List;

import com.estore.utils.JsonMsg;

public interface LogFileService {

	List<String> getAppLogFileList(String appName);
	
	

}