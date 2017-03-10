<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<form class="form-horizontal" action="role/edit" method="post" id="roleEditForm" data-ajax-after="refresh" isDialog="true">
	<input type="hidden" name="roleId" value="${pd.roleId}" id="roleIdEdit"/>
    <div class="modal-header s-modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">编辑角色</h4>
    </div>

    <div class="modal-body s-modal-body">

        <div class="row">

            <div class="s-form-group">
                <label for="roleName_edit" class="col-sm-3 control-label s-control-label"><span style="color:red;">*</span>角色名称</label>
                <input id="roleName_edit" name="roleName" value="${pd.roleName}"  type="text" maxlength="8" class="form-control s-form-control isValidata" isEsAndZhAndNum_1_8="角色名称格式错误" isnull="请输入角色名" onblur="validateForm(1,this)" onfocus="validateForm(2,this)" placeholder="1-8位英文、数字、汉字及组合" autocomplete="off">
            </div>

            <hr/>

            <div class="s-form-group">
                <label for="role_edit_description" class="col-sm-3 control-label s-control-label"><span style="color:red;">*</span>角色描述</label>
                <textarea id="role_edit_description" name="description" class="form-control s-form-control isValidata" rows="5" maxlength="50" isnull="请输入角色描述" onblur="validateForm(1,this)" onfocus="validateForm(2,this)" placeholder="50字以内">${pd.description}</textarea>
            </div>

        </div>

    </div>
    <div class="modal-footer">
	    <button type="button" title="取消" class="btn btn-default" data-dismiss="modal"><i class="glyphicon  glyphicon-remove"></i>取消</button>
	    <shiro:hasPermission name="role/edit">
	    <button type="submit" title="保存" class="btn btn-primary"><i class="glyphicon  glyphicon-ok"></i>保存</button>
	    </shiro:hasPermission>
	</div>
</form>
<script type="text/javascript">
$("#roleEditForm").validate();
</script>