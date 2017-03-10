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
        <li><a href="javascript: void(0)">用户库</a></li>
        <li><a href="javascript: void(0)">企业用户</a></li>
        </ol>
        </div>

        <form class="form-horizontal" data-bt-form>
            <div class="row">
                <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label class="control-label s-control-label">用户：</label>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select class="form-control" name="selectType" id="selectType">
                            <optgroup label="请选择查询条件">
                            <option value="1">账号</option>
                            <option value="2">微信号</option>
                            <option value="3">微信昵称</option>
                        </select>
                    </div>
                    <div class="col-sm-11 g-input">
                        <input type="text" name="key" class="form-control" placeholder="请输入...">
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
                 <div class="col-sm-5 g-col-gap s-row">
                    <div class="col-sm-2 g-button">
                        <button type="submit" title="查询" class="btn btn-primary"><i class="glyphicon  glyphicon-search"></i> 查询</button>
                    </div>
                    </div>
                </div>

        </form>
        <div id="toolbar">
        </div>
        <div class="row">
        <table  data-bt-table data-url="wx/wxUser/list"
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
        <th data-field="createTime" data-formatter="dateFormatter">加入时间</th>
        <th data-field="userName" data-formatter="nameFormatter">账号</th>
        <th data-field="wxNo">微信号</th>
        <th data-field="wxName">昵称</th>
        <th data-field="attentionCount" >关注商户数</th>
        </tr>
        </thead>
        </table>
        </div>
        <script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
        <script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
        <script type="text/javascript">

        function dateFormatter(value, row, index) {
            return new Date(value).format("yyyy-MM-dd hh:mm")
        }

        function nameFormatter(value, row, index) {
            return '<a href="<%=basePath%>wx/wxUser/detail?id='+row.id+'" data-target="navTab">'+value+'</a>';
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
