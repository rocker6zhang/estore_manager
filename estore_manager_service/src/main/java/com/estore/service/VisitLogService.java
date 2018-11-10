package com.estore.service;

import java.util.List;


import com.estore.bean.VisitLog;


public interface VisitLogService {

	
	void addVisitLog(VisitLog obj)throws Exception;

	List<VisitLog> getVisitLogs();

	VisitLog getVisitLog(int id);
	
	List<VisitLog> getVisitLogByVisitIp(String ip);
	
	List<VisitLog> getVisitLogWithVisitIp();

	String removeVisitLog(Integer id);

	String updateVisitLog(VisitLog obj);

}