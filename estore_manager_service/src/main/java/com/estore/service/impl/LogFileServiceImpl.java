package com.estore.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.estore.service.LogFileService;
import com.estore.utils.JsonMsg;
@Service("logFileService")
public class LogFileServiceImpl implements LogFileService {
	
	@Value("${LOG_PATH}")
	String LOG_PATH;
	
	@Value("${TOMCAT_PATH}")
	String TOMCAT_PATH;

	/**
	 * 读取文件夹内的问价列表
	 * 返回 文件list
	 */
	@Override
	public List<String> getAppLogFileList(String path) {
		// TODO Auto-generated method stub
		
		File f = new File( path);
		System.out.println(path);
		File[] files = f.listFiles();
		List<String> list = new ArrayList<String>(50);
		for(File file : files) {
			list.add(file.toString().replaceAll("\\\\", "/"));
		}
//		System.out.println(Arrays.toString(files));
//		System.out.println(list);
		
		return list;
	}

	StringBuilder readFile(File file) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		BufferedReader bufferedReader = null;
		//使用缓冲包装
		try {
		bufferedReader = new BufferedReader(new FileReader(file),1024*100);
		String buf = null;
		//读取
		
			while((buf = bufferedReader.readLine()) != null) {
				stringBuilder.append(buf);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(bufferedReader != null) {
				bufferedReader.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stringBuilder;
	}
	

}
