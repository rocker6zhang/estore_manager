package com.estore.service;

import java.util.List;


import com.estore.bean.Proposal;


public interface ProposalService {

	
	void addProposal(Proposal obj);

	List<Proposal> getProposals();

	Proposal getProposal(int id);

	String removeProposal(Integer id);

	String updateProposal(Proposal obj);

}