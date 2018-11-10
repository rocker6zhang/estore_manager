package com.estore.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.estore.bean.Proposal;
import com.estore.service.ProposalService;
import com.estore.utils.JsonMsg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

//标注为控制器,  已经配制了自动扫描
@Controller
@RequestMapping("/index")
public class ProposalController {

	@Autowired
	ProposalService proposalService;


	@RequestMapping(value="/Proposal",method=RequestMethod.POST)
	@ResponseBody
	public JsonMsg addProposal(Proposal proposal, String checkCode, HttpSession httpSession) throws Exception {
		String string = (String)httpSession.getAttribute("checkCode");
		//校验验证码
		if(checkCode == null || !checkCode.equalsIgnoreCase(string)) {
			return JsonMsg.fail("验证码错误"); 
		}
		
		httpSession.removeAttribute("checkCode");
		
		proposalService.addProposal(proposal);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}


	@RequestMapping(value="/Proposal",method=RequestMethod.DELETE)
	@ResponseBody
	public JsonMsg deleteProposal(Integer id) {

		if(id == null) {
			return JsonMsg.fail("id不能为null");
		}
		proposalService.removeProposal(id);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();
	}

	@RequestMapping(value="/Proposal",method=RequestMethod.PUT)
	@ResponseBody
	public JsonMsg updateProposal(Proposal Proposal ) {


		proposalService.updateProposal(Proposal);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success();

	}

	@RequestMapping(value="/Proposal",method=RequestMethod.GET)
	@ResponseBody
	public JsonMsg getProposal(Integer  id) {


		Proposal Proposal = proposalService.getProposal(id);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pojo", Proposal);
	}

	

	@RequestMapping(value="/Proposal/list")
	@ResponseBody
	public JsonMsg getAllProposal(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize) {

		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		// PageHelper会在 影响 数据库操作, 在mybatis配制文件中配制了
		PageHelper.startPage(pageNum, pageSize);
		List<Proposal> list = proposalService.getProposals();
		// pageInfo包装查询后的结果,封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page = new PageInfo(list, pageSize);

		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pageInfo", page);
	}





}