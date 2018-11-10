/**
 * 商品列表页面 js
 */

//项目路径
var APP_PATH;
//总记录数  
var totalRecord;
//当前页
var currentPage;


//页面加载完后执行
function startPage(){
	getData(1);
}




/*
 * 请求数据
 * 输入 页面编号
 * 输出 json数据
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
	request.open("post",APP_PATH+"/role/list",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");

	request.send("pageNum="+pageNum);

	//响应 的回调函数 
	request.onreadystatechange = function(){
		var message
		if(request.readyState == 4 && request.status == 200){
			//解析xml
			var message = request.responseText;
			var jsonMsg = JSON.parse(message);
			
			//构建页面
			updateList(jsonMsg);

		}

	}
	
}



//填充页面数据
function fullPage(jsonMsg){
	//1、解析并显示员工数据
	build_products(jsonMsg);
	//2、解析并显示分页信息 page_info
	build_page_info(jsonMsg);
	//3、解析显示分页条数据
	build_page_nav(jsonMsg);
	
}

function build_products(jsonMsg){
	$("#product_list").empty();
	
	var list = jsonMsg.extend.pageInfo.list;
	var tbody = document.getElementById("product_list");
	
	for(var i = 0; i < list.length; i++){
		
		//构建节点
		var tr = document.createElement("tr");
		
		var id = document.createElement("td");
		id.appendChild(document.createTextNode(list[i].id));
		
		var name = document.createElement("td");
		name.appendChild(document.createTextNode(list[i].name));
		
		var price = document.createElement("td");
		price.appendChild(document.createTextNode(list[i].price));
		
		var pnum = document.createElement("td");
		pnum.appendChild(document.createTextNode(list[i].pnum));
		
		var description = document.createElement("td");
		description.appendChild(document.createTextNode(list[i].description));
		
		var edit = document.createElement("td");
		var update = document.createElement("button");
		var remove = document.createElement("button");
		
//		var delBtn =  $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
//		.append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
		//设置样式
		update.className="btn btn-primary";
		remove.className="btn btn-danger";
		
		var update_span = document.createElement("span");
		var remove_span = document.createElement("span");
	
		
		
		update_span.className="glyphicon glyphicon-pencil";
		remove_span.className="glyphicon glyphicon-trash"
		
		
		update_span.appendChild(document.createTextNode(" update"));
		remove_span.appendChild(document.createTextNode(" remove"));
		
		update.appendChild(update_span);
		remove.appendChild(remove_span);
		
		
		edit.appendChild(update);
		edit.appendChild(document.createTextNode("  "));
		edit.appendChild(remove);
		
		
		
		//add节点
		tr.appendChild(id);
		tr.appendChild(name);
		tr.appendChild(price);
		tr.appendChild(pnum);
		tr.appendChild(description);
		tr.appendChild(edit);
		
		
		tbody.appendChild(tr);
		
		
		
		
	}
	
	
//	pid = id;
//	var name = document.getElementById(pid+"_name").value;
//	//是删除单个产品? 
//	if(flag){
//		//是 
//		var s = confirm(" 确认删除产品 :"+name+"--并不删除数据库 ");
//		if(!s){
//			return;	
//		}
//	}
//	

}

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

function updateList(jsonMsg){
	//数据前处理
	data = new Object();
	//抽取json中的list数据, 赋值给daomedate, demodate是构建页面的数据源
	data.roleList = jsonMsg.extend.pageInfo.list;
	
	demoData = data;
	
	//清空原有内容
	$("tbody").empty();
	//添加模板,  loadDemoData填充数据要用到模板
	document.getElementById("TableData").innerHTML="<tr class='TableDetail1 template'> <td id='td_name_${id}'>${name}</td> <td id='td_description_${id}'>${description}</td> <td> <button type='button' class='btn btn-primary btn-xs' onclick='remove(${id})'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span> 删除</button>  <button type='button' class='btn btn-primary btn-xs' onclick='update_page(${id})'><span class='glyphicon glyphicon-pencil' aria-hidden='true'></span> 修改</button></td></tr>";
	
	// 加载演示数据
	loadDemoData();
	
	//构建分页信息
	build_page_info(jsonMsg);
	build_page_nav(jsonMsg);
}

function add_page(){
	var submit = document.getElementById("r_submit");
	submit.setAttribute("onclick","add()");
	$('#myModal').modal();
}

function update_page(id){
	var submit = document.getElementById("r_submit");
	//设置提交处理函数
	submit.setAttribute("onclick","update("+id+")");
	//填充数据
	//原数据
	var name = document.getElementById("td_name_"+id).innerHTML;
	var description = document.getElementById("td_description_"+id).innerHTML;
	//填充
	document.getElementById("r_name").value = name;
	document.getElementById("r_description").value = description;
	
	$('#myModal').modal();
}

/**
 * add role
 * */
function add(){
	var name = document.getElementById("r_name").value;
	var description = document.getElementById("r_description").value;
	
	/*
	用AJAX提交给服务器  post 方式
	 */
	var request =  getXMLHttp();
	request.open("post",APP_PATH+"/role/add",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");

	request.send("name="+name+"&description="+description);

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
//				错误信息不在这里放了, 直接放在 msg 字段
//				var map = jsonMsg.extend.errorFields;
//				var errorMsg = "";
//				//alert(list.name);
//				//alert(list[0]);以map 键值对结构的集合 不能用下标遍历
//				
//				//console.log(map);
//				 //遍历map
//				for(var key in map){
//				  console.log("属性：" + key + ",值：" + map[key]);
//				  errorMsg+=map[key];
//				  errorMsg+="; ";
//				}
//				
				document.getElementById("add_msg").innerHTML="添加失败,失败原因是:"+jsonMsg.msg;
				
			}
			

		}

	}
	
}

/**
 * 更新role
 * 
 * */

function update(id){
	var name = document.getElementById("r_name").value;
	var description = document.getElementById("r_description").value;
	
	/*
	用AJAX提交给服务器  post 方式
	 */
	var request =  getXMLHttp();
	request.open("post",APP_PATH+"/role/update",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");

	request.send("id="+id+"&name="+name+"&description="+description);

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

/**
 * 初始化
 * */
function init(){
	
	
	
	 //初始化项目路径
		 
	APP_PATH = "/OA";
	 //获取填充数据
	getData();
	
//
//	/*
//	用AJAX提交给服务器  post 方式
//	 */
//	var request =  getXMLHttp();
//	request.open("post",APP_PATH+"/role/list",true);
//	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
//
//	request.send("pageNum="+1);
//
//	//响应 的回调函数 
//	request.onreadystatechange = function(){
//		var message
//		if(request.readyState == 4 && request.status == 200){
//			//解析xml
//			var message = request.responseText;
//			var jsonMsg = JSON.parse(message);
////			console.log(demoData);
////			console.log(jsonMsg);
//			data = new Object();
//			data.roleList = jsonMsg.extend.pageInfo.list;
////			console.log(data);
//			
//			demoData = data;
//			
//			
//			// 加载演示数据
//			loadDemoData();
//			
//			//构建分页信息
//			build_page_info(jsonMsg);
//			build_page_nav(jsonMsg);
//			
//
//		}
//
//	}
}

/*
 * 删除
 * */
function remove(id){
	/*
	用AJAX提交给服务器  post 方式
	 */
	var request =  getXMLHttp();
	request.open("post",APP_PATH+"/role/remove",true);
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







