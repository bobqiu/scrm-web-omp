<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
					<li><a href="right" data-target="navTab">分类信息</a></li>
					<li><a href="right/subMenu?parentId=${parentId}" data-target="navTab">菜单信息</a></li>
					<li><a href="javascript: void(0)">添加</a></li>
				</ol>
			</div>
<form class="form-horizontal" action="right/addMenu" method="post" id="menuAddForm" target="my_modal"
	data-ajax-after="right/subMenu?parentId=${parentId}">
	<div class="modal-body">
    <div class="row">
        <div class="form-group">
        	<input type="hidden" name="parentId" value="${parentId}" />
        	<input type="hidden" name="menuType" value="${menuType}" />
            <label for="menuName" class="col-sm-2 control-label">名称</label>
            <div class="col-sm-7">
                <input id="menuName" name="menuName"  type="text" maxlength="32" minlength="2" class="form-control required"  placeholder="请输入名称">
            </div>
        </div>
        <div class="form-group">
            <label for="menuUrl" class="col-sm-2 control-label">路径</label>
            <div class="col-sm-7">
            	<c:choose><c:when test="${menuType == 1}">
                <input id="menuUrl" name="menuUrl" type="text" maxlength="32" class="form-control" value="#" readonly="readonly">
            	</c:when><c:otherwise>
            	<input id="menuUrl" name="menuUrl" type="text" maxlength="32" class="form-control required"  placeholder="请输入路径">
            	</c:otherwise></c:choose>
            </div>
        </div>
        <div class="form-group">
            <label for="menuOrder" class="col-sm-2 control-label">排序</label>
            <div class="col-sm-7">
                <input id="menuOrder" name="menuOrder"  type="text" maxlength="32" class="form-control required digits"  placeholder="请输入数值">
            </div>
        </div>
         <%--<div class="form-group">
			<label for="removable" class="col-sm-2 control-label">可删除</label>
			<div class="col-sm-7">
				<select class="form-control" id="removable" name="removable">
					<option value="1">是</option>
					<option value="0">否</option>
				</select>
			</div>
		</div>--%>
        <div class="form-group">
            <label for="description" class="col-sm-2 control-label">描述</label>
            <div class="col-sm-7">
                <textarea id="description" name="description" class="form-control" rows="3" maxlength="200"></textarea>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
    <shiro:hasPermission name="right/addMenu">
    <button type="submit" class="btn btn-primary">保存</button>
    </shiro:hasPermission>
</div>
</form>
<script type="text/javascript">
$("#menuAddForm").validate();
</script>
</body>
</html>