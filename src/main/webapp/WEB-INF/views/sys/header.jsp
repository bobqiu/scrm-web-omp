<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-default navbar-static-top s-navbar-default" style="background-color: #438eb9; margin-bottom: 0px;position:fixed;top:0px;width:100%;" id="nav_top">
    <div class="container-fluid s-container-fluid">
        <div class="collapse navbar-collapse" id="admin">
            <%--logo--%>
            <div class="s-header-img"></div>

            <%--tab--%>
            <div class="s-nav-tab">
                <ul>

                </ul>
            </div>

            <%--操作项--%>
            <ul class="nav navbar-nav navbar-right s-navbar-right">
                <li class="dropdown s-dropdown s-admin">
                    <a href="#" class="dropdown-toggle s-dropdown-toggle" id="user_ul_menu" data-toggle="dropdown" role="button" aria-expanded="false"><i class="s-glyphicon"></i><span>${user.loginName}</span><span class="caret"></span></a>
                    <ul class="dropdown-menu s-dropdown-menu" role="menu" aria-labelledby="user_ul_menu">
                        <li><a href="javascript:void(0);" data-url="user/editPwd?userId=${user.userId }" data-targetid="editpassword"  data-model='dialog'><i class="glyphicon glyphicon-user s-glyphicon-user"></i><span>修改密码</span></a></li>

                        <li class="divider"></li>
                        <li><a href="logout" ><i class="glyphicon glyphicon-off s-glyphicon-user"></i><span>退出登录</span></a></li>
                    </ul>
                </li>
                <li class="s-col"></li>
            </ul>
        </div>
    </div>
</nav>
<div class="s-nav"></div>

