<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="commonForm" class="form-horizontal" method="post" action="user/editPwd" role="form" data-ajax-after="refresh" isDialog="true">
    <div class="modal-header s-modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">修改密码</h4>
    </div>

    <div class="modal-body s-modal-body">

        <div class="row">

            <div class="s-form-group">
                <input type="hidden" name="userId" value="${userId}">
                <label for="password" class="col-sm-3 control-label s-control-label"><span style="color:red;">*</span>原密码</label>
                <input id="oldpassword" name="oldpassword"  type="password"  maxlength="12" class="form-control s-form-control isValidata"  placeholder="6-12位数字、英文及组合" isnull="请输入原密码" isEsAndNum_6_12="原密码格式错误" onblur="validateForm(1,this)" onfocus="validateForm(2,this)">
            </div>

            <hr/>

            <div class="s-form-group">
                <label for="password" class="col-sm-3 control-label s-control-label"><span style="color:red;">*</span>新密码</label>
                <input id="password" name="password"  type="password"  maxlength="12" class="form-control s-form-control isValidata"  placeholder="6-12位数字、英文及组合" isnull="请输入新密码" isEsAndNum_6_12="新密码格式错误" onblur="validateForm(1,this)" onfocus="validateForm(2,this)">
            </div>

            <hr/>

            <div class="s-form-group">
                <label for="passwordAgain" class="col-sm-3 control-label s-control-label"><span style="color:red;">*</span>重复新密码</label>
                <input id="passwordAgain"  equalID="#password" equalMsg="两次密码不一致"  type="password" maxlength="12" class="form-control s-form-control isValidata"  placeholder="6-12位数字、英文及组合" isnull="请输入新密码" isEsAndNum_6_12="新密码格式错误" onblur="validateForm(1,this)" onfocus="validateForm(2,this)">
            </div>

        </div>

    </div>

    <div class="modal-footer">
    <button type="button" title="取消" class="btn btn-default" data-dismiss="modal"><i class="glyphicon  glyphicon-remove"></i>取消</button>
    <button type="submit" title="保存" class="btn btn-primary"><i class="glyphicon  glyphicon-ok"></i>保存</button>
    </div>
</form>
<script>
$(function(){
	$("#commonForm").validate();
});
</script>