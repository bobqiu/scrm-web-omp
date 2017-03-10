<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%> 
<form class="form-horizontal" action="right/addBtn" method="post" id="buttonAddForm"
	data-ajax-after="refresh">
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">添加按钮</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="form-group">
        	<input type="hidden" name="menuId" value="${menuId}" />
            <label for="buttonName" class="col-sm-2 control-label">名称</label>
            <div class="col-sm-7">
                <input id="buttonName" name="buttonName"  type="text" maxlength="32" minlength="2" class="form-control required"  placeholder="请输入名称">
            </div>
        </div>
        <div class="form-group">
            <label for="buttonUrl" class="col-sm-2 control-label">路径</label>
            <div class="col-sm-7">
                <input id="buttonUrl" name="buttonUrl"  type="text" maxlength="32" class="form-control required"  placeholder="请输入路径">
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
    <shiro:hasPermission name="right/addbtn">
    <button type="submit" class="btn btn-primary">保存</button>
    </shiro:hasPermission>
</div>
</form>
<script type="text/javascript">
$("#buttonAddForm").validate();
</script>