<%--
  Created by IntelliJ IDEA.
  User: laishaoqiang
  Date: 2017/2/27
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>branch-list</title>
</head>
<body>

	<div class="row">
	    <ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
	        <li><a href="javascript: void(0)" data-target="navTab">连锁机构</a></li>
	        <li><a href="javascript: void(0)">连锁分店列表</a></li>
	    </ol>
	</div>
	
    <form class="form-horizontal" data-bt-form>
            <div class="row">
                <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label for="accountOrName" class="control-label s-control-label">分店：</label>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select class="form-control" name="accountOrName" id="accountOrName">
                            <option value="0">账号</option>
                            <option value="1">分店名称</option>
                        </select>
                    </div>
                     <div class="col-sm-10 g-input">
                          <input id="key" name="key"  type="text" class="form-control" placeholder="请输入......" autocomplete="off"/>
                     </div>
                </div>
                
                <div class="col-sm-3 g-col-gap s-row">
                     <div class="col-sm-1 g-label">
		                <label for="topOrgName" class="control-label s-control-label">所属机构：</label>
		            </div>
		            <div class="col-sm-10 g-input">
		                <input id="topOrgName" name="topOrgName"  type="text" class="form-control" placeholder="请输入机构简称" autocomplete="off"/>
		            </div>
                </div>
                
                <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label for="startTimeStr" class="control-label s-control-label">加入时间：</label>
                    </div>
                    <div class="col-sm-4 g-input-time">
                        <div class="input-group date form_datetime">
                            <input type="text" readonly class="form-control" placeholder="请选择" id="startTimeStr" name="startTimeStr">
                            <span class="input-group-addon">
                                      <span class="glyphicon glyphicon-th"></span>
                        </span>
                        </div>
                    </div>
                    <div class="col-sm-1 g-label-time">
                        <label for="endTimeStr" class="control-label s-control-label">至</label>
                    </div>

                    <div class="col-sm-4 g-input-time">
                        <div class="input-group date form_datetime">
                            <input type="text" readonly class="form-control" placeholder="请选择" id="endTimeStr" name="endTimeStr">
                            <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-th"></span>
                                   </span>
                        </div>
                    </div>
                </div>
                
                <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label for="firstIndustryId" class="control-label s-control-label">请选择行业：</label>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select class="form-control" name="firstIndustryId" id="firstIndustryId" onchange="changeSecondIndustry(this);">
                            <option value="">请选择一级行业</option>
                            <c:forEach items="${industrys.industrys}" var="indu" varStatus="st">
                                <option value="${indu.id}">${indu.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select class="form-control" name="secondIndustryId" id="secondIndustryId">
                            <option value="">请选择二级行业</option>
                        </select>
                    </div>
                </div>
                
                <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label for="provinceId" class="control-label s-control-label">地区:</label>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select id="provinceId" name="provinceId" class="form-control " onchange="changeProvince(this);">
							<option value="">请选择省份</option>
                            <c:forEach items="${provinceList}" var="pr" varStatus="st">
                                <option value="${pr.id}">${pr.name}</option>
                            </c:forEach>
						</select>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select id="cityId" name="cityId" class="form-control " onchange="changeCity(this);">
							<option value="">请选择城市</option>
						</select>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select id="areaId" name="areaId" class="form-control ">
							<option value="">请选择地区</option>
						</select>
                    </div>
                    
                     <div class="col-sm-1 g-button">
		                <button type="submit" title="查询" class="btn btn-primary"><i class="glyphicon  glyphicon-search"></i> 查询</button>
		            </div>
                </div>
            </div>
        </form>

<div class="row">
    <table  data-bt-table data-url="org/branchOrg/list"
            data-pagination="true"
            data-side-pagination="server"
            data-undefined-text=""
            data-show-refresh="true"
            data-show-columns="true"
            data-toolbar=""
            data-unique-id="id">
        <thead>
        <tr>
            <th data-field="createTimeStr">加入时间</th>
            <th data-field="orgAccount"  data-formatter="actionFormatter">分店账号</th>
            <th data-field="orgName">分店名称</th>
            <th data-field="topOrgName">所属机构</th>
            <th data-field="industryStr">所属行业</th>
            <th data-field="staffCount">员工数</th>
        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
<script type="text/javascript">
	$(".form_datetime").datetimepicker({
	    language: 'zh-CN',
	    format: 'yyyy-mm-dd',
	    minView:2,
	    autoclose: true,
	    todayBtn: true,
	    clearBtn:true
	 });
	
	 function actionFormatter(value, row, index) {
		 var orgAccount = row.orgAccount;
		 if (orgAccount == null) {
			 orgAccount = '';
         } 
		 
         var html = "";
         //html += '<a href="org/branchOrgDetailPage?id=' + row.id + '">';
         html += '<a style="text-decoration: underline!important;" href="org/branchOrg/branchOrgDetailPage?id=' + row.id + '"   data-target="navTab" data-targetid="my_modal" data-backdrop="static" title="'+ orgAccount + '">' + orgAccount + '</a>';
         //html += orgAccount;
         html += '</a>';
         
         return html;
     }
	 
    
    //改变省份
	function changeProvince(obj) {
		var provinceId = $(obj).val();
		
		$("#cityId").html("<option value=''>请选择城市</option>");
		$("#areaId").html("<option value=''>请选择地区</option>");
		
        $.ajax(
        {
            type: "post",
            url: "<%=basePath %>bussiness/address/getCitys",
            data: { "provinceId": provinceId},
            success: function (msg) {
                for (var i = 0; i < msg.citys.length; i++) {
                    $("#cityId").append("<option value=" + msg.citys[i].id + ">" + msg.citys[i].name + "</option>");
                } 
            }
        })
    };
    
    //改变城市
    function changeCity(obj) {
    	var cityId = $(obj).val();
    	
    	$("#areaId").html("<option value=''>请选择地区</option>");
    	
        $.ajax(
        {
            type: "post",
            url: "<%=basePath %>bussiness/address/getAreas",
            data: { "cityId": cityId},
            success: function (msg) {
                for (var i = 0; i < msg.areas.length; i++) {
                    $("#areaId").append("<option value=" + msg.areas[i].id + ">" + msg.areas[i].name + "</option>");
                } 
                
            }
        })
    };
    
    //改变二级行业
	function changeSecondIndustry(obj) {
		var firstIndustryId = $(obj).val();
		
		$("#secondIndustryId").html("<option value=''>请选择二级行业</option>");
		
        $.ajax(
        {
            type: "post",
            url: "<%=basePath %>shopBasic/industry/getIndustryByParentId",
            data: { "parentId": firstIndustryId},
            success: function (msg) {
                for (var i = 0; i < msg.industryList.length; i++) {
                    $("#secondIndustryId").append("<option value=" + msg.industryList[i].id + ">" + msg.industryList[i].name + "</option>");
                } 
            }
        })
    };
</script>
</body>
</html>