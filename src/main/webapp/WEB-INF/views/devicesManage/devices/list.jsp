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
        <li><a href="<%=basePath%>shopBasic/industry" data-target="navTab">设备管理</a></li>
        <li><a href="javascript: void(0)">工作机管理</a></li>
    </ol>
</div>
<form class="form-horizontal" data-bt-form>
    <div class="row">

        <div class="col-sm-6 g-col-gap s-row">
            <div class="col-sm-1 g-label">
                <label for="deviceStatus" class="control-label s-control-label">设备状态：</label>
            </div>
            <div class="col-sm-11 g-input">
                <select class="form-control" name="deviceStatus" id="deviceStatus">
                    <option value="">全部</option>
                    <option value="2">正常</option>
                    <option value="1">离线</option>
                </select>
            </div>
        </div>
        <div class="col-sm-6 g-col-gap s-row">
            <div class="col-sm-1 g-label">
                <label for="imei" class="control-label s-control-label">IMEI：</label>
            </div>
            <div class="col-sm-10 g-input">
                <input id="imei" name="imei"  type="text" class="form-control" placeholder="请输入IMEI" autocomplete="off"/>
            </div>

            <div class="col-sm-1 g-button">
                <button type="submit" title="查询" class="btn btn-primary"><i class="glyphicon  glyphicon-search"></i> 查询</button>
            </div>
        </div>
    </div>
</form>

<div class="row">
    <table  data-bt-table data-url="deviceManage/devices/list"
            data-pagination="true"
            data-side-pagination="server"
            data-undefined-text=""
            data-show-refresh="true"
            data-show-columns="true"
            data-toolbar=""
            data-unique-id="id">
        <thead>
        <tr>
            <th data-field="activeTime">激活时间</th>
            <th data-field="imei">IMEI</th>
            <th data-field="shopAccount">店铺账号</th>
            <th data-field="shopName">店铺名称</th>
            <th data-field="wechatId">微信ID</th>
            <th data-field="wechatStatus" data-formatter="wechatStatusFormatter">微信状态</th>
            <th data-field="deviceStatus" data-formatter="deviceStatusFormatter">设备状态</th>
        </tr>
        </thead>
    </table>
</div>
<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
<script type="text/javascript">
    function wechatStatusFormatter(value, row, index){
       if(value==1){
           return "未登录";
       }
       if(value==2){
           return "已登录";
       }
    }
    function deviceStatusFormatter(value, row, index){
        if(value==1){
            return "离线";
        }
        if(value==2){
            return "正常";
        }
    }
</script>
</body>
</html>