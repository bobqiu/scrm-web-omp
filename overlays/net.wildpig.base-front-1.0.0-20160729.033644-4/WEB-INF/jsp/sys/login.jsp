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
<title>system</title><!--TODO:  system name-->
<link type="text/css" rel="stylesheet" href="<%=basePath %>static/plugins/bootstrap/css/bootstrap.min.css">
<link type="text/css" rel="stylesheet" href="<%=basePath %>static/css/admin.css">
<script type="text/javascript" src="<%=basePath %>static/js/wildpig/message.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.form.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container" style="margin-top: 50px;">
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
									<input class="form-control" placeholder="请输入用户名" value="admin" maxlength="32"
										id="loginName" name="loginName" type="text" >
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="请输入密码" name="password" value="1" maxlength="32"
										id="password" type="password" value="">
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="验证码" name="code" id="code" type="text" maxlength="4"
										style="width: 40%; float: left;"  value="8888"> <img onclick="changeCode()"
										id="codeImg" title="点击更换"
										style="width: 40%; height: 34px; margin-left: 12px;" src="code">
								</div>
								<div class="checkbox">
									<label> <input name="rememberMe" id="saveid"
										type="checkbox" value="Remember Me">记住密码
									</label>
								</div>
								<div class="form-group">

									<input type="submit" value="登陆"
										class="btn btn-lg btn-success btn-block">
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
							$("#loginName").tips({
								side : 1,
							msg : "用户名或密码有误",
								bg : '#FF5080',
								time : 15
							});
							$("#loginName").focus();
						} else if ("codeerror" == data.result) {
							$("#code").tips({
								side : 1,
							msg : "验证码输入有误",
								bg : '#FF5080',
								time : 15
							});
							$("#code").focus();
						} else {
							$("#loginName").tips({
								side : 1,
							msg : "缺少参数",
								bg : '#FF5080',
								time : 15
							});
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
			$("#loginName").tips({
				side : 2,
				msg : '用户名不得为空',
				bg : '#AE81FF',
				time : 3
			});
			$("#loginName").focus();
			return false;
		} else {
			$("#loginName").val($.trim($('#loginName').val()));
		}

		if ($("#password").val() == "") {
			$("#password").tips({
				side : 2,
				msg : '密码不得为空',
				bg : '#AE81FF',
				time : 3
			});
			$("#password").focus();
			return false;
		}
		
		if ($("#code").val() == "") {
			$("#code").tips({
				side : 2,
				msg : '验证码不得为空',
				bg : '#AE81FF',
				time : 3
			});
			$("#code").focus();
			return false;
		}

		$("#loginbox").tips({
			side : 1,
			msg : '正在登录 , 请稍后 ...',
			bg : '#68B500',
			time : 1
		});
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