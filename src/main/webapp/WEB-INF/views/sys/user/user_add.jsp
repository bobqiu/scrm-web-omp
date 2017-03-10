<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="form-horizontal" action="user/add" method="post" id="userAddForm"
	data-ajax-before="" data-ajax-after="refresh" isDialog="true">
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">新增工作人员</h4>
</div>
<div class="modal-body s-modal-body">
    <div class="row">
        <div class="s-form-group">
            <label for="loginName" class="col-sm-3 control-label s-control-label"><span style="color: red">*</span>用户名</label>
            <input id="loginName" name="loginName"  type="text" maxlength="20" class="form-control s-form-control isValidata"  
             placeholder="1-20位数字、英文及组合" autocomplete="off" isnull="请输入用户名" isEsAndNum_1_20="用户名格式错误" 
              onblur="validateForm(1,this)" onfocus="validateForm(2,this)"/>
        </div>
        <hr/>
        <div class="s-form-group">
            <label for="password" class="col-sm-3 control-label s-control-label"><span style="color: red">*</span>密码</label>
            <input id="password" name="password"  type="password"  maxlength="12" class="form-control s-form-control isValidata"
            autocomplete="off" placeholder="6-12位数字、英文及组合" isnull="请输入初始密码"  isEsAndNum_6_12="初始密码格式错误" onblur="validateForm(1,this)" onfocus="validateForm(2,this)"/>
        </div>
        <hr/>
        <div class="s-form-group">
            <label for="name" class="col-sm-3 control-label s-control-label"><span style="color: red">*</span>姓名</label>
            <input id="name" name="name"  type="text" maxlength="10" class="form-control s-form-control isValidata"  placeholder="2-10位汉字" autocomplete="off"
            isnull="请输入姓名" isZh_2_10="姓名格式错误" onblur="validateForm(1,this)" onfocus="validateForm(2,this)"/>
        </div>
        <hr/>
        <div class="s-form-group">
            <label for="phone" class="col-sm-3 control-label s-control-label"><span style="color: red">*</span>电话</label>
            <input id="phone" name="phone"  type="text" maxlength="11" class="form-control s-form-control isValidata" placeholder="11位手机号码" autocomplete="off"
             isnull="请输入手机号码" isPhone="手机号码格式错误" onblur="validateForm(1,this)" onfocus="validateForm(2,this)"/>
        </div>
        <hr/>
        <div class="s-form-group">
            <label for="roleId" class="col-sm-3 control-label s-control-label"><span style="color: red">*</span>选择角色</label>
            <select id="roleId" name="roleId" class="form-control s-form-control isValidata" isnull="请选择角色" onblur="validateForm(1,this)" onfocus="validateForm(2,this)">
                    <option value="">请选择</option>
                    <c:forEach items="${roleList}" var="role">
                        <c:if test="${role.roleId != 1}">
                            <option value="${role.roleId}">${role.roleName}</option>
                        </c:if>
                    </c:forEach>
                </select>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" title="取消" class="btn btn-default" data-dismiss="modal"><i class="glyphicon  glyphicon-remove"></i>取消</button>
    <shiro:hasPermission name="user/add">
    <button type="submit" title="保存" class="btn btn-primary"><i class="glyphicon  glyphicon-ok"></i>保存</button>
    </shiro:hasPermission>
</div>
</form>
<script type="text/javascript">
$("#userAddForm").validate();
</script>