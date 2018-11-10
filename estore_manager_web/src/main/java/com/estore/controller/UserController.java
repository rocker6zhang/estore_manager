package com.estore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estore.bean.User;
import com.estore.service.DataService;
import com.estore.service.UserService;
import com.estore.utils.JsonMsg;

//标注为控制器,  已经配制了自动扫描
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	DataService dataService;
	


	@RequestMapping("/register")
	@ResponseBody
	public JsonMsg addUser(@Valid User u,BindingResult result ,HttpSession httpSession ,@RequestParam(value = "checkCode") String checkCode) {
		
		String string = (String)httpSession.getAttribute("checkCode");
		//校验验证码
		if(checkCode == null || !checkCode.equalsIgnoreCase(string)) {
			return JsonMsg.fail("验证码错误"); 
		}
		
		//校验完成后删除
		httpSession.removeAttribute("checkCode");
		
		if(result.hasErrors()){
			//校验失败，应该返回失败，返回校验失败的错误信息
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

		JsonMsg jsonMsg = userService.regist(u);
		
		
		//spring mvc 会自动将返回结果 json 化
		return jsonMsg;
	}

	
	
	@RequestMapping("/loginByToken")
	@ResponseBody
	public JsonMsg loginByToken(String token) throws Exception {
		boolean login = dataService.isLogin();
		if(login) {
			return JsonMsg.success();
		}
		return JsonMsg.fail();
	 
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public JsonMsg login(@Valid User u,BindingResult result,HttpSession httpSession,@RequestParam(value = "checkCode") String checkCode) {

		String string = (String)httpSession.getAttribute("checkCode");
		//校验验证码
		if(checkCode == null || !checkCode.equalsIgnoreCase(string)) {
			return JsonMsg.fail("验证码错误");
		}
		
		//校验完成后删除
		httpSession.removeAttribute("checkCode");
		
		//校验数据
		if(result.hasErrors()){
			//校验失败，应该返回失败，返回校验失败的错误信息
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

		
		
		User user = userService.loginOfSession(u,httpSession);
		
		//如果user为空表示没查到, 登录失败
		if(user == null) {
			return JsonMsg.fail("登录失败:用户名或密码错误");
		}
		//登录成功
		httpSession.setAttribute("user", user);
		
		return JsonMsg.success().addResult("user", user);
		
	}


	@RequestMapping("/logout")
	@ResponseBody
	public JsonMsg logout() {
		
		JsonMsg logout = dataService.logout();
		return logout; 
	
	
	}
	
	@RequestMapping("/removeUserInfo")
	@ResponseBody
	public JsonMsg removeUserInfo() {
		
		JsonMsg logout = dataService.removeUserInfo();
		return logout;
		
		
	}
	
	@RequestMapping("/checkUserName")
	@ResponseBody
	public JsonMsg checkUserName(@RequestParam(value = "username") String username) {

		if (userService.hasName(username)) {
			return JsonMsg.fail("用户名已存在");
		}
		return JsonMsg.success("用户名可以使用");
	}
	
	@RequestMapping("/checkUserEmail")
	@ResponseBody
	public JsonMsg checkUserEmail(@RequestParam(value = "userEmail") String userEmail) {
		
		if (userService.hasEmail(userEmail)) {
			return JsonMsg.fail("email已存在");
		}
		return JsonMsg.success("email可以使用");
	}
	
	
	
	
	@RequestMapping("/token/{token}")
	@ResponseBody
	public JsonMsg getUserByToken(@PathVariable String token, String callback) {
		
		
		return JsonMsg.fail();
	}
	
	
}
