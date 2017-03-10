<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<form class="form-horizontal" action="role/add" method="post" id="roleAddForm" data-ajax-after="refresh" isDialog="true">
    <div class="modal-header s-modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">新增角色</h4>
    </div>

    <div class="modal-body s-modal-body">

        <div class="row">

            <div class="s-form-group">
                <label for="roleName_add" class="col-sm-3 control-label s-control-label"><span style="color:red;">*</span>角色名称</label>
                <input id="roleName_add" name="roleName"  type="text"  maxlength="8" class="form-control s-form-control isValidata" isEsAndZhAndNum_1_8="角色名称格式错误" isnull="请输入角色名" onblur="validateForm(1,this)" onfocus="validateForm(2,this)" placeholder="1-8位英文、数字、汉字及组合" autocomplete="off">
            </div>

            <hr/>

            <div class="s-form-group">
                <label for="role_add_description" class="col-sm-3 control-label s-control-label"><span style="color:red;">*</span>角色描述</label>
                <textarea id="role_add_description" name="description" class="form-control s-form-control isValidata" rows="5" maxlength="50" isnull="请输入角色描述" onblur="validateForm(1,this)" onfocus="validateForm(2,this)" placeholder="50字以内"></textarea>
            </div>
        </div>

    </div>
    <div class="modal-footer">
		<button type="button" title="取消" class="btn btn-default" data-dismiss="modal"><i class="glyphicon  glyphicon-remove"></i>取消</button>
	    <shiro:hasPermission name="role/add">
		<button type="submit" title="保存" class="btn btn-primary"><i class="glyphicon  glyphicon-ok"></i>保存</button>
	    </shiro:hasPermission>
	</div>
</form>
<script type="text/javascript">
	$("#roleAddForm").validate();
</script>