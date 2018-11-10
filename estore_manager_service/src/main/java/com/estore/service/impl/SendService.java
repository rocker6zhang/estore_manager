package com.estore.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.bean.Send;
import com.estore.bean.SendExample;
import com.estore.dao.SendMapper;


/**
 * 
 * @ClassName: Sendervice 
 * @Description: TODO 用业务逻辑封装的商品操作类
 * @author: zw
 * @date: 2018年3月26日 下午1:58:39
 */

@Service("sendService")
public class SendService  {
	

	//mybatis 的 对象关系映射, 操作user表
	//ssm整合后有spring 注入
	@Autowired
	private SendMapper sendMapper ;
	


	

	public SendMapper getSendMapper() {
		return sendMapper;
	}

	public void setSendMapper(SendMapper SendMapper) {
		this.sendMapper = SendMapper;
	}

	
	public String addSend(Send p) {
	
		p.setCreateDate(new Date());
		sendMapper.insertSelective(p);
		return null;
	}

	public List<Send> getSends() {
		// TODO Auto-generated method stub
		return sendMapper.selectByExample(null);
	}

	

	public List<Send>  findSend(String name) {
		// TODO Auto-generated method stub
		SendExample sendExample = new SendExample();
		sendExample.or().andTargetLike("%"+name+"%");
		
		return sendMapper.selectByExample(sendExample);
	}
	public Send getSend(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String removeSend(Integer id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateSend(Send p) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Send> getSendByKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
