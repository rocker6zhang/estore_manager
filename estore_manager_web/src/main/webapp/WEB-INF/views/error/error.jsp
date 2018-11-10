<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出错了 -estore</title>
</head>
<body>
<span class="txt">糟了...系统出错了...</span>
			<ul class="m">
				<li class="fore1">您可以：稍后再试或联系客服。 </li>
				<li class="fore2">返回<a href="${home}" target="_blank">首页</a></li>
				<li class="fore3">错误代码</li>
 				<li class="fore4">${code}</li>
				<li class="fore3">错误消息</li>
 				<li class="fore4">${message}</li>
			</ul>
</body>
</html>