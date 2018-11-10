<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>product list</title>

<% pageContext.setAttribute("APP_PATH", request.getContextPath()); %>
<script type="text/javascript"
	src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
<link
	href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">


<!-- 标题 -->
	<div class="row">
	  <div class="col-md-12"><h1>商品列表</h1></div>
	</div>
	<!-- 操作按钮 -->
	<div class="row">
	  <div class="col-md-3"></div>
	  <div class="col-md-3"></div>
	  <div class="col-md-3"></div>
	  <div class="col-md-3">
	  	<button type="button" class="btn btn-primary">新增</button>
	  	<button type="button" class="btn btn-danger">删除</button>
	  </div>
	</div>
	<!-- 商品列表 -->
	<div class="row">
		<div class="col-md-12">
			<table class="table table-hover">
				<thead>
				<tr>
						<td>id</td>
						<td>名称</td>
						<td>单价</td>
						<td>数量</td>
						<td>描述</td>
					</tr>
				</thead>
				<tbody>
				<tr>
						<td>id</td>
						<td>名称</td>
						<td>单价</td>
						<td>数量</td>
						<td>描述</td>
					</tr>
				</tbody>
				
			</table>
		</div>
		<div class="row">
			<div class="col-md-4">第 页,共 页,共 条</div>
			<div class="col-md-2"></div>
			<div class="col-md-6">
					<nav aria-label="Page navigation">
					  <ul class="pagination">
					    <li><a href="#">首页</a></li>

					    <li>
					      <a href="#" aria-label="Previous">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
					    </li>
					    <li><a href="#">1</a></li>
					    <li><a href="#">2</a></li>
					    <li><a href="#">3</a></li>
					    <li><a href="#">4</a></li>
					    <li><a href="#">5</a></li>
					    <li>
					      <a href="#" aria-label="Next">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
					    </li>
					    <li><a href="#">末页</a></li>
					  </ul>
					</nav>
				</div>
						
			</div>
		</div>
		
</div>

</body>
</html>

