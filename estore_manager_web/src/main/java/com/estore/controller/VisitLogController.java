package com.estore.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estore.bean.VisitLog;
import com.estore.service.VisitLogService;
import com.estore.utils.JsonMsg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

//标注为控制器,  已经配制了自动扫描
@Controller
@RequestMapping("/index")
public class VisitLogController {

	@Autowired
	VisitLogService visitLogService;


	

	@RequestMapping(value="/visitLog/list")
	@ResponseBody
	public JsonMsg getAllVisitLog(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize) {

		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		// PageHelper会在 影响 数据库操作, 在mybatis配制文件中配制了
		PageHelper.startPage(pageNum, pageSize);
		List<VisitLog> list = visitLogService.getVisitLogs();
		// pageInfo包装查询后的结果,封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page = new PageInfo(list, pageSize);

		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pageInfo", page);
	}
	
	
	
	
	@RequestMapping(value="/visitLog/getVisitLogWithIp")
	@ResponseBody
	public JsonMsg getVisitLogWithIp(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize) {
		
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		// PageHelper会在 影响 数据库操作, 在mybatis配制文件中配制了
		PageHelper.startPage(pageNum, pageSize);
		List<VisitLog> list = visitLogService.getVisitLogWithVisitIp();
		// pageInfo包装查询后的结果,封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page = new PageInfo(list, pageSize);
		
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pageInfo", page);
	}
	
	@RequestMapping(value="/visitLog/getVisitLogByIp")
	@ResponseBody
	public JsonMsg getVisitLogByIp(String ip, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "5")Integer pageSize) {
		
		if (ip == null) {
			return JsonMsg.fail("ip不能为空");
		}
		System.out.println(visitLogService.getVisitLog(588).getVisitTime()+"---------------------------");
		
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		// PageHelper会在 影响 数据库操作, 在mybatis配制文件中配制了
		PageHelper.startPage(pageNum, pageSize);
		List<VisitLog> list = visitLogService.getVisitLogByVisitIp(ip);
		// pageInfo包装查询后的结果,封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo page = new PageInfo(list, pageSize);
		
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("pageInfo", page);
	}





}