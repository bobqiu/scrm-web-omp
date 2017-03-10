<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form class="form-horizontal" action="shopBasic/industry/add" method="post" id="industryAddForm"
	data-ajax-before="" data-ajax-after="refresh" isDialog="true">
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">新增行业</h4>
</div>
<div class="modal-body s-modal-body">
    <div class="row">
        <div class="s-form-group">
            <label for="level" class="col-sm-3 control-label s-control-label"><span style="color:red;">*</span>行业等级</label>
            <select name="level" id="level" class="form-control s-form-control isValidata" onchange="switchLevel(this.value)"  isnull="请选择行业等级" onblur="validateForm(1,this)" onfocus="validateForm(2,this)">
                <option value="">请选择</option>
                <option value="1">一级行业</option>
                <option value="2">二级行业</option>
            </select>
        </div>

        <hr/>

        <div class="s-form-group">
            <label for="parentId" class="col-sm-3 control-label s-control-label"><span style="color:red;">*</span>上级行业</label>
            <select name="parentId" id="parentId" class="form-control s-form-control isValidata"  isnull="请选择上级行业"  onblur="validateForm(1,this)" onfocus="validateForm(2,this)">
                <option value="">请选择</option>
                <c:forEach items="${industrys.industrys}" var="indu" varStatus="st">
                    <option value="${indu.id}">${indu.name}</option>
                </c:forEach>
            </select>
        </div>

        <hr/>

        <div class="s-form-group">
            <label for="industryName" class="col-sm-3 control-label s-control-label"><span style="color:red;">*</span>行业名称</label>
            <input id="industryName" name="name" type="text" maxlength="6" autocomplete="off" class="form-control s-form-control isValidata" isEsAndZhAndNum_1_6="行业名称格式错误" placeholder="1-6位英文、数字、汉字及组合" isnull="请输入行业名称" onblur="validateForm(1,this)" onfocus="validateForm(2,this)"/>
        </div>

    </div>
</div>
<div class="modal-footer">
    <button type="button" title="取消" class="btn btn-default" data-dismiss="modal"><i class="glyphicon  glyphicon-remove"></i>取消</button>
    <button type="submit" title="保存" class="btn btn-primary"><i class="glyphicon  glyphicon-ok"></i>保存</button>
</div>
</form>
<script type="text/javascript">
$("#industryAddForm").validate();
function switchLevel(val){
	if(val==1) {
        $("#parentId").val("");
        $("#parentId").attr("disabled","disabled");
		$("#parentId").next().remove();
	}else{
		$("#parentId").removeAttr("disabled");
	}
}

</script>