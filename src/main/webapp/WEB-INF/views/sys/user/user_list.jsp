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
					<li><a href="javascript: void(0)">系统管理</a></li>
					<li><a href="javascript: void(0)">工作人员管理</a></li>
				</ol>
	</div>

		<form class="form-horizontal" data-bt-form>
			<div class="row">
				<div class="col-sm-6 g-col-gap s-row">
					<div class="col-sm-1 g-label">
						<label for="name" class="control-label s-control-label">姓名：</label>
					</div>

					<div class="col-sm-11 g-input">
						<input type="text" class="form-control" placeholder="请输入姓名" id="name" name="name" autocomplete="off">
					</div>
				</div>
				<div class="col-sm-6 g-col-gap s-row">
					<div class="col-sm-1 g-label">
						<label for="roleId" class="control-label s-control-label">角色名称：</label>
					</div>
					<div class="col-sm-10 g-input">
						<select id="roleId" name="roleId" class="form-control ">
							<option value="">全部</option>
							<c:forEach items="${roleList}" var="role">
								<c:if test="${roleId ==role.roleId}">
									<option value="${role.roleId}" selected>${role.roleName}</option>
								</c:if>
								<c:if test="${roleId !=role.roleId}">
									<option value="${role.roleId}">${role.roleName}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>

					<shiro:hasPermission name="user">
						<div class="col-sm-1 g-button">
							<button type="submit" title="查询" class="btn btn-primary"><i class="glyphicon  glyphicon-search"></i> 查询</button>
						</div>
					</shiro:hasPermission>
				</div>
			</div>
		</form>

	<div id="toolbar">
		<shiro:hasPermission name="user/add">
		<button type="button" class="btn btn-primary"
			data-url="user/add"
			data-model="dialog"
			data-targetid="my_modal"
			data-backdrop="static"
			title="新增"><i class="glyphicon glyphicon-plus"></i>
			 新增</button>
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
				<th data-field="state" data-sequencenum="true">序号</th>
				<th data-field="loginName">用户名</th>
				<th data-field="name">姓名</th>
				<th data-field="phone" data-formatter="phoneFormatter">联系电话</th>
				<th data-field="roleName">角色名称</th>
				<th data-field="lastLogin">上次登录时间</th>
				<th data-field="action" data-formatter="actionFormatter">操作</th>
			</tr>
			</thead>
		</table>
	</div>
	<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
	<script type="text/javascript">
		function phoneFormatter(value, row, index) {
			if(!value){
				return "--";
			}
			else{
				return value;
			}
		}
		function actionFormatter(value, row, index) {
			var html = "";
			if (${userId} != 1 && row.userId == 1) return html;
			<shiro:hasPermission name="user/edit">
			html += '<button class="btn btn-primary btn-xs" data-url="user/edit?userId=' + row.userId + '" data-model="dialog" data-targetid="my_modal" data-backdrop="static" title="修改"><i class="glyphicon glyphicon-pencil"></i></button> ';
			</shiro:hasPermission>
			if (row.userId == 1 || row.userId == ${userId}) return html;
			<shiro:hasPermission name="user/delete">
				html += '<button class="btn btn-danger btn-xs" data-url="user/delete?userId=' + row.userId + '" data-msg="确定删除吗？" data-model="ajaxToDo" data-targetid="my_delete_modal" title="删除"><i class="glyphicon glyphicon-trash"></i></button> ';
			</shiro:hasPermission>
			return html;
		}
	</script>
</body>
</html>