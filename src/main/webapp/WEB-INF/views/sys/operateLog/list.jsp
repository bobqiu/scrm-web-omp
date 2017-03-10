<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="utf-8">
	    <title>Apply Join</title>
		<style type="text/css">
			.fixed-table-container thead th:nth-child(1) .th-inner{

			}
			.fixed-table-container thead th:nth-child(2) .th-inner{
				padding-right: 80px;
			}
			.fixed-table-container thead th:nth-child(3) .th-inner{

			}
			.fixed-table-container thead th:nth-child(4) .th-inner{
				padding-right: 70px;
			}
			.fixed-table-container thead th:nth-child(5) .th-inner{
				padding-right: 100px;
			}
		</style>
	    <script src="<c:url value="/static/js/jquery.cityselect.js"/>"/>
	    <script type="text/javascript">
			$(function(){
				$("#city").citySelect({
					nodata:"none",
					required:false
				}); 
			});
		</script>
	</head>
	<body>
	
	<div class="row">
		<ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
			<li><a href="javascript: void(0)">系统管理</a></li>
			<li><a href="javascript: void(0)">操作日志</a></li>
		</ol>
	</div>
		

   

    <form id="queryForm" class="form-horizontal" data-bt-form>

        <div class="row">

            <div class="col-sm-3 g-col-gap s-row">
                <div class="row">
                      <div class="col-sm-1 g-label">
                          <label for="name" class="control-label s-control-label">姓名：</label>
                      </div>

                      <div class="col-sm-11 g-input">
                          <input type="text" class="form-control" placeholder="请输入姓名" id="name" name="name" autocomplete="off">
                      </div>
                </div>
            </div>

            <div class="col-sm-6 g-col-gap s-row">
                <div class="row">
                      <div class="col-sm-1 g-label">
                          <label for="startTime" class="control-label s-control-label">操作时间：</label>
                      </div>

                       <div class="col-sm-5 g-input-time">
                          <div class="input-group date form_datetime">
                              <input type="text" readonly class="form-control" placeholder="请选择" id="startTime" name="startTime">
                              <span class="input-group-addon">
                                  <span class="glyphicon glyphicon-th"></span>
                              </span>
                          </div>
                       </div>

                      <div class="col-sm-1 g-label-time">
                          <label for="endTime" class="control-label s-control-label">至</label>
                      </div>

                       <div class="col-sm-5  g-input-time">
                           <div class="input-group date form_datetime">
                               <input type="text" readonly class="form-control" placeholder="请选择" id="endTime" name="endTime">
                               <span class="input-group-addon">
                                   <span class="glyphicon glyphicon-th"></span>
                               </span>
                           </div>
                       </div>
                </div>

            </div>

            <div class="col-sm-3 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                      <label for="name" class="control-label s-control-label">关键字：</label>
                    </div>

                    <div class="col-sm-10 g-input">
                      <input type="text" class="form-control" placeholder="请输入操作关键字"  id="description" name="description" autocomplete="off">
                    </div>

                   <div class="col-sm-1 g-button">
                   <button type="submit" title="查询" class="btn btn-primary"><i class="glyphicon  glyphicon-search"></i> 查询</button>
                   </div>
            </div>

        </div>

    </form>

		<!-- 商户入驻 -->
		<div class="tab-pane fade in active" id="shop">
		    <style>
		    #shopTable td{
		        width:10px!important
		    }
		    </style>
			<div class="row">
				<table  id="shopTable"
						data-bt-table data-url="operateLog/list"
						data-pagination="true"
						data-side-pagination="server"
						data-page-size="10"
						data-undefined-text=""
						data-toolbar="#toolbar1"
						data-unique-id="id"
						data-show-refresh="true"
						data-show-columns="true"
						data-show-export="true"
						data-export-data-type="all">
					<thead>
					<tr>
						<th data-field="state" data-sequencenum="true" class="look-num">序号</th>
						<th data-field="name">姓名</th>
						<th data-field="userName">用户名</th>
						<th data-field="roleName">角色</th>
						<th data-field="createTime">操作时间</th>
						<th data-field="description">操作描述</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>

	<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
	<script type="text/javascript">
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
</html>