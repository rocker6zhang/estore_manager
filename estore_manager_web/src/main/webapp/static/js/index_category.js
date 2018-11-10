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
 * 初始化
 * */
function init(){
	
	
	

	 //初始化项目参数
	set_pram();
	
	 //获取填充数据
	getData();

}




/*
 * 请求数据
 * 请求地址 : path
 * 输入 页面编号, 作为path的参数  
 * 输出 json数据 回调方式
 * */
function getData(){

	/*
	用AJAX提交给服务器  post 方式
	 */
	var request =  getXMLHttp();
	request.open("post",APP_PATH+"/index/category/all",true);
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");

	request.send();

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

//
function updateList(jsonMsg){
	//数据前处理
	data = new Object();
	//抽取json中的list数据, 赋值给daomedate, demodate是构建页面的数据源
	data.categoryList = jsonMsg.extend.list;
	
	demoData = data;
	
	//清空原有内容
	$("tbody").empty();
	//添加模板,  loadDemoData填充数据要用到模板
	document.getElementById("TableData").innerHTML="<tr class='template' >" +
			" <td id='td_id_${id}'>${id}</td>" +
			" <td id='td_name_${id}'>${name}</td>" +
			" <td id='td_parentId_${id}'>${parentId}</td> " +
			" <td id='td_description_${id}'>${description}</td>" +
			" <td><button type='button' class='btn btn-primary btn-xs' onclick='remove(${id})'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span> 删除</button> <button type='button' class='btn btn-primary btn-xs' onclick='update_page(${id})'><span class='glyphicon glyphicon-pencil' aria-hidden='true'></span> 修改</button></td></tr>";
	// 加载演示数据
	loadDemoData();
	
	//构建分页信息, 没有分页了
	
//	build_page_info(jsonMsg);
//	build_page_nav(jsonMsg);
}

//
function add_page(){
	var submit = document.getElementById("r_submit");
	submit.setAttribute("onclick","add()");
	$('#myModal').modal();
}

//
function update_page(id){
	var submit = document.getElementById("r_submit");
	//设置提交处理函数
	submit.setAttribute("onclick","update("+id+")");
	//填充数据
	//原数据
	var name = document.getElementById("td_name_"+id).innerHTML;
	var description = document.getElementById("td_description_"+id).innerHTML;
	var parentId = document.getElementById("td_parentId_"+id).innerHTML;
	//填充
	document.getElementById("m_name").value = name;
	document.getElementById("m_description").value = description;
	document.getElementById("m_id").value = id;
	
	setSelect("m_parentId",parentId);
	
	$('#myModal').modal();
}

/**
 * add 
 * */
function add(){
	
	//回调函数
	var fun = function(result){
			if(result.code == 100){
				document.getElementById("add_msg").innerHTML="添加成功";
				//更新list, 跳转到最后一页
				getData(totalRecord);
			}else{
				document.getElementById("add_msg").innerHTML="添加失败,失败原因是:"+result.msg;
		}
	}
	
	
	
	$.ajax({
	     //几个参数需要注意一下
	         type: "POST",//方法类型
	         dataType: "json",//预期服务器返回的数据类型
	         url: APP_PATH+"/index/category" ,//url
	         data: $('#save_from').serialize(),
	         success: fun,
	         error : function() {
	        	 console.log("add request error!!!");
	         }
	     });
	
}

/**
 * 更新
 * 
 * */

function update(id){
	
	
	//回调函数
	var fun = function(result){
			if(result.code == 100){
				document.getElementById("add_msg").innerHTML="update成功";
				//更新list, 跳转到最后一页
				getData(totalRecord);
			}else{
				document.getElementById("add_msg").innerHTML="update失败,失败原因是:"+result.msg;
		}
	}
	
	
	
	$.ajax({
	     //几个参数需要注意一下
	         type: "PUT",//方法类型
	         dataType: "json",//预期服务器返回的数据类型
	         url: APP_PATH+"/index/category" ,//url
	         data: $('#save_from').serialize(),
	         success: fun,
	         error : function() {
	        	 console.log("add request error!!!");
	         }
	     });
	
}

/*
 * 删除
 * */
function remove(id){
	
	//确认眼神
	var s = confirm(" 确认删除分类 :"+document.getElementById("td_name_"+id).innerHTML+"吗?");
	if(!s){
		return;	
	}
	
	//回调函数
	var fun = function(result){
		if(result.code == 100){
			alert("删除成功");
			getData(currentPage);
		}else{
			alert("删除失败,失败原因是:"+result.msg);
			
		}
	}
	
	
	
	$.ajax({
	     //几个参数需要注意一下
	         type: "POST",//方法类型
	         dataType: "json",//预期服务器返回的数据类型
	         url: APP_PATH+"/index/category" ,//url
	         data: "id="+id+"&_method=DELETE",
	         success: fun,
	         error : function() {
	        	 console.log("delete request error!!!");
	         }
	     });
	
	

	
}












