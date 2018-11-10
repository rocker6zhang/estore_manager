/**
 * 可复用的业务逻辑方法和工具方法
 */


/**
 * 发送异步的ajax post请求
 * @param path  请求的路径,相对于APP_PATH
 * @param obj	响应成功后的回调函数,传入响应内容作为参数
 * @param map	请求参数 map对象
 * @returns
 */
function sendRequest(path,map,obj){

	/*
	用AJAX提交给服务器  post 方式
	 */
	var request =  getXMLHttp();
	request.open("post",path,true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");

	//构建请求参数
	if(map == "" || map == undefined || map == null){
		//无参
		request.send();
	}else{
		var str = "";
		for(var key in map){
			v = "&"+key+"="+map[key];
			
			str+=v;
			
		}
		//str 前面多一个 & 可以提交
		request.send(str);
	}

	//响应 的回调函数 
	request.onreadystatechange = function(){
		if(request.readyState == 4 && request.status == 200){
			//解析xml
			var message = request.responseText;
			var jsonMsg = JSON.parse(message);
			
			//回调函数
			obj(jsonMsg);
		}

	}
	
}

/**
 * 参考用
 * @returns
 */
function ss(){
	//alert($('#login_form').serialize());
	 $.ajax({
	     //几个参数需要注意一下
	         type: "POST",//方法类型
	         dataType: "json",//预期服务器返回的数据类型
	         url: APP_PATH+"/user/login" ,//url
	         data: $('#login_form').serialize(),
	         success: function (result) {
	             console.log(result);//打印服务端返回的数据(调试用)
	             document.getElementById("login_msg").innerHTML="<b style='color:red'>"+result.msg+"</b>";
	         },
	         error : function() {
	         }
	     });
}


/**
 * 发送异步的ajax post请求
 * @param path  请求的路径,相对于APP_PATH
 * @param obj	响应成功后的回调函数,传入响应内容作为参数
 * @param parameter	请求参数格式为"key=value&kay=value"
 * @returns
 */
function myRequest(path,parameter,obj){

	/*
	用AJAX提交给服务器  post 方式
	 */
	var request =  getXMLHttp();
	request.open("post",path,true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");

	
	request.send(parameter);

	//响应 的回调函数 
	request.onreadystatechange = function(){
		if(request.readyState == 4 && request.status == 200){
			//解析xml
			var message = request.responseText;
			var jsonMsg = JSON.parse(message);
			
			//回调函数
			obj(jsonMsg);
		}

	}
	
}

/**
 * 设置指定value 的option 为选中状态
 * @param opts_id select 标签的 id
 * @param value 要选中的option 的 value
 * @returns
 */
function setSelect(opts_id,value){
	var opts = document.getElementById(opts_id);
	 for(var i = 0; i < opts.options.length; i++){
        if(value == opts.options[i].value){
           opts.options[i].selected = 'selected';
           break;
          }
       }
}



/**
 * 遍历element 下的所有的 input, 返回其中被选中的的元素节点
 * @param element  input的父元素
 * @returns
 */
function getSelectAll(E){
	var inputs = E.getElementsByTagName("input");
	var arr = [];
	for(var i = 0; i < inputs.length; i++){
		if(inputs[i].checked == true){
			arr[arr.length] =  inputs[i];
		}
	}
	
	return arr;

}

/**
 * 
 * @returns XMLHTTPRequest 对象
 */

function getXMLHttp(){
	var xmlhttp;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}else{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}

	return xmlhttp;
}

/**
 * 判断设备是手机还是pc
 * @returns boolean 是pc:true; 不是pc:flase
 */
function IsPC() {
	   var userAgentInfo = navigator.userAgent;
	   var Agents = ["Android", "iPhone",
	      "SymbianOS", "Windows Phone",
	      "iPad", "iPod"];
	   var flag = true;
	   for (var v = 0; v < Agents.length; v++) {
	      if (userAgentInfo.indexOf(Agents[v]) > 0) {
	         flag = false;
	         break;
	      }
	   }
	   return flag;
	}
















