<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <title>行业管理列表</title>
        <style type="text/css">


            .oneLevel {
                background: rgba(0, 0, 0, .2);
            }
        </style>
    </head>
    <body>
        <div class="row">
            <ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
                <li><a href="<%=basePath%>shopBasic/industry" data-target="navTab">门店基础设置</a></li>
                <li><a href="javascript: void(0)">行业管理</a></li>
            </ol>
        </div>

            <form class="form-horizontal" data-bt-form>

                <div class="row">

                <div class="col-sm-12 g-col-gap s-row">
                    <div class="col-sm-1 g-label">
                        <label for="firstLevel" class="control-label s-control-label">一级行业：</label>
                    </div>

                    <div class="col-sm-10 g-input">
                        <select class="form-control" id="firstLevel" name="id">
                            <option value="">全部</option>
                            <c:forEach items="${industrys.industrys}" var="indu" varStatus="st">
                                <option value="${indu.id}">${indu.name}</option>
                            </c:forEach>
                        </select>
                    </div>


                    <div class="col-sm-1 g-button">
                        <button type="submit" title="查询" class="btn btn-primary"><i class="glyphicon  glyphicon-search"></i> 查询</button>
                    </div>
                </div>


                </div>
            </form>

        <div id="toolbar">
            <shiro:hasPermission name="shopBasic/industry/add">
                <button type="button" class="btn btn-primary"
                        data-url="shopBasic/industry/add"
                        data-model="dialog"
                        data-targetid="my_modal"
                        data-backdrop="static"
                        title="新增行业"><i class="glyphicon glyphicon-plus"></i> 新增
                </button>
            </shiro:hasPermission>
        </div>

        <div class="row">
            <table data-bt-table data-url="shopBasic/industry/list"
                   data-pagination="true"
                   data-side-pagination="server"
                   data-page-size="10"
                   data-undefined-text=""
                   data-toolbar="#toolbar"
                   data-unique-id="id"
                   data-row-style="rowFormat"
                   data-show-refresh="true"
                   data-show-columns="true"
                   data-show-header="false">
                <thead>
                <tr>
                    <th data-field="industry" data-formatter="actionFormatter">操作</th>
                </tr>
                </thead>
            </table>
        </div>
        <script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-initTable.js"></script>
        <script type="text/javascript">
            function actionFormatter(value, row, index) {
                var html = "";
                html += '<div">';
                if (row.parent_id == 0) {
                    html += row.name;
                } else {
                    html += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + row.name;
                }
                html += '<shiro:hasPermission name="shopBasic/industry/edit">';	
                html += '<span style="float:right;"><button class="btn btn-primary btn-xs" data-url="shopBasic/industry/edit?id=' + row.id + '&name=' + row.name + '" data-model="dialog" data-targetid="my_modal" data-backdrop="static" title="编辑"><i class="glyphicon glyphicon-pencil"></i></button></span>';
                html += '</shiro:hasPermission>';
                html += '</div>';
                return html;
            }
            function rowFormat(row, index) {
                if (row.parent_id == 0) {
                    return {classes: "oneLevel"};
                }
                else {
                    return {};
                }
            }
        </script>
    </body>
</html>