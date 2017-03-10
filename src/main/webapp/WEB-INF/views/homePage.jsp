<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<base href="<%=basePath%>">
</head>
 
<body>
	<div style="width: 100%;height: 80px;color: #333333;size: 16px;font-weight: bold;line-height: 80px;padding-left: 20px;background-color: #f8f8f8;border: 1px solid #eeeeee;">
		${loginName},欢迎您!
	</div>
</body>
</html>
