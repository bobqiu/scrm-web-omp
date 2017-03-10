<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<form class="form-horizontal" action="user/edit" method="post" id="userEditForm"
	data-ajax-after="refresh">
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">修改用户</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="form-group">
	    	<input type="hidden" name="userId" value="${pd.userId}"/>
            <label for="loginName" class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-7">
                <input id="loginName" value="${pd.loginName}" type="text" maxlength="32" class="form-control" readonly="readonly">
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">密码</label>
            <div class="col-sm-7">
                <input id="password" name="password" type="password" maxlength="32" minlength="2" class="form-control"  placeholder="请输入密码">
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">姓名</label>
            <div class="col-sm-7">
                <input id="name" name="name" value="${pd.name}"  type="text" maxlength="32" class="form-control required"  placeholder="请输入姓名">
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">邮箱</label>
            <div class="col-sm-7">
                <input id="email" name="email" value="${pd.email}"  type="text" maxlength="32" class="form-control required email"  placeholder="请输入邮箱">
            </div>
        </div>
        <div class="form-group">
            <label for="phone" class="col-sm-2 control-label">电话</label>
            <div class="col-sm-7">
                <input id="phone" name="phone" value="${pd.phone}"  type="text" maxlength="32" class="form-control required"  placeholder="请输入电话">
            </div>
        </div>
        <div class="form-group">
            <label for="description" class="col-sm-2 control-label">描述</label>
            <div class="col-sm-7">
                <textarea id="description" name="description" class="form-control" rows="3" maxlength="200">${pd.description}</textarea>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
    <shiro:hasPermission name="user/edit">
    <button type="submit" class="btn btn-primary">保存</button>
    </shiro:hasPermission>
</div>
</form>
<script type="text/javascript">
$("#userEditForm").validate();
</script>