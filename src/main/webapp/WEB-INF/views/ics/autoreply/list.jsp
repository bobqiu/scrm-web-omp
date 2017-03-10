<%--
  Created by IntelliJ IDEA.
  User: guohao
  Date: 2016/12/6
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
    <title>device</title>
</head>
<body>

<div class="row">
    <ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
        <li><a href="<%=basePath%>shopBasic/industry" data-target="navTab">智能客勤</a></li>
        <li><a href="javascript: void(0)">自动回复</a></li>
    </ol>
</div>

<div class="row">
    <table  data-bt-table data-url="/ics/autoreply/list"
            data-pagination="true"
            data-side-pagination="server"
            data-undefined-text=""
            data-show-refresh="true"
            data-show-columns="true"
            data-toolbar=""
            data-unique-id="id">
        <thead>
        <tr>
            <th data-field="state" data-sequencenum="true">序号</th>
            <th data-field="type" data-formatter="eventFormatter">触发事件</th>
            <th data-field="content">自动发送模板</th>
            <th data-field="isSupportCustom" data-formatter="customFormatter">支持自定义</th>
            <th data-field="action" data-formatter="actionFormatter">操作</th>
        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
<script type="text/javascript">
    function customFormatter(value){
        return value == 1 ? "否":"是";
    }
    function eventFormatter(value){
        var text = '';
        switch (value){
            case 1:
                text ='扫码加粉成功发送会员编号';
                break;
            case 2:
                text ='扫码加粉成功提示';
                break;
            case 3:
                text ='绑定业务员成功';
                break;
            case 4:
                text ='会员生日祝福';
                break;
        }
        return text;
    }
    function actionFormatter(value,row){
        var html = "";
        <shiro:hasPermission name="/ics/autoreply/edit">
        html += '<button class="btn btn-primary btn-xs" data-url="/ics/autoreply/toEdite?id=' + row.id + '" title="编辑" data-target="navTab" data-backdrop="static"><i class="glyphicon glyphicon-pencil"></i></button> ';
        </shiro:hasPermission>
        <shiro:hasPermission name="/ics/autoreply/disable">
        html += '<button class="btn btn-danger btn-xs"'+ (row.isDisable == 1 ? 'title="禁用"' : 'title="启用"')+' data-url="/ics/autoreply/disable?id=' + row.id + '&isDisable='+(row.isDisable == 1? 2:1)+'" data-msg="确认'+(row.isDisable == 1?"禁用":"启用")+'此触发事件？" data-model="ajaxToDo" data-targetid="my_delete_modal"><i class="glyphicon '+(row.isDisable == 1 ? "glyphicon-ban-circle":"glyphicon-play-circle")+'"></i></button> ';
        </shiro:hasPermission>
        return html;
    }
</script>
</body>
</html>