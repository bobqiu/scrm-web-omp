<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="navbar navbar-default navbar-static-top" style="background-color: #438eb9; margin-bottom: 0px;" id="nav_top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#admin">
                <span class="sr-only">${user.loginName}</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="javascript:"><i class="glyphicon glyphicon-leaf"></i>system name</a>
        </div>
        <div class="collapse navbar-collapse" id="admin">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown" style="width: 160px;">
                    <a href="#" class="dropdown-toggle" id="user_ul_menu" data-toggle="dropdown" role="button" aria-expanded="false"><i class="glyphicon glyphicon-user"></i>${user.loginName}<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="user_ul_menu">
                        <li><a href="javascript:void(0);" data-url="user/editPwd?userId=${user.userId }" data-targetid="editpassword"  data-model='dialog'><i class="glyphicon glyphicon-user"></i>编辑</a></li>
                       
                        <li class="divider"></li>
                        <li><a href="logout" ><i class="glyphicon glyphicon-off"></i>退出</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
