<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<form class="form-horizontal" action="shopBasic/industry/edit" method="post" id="industryEditForm"
	data-ajax-before="" data-ajax-after="refresh" isDialog="true">
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">编辑行业</h4>
</div>
<div class="modal-body s-modal-body">
    <div class="row">
        <div class="form-group">
            <div class="s-form-group">
                <input type="hidden" name="id" value="${industryId}">
                <label for="industryName" class="col-sm-3 control-label s-control-label"><span style="color:red;">*</span>行业名称</label>
                <input id="oldIndustryName" name="oldIndustryName"  value="${industryName }" type="text" style="display:none;"/>
                <input id="industryName" name="name"  value="${industryName }" type="text" maxlength="6" autocomplete="off" class="form-control s-form-control isValidata" isEsAndZhAndNum_1_6="行业名称格式错误" placeholder="1-6位英文、数字、汉字及组合" isnull="请输入行业名称" onblur="validateForm(1,this)" onfocus="validateForm(2,this)"/>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" title="取消" class="btn btn-default" data-dismiss="modal"><i class="glyphicon  glyphicon-remove"></i>取消</button>
    <button type="submit" title="保存" class="btn btn-primary"><i class="glyphicon  glyphicon-ok"></i>保存</button>
</div>
</form>
<script type="text/javascript">
$("#industryEditForm").validate();
</script>