<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"  %>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="date" uri="http://www.youanmi.com/jstl/date" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<head>
    <meta charset="utf-8">
    <style type="text/css">
        .mass-input {
            width: 100%;
            line-height: 29px;
            padding-top: 6px;
            padding-right: 6px;
            padding-bottom: 6px;
            color: #999;
            font-size: 12px;
            position: relative;
        }

        .mass-box {
            padding-left: 6px;
            border-color: #CCCCCC;
            border-left-style: solid;
            border-right-style: solid;
            border-top-style: solid;
            border-left-width: 1px;
            border-right-width: 1px;
            border-top-width: 1px;
        }

        .mass-input > span {
            float: right;
        }

        .face-div {
            width: 438px;
            height: 250px;
            padding: 12px 10px;
            top: 36px;
            left: 0;
            display: none;
        }

        .tool-tips {
            border: 1px solid #d1d1d1;
            position: absolute;
            z-index: 302;
            -webkit-box-shadow: 1px 3px 5px #d1d1d1;
            background: #fff;
        }

        .face-div .bdnuarrow {
            left: 10px;
            right: auto;
        }

        .bdnuarrow {
            width: 0;
            height: 0;
            font-size: 0;
            line-height: 0;
            display: block;
            position: absolute;
            top: -10px;
            /*left: 50%;*/
            right: 50px;
            margin-left: -5px;
        }

        .bdnuarrow em, .bdnuarrow i {
            width: 0;
            height: 0;
            font-size: 0;
            line-height: 0;
            display: block;
            position: absolute;
            border: 5px solid transparent;
            border-style: dashed dashed solid;
        }

        .bdnuarrow em {
            border-bottom-color: #d8d8d8;
            top: -1px;
        }

        .bdnuarrow i {
            border-bottom-color: #fff;
            top: 0;
        }

        .tags:hover {
            border-color: #b5b5b5;
        }

        .tags {
            display: inline-block;
            padding: 4px 6px;
            color: #777;
            vertical-align: middle;
            background-color: #FFF;
            border: 1px solid #d5d5d5;
            width: 506px;
        }

        .tags .tag {
            display: inline-block;
            position: relative;
            font-size: 13px;
            font-weight: 400;
            vertical-align: baseline;
            white-space: nowrap;
            background-color: #91b8d0;
            color: #FFF;
            text-shadow: 1px 1px 1px rgba(0, 0, 0, .15);
            padding: 4px 22px 5px 9px;
            margin-bottom: 3px;
            margin-right: 3px;
        }

        .tags .tag .close {
            font-size: 15px;
            line-height: 20px;
            opacity: 1;
            filter: alpha(opacity=100);
            color: #FFF;
            text-shadow: none;
            float: none;
            position: absolute;
            right: 0;
            top: 0;
            bottom: 0;
            width: 18px;
            text-align: center;
        }

        .tags input[type=text], .tags input[type=text]:focus {
            border: none;
            outline: 0;
            margin: 0;
            padding: 0;
            line-height: 18px;
            width: 100%;
        }
    </style>
</head>
<body>

<div class="row">
    <ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
        <li><a href="javascript: void(0)">客能客勤</a></li>
        <li><a href="<%=basePath%>om/notice" data-target="navTab">自动回复</a></li>
        <li><a href="javascript: void(0)">编辑
            <c:if test="${data.type == 1}">
                扫码加粉成功
            </c:if><c:if test="${data.type == 2}">
                绑定业务员成功
            </c:if><c:if test="${data.type == 3}">
                积分变更
            </c:if><c:if test="${data.type == 4}">
                会员生日祝福
            </c:if>
            <%--<c:if test="${data.type == 5}">--%>
                <%--积分关键字查询--%>
            <%--</c:if>--%>
        </a></li>
    </ol>
</div>
<form class="form-horizontal" id="autoreplyEditForm" action="<%=basePath%>ics/autoreply/edite"
      data-ajax-after="ics/autoreply"
      method="post">
    <div class="row">
        <input type="hidden" name="id" value="${data.id}">
        <input type="hidden" name="type" value="${data.type}">
        <div class="row">
            <c:if test="${data.type!= 4}">
                <label class="control-label s-control-label">事件:</label>
                <span>
                    <c:if test="${data.type == 2}">
                        扫码加粉成功提示
                    </c:if><c:if test="${data.type == 3}">
                    绑定业务员成功
                </c:if><c:if test="${data.type == 4}">
                    会员生日祝福
                </c:if>
                </span>
            </c:if>
        </div>
        <c:if test="${data.type==4}">
            <div class="row" style="">
                <label class="control-label s-control-label">开头:</label>
                <span>“${birthdayHeader}”</span>
                <input type="hidden" name="birthdayHeader" value="${birthdayHeader}">
            </div>
        </c:if>
        <%--<c:if test="${data.type==5}">--%>
            <%--<div class="row">--%>
                <%--<label class="col-sm-1 control-label no-padding-right" style="width: 60px;"--%>
                       <%--for="form-field-tags">*关键字:</label>--%>
                <%--<div class="col-sm-9" style="padding-left:0px;">--%>
                    <%--<div class="inline">--%>
                        <%--<input type="text" name="keywords"  class="isValidata focusValidata" isKeysorkds_1_15="关键字不能超过15个" id="form-field-tags" value="${data.keywords}"--%>
                               <%--placeholder="限30字以内,回车键确认"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</c:if>--%>
        <div class="row" style="padding-top: 10px;">
            <%--<c:if test="${data.type==5}"><label class="col-sm-1 control-label no-padding-right" style="width: 60px;">*回复:</label></c:if>--%>
            <div class="col-sm-9" style="width:;margin-right: 0; padding-left:0px;">
                <div class="mass-box" style="width: 100%;">
                    <div class="mass-input">
                        <span style="float: left;">&nbsp;&nbsp;插入表情&nbsp;&nbsp;</span>
                        <img src="<%=basePath%>static/images/face/face.png" class="cjw-face">
                        <span><!-- react-text: 429 -->还可以输入<!-- /react-text --><span id="limit-text"
                                                                                     class="limit-text"></span>
                            <!-- react-text: 431 -->字<!-- /react-text --></span>
                    </div>
                </div>
                <div class="col-sm-20" style="width: 100%;">
                    <textarea class="form-control s-form-control changeContent isValidata focusValidata" placeholder="请输入内容"
                              isnull="内容不能为空"  maxlength="1500"
                              style="height: 250px;" name="content" id="content">${data.content}</textarea>
                </div>
                <div class="row" style="padding-top: 10px;margin:0 auto;text-align:center">
                    <div class="col-sm-1" style="width:100%;">
                        <button type="submit" title="保存" class="btn btn-primary"><i
                                class="glyphicon  glyphicon-ok"></i>保存
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript" src="<%=basePath %>static/js/jquery-migrate-1.2.1.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-validate.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/face.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/ace-elements.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/bootstrap-tag.min.js"></script>
<script type="text/javascript">
    $("#autoreplyEditForm").validate();
    $('.cjw-face').qqFace({
        id: 'facebox',
        assign: 'content',
        path: '<%=basePath%>static/images/face/',	//表情存放的路径
        left: '63px',
        top: '36px'
    });
    $("#content").on("input", function () {
        FontNum("changeContent", "limit-text", 1500);
    });

    FontNum("changeContent", "limit-text", 1500);
    function FontNum(obj, ele, num) {
        var length_text = $("." + obj).val().length;
        if (length_text > num) return;
        $("." + ele).html(parseInt(num) - parseInt(length_text));
    }

    <%--<c:if test="${data.type==5}">--%>
    <%--var tag_input = $('#form-field-tags');--%>
    <%--tag_input.tag({placeholder: tag_input.attr('placeholder')});--%>
    <%--tag_input.next().attr("maxlength",30).addClass("focusValidata").attr("isKeysorkds_1_15","关键字不能超过15个");--%>
    <%--</c:if>--%>
    focusValidata();
</script>
</body>
