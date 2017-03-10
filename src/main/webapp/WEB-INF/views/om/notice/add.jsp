<%--
  Created by IntelliJ IDEA.
  User: xiao8
  Date: 2016/12/3
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<head>
    <meta charset="utf-8">
    <title>right</title>
    <link rel="stylesheet" href="<%=basePath%>static/plugins/simditor/css/simditor.css"/>
    <style>
        .padding-top-10 {
            /*padding-top: 10px;*/
        }

        .error {
            color: red;
        }

        .simditor {
            position: relative;
        }

        .simditor .simditor-wrapper .s-required {
            opacity: 0;
            border: 1px solid red;
            position: absolute;
            top: 80px;
            display: block !important;
        }

        .simditor .simditor-body img {
            width: 100%;
        }

        .s-col-sm-10 {
            margin-bottom: 20px;
            padding-right: 9.4%;
        }

        .s-col-sm-10 div:first-of-type {
            float: right;
        }

        .s-col-sm-10 div:last-of-type {
            float: right;
        }

        .h-col-sm-10 {
            margin-bottom: 20px;
            padding-right: 9.4%;
        }

        .h-col-sm-10 div:first-of-type {
            float: right;
        }

        .h-col-sm-10 div:last-of-type {
            float: right;
        }
        .s-form-control{
            width:auto;
        }
        #sendObject {
            width:270px;
        }
        #deviceType{
            width:270px;
        }
        #sendTimeStr{
            width:230px;
        }
        .s-col-sm-7{
            width:auto;
            margin-bottom:20px;
        }
        .s-col-sm-7 .col-sm-3{
            width:auto;
        }
    </style>
</head>
<body>

<div class="row">
    <ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
        <li><a href="javascript: void(0)">消息管理</a></li>
        <li><a href="<%=basePath%>om/notice" data-target="navTab">系统公告</a></li>
        <li><a href="javascript: void(0)">新增公告</a></li>
    </ol>
</div>
<form class="form-horizontal" id="addNoticeForm" action="<%=basePath%>om/notice/add" data-ajax-before=""
      data-ajax-after="om/notice" method="post">
    <div class="container-fluid">
        <div class="row padding-top-10">
            <div class="col-sm-1"></div>
            <div class="col-sm-10">
                <label for="title" class="col-sm-1 control-label">公告标题<span class="error">*</span>：</label>
                <div class="col-sm-10">
                    <input class="form-control isValidata" name="title" id="title" placeholder="1-50位英文、数字、汉字、符号及组合" maxlength="50"
                           autocomplete="off" checknull="请输入公告标题" checkEsAndZhAndNum_1_50="公告标题格式错误" onblur="validateHorizentalForm(1,this)" onfocus="validateHorizentalForm(2,this)">
                    <span style="color:red;padding-left: 5px;float:left;visibility: hidden;">&nbsp;</span>
                </div>
                <div class="col-sm-2">
                    <span id="messages"></span>
                </div>
            </div>
            <div class="col-sm-1"></div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-1"></div>
            <div class="col-sm-10">
                <label for="sendObject" class="col-sm-1 control-label">发送对象<span class="error">*</span>：</label>
                <div class="col-sm-3">
                    <select class="form-control isValidata" id="sendObject" name="sendObject" checknull="请选择发送对象" onblur="validateHorizentalForm(1,this)" onfocus="validateHorizentalForm(2,this)">
                        <option value="1">商户</option>
                    </select>
                    <span style="color:red;padding-left: 5px;float:left;visibility: hidden;">&nbsp;</span>
                </div>
            </div>
            <div class="col-sm-1"></div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-1"></div>
            <div class="col-sm-10">
                <label for="deviceType" class="col-sm-1 control-label">指定设备<span class="error">*</span>：</label>
                <div class="col-sm-3">
                    <select class="form-control isValidata" id="deviceType" name="deviceType" checknull="请选择指定设备" onblur="validateHorizentalForm(1,this)" onfocus="validateHorizentalForm(2,this)">
                        <option value="0">全部</option>
                        <option value="1">安卓</option>
                        <option value="2">IOS</option>
                    </select>
                    <span style="color:red;padding-left: 5px;float:left;visibility: hidden;">&nbsp;</span>
                </div>
            </div>
            <div class="col-sm-1"></div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-12" style="padding-right:0;margin-left:8.33333333%;">
                <label for="province" class="col-sm-1 control-label">区域范围：</label>
                <div class="col-sm-7 s-col-sm-7" style="margin-left: -15px;padding-right:0;">
                    <div class="col-sm-3" style="padding-right:0;">
                        <select class="form-control s-form-control" id="province" name="province">
                            <option value="0">请选择省</option>
                        </select>
                    </div>
                    <div class="col-sm-3" style="padding-right:0;">
                        <select class="form-control s-form-control" id="city" name="city">
                            <option value="0">请选择市</option>
                        </select>
                    </div>
                    <div class="col-sm-3" style="padding-right:0;">
                        <select class="form-control s-form-control" id="county" name="county">
                            <option value="0">请选择区</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-1"></div>
            <div class="col-sm-10">
                <label for="orgId" class="col-sm-1 control-label">选择门店：</label>
                <div class="col-sm-7 s-col-sm-7" style="margin-left: -15px;">
                    <div class="col-sm-3">
                        <select class="form-control s-form-control" id="orgId" name="orgId">
                            <option value="0">请选择门店</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-sm-1"></div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-1"></div>
            <div class="col-sm-10">
                <label for="sendTimeStr" class="col-sm-1 control-label">发送时间：</label>
                <div class="col-sm-7" style="margin-left: -15px;">
                    <div class="col-sm-5">
                        <div class="input-group date form_datetime">
                            <input type="text" class="form-control" name="sendTimeStr" id="sendTimeStr"
                                   style="background-color:white;" readonly="readonly" placeholder="请选择">
                            <span class="input-group-addon">
                                  <span class="glyphicon glyphicon-th"></span>
                            </span>
                        </div>
                        <span style="color:red;padding-left: 5px;float:left;visibility: hidden;">&nbsp;</span>

                    </div>
                </div>
            </div>
            <div class="col-sm-1"></div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-1"></div>
            <div class="col-sm-10">
                <label for="content" class="col-sm-1 control-label">公告内容<span class="error">*</span>：</label>
                <div class="col-sm-10">
                    <textarea class="form-control" name="content" id="content"></textarea>
                    <span style="color:red;padding-left: 5px;float:left;visibility: hidden;">&nbsp;</span>
                </div>
            </div>
            <div class="col-sm-1"></div>
        </div>
        <div class="row padding-top-10">
            <div class="col-sm-1"></div>
            <div class="col-sm-10 h-col-sm-10">
                <div class="col-sm-1" style="width: 100px;">
                    <button type="submit" title="保存" class="btn btn-primary"><i class="glyphicon  glyphicon-ok"></i>保存
                    </button>
                </div>
                <div class="col-sm-1" style="width: 100px;">
                    <button type="button" title="取消" class="btn btn-default"
                            data-url="<%=basePath%>om/notice" data-target="navTab"><i
                            class="glyphicon  glyphicon-remove"></i>取消
                    </button>
                </div>
            </div>
            <div class="col-sm-1"></div>
        </div>
    </div>
</form>

<div class="row" style="display: none;">
    <div id="phone" style="width: 414px;height: 736px;background-color: #eee">
        <div id="phoneTitle" style="height: 30px;width: 100%;background-color: #ddd;"></div>
        <div id="preview" style="background-color: #ccc;"></div>
    </div>
</div>
<script type="text/javascript" src="<%=basePath %>static/js/wildpig/wildpig-validate.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/module.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/uploader.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/hotkeys.js"></script>
<script type="text/javascript" src="<%=basePath %>static/plugins/simditor/js/simditor.js"></script>
<script type="text/javascript" src="<%=basePath %>static/js/jquery.tips.js"></script>
<script type="text/javascript">


    $(function () {
        $(".form_datetime").datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd hh:ii',
            startDate: new Date(),
            autoclose: true,
            todayBtn: true,
            clearBtn: true
        });
        $("#addNoticeForm").validate();
        //        $('#addNoticeForm').validate({
        //            onKeyup : true,
        //            eachValidField : function() {
        //
        //                if($(this).attr("id")=="content"){
        //                    $(this).closest('div').removeClass("error");
        //                }
        //                $(this).closest('div').removeClass('has-error');
        //            },
        //            eachInvalidField : function() {
        //                if($(this).attr("id")=="content"){
        //                    $(this).closest('div').addClass("error");
        //                }
        //                $(this).closest('div').addClass('has-error');
        //            }
        //        });
        edit();
        //加载省
        loadProvince();
        //级联选择
        //        debugger;
        $("#province").change(function () {
            loadCity();
            //loadOrg();
        });

        $("#city").change(function () {
            loadCounty();
            //loadOrg();
        });

        $("#county").change(function () {
            //loadOrg();
        });
    });


    function edit() {
        var $preview, editor, toolbar;
        Simditor.locale = 'zh-CN';
        toolbar = ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', '|', 'link', 'image', 'hr', '|', 'indent', 'outdent', 'alignment'];
        editor = new Simditor({
            textarea: $('#content'),
            toolbar: toolbar,
            pasteImage: true,
            upload: {
                url: '<%=basePath %>om/notice/uploadImage',
                fileKey: 'uploadImage',
                params: null,
                connectionCount: 10,
                leaveConfirm: '正在上传文件，如果离开上传会自动取消'
            }
        });
        $preview = $('#preview');
        if ($preview.length > 0) {
            return editor.on('valuechanged', function (e) {
                return $preview.html(editor.getValue());
            });
        }
    }
    function loadProvince() {
        $("city").html('<option value="0">请选择市</option>');
        $("county").html('<option value="0">请选择区</option>');
        $.ajax({
            url: "<%=basePath %>bussiness/address/getProvinces",
            dataType: "json",
            success: function (data) {
                var html = '<option value="0">请选择省</option>';
                if (data) {
                    $.each(data.provinces, function (index, item) {
                        html += '<option value="' + item.id + '">' + item.name + '</option>';
                    });
                }
                $("#province").html(html);
            }
        });
    }
    function loadCity() {
        $("#county").html('<option value="0">请选择区</option>');
        $.ajax({
            url: "<%=basePath %>bussiness/address/getCitys?provinceId=" + $("#province").val(),
            dataType: "json",
            success: function (data) {
                var html = '<option value="0">请选择市</option>';
                if (data) {
                    $.each(data.citys, function (index, item) {
                        html += '<option value="' + item.id + '">' + item.name + '</option>';
                    });
                }
                $("#city").html(html);
            }
        });
    }
    function loadCounty() {
        $.ajax({
            url: "<%=basePath %>bussiness/address/getAreas?cityId=" + $("#city").val(),
            dataType: "json",
            success: function (data) {
                var html = '<option value="0">请选择区</option>';
                if (data) {
                    $.each(data.areas, function (index, item) {
                        html += '<option value="' + item.id + '">' + item.name + '</option>';
                    });
                }
                $("#county").html(html);
            }
        });
    }
    function loadOrg() {
        $.ajax({
            url: "",
            data: {"provinceId": $("#province").val(), "cityId": $("#city").val(), "countyId": $("#county").val()},
            dataType: "json",
            success: function (data) {
                var html = '<option value="0">请选择门店</option>';
                $.each(data, function (index, item) {
                    html += '<option value="' + item.id + '">' + item.name + '</option>';
                });
                $("#province").html(html);
            }
        });
    }


</script>
</body>
