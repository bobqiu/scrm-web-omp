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
	<div class="dialog">
		<div style="width: 100%;height: 80px;color: #333333;size: 16px;font-weight: bold;text-align: center;line-height: 80px;border: 1px solid #eeeeee;">
			出错了，您没有权限访问此功能！
		</div>
	</div>
</body>
</html>
