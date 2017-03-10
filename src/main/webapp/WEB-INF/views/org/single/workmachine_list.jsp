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
<%@ taglib prefix="datetag" uri="/WEB-INF/tld/datetag.tld"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>single-workmachine-list</title>
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
	        <li><a href="javascript: void(0)">工作机</a></li>
	    </ol>
	</div>
	
	 <div class="row">
		  <ul class="nav nav-pills" style="border-bottom:1px solid #ccc;">
			  <li><a href="<%=basePath%>org/singleOrg/detail?id=${id}" data-target="navTab">单店详情</a></li>
			  <li class="active"><a href="javascript: void(0)">工作机（${workTotalCount }）</a></li>
		 </ul>
        
     </div>
     
<div class="row">
    <table  data-bt-table data-url="org/singleOrg/detail/form?id=${id}"
            data-pagination="true"
            data-side-pagination="server"
            data-undefined-text=""
            data-show-refresh="true"
            data-show-columns="true"
            data-toolbar=""
            data-unique-id="id">
        <thead>
        <tr>
            <th data-field="createTime" data-formatter="createTimeFormatter">绑定时间</th>
            <th data-field="imei">IMEI</th>
            <th data-field="wxNo">绑定商户微信号</th>
            <th data-field="memberCount">会员数</th>
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
	
	function createTimeFormatter(value, row, index){
		var d = new Date(row.createTime);
		var year = d.getFullYear();
		var month = d.getMonth() + 1;
		var day = d.getDate();
		var hour = d.getHours();
		var minutes = d.getMinutes();
		var timeStr = year;
		if(month<10){
			timeStr += "-0" + month;
		}else{
			timeStr += "-" + month;
		}
		if(day<10){
			timeStr += "-0" + day;
		}else{
			timeStr += "-" + day;
		}
		if(hour<10){
			timeStr += " 0" + hour;
		}else{
			timeStr += " " + hour;
		}
		if(minutes<10){
			timeStr += ":0" + minutes;
		}else{
			timeStr += ":" + minutes;
		}
		return timeStr;
	}
</script>
</body>
</html>