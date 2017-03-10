<%--
  Created by IntelliJ IDEA.
  User: xiao8
  Date: 2016/12/3
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="date" uri="http://www.youanmi.com/jstl/date" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<body>

<div class="row">
    <ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
        <li><a href="javascript: void(0)">消息管理</a></li>
        <li><a href="<%=basePath%>om/notice" data-target="navTab">系统公告</a></li>
        <li><a href="javascript: void(0)">公告详情</a></li>
    </ol>
</div>

<h4 style="text-align:center;">${notice.title}</h3>
<br>
<div class="row" style="text-align:center;">
    <div>
        <span>来源：<c:if test="${notice.sendChannel=='omp'}">运营后台</c:if></span>
        <span>发布时间：<date:date pattern="yyyy-MM-dd" value="${notice.sendTime}" ></date:date></span>
    </div>
</div>
<hr>
<div class="row">
    <div class="col-sm-1"></div>
    <div class="col-sm-10">${notice.content}</div>
    <div class="col-sm-1"></div>
</div>

</body>
