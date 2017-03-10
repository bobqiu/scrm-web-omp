<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="utf-8">
	    <title>role</title>
	</head>
	<body>
			<div class="row">
				<ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
					<li><a href="javascript: void(0)">首页</a></li>
					<li><a href="javascript: void(0)">角色管理</a></li>
				</ol>
			</div>
	<div class="row">
		<form class="form-horizontal" data-bt-form>
			<div class="col-sm-4">
				<!-- <div class="form-group"> -->
					<label for="roleName" class="col-sm-4 control-label">角色名称：</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="roleName" name="roleName">
					</div>
				<!-- </div> -->
			</div>
			<shiro:hasPermission name="role">
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
		<shiro:hasPermission name="role/add">
		<button type="button" class="btn btn-primary btn-sm"
			data-url="role/add"
			data-model="dialog"
			data-targetid="my_modal"
			data-backdrop="static"
			title="添加"><i class="glyphicon glyphicon-plus"></i>
			添加</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="role/batchDelete">
		<button type="button" class="btn btn-danger btn-sm"
			data-url="role/batchDelete"
			data-msg="确定批量删除吗？"
			data-model="ajaxToDo"
			data-targetid="my_delete_modal"
			data-batch-action="true"
			title="批量删除"><i class="glyphicon glyphicon-remove"></i>
			批量删除</button>
		</shiro:hasPermission>
	</div>
	<div class="row">
		<table  data-bt-table data-url="role/list"
				data-pagination="true"
				data-side-pagination="server"
				data-undefined-text=""
				data-toolbar="#toolbar"
				data-unique-id="roleId"
				data-show-refresh="true"
				data-show-columns="true">
			<thead>
			<tr>
				<th data-field="state" data-checkbox="true"></th>
				<th data-field="roleName">角色名称</th>
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
			if (row.roleId == 1) return html;
			<shiro:hasPermission name="role/edit">
			html += '<button class="btn btn-warning btn-xs" data-url="role/edit?roleId=' + row.roleId + '" data-model="dialog" data-targetid="my_modal" data-backdrop="static" title="修改"><i class="glyphicon glyphicon-edit"></i> 修改</button> ';
			</shiro:hasPermission>
			<shiro:hasPermission name="role/delete">
			html += '<button class="btn btn-danger btn-xs" data-url="role/delete?roleId=' + row.roleId + '" data-msg="确定删除吗？" data-model="ajaxToDo" data-targetid="my_delete_modal" title="删除"><i class="glyphicon glyphicon-remove"></i> 删除</button> ';
			</shiro:hasPermission>
			<shiro:hasPermission name="role/editRight">
			html += '<button class="btn btn-warning btn-xs" data-url="role/editRight?roleId=' + row.roleId + '" data-model="dialog" data-targetid="my_modal" data-backdrop="static" title="编辑权限"><i class="glyphicon glyphicon-wrench"></i> 编辑权限</button> ';
			</shiro:hasPermission>
			return html;
		}
	</script>
</body>
</html>