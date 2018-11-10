package com.estore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estore.bean.Send;
import com.estore.service.impl.SendService;
import com.estore.utils.JsonMsg;

//标注为控制器,  已经配制了自动扫描
@Controller
public class JobController {

	@Autowired
	SendService sendService;


	@RequestMapping("/save_job")
	@ResponseBody
	public JsonMsg save_job(@Valid Send send, BindingResult result) {
		
//		System.out.println(send);

		if(result.hasErrors()){
			//校验失败，应该返回失败，的错误信息
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> errors = result.getFieldErrors();
			String msg = "";
			for (FieldError fieldError : errors) {
//				System.out.println("错误的字段名："+fieldError.getField());
//				System.out.println("错误信息："+fieldError.getDefaultMessage());
				//map.put(fieldError.getField(), fieldError.getDefaultMessage());
				msg+=fieldError.getDefaultMessage();
				msg+=";";
			}
			
			
			return JsonMsg.fail(msg);
		}
		
		
		//去除前后空格
		send.setTarget(send.getTarget().trim());

		List<Send> list = sendService.findSend(send.getTarget());
		
		//productService.addProduct(p);
		sendService.addSend(send);

		if (list == null) {
			
			return JsonMsg.success();
		}
		String string = "";
		if(list.size() >= 1 ) {
			 string = list.get(0).getCreateDate().toString();
		}
		return JsonMsg.success(" 处理成功 repeat 上次投递时间:"+string);
	}
	
	@RequestMapping("/search_job")
	@ResponseBody
	public JsonMsg search_job(@Valid String name) {

		if(name == null || "".equals(name)) {
			return JsonMsg.fail();
		}
		List<Send> list = sendService.findSend(name.trim());
//		System.out.println(list);
		//spring mvc 会自动将返回结果 json 化
		return JsonMsg.success().addResult("list",list);
	}


}
