<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="date" uri="http://www.youanmi.com/jstl/date" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<head>
    <meta charset="utf-8">
    <title>right</title>
    <link rel="stylesheet" href="<%=basePath%>static/plugins/simditor/css/simditor.css"/>
    <style>
        .padding-top-10 {
            /*padding-top: 10px;*/
        }

        .error {
            color: red;
        }

        .simditor {
            position: relative;
        }

        .simditor .simditor-wrapper .s-required {
            opacity: 0;
            border: 1px solid red;
            position: absolute;
            top: 80px;
            display: block !important;
        }

        .simditor .simditor-body img {
            width: 100%;
        }

        .s-col-sm-10 {
            margin-bottom: 20px;
            padding-right: 9.4%;
        }

        .s-col-sm-10 div:first-of-type {
            float: right;
        }

        .s-col-sm-10 div:last-of-type {
            float: right;
        }

        .h-col-sm-10 {
            margin-bottom: 20px;
            padding-right: 9.4%;
        }

        .h-col-sm-10 div:first-of-type {
            float: right;
        }

        .h-col-sm-10 div:last-of-type {
            float: right;
        }
        .s-form-control{
            width:auto;
        }
        #sendObject {
            width:270px;
        }
        #deviceType{
            width:270px;
        }
        #sendTimeStr{
            width:230px;
        }
        .s-col-sm-7{
            width:auto;
            margin-bottom:20px;
        }
        .s-col-sm-7 .col-sm-3 {
            width: auto;
        }
        .col-sm-3  {
            padding-top:15px;
        }
        .col-sm-6 {
            padding-top:15px;
        }
        .col-sm-1 {
            padding-top:20px;
        }

    </style>
</head>
<body>

<div class="row">
    <ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
        <li><a href="javascript: void(0)">用户库</a></li>
        <li><a href="<%=basePath%>om/notice" data-target="navTab">企业用户</a></li>
        <li><a href="javascript: void(0)">企业用户详情</a></li>
        <li><a href="javascript: void(0)">修改资料</a></li>
    </ol>
</div>
<form class="form-horizontal" id="editUserForm" action="<%=basePath%>company/editInfo" data-ajax-after="om/notice"
      method="post">
    <input type="hidden" name="id" value="${orgUser.id}">
    <div class="container-fluid">
        <div class="row padding-top-10">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <label class="col-sm-3 ">账号：</label>
                <span class="col-sm-3">${orgUser.userName}</span>
                <span class="col-sm-6">

                        <input type="radio" name="isForbid" value="1"<c:if test="${orgUser.isForbid == 2}">checked="checked"</c:if>/>启用&nbsp;&nbsp;&nbsp;&nbsp;

                        <input type="radio" name="isForbid" value="2"<c:if test="${orgUser.isForbid == 1}">checked="checked"</c:if>/>禁用

                </span>
            </div>
            <div class="col-sm-3"></div>
        </div>

        <div class="row padding-top-10">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <label class="col-sm-3 ">绑定手机号：</label>
                <span class="col-sm-3">${orgUser.mobilePhone}</span>
                <span class="col-sm-6">
                    <input type="checkbox" name="unbound" value="1">解绑
                </span>
            </div>
            <div class="col-sm-3"></div>
        </div>

        <div class="row padding-top-10">
            <div class="col-sm-4"></div>
            <div class="col-sm-4 ">
                <div class="col-sm-1" style="width: 100px;">
                    <button type="submit" title="保存" class="btn btn-primary"><i
                            class="glyphicon  glyphicon-ok"></i>保存
                    </button>
                </div>
                <div class="col-sm-1" style="width: 100px;">
                    <button type="button" title="取消" class="form-control btn btn-default"
                            data-url="<%=basePath%>company/orgUserPage" data-target="navTab"><i
                            class="glyphicon  glyphicon-remove"></i>取消
                    </button>
                </div>
            </div>
            <div class="col-sm-4"></div>
        </div>
    </div>
</form>


<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-validate.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/module.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/uploader.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/hotkeys.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/simditor.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>
</body>
