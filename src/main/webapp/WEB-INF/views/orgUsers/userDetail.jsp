<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        .s-col-sm-7 .col-sm-3{
            width:auto;
        }
        .col-sm-8 {
            padding-top:15px;
        }
    </style>
</head>
<body>

<div class="row">
    <ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
        <li><a href="javascript: void(0)">用户库</a></li>
        <li><a href="<%=basePath%>company/orgUserPage" data-target="navTab">企业用户</a></li>
        <li><a href="javascript: void(0)">用户详情</a></li>
    </ol>
</div>

<div class="container-fluid">
	<div class="col-lg-4">
	
	 <br />
	    <div class="row padding-top-10">
	    <div class="col-sm-8">
	    <img class="img-rounded" src="<%=basePath%>static/images/test.jpg">
	    </div>
	    </div>
	</div>
	<div class="col-lg-8">
        <br />
        <div class="row padding-top-10">
            <div class="col-sm-8">
                <label class="col-sm-4">账号：</label>
                <span class="col-sm-4">${dto.userName}</span>
                <c:choose>
                    <c:when test="${dto.isActive == 1}">
                        <label class="col-sm-2">启用中</label>
                    </c:when>
                    <c:otherwise>
                        <label class="col-sm-2">已禁用</label>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-8">
                <label class="col-sm-4">姓名：</label>
                <span class="col-sm-4">${dto.name}</span>
            </div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-8">
                <label class="col-sm-4 ">绑定手机号：</label>
                <c:choose>
                 <c:when test="${dto.phone != null }">
                     <span class="col-sm-4">${dto.phone}</span>
                 </c:when>
                 <c:otherwise>
                     <span class="col-sm-4">-</span>
                 </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-8">
                <label class="col-sm-4 ">性别：</label>
                <c:choose>
                 <c:when test="${dto.gender == 1 }">
                     <span class="col-sm-4">男</span>
                 </c:when>
                 <c:when test="${dto.gender == 2 }">
                     <span class="col-sm-4">女</span>
                 </c:when>
                 <c:otherwise>
                     <span class="col-sm-4">-</span>
                 </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-8">
                <label class="col-sm-4">加入时间：</label>
                <c:choose>
                    <c:when test="${dto.createTime != null }">
                        <span class="col-sm-6">${dto.createTime}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="col-sm-4">-</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-8">
                <label class="col-sm-4 control-label">生日：</label>
                <c:choose>
                    <c:when test="${dto.birthday != null }">
                        <span class="col-sm-4">${dto.birthday}
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="col-sm-4">-</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <br />
         <div class="row padding-top-10">
             <div class="col-sm-8">
                 <label class="col-sm-4">所在商户：</label>
                 <c:choose>
                     <c:when test="${dto.topOrgName!= null }">
                         <span class="col-sm-4">${dto.topOrgName} - ${dto.secondOrgName}</span>
                     </c:when>
                     <c:otherwise>
                         <span class="col-sm-4">-</span>
                     </c:otherwise>
                 </c:choose>
             </div>
         </div>
         <div class="row padding-top-10">
             <div class="col-sm-8">
                 <label class="col-sm-4 control-label">职位：</label>
                 <c:choose>
                     <c:when test="${dto.postName != '' }">
                         <span class="col-sm-4">${dto.postName}</span>
                     </c:when>
                     <c:otherwise>
                         <span class="col-sm-4">-</span>
                     </c:otherwise>
                 </c:choose>
             </div>
         </div>
         <div class="row padding-top-10">
             <div class="col-sm-8">
                 <label class="col-sm-4">是否在职：</label>
                 <span class="col-sm-4">在职</span>
             </div>
         </div>
    </div>
</div>
<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-validate.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/module.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/uploader.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/hotkeys.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/simditor.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>
</body>
