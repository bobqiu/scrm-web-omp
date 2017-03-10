<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="utf-8">
	    <title>user</title>
	</head>
	<body>
			<div class="row">
				<ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
					<li><a href="javascript: void(0)">首页</a></li>
					<li><a href="javascript: void(0)">用户管理</a></li>
				</ol>
	</div>
	<div class="row">
		<form class="form-horizontal" data-bt-form>
			<div class="col-sm-4">
				<!-- <div class="form-group"> -->
					<label for="loginName" class="col-sm-4 control-label">用户名：</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="loginName" name="loginName">
					</div>
				<!-- </div> -->
			</div>
			<shiro:hasPermission name="user">
			<div class="col-sm-4">
				<!-- <div class="form-group"> -->
					<input type="submit" class="btn btn-primary"
						value="查询" />
				<!-- </div> -->
			</div>
			</shiro:hasPermission>
		</form>
	</div>
	<div id="toolbar">
		<shiro:hasPermission name="user/add">
		<button type="button" class="btn btn-primary btn-sm"
			data-url="user/add"
			data-model="dialog"
			data-targetid="my_modal"
			data-backdrop="static"
			title="新增"><i class="glyphicon glyphicon-plus"></i>
			新增</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="user/batchDelete">
		<button type="button" class="btn btn-danger btn-sm"
			data-url="user/batchDelete"
			data-msg="确定批量删除吗？"
			data-model="ajaxToDo"
			data-targetid="my_delete_modal"
			data-batch-action="true"
			title="批量删除"><i class="glyphicon glyphicon-remove"></i>
			批量删除</button>
		</shiro:hasPermission>
	</div>
	<div class="row">
		<table  data-bt-table data-url="user/list"
				data-pagination="true"
				data-side-pagination="server"
				data-undefined-text=""
				data-toolbar="#toolbar"
				data-unique-id="userId"
				data-show-refresh="true"
				data-show-columns="true">
			<thead>
			<tr>
				<th data-field="state" data-checkbox="true"></th>
				<th data-field="loginName">用户名</th>
				<th data-field="lastLogin">上次登录时间</th>
				<th data-field="email">邮箱</th>
				<th data-field="phone">电话</th>
				<th data-field="description">描述</th>
				<th data-field="action" data-formatter="actionFormatter">操作</th>
			</tr>
			</thead>
		</table>
	</div>
	<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
	<script type="text/javascript">
		function actionFormatter(value, row, index) {
			var html = "";
			<shiro:hasPermission name="user/edit">
			html += '<button class="btn btn-warning btn-xs" data-url="user/edit?userId=' + row.userId + '" data-model="dialog" data-targetid="my_modal" data-backdrop="static" title="修改"><i class="glyphicon glyphicon-edit"></i> 修改</button> ';
			</shiro:hasPermission>
			if (row.userId == 1) return html;
			<shiro:hasPermission name="user/delete">
			html += '<button class="btn btn-danger btn-xs" data-url="user/delete?userId=' + row.userId + '" data-msg="确定删除吗？" data-model="ajaxToDo" data-targetid="my_delete_modal" title="删除"><i class="glyphicon glyphicon-remove"></i> 删除</button> ';
			</shiro:hasPermission>
			<shiro:hasPermission name="user/editRole">
			html += '<button class="btn btn-warning btn-xs" data-url="user/editRole?userId=' + row.userId + '" data-model="dialog" data-targetid="my_modal" data-backdrop="static" title="授权"><i class="glyphicon glyphicon-wrench"></i> 授权</button> ';
			</shiro:hasPermission>
			return html;
		}
	</script>
</body>
</html>