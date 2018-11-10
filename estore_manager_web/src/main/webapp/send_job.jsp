<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://47.104.191.132:8089/js/common_utils.js"></script>

<% pageContext.setAttribute("APP_PATH", request.getContextPath()); %>

<script type="text/javascript">
	var APP_PATH = "/manager";
	var flag = 0;
</script>

 <script type="text/javascript">
 

 /**
  * 发送异步的ajax post请求
  * @param path  请求的路径,相对于APP_PATH
  * @param obj	响应成功后的回调函数,传入响应内容作为参数
  * @param map	请求参数
  * @returns
  */
 function save(form_id,path){

 	/*
 	用AJAX提交给服务器  post 方式
 	 */
 	var request =  getXMLHttp();
 	request.open("post",APP_PATH+path,true);
 	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
 	request.send($('#'+form_id).serialize());

 	//响应 的回调函数 
 	request.onreadystatechange = function(){
 		if(request.readyState == 4 && request.status == 200){
 			//解析xml
 			var message = request.responseText;
 			var jsonMsg = JSON.parse(message);
 			
 			//构建页面
 			//obj(jsonMsg);
 			console.log(jsonMsg.msg);
 			flag = (flag+1)%2;
 			if(flag == 0){
 				
 			document.getElementById("save_msg").innerHTML="<b style='color:red'>"+jsonMsg.msg+"</b>";
 			}else{
 				
 			document.getElementById("save_msg").innerHTML=jsonMsg.msg;
 			}
 		}

 	}
 	
 }
 function search(form_id){

	 	/*
	 	用AJAX提交给服务器  post 方式
	 	 */
	 	var request =  getXMLHttp();
	 	request.open("post",APP_PATH+"/search_job",true);
	 	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	 	request.send($('#'+form_id).serialize());

	 	//响应 的回调函数 
	 	request.onreadystatechange = function(){
	 		if(request.readyState == 4 && request.status == 200){
	 			//解析xml
	 			var message = request.responseText;
	 			var jsonMsg = JSON.parse(message);
	 			
	 			//obj(jsonMsg);
	 			console.log(jsonMsg.msg);
	 			var msg = "nothing!!!";
	 			var list = jsonMsg.extend.list;
	 			
	 			if(jsonMsg.code == 100 && list != null && list.length != 0){
	 				msg="";
	 				for(var i = 0; i < list.length; i++){
	 					
	 				msg += "  target=";
	 				msg += list[i].target;
	 				msg += "  create_date=";
	 				msg += Format(new Date(list[i].createDate),"yyyy-MM-dd HH:mm");
	 				msg += list[i].description;
	 				
	 				msg+="</br>";
	 				}
	 				
	 			}
	 			document.getElementById("search_msg").innerHTML=msg;
	 		}

	 	}
	 	
	 }

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

 function login() {
     $.ajax({
     //几个参数需要注意一下
         type: "POST",//方法类型
         dataType: "json",//预期服务器返回的数据类型
         url: "/users/login" ,//url
         data: $('#form1').serialize(),
         success: function (result) {
             console.log(result);//打印服务端返回的数据(调试用)
             if (result.resultCode == 200) {
                 alert("SUCCESS");
             }
             ;
         },
         error : function() {
             alert("异常！");
         }
     });
 }
       
    </script>



<link href="http://47.104.191.132:8089/css/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="http://47.104.191.132:8089/js/jquery-1.12.4.min.js"></script>

<script type="text/javascript" src="http://47.104.191.132:8089/css/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>


</head>
<body>


	<div class="container">
	
	<!-- 标题 -->
	<div class="row">
		<div class="col-md-3"></div>
	  <div class="col-md-6"><h1>标题</h1></div>
	  <div class="col-md-3"></div>
	</div>
	<!-- 一行-->
	<div class="row">
	  <div class="col-md-12"><br/></div>
	</div>
	<!-- 一行-->
	<div class="row">
		<div class="col-md-3"></div>
		 <div class="col-md-6">
			<form id="search_from" class="form-inline">
			  <div class="form-group">
			    <label for="exampleInputName2"> </label>
			    <input type="text" class="form-control" id="exampleInputName2" name="name" placeholder="公司关键字(排除'有限公司')">
			  </div>
			  
			  <button type="button" class="btn btn-default" onclick="search('search_from')">search</button>
			  
			  <div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label"></label>
				    <div class="col-sm-10">
				      <p id="search_msg"></p>
				    </div>
				    
				</div>
			</form>
		  
		 </div>
	  <div class="col-md-3"></div>
	
	</div>
	
	<!-- 一行-->
	<div class="row">
	  <div class="col-md-12"><br/></div>
	</div>
	
	<!-- 一行-->
	<div class="row">
	  <div class="col-md-12">
		<form id="save_job" class="form-horizontal">
		  <div class="form-group">
		    <label for="inputEmail3" class="col-sm-2 control-label">公司</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="" name="target" placeholder="name">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="inputPassword3" class="col-sm-2 control-label">职位</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="" name="role" placeholder="role">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="inputPassword3" class="col-sm-2 control-label">date</label>
		    <div class="col-sm-10">
		      <input type="date" class="form-control" id="" name="send_Date" placeholder="date">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="inputPassword3" class="col-sm-2 control-label">备注</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="" name="description" placeholder="description">
		    </div>
		  </div>
		  
		  
		  <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <button type="button" class="btn btn-primary"  onclick="save('save_job','/save_job')">提交</button>
		    </div>
		  </div>
		  
		    <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label"></label>
			    <div class="col-sm-10">
			      <p id="save_msg"></p>
			    </div>
			    
			  </div>
		  
		</form>
	
		</div>
	</div>
	</div>

</body>
</html>