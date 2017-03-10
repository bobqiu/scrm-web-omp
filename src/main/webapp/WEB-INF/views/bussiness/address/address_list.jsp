<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="utf-8">
	    <title>user</title>
<%--	    <style>
            .table th, .table td { 
				text-align: center; 
				vertical-align:middle;
			}
        </style>--%>
	</head>
	<body>
			<div class="row">
				<ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
					<li><a href="javascript: void(0)">门店基础设置</a></li>
					<li><a href="javascript: void(0)">区域管理</a></li>
				</ol>
	</div>

		<form class="form-horizontal" data-bt-form>
			<div class="row">
				<div class="col-sm-12 g-col-gap s-row">
					<div class="col-sm-1 g-label">
						<label for="provinceId" class="control-label s-control-label">省份：</label>
					</div>

					<div class="col-sm-11 g-input">
						<select id="provinceId" name="provinceId" class="form-control " onchange="selectAddress(this);">
							<c:forEach items="${provinceList}" var="pl">
								<c:if test="${provinceId ==pl.id}">
									<option value="${pl.id}" selected>${pl.name}</option>
								</c:if>
								<c:if test="${provinceId !=pl.id}">
									<option value="${pl.id}">${pl.name}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</form>

	<div class="row">
		<table  data-bt-table data-url="bussiness/address/list"
				data-pagination="true"
				data-side-pagination="server"
				data-undefined-text=""
				data-unique-id="id"
				data-toolbar=""
				data-show-refresh="true"
				data-show-columns="true"
				id="address_list_table">
			<thead>
			<tr>
				<th data-field="id">编号</th>
				<th data-field="name" data-width="10%">城市</th>
				<th data-field="areaList" data-formatter="areaListFormatter">管辖区域</th>
			</tr>
			</thead>
		</table>
	</div>
	<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
	<script type="text/javascript">
		function areaListFormatter(value, row, index) {
			var html = "";
			var len = value.length;
			$.each(value,function(i,item){
				var ss = " / ";
/*				if((i+1)%8==0){
					ss+="<br/>";
				}*/
				if(i==(len-1)){
					ss="";
				}
				html += item.name+ss;
			});
			
			return html;
		}
		
		function selectAddress(obj){
			$("#address_list_table").bootstrapTable('selectPage',1);
			$("#address_submit").click();
		}
	</script>
</body>
</html>