<%--
  Created by IntelliJ IDEA.
  User: laishaoqiang
  Date: 2017/2/27
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="datetag" uri="/WEB-INF/tld/datetag.tld"%>
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
    <title>chain-list</title>
</head>

<style type="text/css">
    .czf-vip-member-data-main{ margin-top: 30px;}
	.czf-vip-member-data-main .vip-main-left{ width: 80px; float: left;}
    	.vip-main-right{width: 500px;float: left;padding-left:10px;}
	.vip-main-right p{ color: #333; font-size: 12px; margin-bottom:15px; }
	.vip-main-right p:last-of-type{ margin-bottom: 15px!important;}
</style>
     
     
<body>

	<div class="row">
	    <ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
	        <li><a href="javascript: void(0)" data-target="navTab">单店库</a></li>
	        <li><a href="<%=basePath%>org/singleOrg" data-target="navTab">单店列表</a></li>
	        <li><a href="javascript: void(0)">单店详情</a></li>
	    </ol>
	</div>
	
	 <div class="row">
		  <ul class="nav nav-pills" style="border-bottom:1px solid #ccc;">
			  <li class="active"><a href="javascript: void(0)">单店详情</a></li>
			  <li><a href="<%=basePath%>org/singleOrg/detail/list?id=${id}" data-target="navTab">工作机（${workTotalCount }）</a></li>
		 </ul>
        
     </div>
     
      <div class="czf-vip-member-data-main clearfix">
        <div class="vip-main-left">
            <c:if test="${dto.thumLogo!=null&&dto.thumLogo!=''}">
            	<img src="${dto.thumLogo}" alt="" width=70px heigth=80px;>
        	</c:if>
        	<c:if test="${dto.thumLogo==null||dto.thumLogo==''}">
            	<img src="<%=basePath %>static/images/user_142px_8px.png" alt="" width=70px heigth=80px;>
        	</c:if>
        </div>
        <div class="vip-main-right">
            <p>&nbsp;&nbsp;&nbsp;单店全称：${dto.orgFullName}</p>
            <p>&nbsp;&nbsp;&nbsp;单店简称：${dto.orgName}</p>
            <p>&nbsp;&nbsp;&nbsp;单店账号：${dto.orgAccount}</p>
            <p>营业执照号：${dto.orgDetail.businessLicense}</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地址：
            <c:if test="${dto.orgDetail.provinceName!=null}">${dto.orgDetail.provinceName}</c:if>
            <c:if test="${dto.orgDetail.cityName!=null}">${dto.orgDetail.cityName}</c:if>
            <c:if test="${dto.orgDetail.areaName!=null}">${dto.orgDetail.areaName}</c:if>
            <c:if test="${dto.orgDetail.address!=null}">${dto.orgDetail.address}</c:if></p>
            <p>&nbsp;&nbsp;&nbsp;所属行业：${dto.firstIndustryName}-${dto.secondIndustryName}</p>
            <p>&nbsp;&nbsp;&nbsp;加入时间：<datetag:date pattern="YYYY-MM-dd" value="${dto.createTime}"></datetag:date></p>
            <p>&nbsp;&nbsp;&nbsp;到期时间：<datetag:date pattern="YYYY-MM-dd" value="${dto.orgExpireTime.expireTime}"></datetag:date></p>
            <p>&nbsp;&nbsp;&nbsp;使用状态：
            	<c:choose>
            		<c:when test="${dto.orgExpireTime.expireTime<=currentTime }">
            			已到期
            		</c:when>
            		<c:when test="${dto.isForbid==2 }">
            			已禁用
            		</c:when>
            		<c:otherwise>
            			启用中
            		</c:otherwise>
            	</c:choose>
            </p>
        </div>
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
	
	 
</script>
</body>
</html>