/**
 * 商品列表页面 js
 */

//项目路径
var APP_PATH;
//总记录数  
var totalRecord;
//当前页
var currentPage;

//add path
var add_path;

//update path
var update_path;

//remove path
var remove_path;

//get one path
var get_path;

//请求列表数据的path
var list_path;


/**
 * 发送异步的ajax post请求
 * @param path  请求的路径,相对于APP_PATH
 * @param obj	响应成功后的回调函数,传入响应内容作为参数
 * @param map	请求参数
 * @returns
 */
function sendRequest(path,map,obj){

	/*
	用AJAX提交给服务器  post 方式
	 */
	var request =  getXMLHttp();
	request.open("post",APP_PATH+path,true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");

	//构建请求参数
	if(map == "" || map == undefined || map == null){
		//无参
		request.send();
	}else{
		var str = "";
		map.forEach(function (value, key, map) {
			var v = "&"+key+"="+value;
			
			str+=v;
		});
			
			
		
		//str 前面多一个 & 可以提交
		request.send(str);
	}

	//响应 的回调函数 
	request.onreadystatechange = function(){
		if(request.readyState == 4 && request.status == 200){
			//解析xml
			var message = request.responseText;
			var jsonMsg = JSON.parse(message);
			
			//构建页面
			obj(jsonMsg);
		}

	}
	
}


/*
 * 请求数据
 * 请求地址 : path
 * 输入 页面编号, 作为path的参数  
 * 输出 json数据 回调方式
 * */
function getData(pageNum){
	
	if(pageNum == "" || pageNum == undefined || pageNum == null){
		//默认值
		pageNum = 1;
	}
	
	

	/*
	用AJAX提交给服务器  post 方式
	 */
	var request =  getXMLHttp();
	request.open("post",APP_PATH+path,true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");

	request.send("pageNum="+pageNum);

	//响应 的回调函数 
	request.onreadystatechange = function(){
		if(request.readyState == 4 && request.status == 200){
			//解析xml
			var message = request.responseText;
			var jsonMsg = JSON.parse(message);
			
			//构建页面
			updateList(jsonMsg);

		}

	}
	
}

//
function build_page_info(jsonMsg){
	
	var pageInfo = jsonMsg.extend.pageInfo;
	var div = document.getElementById("page_info");
	var str = "当前"+pageInfo.pageNum+"页,总"+
					pageInfo.pages+"页,总"+
					pageInfo.total+"条记录";
		
	div.innerHTML=str;
	
	
	//总记录数  
	totalRecord = jsonMsg.extend.pageInfo.total;
	//当前页
	currentPage = jsonMsg.extend.pageInfo.pageNum;
	
}


//
function build_page_nav(result){
	
	$("#page_info_area").empty();
	$("#page_info_area").append("当前"+result.extend.pageInfo.pageNum+"页,总"+
			result.extend.pageInfo.pages+"页,总"+
			result.extend.pageInfo.total+"条记录");
	totalRecord = result.extend.pageInfo.total;
	currentPage = result.extend.pageInfo.pageNum;
	}
	//解析显示分页条，点击分页要能去下一页....
	function build_page_nav(result){
	//page_nav_area
	$("#page_nav_area").empty();
	var ul = $("<ul></ul>").addClass("pagination");
	
	//构建元素
	var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
	var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
	if(result.extend.pageInfo.hasPreviousPage == false){
		firstPageLi.addClass("disabled");
		prePageLi.addClass("disabled");
	}else{
		//为元素添加点击翻页的事件
		firstPageLi.click(function(){
			to_page(1);
		});
		prePageLi.click(function(){
			to_page(result.extend.pageInfo.pageNum -1);
		});
	}
	
	
	
	var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
	var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
	if(result.extend.pageInfo.hasNextPage == false){
		nextPageLi.addClass("disabled");
		lastPageLi.addClass("disabled");
	}else{
		nextPageLi.click(function(){
			to_page(result.extend.pageInfo.pageNum +1);
		});
		lastPageLi.click(function(){
			to_page(result.extend.pageInfo.pages);
		});
	}
	
	
	
	//添加首页和前一页 的提示
	ul.append(firstPageLi).append(prePageLi);
	//1,2，3遍历给ul中添加页码提示
	$.each(result.extend.pageInfo.navigatepageNums,function(index,item){
		
		var numLi = $("<li></li>").append($("<a></a>").append(item));
		if(result.extend.pageInfo.pageNum == item){
			numLi.addClass("active");
		}
		numLi.click(function(){
			to_page(item);
		});
		ul.append(numLi);
	});
	//添加下一页和末页 的提示
	ul.append(nextPageLi).append(lastPageLi);
	
	//把ul加入到nav
	var navEle = $("<nav></nav>").append(ul);
	navEle.appendTo("#page_nav_area");
}

/*
 * 跳转页面
 * */
function to_page(pn){

	getData(pn);
}

//填充用户列表
function updateList(jsonMsg){
	//数据前处理
	data = new Object();
	//抽取json中的list数据, 赋值给daomedate, demodate是构建页面的数据源
	data.userList = jsonMsg.extend.pageInfo.list;
	
	demoData = data;
	
	//清空原有内容
	$("tbody").empty();
	//添加模板,  loadDemoData填充数据要用到模板
	document.getElementById("TableData").innerHTML="<tr class='TableDetail1 template'> " +
	"<td id='td_name_${id}'>${name}</td>" +
	"<td id='td_department_${id}'>${department}</td>" +
	"<td><button type='button' class='btn btn-primary btn-xs' onclick='remove(${id})'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span> 删除</button>" +
	"	 <button type='button' class='btn btn-primary btn-xs' onclick='update_page(${id})'><span class='glyphicon glyphicon-pencil' aria-hidden='true'></span> 修改</button> " +
	"	 <button type='button' class='btn btn-primary btn-xs' onclick='add_role_page(${id})'><span class='glyphicon glyphicon-plus' aria-hidden='true'></span> 设置角色</button>" +
	"	 <button type='button' class='btn btn-primary btn-xs' onclick='add_privilege_page(${id})'><span class='glyphicon glyphicon-pencil' aria-hidden='true'></span> 设置权限</button>" +
	"</td> </tr>";

	// 加载演示数据
	loadDemoData();
	
	//构建分页信息
	build_page_info(jsonMsg);
	build_page_nav(jsonMsg);
}



//唤醒添加用户权限模态框
function add_privilege_page(id){
	var m = new Map();
		sendRequest("/privilege/list",  null, function(msg){
			
		});
	//请求用户权限列表
	
	//填充到模态框
	
	//显示模态框
	
	
//	var submit = document.getElementById("r_submit");
//	submit.setAttribute("onclick","add()");
//	$('#myModal').modal();
	
}

//唤醒添加用户角色模态框
function add_role_page(id){
	$("#role_checkbox").empty();
	//请求职位数据
	sendRequest("/role/totalList",  null, function(msg){
		 //<input type="checkbox">Remember me &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		$("#role_checkboc").empty();
		$("#role_msg").empty();
		var lab = document.getElementById("role_checkbox");
		var roleList = msg.extend.list;
		for(var i = 0; i < roleList.length; i++){
			var node = document.createElement("input");
			node.setAttribute("name",roleList[i].name);
			node.setAttribute("type","checkbox");
			node.setAttribute("id","role_checkbox_"+roleList[i].id);
			
			
			lab.appendChild(node);
			lab.appendChild(document.createTextNode(roleList[i].name+"\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0"));
			//每3列换一行
			if((i+1) % 3 == 0){
				lab.appendChild(document.createElement("br"));
			}
			
		}
		
		//填充用户已有role
		var user = new Map();
		user.set("id",id);
		sendRequest("/user/roles",  user, function(msg){
			//jaing
			var roleList = msg.extend.list;
			//将用户已有的role checkbox 置为选中状态,
			for(var i = 0; i < roleList.length; i++){
				var checkbox = document.getElementById("role_checkbox_"+roleList[i].id);
				checkbox.checked = true;
			}
		});

	});
	
	//
	
	var submit = document.getElementById("r_submit");
	//role 操作都在一个方法里,
	submit.setAttribute("onclick","update_role("+id+")");
	
	
	//填充数据
	//原数据
	var name = document.getElementById("td_name_"+id).innerHTML;
	var description = document.getElementById("td_description_"+id).innerHTML;
	var department = document.getElementById("td_department_"+id).innerHTML;
	//填充
	document.getElementById("u_role_name").value = name;
	document.getElementById("u_role_description").value = description;
	document.getElementById("u_role_department").value = department;
	
	$('#role_Modal').modal();
}

//update role fro user
function update_role(uid){
	
//	var arr = getSelectAll(document.getElementById("role_checkbox"));
//	var arr2 = [];
//	for(var i = 0; i < arr.length; i++){
//		var id = arr[i].id
//		arr2[i] = id.substring(id.lastIndexOf("_")+1,id.length);
//	}
////	console.log(arr);
////	console.log(arr2);
////	var ids = JSON.stringify(arr2);
////	console.log(ids);
//	
//	var role = new Map();
//	
//	role.set("id",uid);
//	
//	for(var i = 0; i < arr2.length; i++){
//		
//		role.set("ids",parseInt(arr2[i]));
//	}
	
	var arr = getSelectAll(document.getElementById("role_checkbox"));
	var arr2 = [];
	for(var i = 0; i < arr.length; i++){
		var id = arr[i].id
		arr2[i] = id.substring(id.lastIndexOf("_")+1,id.length);
	}
//	console.log(arr);
//	console.log(arr2);
	var ids = JSON.stringify(arr2);
//	console.log(ids);
	
	var role = new Map();
	
	role.set("id",uid);
	role.set("ids",ids);
	
	
	
	sendRequest("/user/updateRole", role, function(msg){
		//..role_msg
		if(msg.code == 100){
			document.getElementById("role_msg").innerHTML="更新成功";
		}else{
			document.getElementById("role_msg").innerHTML="更新失败,失败原因是:"+msg.msg;
			
		}
	});
}

/**
 * 遍历element 下的所以checkbox , 返回其中被选中的的元素的id
 * @param element checkbox 的父元素
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


//
function add_page(){
	$("#role_msg").empty();
	var submit = document.getElementById("u_submit");
	submit.setAttribute("onclick","add()");
	$('#myModal').modal();
}

//
function update_page(id){
	$("#role_msg").empty();
	var submit = document.getElementById("u_submit");
	//设置提交处理函数
	submit.setAttribute("onclick","update("+id+")");
	//填充数据
	//原数据
	var name = document.getElementById("td_name_"+id).innerHTML;
	var description = document.getElementById("td_description_"+id).innerHTML;
	var loginName = document.getElementById("td_loginName_"+id).innerHTML;
	var department = document.getElementById("td_department_"+id).innerHTML;
	//填充
	document.getElementById("u_name").value = name;
	document.getElementById("u_description").value = description;
	document.getElementById("u_loginName").value = loginName;
	document.getElementById("u_department").value = department;
	
	$('#myModal').modal();
}

/**
 * add 
 * */
function add(){

	var name = document.getElementById("u_name").value;
	var description = document.getElementById("u_description").value;
	var loginName = document.getElementById("u_loginName").value;
	var password = document.getElementById("u_password").value;
	var department = document.getElementById("u_department").value;
	
	/*
	用AJAX提交给服务器  post 方式
	 */
	var request =  getXMLHttp();
	request.open("post",APP_PATH+"/user/add",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");

	request.send("name="+name+"&description="+description+"&password="+password+"&loginName="+loginName+"&department="+department);

	//响应 的回调函数 
	request.onreadystatechange = function(){
		var message
		if(request.readyState == 4 && request.status == 200){
			
			var message = request.responseText;
			var jsonMsg = JSON.parse(message);
			if(jsonMsg.code == 100){
				document.getElementById("add_msg").innerHTML="添加成功";
				//更新list, 跳转到最后一页
				getData(totalRecord);
			}else{
//				
				document.getElementById("add_msg").innerHTML="添加失败,失败原因是:"+jsonMsg.msg;
				
			}
			

		}

	}
	
}

/**
 * 更新
 * 
 * */

function update(id){

	var name = document.getElementById("u_name").value;
	var description = document.getElementById("u_description").value;
	var loginName = document.getElementById("u_loginName").value;
	var password = document.getElementById("u_password").value;
	var department = document.getElementById("u_department").value;
	
	/*
	用AJAX提交给服务器  post 方式
	 */
	var request =  getXMLHttp();
	request.open("post",APP_PATH+"/user/update",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");

	request.send("id="+id+"&name="+name+"&description="+description+"&password="+password+"&loginName="+loginName+"&department="+department);

	//响应 的回调函数 
	request.onreadystatechange = function(){
		var message
		if(request.readyState == 4 && request.status == 200){
			
			var message = request.responseText;
			var jsonMsg = JSON.parse(message);
			if(jsonMsg.code == 100){
				document.getElementById("add_msg").innerHTML="修改成功";
				//更新list
				getData(currentPage);
			}else{
				document.getElementById("add_msg").innerHTML="修改失败,失败原因是:"+jsonMsg.msg;
				
			}
			

		}

	}
}


/*
 * 删除
 * */
function remove(id){
	
	//确认眼神
	var s = confirm(" 确认删除员工 :"+document.getElementById("td_name_"+id).innerHTML+"吗?");
	if(!s){
		return;	
	}
	
	
	/*
	用AJAX提交给服务器  post 方式
	 */
	var request =  getXMLHttp();
	request.open("post",APP_PATH+"/user/remove",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");

	request.send("id="+id);

	//响应 的回调函数 
	request.onreadystatechange = function(){
		var message
		if(request.readyState == 4 && request.status == 200){
			
			var message = request.responseText;
			var jsonMsg = JSON.parse(message);
			if(jsonMsg.code == 100){
				alert("删除成功");
				getData(currentPage);
			}else{
				alert("删除失败,失败原因是:"+jsonMsg.msg);
				
			}
			

		}

	}
}



/**
 * 初始化
 * */
function init(){
	
	
	
	 //初始化项目路径
		 
	APP_PATH = "/OA";
	path = "/user/list";
	 //获取填充数据
	getData();
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







