<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>product list</title>

<% pageContext.setAttribute("APP_PATH", request.getContextPath()); %>

<script type="text/javascript">
	var APP_PATH = "${APP_PATH }";
	var totalRecord,currentPage;
</script>

<script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
<link
	href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<script src="${APP_PATH }/static/js/ProductList.js"></script>


</head>
<body onload="startPage()">

<!-- Modal 模态框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">添加商品</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
        
		  <div class="form-group">
		    <label for="inputEmail3" class="col-sm-2 control-label">name</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" name="name" id="p_name"  placeholder="名称">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="inputPassword3" class="col-sm-2 control-label">price</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" name="price" id="p_price"  placeholder="单价">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="inputPassword3" class="col-sm-2 control-label">number</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" name="number" id="p_number"  placeholder="数量">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="inputPassword3" class="col-sm-2 control-label">description</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" name="description" id="p_description"  placeholder="描述">
		    </div>
		  </div>
		  
		   <div class="form-group">
		    <label for="inputPassword3" class="col-sm-2 control-label"></label>
		    <div class="col-sm-10">
		      <p id="add_msg"></p>
		    </div>
		    
		  </div>
		  <!-- 
		  <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <div class="checkbox">
		        <label>
		          <input type="checkbox"> Remember me
		        </label>
		      </div>
		    </div>
		  </div>
		   -->
		   <!--
		  <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <button type="submit" class="btn btn-default">Sign in</button>
		    </div>
		  </div>
		  -->
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" onclick="add()">保存</button>
      </div>
    </div>
  </div>
</div>


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
	  	<button type="button" class="btn btn-primary" onclick="add_page()">新增</button>
	  	<button type="button" class="btn btn-danger" onclick="remove()">删除</button>
	  </div>
	</div>
	<!-- 商品列表 -->
	<div class="row">
		<div class="col-md-12">
			<table class="table table-hover">
				<thead>
				<tr>
						<th>id&nbsp;&nbsp;&nbsp;&nbsp;</th>
						<th>名称&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
						<th>单价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
						<th>数量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
						<th>描述&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
						<th>操作</th>
				</tr>
				</thead>
				<tbody id="product_list">
				</tbody>
				
			</table>
		</div>
		<div class="row">
			<div class="col-md-4" id="page_info"></div>
			<div class="col-md-2"></div>
			<div class="col-md-6" id="page_nav_area">
					
			</div>
						
		</div>
	</div>
		
</div>

</body>
</html>

