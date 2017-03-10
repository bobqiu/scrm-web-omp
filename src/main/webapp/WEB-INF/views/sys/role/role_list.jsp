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
		<style type="text/css">
			.fixed-table-container table tr td:nth-child(3){
				width: 614px;
			}
			.fixed-table-container table tr td:nth-child(3)>a{
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
				width: 614px;
				display: block;
			}
		</style>
	</head>

	<body>
	<div class="row">
				<ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
					<li><a href="javascript: void(0)">系统管理</a></li>
					<li><a href="javascript: void(0)">角色管理</a></li>
				</ol>
			</div>

		<form class="form-horizontal" data-bt-form>
			<div class="row">
				<div class="col-sm-12 g-col-gap s-row">
					<div class="col-sm-1 g-label">
						<label for="roleName" class="control-label s-control-label">角色名称：</label>
					</div>

					<div class="col-sm-10 g-input">
						<input type="text" class="form-control" id="roleName" name="roleName" placeholder="请输入角色名称" autocomplete="off">
					</div>

					<shiro:hasPermission name="role">
						<div class="col-sm-1 g-button">
							<button type="submit" title="查询" class="btn btn-primary"><i class="glyphicon  glyphicon-search"></i> 查询</button>
						</div>
					</shiro:hasPermission>
				</div>
			</div>
		</form>

	<div id="toolbar">
		<shiro:hasPermission name="role/add">
			<button type="button" class="btn btn-primary"
					data-url="role/add"
					data-model="dialog"
					data-targetid="my_modal"
					data-backdrop="static"
					title="新增"><i class="glyphicon glyphicon-plus"></i>
				 新增</button>
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
				data-show-columns="true"
				id="role_list_table">
			<thead>
			<tr>
				<th data-field="update_time">更新时间</th>
				<th data-field="roleName">角色名称</th>
				<th data-field="description" data-width="25%">角色描述</th>
				<th data-field="nums" data-formatter="numsFormatter">当前人数</th>
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
			html += '<button class="btn btn-primary btn-xs" data-url="role/edit?roleId=' + row.roleId + '" data-model="dialog" data-targetid="my_modal" data-backdrop="static" title="编辑"><i class="glyphicon glyphicon-pencil"></i></button> ';
			</shiro:hasPermission>
			<shiro:hasPermission name="role/editRight">
			html += '<button class="btn btn-warning btn-xs" data-url="role/editRight?roleId=' + row.roleId + '" data-model="dialog" data-targetid="my_modal" data-backdrop="static" title="权限"><i class="glyphicon glyphicon-flag"></i></button> ';
			</shiro:hasPermission>
			<shiro:hasPermission name="role/delete">
			html += '<button class="btn btn-danger btn-xs" data-url="role/delete?roleId=' + row.roleId + '" data-msg="确定删除吗？" data-model="ajaxToDo" data-targetid="my_delete_modal" title="删除"><i class="glyphicon glyphicon-trash"></i></button> ';
			</shiro:hasPermission>
			return html;
		}
		
		function numsFormatter(value, row, index) {
			var html = "";
			if(row.nums ==0) return row.nums;
			html += '<a href="user?roleId='+row.roleId+'" data-target="navTab">'+ row.nums +'</a>';
			return html;
		}
		function tip(){
			dialogMng.msg("no_use_per", "没有工作人员管理权限");
		}
		
		function descFormatter(value, row, index) {
			var html = "";
			if(value.length>20){
				html += value.substring(0,20)+"...";
			}else{
				html += value;
			}
			return '<a title="'+value+'" style="cursor:pointer;color:#524d4d;">'+html+'</a> ';
		}
		
		$(function(){
			$($("#role_list_table thead tr th").get(0)).find(".th-inner").attr("style","width:160px;");
			$($("#role_list_table thead tr th").get(4)).find(".th-inner").attr("style","width:180px;");
	    });
	</script>
</body>
</html>