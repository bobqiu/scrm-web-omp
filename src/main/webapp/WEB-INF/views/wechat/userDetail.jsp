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
        <li><a href="javascript: void(0)">用户库</a></li>
        <li><a href="javascript: void(0)" data-target="navTab">微信用户</a></li>
        <li><a href="javascript: void(0)">微信用户详情</a></li>
    </ol>
</div>

    <div class="czf-vip-member-data-main clearfix">
        <div class="vip-main-left">
            <c:if test="${dto.thumHeadUrl!=null&&dto.thumHeadUrl!=''}">
            	<img src="${dto.thumHeadUrl}" alt="" width=70px heigth=80px;>
        	</c:if>
        	<c:if test="${dto.thumHeadUrl==null||dto.thumHeadUrl==''}">
            	<img src="<%=basePath %>static/images/user_142px_8px.png" alt="" width=70px heigth=80px;>
        	</c:if>
        </div>
        <div class="vip-main-right">
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账号：${dto.userName}</p>
            <p>&nbsp;&nbsp;&nbsp;微信号：${dto.wxNo}</p>
            <p>微信昵称：${dto.wxName}</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别：
                <c:if test="${dto.gender==1}">
            	     男
        	     </c:if>
                <c:if test="${dto.gender==2}">
            	     女
        	     </c:if>
            </p>
            <p>&nbsp;加入时间：${dto.createTimeStr}</p>
        </div>
    </div>
    
    <hr>
    <div>
         <p>该微信用户已关注${dto.attentionCount}家商户</p>
    </div>
    
    
        <div class="row">
            <table  data-bt-table data-url="wx/wxUser/detailList?id=${dto.id}"
                    data-pagination="true"
                    data-side-pagination="server"
                    data-undefined-text="-"
                    data-toolbar="#toolbar"
                    data-unique-id="id"
                    data-show-refresh="true"
                    data-show-columns="true"
                    data-export-data-type="all">
                <thead>
                <tr>
                    <th data-field="createTime" data-formatter="dateFormatter">关注时间</th>
                    <th data-field="topOrgName">关注商户名称</th>
                    <th data-field="industryStr">商户所属行业</th>
                    <th data-field="name" data-formatter="markNameFormatter">备注姓名</th>
                    <th data-field="mobile" data-formatter="phoneFormatter">备注手机号</th>
                    <th data-field="birthday" data-formatter="birthdateFormatter">备注生日</th>
                </tr>
                </thead>
            </table>
        </div>
        
        <script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
        <script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
        <script type="text/javascript">
            function markNameFormatter(value, row, index) {
                if (value == null || value=="")  {
                    return "-";
                }
            }
            function phoneFormatter(value, row, index) {
                if (value == null)  {
                    return "-";
                }
            }
            function dateFormatter(value, row, index){
                if (value != null) {
                    return new Date(value).format("yyyy-MM-dd hh:mm:ss")
                } else {
                    return "-"
                }
            }
            function birthdateFormatter(value, row, index){
                if (value != null) {
                    return new Date(value).format("MM-dd")
                } else {
                    return "-"
                }
            }
            
            $(".form_datetime").datetimepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                minView:2,
                autoclose: true,
                todayBtn: true,
                clearBtn:true
            });

        </script>
    </div>
<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-validate.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/module.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/uploader.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/hotkeys.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/simditor.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>
</body>
