package com.estore.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.bean.VisitLog;
import com.estore.bean.VisitLogExample;
import com.estore.dao.VisitLogMapper;
import com.estore.service.VisitLogService;


/**
 * 
 * @ClassName: VisitLogervice 
 * @Description: TODO 用业务逻辑封装的商品操作类
 * @author: zw
 * @date: 2018年3月26日 下午1:58:39
 */

@Service("visitLogService")
public class VisitLogServiceImpl implements VisitLogService {
	
	@Autowired
	VisitLogMapper visitLogMapper ;
	
	@Autowired
	HttpServletRequest httpServletRequest ;

	@Override
	public void addVisitLog(VisitLog obj) throws Exception{
		//System.out.println(visitLogMapper+"000000"+httpServletRequest);
		
		// TODO Auto-generated method stub
		String requestURL = httpServletRequest.getRemoteAddr();
		//httpServletRequest.getRequestURI()
		String uri = httpServletRequest.getRequestURI();
		String contextPath = httpServletRequest.getContextPath();
		String path = uri.substring(contextPath.length());
		
		obj.setApplication(httpServletRequest.getContextPath());
		obj.setVisitDate(new Date());
		obj.setVisitTime(new Time(System.currentTimeMillis()));
		obj.setVisitIp(requestURL);
		obj.setVisitFor(path);
		
		visitLogMapper.insertSelective(obj);
	}

	@Override
	public List<VisitLog> getVisitLogs() {
		// TODO Auto-generated method stub
		List<VisitLog> selectByExample = visitLogMapper.selectByExample(null);
		
		return selectByExample;
	}

	@Override
	public VisitLog getVisitLog(int id) {
		// TODO Auto-generated method stub
		
		return visitLogMapper.selectByPrimaryKey(id);
	}

	@Override
	public String removeVisitLog(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateVisitLog(VisitLog obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VisitLog> getVisitLogByVisitIp(String ip) {
		// TODO Auto-generated method stub
		VisitLogExample visitLogExample = new VisitLogExample();
		visitLogExample.or().andVisitIpEqualTo(ip);
		List<VisitLog> selectByExample = visitLogMapper.selectByExample(visitLogExample);
		
		return selectByExample;
	}

	@Override
	public List<VisitLog> getVisitLogWithVisitIp() {
		// TODO Auto-generated method stub
		
		List<VisitLog> selectByExample = visitLogMapper.selectAllGorupByIp();
		
		return selectByExample;
	}
	


	
}
