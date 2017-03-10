<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<form class="form-horizontal" action="role/add" method="post" id="roleAddForm"
	data-ajax-after="refresh">
	<div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	    <h4 class="modal-title">添加角色</h4>
	</div>
	<div class="modal-body">
	    <div class="row">
	        <div class="form-group">
	            <label for="roleName" class="col-sm-3 control-label">角色名称</label>
	            <div class="col-sm-8">
	                <input id="roleName" name="roleName"  type="text" maxlength="32" minlength="2" class="form-control required"  placeholder="请输入角色名称">
	            </div>
	        </div>

			<div class="form-group">
	            <label for="description" class="col-sm-3 control-label">描述</label>
	            <div class="col-sm-8">
	                <textarea id="description" name="description" class="form-control" rows="3" maxlength="200"></textarea>
	            </div>
	        </div>
	    </div>
	</div>
	<div class="modal-footer">
	    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	    <shiro:hasPermission name="role/add">
	    <button type="submit" class="btn btn-primary">保存</button>
	    </shiro:hasPermission>
	</div>
</form>
<script type="text/javascript">
$("#roleAddForm").validate();
</script>