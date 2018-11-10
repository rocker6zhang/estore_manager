<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>visit log list</title>

<% pageContext.setAttribute("APP_PATH", request.getContextPath()); %>




<link href="http://47.104.191.132:8089/css/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="http://47.104.191.132:8089/js/jquery-1.12.4.min.js"></script>
<script src="http://47.104.191.132:8089/css/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://47.104.191.132:8089/js/common_utils.js"></script>
<script type="text/javascript" src="http://47.104.191.132:8089/store_manager/js/store_manager_common.js"></script>
<!-- <script type="text/javascript" src="http://47.104.191.132:8089/store_manager/js/showFileList.js"></script>
 -->
</head>
<body >



<div class="container">
<h3><a href="showFileList?index=1">E:\logs\</a></h3>
<h3><a href="showFileList?index=2">store log path :/home/wwwroot/ftptest/staticServer/log/</a></h3>
<h3><a href="showFileList?index=3">tomcat2 log :/usr/local/tomcat8_2/logs</a></h3>
<h3><a href="showFileList?index=4">tomcat1 log : /usr/local/tomcat8/logs</a></h3>

		
</div>

</body>
</html>

