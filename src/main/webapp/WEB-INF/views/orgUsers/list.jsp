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
                        <select class="form-control" name="searchCondition" id="searchCondition">
                            <optgroup label="请选择查询条件">
                            <option value="1">账号</option>
                            <option value="2">姓名</option>
                            <option value="3">手机号</option>
                        </select>
                    </div>
                    <div class="col-sm-11 g-input">
                        <input type="text" name="content" class="form-control" placeholder="请输入...">
                    </div>
                </div>
                <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label class="control-label s-control-label">是否激活：</label>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select class="form-control" name="isActive" id="isActive">
                            <option value="">全部</option>
                            <option value="1">已激活</option>
                            <option value="2">未激活</option>
                        </select>
                    </div>
                </div>
                <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label for="startSendTimeStr" class="control-label s-control-label">加入时间：</label>
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
                    <div class="col-sm-1 g-label">
                        <label class="control-label s-control-label">用户状态：</label>
                    </div>
                    <div class="col-sm-11 g-input">
                        <select class="form-control" name="isForbid" id=""isForbid"">
                            <option value="">全部</option>
                            <option value="1">已启用</option>
                            <option value="2">已停用</option>
                        </select>
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
        <table  data-bt-table data-url="company/totalUsers"
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
        <th data-field="userName" data-formatter="titleFormatter">账号</th>
        <th data-field="name">姓名</th>
        <th data-field="isActive" data-formatter="activeFormatter">是否激活</th>
        <th data-field="mobilePhone" >绑定手机号</th>
        <th data-field="isForbid" data-formatter="statusFormatter">使用状态</th>
        </tr>
        </thead>
        </table>
        </div>
        <script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
        <script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
        <script type="text/javascript">
        function sendAreaFormatter(value, row, index){
	        if(value==null || value==""){
	        return "全部";
	        }
	        return value;
        }
        function activeFormatter(value, row, index) {
        	if (value == 1) {
        		return "已激活";
        	} else if (value == 2) {
        		return "未激活";
        	} else {
        		return "-";
        	}
        }
        function statusFormatter(value, row, index) {
        	if (value == 1) {
        		return "启用中";
        	} else if (value == 2) {
        		return "停用中";
        	} else {
        		return "-";
        	}
        }
        function actionFormatter(value, row, index) {
            var html = "";
            html += '<button class="btn btn-primary btn-xs" data-url="<%=basePath%>company/editPage?userId='+row.id+'" data-target="navTab" title="修改"><i class="glyphicon glyphicon-pencil"></i></button> ';
            return html;
        }
        function dateFormatter(value, row, index){
            return new Date(value).format("yyyy-MM-dd hh:mm:ss")
        }
        function titleFormatter(value, row, index){
	        return '<a href="<%=basePath%>company/userDetail?userId='+row.id+'" data-target="navTab">'+value+'</a>';
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
