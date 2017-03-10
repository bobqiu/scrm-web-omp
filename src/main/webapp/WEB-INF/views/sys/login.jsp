<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	session.setAttribute("lang", "zh");//TODO: remove

%>
<!DOCTYPE html>
<html lang="${lang}">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>运营后台</title><!--TODO:  system name-->
<script type="text/javascript">
	//@autor liulj 2016年12月8日10:49:11 如果没有权限就跳转到登陆页面
	if(document.getElementById("user_ul_menu")){
		window.location = location;
	}
	sessionStorage.clear();
</script>
<style type="text/css">
        .s-error{
            background-color: #ffe3e2;
            border: 1px solid #eabdc2;
            border-radius: 4px;
            color: red;
            font-size: 12px;
            width: auto;
            padding: 10px 0;
            padding-left: 14px;
        }
        .s-error span{
            background: url(static/images/icon/gantan.png) left center no-repeat;
            display: block;
            background-size: 5%;
            padding-left: 22px;
        }
    </style>
<link type="text/css" rel="stylesheet" href="<%=basePath %>static/plugins/bootstrap/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="<%=basePath %>static/css/admin.css">
<script type="text/javascript" src="<%=basePath %>static/js/wildpig/message.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.form.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/bootstrap/js/bootstrap.min.js"></script>
</head>
<body style="background-image:url(<%=basePath %>static/images/loginbg.jpg);background-repeat:no-repeat;background-size:100%;">
	<div class="container" style="margin-top: 110px;">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div id="testmsg" class="login-panel panel panel-default" >
					<div  class="panel-heading" id="test1" >
						<h3  class="panel-title" >请登录</h3>
					</div>
					<div class="panel-body" id="loginbox">
						<form role="form" method="post" name="loginForm" onsubmit="return severCheck();">
							<fieldset>

								<div class="form-group">
									<input class="form-control" placeholder="请输入用户名" maxlength="32"
										id="loginName" name="loginName" type="text" >
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="请输入密码" name="password" maxlength="32"
										id="password" type="password" value="">
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="验证码" name="code" id="code" type="text" maxlength="4"
										style="width: 40%; float: left;"> <img onclick="changeCode()"
										id="codeImg" title="点击更换"
										style="width: 40%; height: 34px; margin-left: 12px;" src="code">
								</div>
								<div class="checkbox">
									<label> <input name="rememberMe" id="saveid"
										type="checkbox" value="Remember Me">记住密码
									</label>
								</div>
								<div class="form-group">

									<input type="submit" value="登录"
										class="btn btn-lg btn-success btn-block" style="background-color:#337ab7;border-color: #2e6da4;">
								</div>
								<div class="s-error" id="promptDiv" style="display:none;">
                                        <span></span>
                                    </div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	//服务器校验
	function severCheck() {
		if (check()) {
			var loginName = $("#loginName").val();
			var password = $("#password").val();
			var code = "ksbadmtn1f2izwqy" + loginName + ",00," + password
					+ "ipvb5cxat0zn9eg7" + ",00," + $("#code").val();
			$.ajax({
				type : "POST",
				url : 'login_login',
				data : {
					keyData : code,
					tm : new Date().getTime()
				},
				dataType : 'json',
				cache : false,
				success : function(data) {
					saveCookie();
					if ("success" == data.result) {
						window.location.href = "sys/index";
					} else {
						if ("usererror" == data.result) {
							$("#promptDiv").show();
                            $("#promptDiv").children().html("用户名或密码有误");
							$("#loginName").focus();
						} else if ("codeerror" == data.result) {
							$("#promptDiv").show();
                            $("#promptDiv").children().html("验证码错误");
							$("#code").focus();
						} else {
							$("#promptDiv").show();
                            $("#promptDiv").children().html("缺少参数");
							$("#loginName").focus();
						}
						changeCode();
						$("#code").val("");
					}
				}
			});
		} else {
			changeCode();
			$("#code").val("");
		}
		return false;
	}

	function changeCode() {
		$("#codeImg").attr("src", "code?t=" + new Date().getTime());
	}

	//客户端校验
	function check() {
		if ($("#loginName").val() == "") {
		   $("#promptDiv").show();
		   $("#promptDiv").children().html("请输入用户名");
			$("#loginName").focus();
			return false;
		} else {
			$("#loginName").val($.trim($('#loginName').val()));
            $("#promptDiv").hide();
            $("#promptDiv").children().html("");
		}

		if ($("#password").val() == "") {
            $("#promptDiv").show();
            $("#promptDiv").children().html("请输入密码");
			$("#password").focus();
			return false;
		}else{
		    $("#promptDiv").hide();
            $("#promptDiv").children().html("");
		}
		
		if ($("#code").val() == "") {
			$("#promptDiv").show();
            $("#promptDiv").children().html("请输入验证码");
			$("#code").focus();
			return false;
		}else{
            $("#promptDiv").hide();
            $("#promptDiv").children().html("");
        }

		return true;
	}

	function saveCookie() {
		if ($("#saveid")[0].checked) {
			$.cookie('loginName', $("#loginName").val(), {expires : 7});
			$.cookie('password', $("#password").val(), {expires : 7});
		} else {
			$.cookie('loginName', '', {expires : -1});
			$.cookie('password', '', {expires : -1});
		}
	}

	$(document).ready(function(){
		var loginName = $.cookie('loginName');
		var password = $.cookie('password');
		if (loginName && password) {
			$("#loginName").val(loginName);
			$("#password").val(password);
			$("#saveid")[0].checked = "checked";
			$("#code").focus();
		}
	});

	//TOCMAT重启之后 点击左侧列表跳转登录首页 
	if (window != top) {
		top.location.href = location.href;
	}
</script>
</html>