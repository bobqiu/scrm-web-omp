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
            <%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

        <head>
            <style>
                .fixed-table-body{
                    overflow-x: hidden;
                }
                .fixed-table-body tr th:nth-child(11){
                    width:60px;
                }
            </style>
        </head>

        <body>
        <div class="row">
        <ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
        <li><a href="javascript: void(0)">消息管理</a></li>
        <li><a href="javascript: void(0)">系统公告</a></li>
        </ol>
        </div>

        <form class="form-horizontal" data-bt-form>
            <div class="row">
                <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label for="sendChannel" class="control-label s-control-label">发送渠道：</label>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select class="form-control" name="sendChannel" id="sendChannel">
                            <option value="omp">运营平台</option>
                        </select>
                    </div>
                </div>
                <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label for="sendStatus" class="control-label s-control-label">发送状态：</label>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select class="form-control" name="sendStatus" id="sendStatus">
                            <option value="0">全部</option>
                            <option value="2">已发送</option>
                            <option value="3">已取消</option>
                            <option value="4">未发送</option>
                        </select>
                    </div>
                </div>
                <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label for="sendObject" class="control-label s-control-label">发送对象：</label>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select class="form-control" name="sendObject" id="sendObject">
                            <option value="0">全部</option>
                            <option value="1">商户</option>
                        </select>
                    </div>
                </div>
                <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label for="startSendTimeStr" class="control-label s-control-label">操作时间：</label>
                    </div>
                    <div class="col-sm-4 g-input-time">
                        <div class="input-group date form_datetime">
                            <input type="text" readonly class="form-control" placeholder="请选择" id="startSendTimeStr" name="startSendTimeStr">
                            <span class="input-group-addon">
                                      <span class="glyphicon glyphicon-th"></span>
                        </span>
                        </div>
                    </div>
                    <div class="col-sm-1 g-label-time">
                        <label for="endSendTimeStr" class="control-label s-control-label">至</label>
                    </div>

                    <div class="col-sm-4 g-input-time">
                        <div class="input-group date form_datetime">
                            <input type="text" readonly class="form-control" placeholder="请选择" id="endSendTimeStr" name="endSendTimeStr">
                            <span class="input-group-addon">
                                       <span class="glyphicon glyphicon-th"></span>
                                   </span>
                        </div>
                    </div>

                </div>  
                <div class="col-sm-3 g-col-gap s-row">
                <div class="col-sm-2 g-button">
                    <button type="submit" title="查询" class="btn btn-primary"><i class="glyphicon  glyphicon-search"></i> 查询</button>
                </div>
                </div>
            </div>
        </form>
        <div id="toolbar">
        <shiro:hasPermission name="om/notice/add">
            <button type="button" class="btn btn-primary"
            data-url="om/notice/add"
            data-target="navTab"
            data-targetid="my_modal"
            data-backdrop="static"
            title="添加公告"><i class="glyphicon glyphicon-plus"></i>
            新增</button>
        </shiro:hasPermission>
        </div>
        <div class="row">
        <table  data-bt-table data-url="om/notice/list"
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
        <th data-field="state" data-sequencenum="true">序号</th>
        <th data-field="title" data-formatter="titleFormatter">标题</th>
        <th data-field="sendObject" data-formatter="sendObjectFormatter">发送对象</th>
        <th data-field="sendArea" data-formatter="sendAreaFormatter">区域范围</th>
        <th data-field="deviceType" data-formatter="deviceTypeFormatter">指定设备</th>
        <th data-field="sendTime" data-formatter="dateFormatter">发送时间</th>
        <th data-field="sendTotal">推送总数</th>
        <th data-field="pushNum">已发送</th>
        <th data-field="msgProcess">已到达</th>
        <th data-field="clickNum">点击数</th>
        <th data-field="sendStatus" data-formatter="sendStatusFormatter">状态</th>
        <th data-field="action" data-width="8%" data-formatter="actionFormatter">操作</th>
        </tr>
        </thead>
        </table>
        </div>
        <script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
        <script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
        <script type="text/javascript">
        function actionFormatter(value, row, index) {
        var html = "";
        if(row.sendStatus==2){
        <shiro:hasPermission name="om/notice/del">
            html += '<button class="btn btn-danger btn-xs" data-url="<%=basePath%>om/notice/del?id='+row.id+'" data-msg="将删除该公告，确定删除吗？" data-model="ajaxToDo" data-targetid="my_delete_modal" title="删除"><i class="glyphicon glyphicon-trash"></i></button> ';
        </shiro:hasPermission>
        }
        if(row.sendStatus==3){
        <shiro:hasPermission name="om/notice/edit">
            html += '<button class="btn btn-primary btn-xs" data-url="<%=basePath%>om/notice/edit?id='+row.id+'" data-target="navTab" title="修改"><i class="glyphicon glyphicon-pencil"></i></button> ';
        </shiro:hasPermission>
        <shiro:hasPermission name="om/notice/del">
            html += '<button class="btn btn-danger btn-xs" data-url="<%=basePath%>om/notice/del?id='+row.id+'" data-msg="将删除该公告，确定删除吗？" data-model="ajaxToDo" data-targetid="my_delete_modal" title="删除"><i class="glyphicon glyphicon-trash"></i></button> ';
        </shiro:hasPermission>
        }
        if(row.sendStatus==4){
        <shiro:hasPermission name="om/notice/cancel">
            html += '<button class="btn btn-warning btn-xs" data-url="<%=basePath%>om/notice/cancel?id='+row.id+'" data-msg="将取消该公告发送，确定要取消吗？" data-model="ajaxToDo" title="取消发送"><i class="glyphicon glyphicon-repeat"></i></button> ';
        </shiro:hasPermission>
        }
        return html;
        }
        function sendAreaFormatter(value, row, index){
        if(value==null || value==""){
        return "全部";
        }
        return value;
        }
        function sendObjectFormatter(value, row, index){
        if(value==1){
        return "商户";
        }
        return "-";
        }
        function deviceTypeFormatter(value, row, index){
        if(value==0){
        return "全部";
        }else if(value==1){
        return "安卓";
        }else if(value==2){
        return "IOS";
        }
        return "全部";
        }
        function sendStatusFormatter(value, row, index){
        //        发送状态（1-草搞，2-已发送，3-取消发送，4-定时发送）
        if(value==1){
        return "草搞";
        }else if(value==2){
        return "已发送";
        }else if(value==3){
        return "已取消";
        }else if(value==4){
        return "未发送";
        }
        return "-";
        }
        function dateFormatter(value, row, index){
        return new Date(value).format("yyyy-MM-dd hh:mm:ss")
        }
        function indexFormatter(value, row, index){
        return index+1;
        }
        function titleFormatter(value, row, index){
        if(value.length > 20) {
            value = value.substring(0,20) + '......';
        }
        return '<a href="<%=basePath%>om/notice/desc?id='+row.id+'" data-target="navTab">'+value+'</a>';
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
        </body>
