<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 设置自动跳转 -->
<meta http-equiv="Refresh" content="3;URL=${jumpURL}">
<title>${title} </title>
</head>
<body>

	<p>${message} 3秒后自动跳转,如未跳转,请点<a href="${jumpURL}">这里</a>.<p>
	
	
</body>
</html>