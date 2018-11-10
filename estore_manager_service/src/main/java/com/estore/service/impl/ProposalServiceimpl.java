package com.estore.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.estore.bean.Proposal;
import com.estore.dao.ProposalMapper;
import com.estore.service.ProposalService;


/**
 * 
 * @ClassName: Proposalervice 
 * @Description: TODO 用业务逻辑封装的商品操作类
 * @author: zw
 * @date: 2018年3月26日 下午1:58:39
 */

@Service("proposalService")
public class ProposalServiceimpl implements ProposalService {
	
	@Autowired
	private ProposalMapper ProposalMapper ;
	@Autowired
	HttpServletRequest httpServletRequest ;

	@Override
	public void addProposal(Proposal obj) {
		// TODO Auto-generated method stub
		obj.setApplication(httpServletRequest.getServletContext().getServletContextName());
		obj.setProposalPerson(obj.getProposalPerson()+new Date().toString());
		ProposalMapper.insertSelective(obj);
	}

	@Override
	public List<Proposal> getProposals() {
		// TODO Auto-generated method stub
		List<Proposal> selectByExample = ProposalMapper.selectByExample(null);
		return selectByExample;
	}

	@Override
	public Proposal getProposal(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeProposal(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateProposal(Proposal obj) {
		// TODO Auto-generated method stub
		return null;
	}
	


	
}
