<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">
<title>运营后台</title>
<link rel="SHORTCUT ICON" href="<%=basePath %>static/images/favicon.png">
<link type="text/css" rel="stylesheet" href="<%=basePath %>static/plugins/bootstrap/css/bootstrap.min.css">
<!-- <link type="text/css" rel="stylesheet" href="<%=basePath %>static/plugins/dataTables/dataTables.bootstrap.css"> -->
<link type="text/css" rel="stylesheet" href="<%=basePath %>static/plugins/ztree/css/zTreeStyle.css">
<link type="text/css" rel="stylesheet" href="<%=basePath %>static/plugins/bootstrap-table/bootstrap-table.min.css">
<link type="text/css" rel="stylesheet" href="<%=basePath %>static/css/common.css">
<link type="text/css" rel="stylesheet" href="<%=basePath %>static/css/header.css">
<link type="text/css" rel="stylesheet" href="<%=basePath %>static/css/admin.css">

<script type="text/javascript" src="<%=basePath %>static/js/wildpig/message.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.form.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/bootstrap/js/bootstrap.js"></script>

<script type="text/javascript" src="<%=basePath %>static/plugins/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<!-- 时间控件 -->
<link type="text/css" rel="stylesheet" href="<%=basePath %>static/plugins/datetimePicker/css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="<%=basePath %>static/plugins/datetimePicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/datetimePicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
	var basePath = '<%=basePath %>';
    var g_menuList = ${menuLists};
</script>
<script type="text/javascript" src="<%=basePath %>static/js/common.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/header.js"></script>

</head>
<body id="main_body" class="s-modal-open">
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container-fluid s-container-fluid" id="main_info" style="margin-top: 10px;">
		<div class="row s-row" style="overflow:hidden;min-height:800px;">
			<div class="col-lg-2 col-md-2 col-sm-3 adminbar s-content-left" id="mainTwoMenu" style="height: 100%;overflow: auto;position: fixed;top: 70px;left: 6px;">
				<jsp:include page="menu_side.jsp"></jsp:include>
			</div>
			<div class="s-left-order"></div>
			<div class="col-lg-10 col-md-10 col-sm-9 s-content-right" style="padding-left:0px;" id="navTab">
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="<%=basePath %>static/js/hw-form-validate.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/hw-validate.js"></script>

<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-validate.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-dialog.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-common.js"></script>
<script type="text/javascript">
	var serverURL = '${basePath}';
	$(document).ready(function() {
		$(window).resize(function() {
			var windowHeight = document.documentElement.clientHeight;
			var navTopHeight = ($("#nav_top")).height();
			var navBottomHeight = ($("#nav_bottom")).height();
			var mainHeight = windowHeight - navTopHeight - navBottomHeight;
			$("#main_info").height(mainHeight - 10);
		}).resize();
	});
	$(function(){
		//防止页面后退
		history.pushState(null, null, document.URL);
		window.addEventListener('popstate', function () {
		history.pushState(null, null, document.URL);
		});
	})
</script>
</html>