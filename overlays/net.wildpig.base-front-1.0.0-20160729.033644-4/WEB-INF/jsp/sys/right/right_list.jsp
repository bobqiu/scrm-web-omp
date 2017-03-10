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
	    <title>right</title>
	</head>
	<body>
			<div class="row">
				<ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
					<li><a href="javascript: void(0)">首页</a></li>
					<li><a href="javascript: void(0)">分类信息</a></li>
				</ol>
			</div>
			<div class="row ">
		<form class="form-horizontal" data-bt-form>
			<input type="hidden" name="menuType" value="1" />
			<div class="col-sm-4">
				<!-- <div class="form-group"> -->
					<label for="menuName" class="col-sm-4 control-label">分类名称：</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="menuName" name="menuName">
					</div>
				<!-- </div> -->
			</div>
			<shiro:hasPermission name="right">
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
		<shiro:hasPermission name="right/addMenu">
		<button type="button" class="btn btn-primary btn-sm"
			data-url="right/addMenu"
			data-model="dialog"
			data-targetid="my_modal"
			data-backdrop="static"
			title="新增"><i class="glyphicon glyphicon-plus"></i>
			新增</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="right/batchDeleteMenu">
		<button type="button" class="btn btn-danger btn-sm"
			data-url="right/batchDeleteMenu"
			data-msg="确定批量删除吗？"
			data-model="ajaxToDo"
			data-targetid="my_delete_modal"
			data-batch-action="true"
			title="批量删除"><i class="glyphicon glyphicon-remove"></i>
			批量删除</button>
		</shiro:hasPermission>
	</div>
	<div class="row">
		<table  data-bt-table data-url="right/list"
				data-pagination="true"
				data-side-pagination="server"
				data-undefined-text=""
				data-toolbar="#toolbar"
				data-unique-id="menuId"
				data-show-refresh="true"
				data-show-columns="true">
			<thead>
			<tr>
				<th data-field="state" data-checkbox="true"></th>
				<th data-field="menuName">名称</th>
				<th data-field="menuUrl">路径</th>
				<th data-field="menuOrder">排序</th>
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
			<shiro:hasPermission name="right/subMenu">
			html += '<button class="btn btn-info btn-xs" data-url="right/subMenu?parentId=' + row.menuId + '" data-target="navTab" title="查看菜单"><i class="glyphicon glyphicon-zoom-in"></i> 查看菜单</button> ';
			</shiro:hasPermission>
			<shiro:hasPermission name="right/editMenu">
			html += '<button class="btn btn-warning btn-xs" data-url="right/editMenu?menuId=' + row.menuId + '" data-model="dialog" data-targetid="my_modal" data-backdrop="static" title="修改"><i class="glyphicon glyphicon-edit"></i>修改</button> ';
			</shiro:hasPermission>
			<shiro:hasPermission name="right/deleteMenu">
			html += '<button class="btn btn-danger btn-xs" data-url="right/deleteMenu?menuId=' + row.menuId + '" data-msg="将删除该分类下的所有菜单和按钮，确定删除吗？" data-model="ajaxToDo" data-targetid="my_delete_modal" title="删除"><i class="glyphicon glyphicon-remove"></i> 删除</button> ';
			</shiro:hasPermission>
			return html;
		}
	</script>
</body>
</html>