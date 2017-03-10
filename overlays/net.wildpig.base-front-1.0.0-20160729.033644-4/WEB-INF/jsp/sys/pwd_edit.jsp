<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="commonForm" class="form-horizontal" method="post" action="user/editPwd" role="form">
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">修改密码</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="form-group">
        	<input type="hidden" name="userId" value="${userId}">
            <label for="password" class="col-sm-2 control-label">原密码</label>
            <div class="col-sm-7">
                <input id="oldpassword" name="oldpassword"  type="password" maxlength="32" class="form-control required"  placeholder="请输入原密码">
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">新密码</label>
            <div class="col-sm-7">
                <input id="password" name="password"  type="password" maxlength="32" class="form-control required"  placeholder="请输入新密码">
            </div>
        </div>
        <div class="form-group">
            <label for="passwordAgain" class="col-sm-2 control-label">重复新密码</label>
            <div class="col-sm-7">
                <input id="passwordAgain"  equalTo="#password"  type="password" maxlength="32" class="form-control required"  placeholder="请再次输入新密码">
            </div>
        </div>
       
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
    <button type="submit" class="btn btn-primary">保存</button>
</div>
</form>
<script>
$(function(){
	$("#commonForm").validate();
});
</script>