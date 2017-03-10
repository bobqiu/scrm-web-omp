<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>branch-list</title>
    <style>
        .error{
            color: red;
        }
        .form-horizontal .float-right{
            text-align: right;
        }
    </style>
</head>
<body>

<div class="row">
    <ol class="breadcrumb" style="border-bottom: 1px solid #0088cc;">
        <li><a href="javascript: void(0)" data-target="navTab">连锁机构</a></li>
        <li><a href="javascript: void(0)">新增机构</a></li>
    </ol>
</div>

<div class="row">
<form action="" class="form-horizontal" id="orgForm" style="padding-left: 150px;">
    <div class="form-group">
        <label  class="col-md-2 control-label float-right">机构全称<span class="error">*</span></label>
        <div class="col-md-6">
            <input type="text" class="form-control" id="orgFullName" placeholder="1-50位汉字、英文、数字、符号及组合">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-md-2 control-label float-right">机构简称<span class="error">*</span></label>
        <div class="col-md-6">
            <input type="text" class="form-control" id="orgName" placeholder="1-15位汉字、英文、数字、符号及组合">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-md-2 control-label float-right">营业执照<span class="error">*</span></label>
        <div class="col-md-6">
            <input type="text" class="form-control" id="businessLicense" placeholder="20位英文、数字、符号及组合">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-md-2 control-label float-right">地址<span class="error">*</span></label>
        <div class="col-md-2">
            <select class="form-control" id="provinceSelect">
                <option value="">请选择</option>
            </select>
        </div>
        <div class="col-md-2">
            <select class="form-control" id="citySelect">
                <option value="">请选择</option>
            </select>
        </div>
        <div class="col-md-2">
            <select class="form-control" id="areaSelect">
                <option value="">请选择</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label  class="col-md-2 control-label float-right"></label>
        <div class="col-md-6">
            <textarea id="address" class="form-control" name="" cols="30" rows="6"></textarea>
        </div>
    </div>
    <div class="form-group">
        <label  class="col-md-2 control-label float-right">所属行业<span class="error">*</span></label>
        <div class="col-md-3">
            <select class="form-control" id="firstIndustrySelect">
                <option value="">选择一级行业</option>
                <c:forEach items="${industries}" var="item">
                    <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-3">
            <select class="form-control" id="secondIndustrySelect">
                <option value="">选择二级行业</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label  class="col-md-2 control-label float-right">机构logo<span class="error">*</span></label>
        <div class="col-md-6">
            <input type="file">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-md-2 control-label float-right"></label>
        <div class="col-md-6">
            <img src="" alt="">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-md-2 control-label float-right">到期时间<span class="error">*</span></label>
        <div class="col-md-3">
            <div class="input-group date form_datetime">
                <input type="text" id="expireTime" class="form-control" readonly="readonly" placeholder="请选择">
                <span class="input-group-addon">
                      <span class="glyphicon glyphicon-th"></span>
                </span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label  class="col-md-2 control-label float-right"></label>
        <div class="col-md-1">
            <button type="button" class="btn btn-cancel ">取消</button>
        </div>
        <div class="col-md-1">
            <button type="submit" class="btn btn-primary">保存</button>
        </div>
    </div>
</form>
</div>





<script type="text/javascript" src="<%=basePath %>static/js/util.js"></script>
<script>
$(function(){
    $(".form_datetime").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        startDate: new Date(),
        autoclose: true,
        todayBtn: true,
        clearBtn: true,
        minView:2,
    });

    $.post("/bussiness/address/getProvinces").success(function (res) {
        var str = '<option value="">请选择</option>';
        res.provinces && res.provinces.forEach(function(item,i){
            str += '<option value="'+item.id+'">'+item.name+'</option>';
        })
        $('#provinceSelect').html(str);
    })

    var loadCity = (function () {
        var cityMap = {};
        return function (provinceId,callback) {
            if(cityMap[provinceId]){
                callback(cityMap[provinceId]);
            }else{
                $.post("/bussiness/address/getCitys",{provinceId:provinceId}).success(function (res) {
                    if(res.citys){
                        cityMap[provinceId] = res.citys;
                        callback(cityMap[provinceId]);
                    }else{
                        callback([]);
                    }
                })
            }
                
        }
    })();

    var loadArea = (function () {
        var areaMap = {};
        return function (cityId,callback) {
            if(areaMap[cityId]){
                callback(areaMap[cityId]);
            }else{
                $.post("/bussiness/address/getAreas",{cityId:cityId}).success(function (res) {
                    if(res.areas){
                        areaMap[cityId] = res.areas;
                        callback(areaMap[cityId]);
                    }else{
                        callback([]);
                    }
                })
            }
                
        }
    })();  

    $('#provinceSelect').change(function () {
        $("#citySelect").html('<option value="">请选择市</option>');
        $("#areaSelect").html('<option value="">请选择区</option>');
        if($('#provinceSelect').val()){
            loadCity($('#provinceSelect').val(),function (citys) {
                var str = '<option value="">请选择市</option>';
                citys.forEach(function(item,i){
                    str += '<option value="'+item.id+'">'+item.name+'</option>';
                })
                $('#citySelect').html(str);
            })
        }
    })

    $('#citySelect').change(function () {
        $("#areaSelect").html('<option value="">请选择区</option>');
        if($('#citySelect').val()){
            loadArea($('#citySelect').val(),function (areas) {
                var str = '<option value="">请选择市</option>';
                areas.forEach(function(item,i){
                    str += '<option value="'+item.id+'">'+item.name+'</option>';
                })
                $('#areaSelect').html(str);
            })
        }
    })


    $('#firstIndustrySelect').change(function () {
        var firstIndustryId = $('#firstIndustrySelect').val();
        if(firstIndustryId){
            $('#secondIndustrySelect').html("<option value=''>选择二级行业</option>");
            $.post(basePath + "shopBasic/industry/getIndustryByParentId",{parentId:firstIndustryId}).success(function (res) {
                var str = "";
                res.industryList.forEach(function(item,i){
                    str += '<option value="'+item.id+'">'+ item.name +'</option>'
                })
                $('#secondIndustrySelect').append(str);
            })
        }
    })


    $('#orgForm').submit(function (e) {
        e.preventDefault();
        var data = {
            orgFullName:$("#orgFullName").val(),
            orgName:$('#orgName').val(),
            businessLicense:$('#businessLicense').val(),
            provinceId:$('#provinceSelect').val(),
            provinceName:$('#provinceSelect :selected').text(),
            cityId:$('#citySelect').val(),
            cityName:$('#citySelect :selected').text(),
            areaId:$('#areaSelect').val(),
            areaName:$('#areaSelect :selected').text(),
            address:$('#address').val(),
            firstIndustryId:$("#firstIndustrySelect").val(),
            secondIndustryId:$('#secondIndustrySelect').val(),
            logo:'',
            thumLogo:'',
            expireTime:new Date($('#expireTime').val()).getTime()
        }
        $.ajax({
            type:'post',
            contentType:'application/json',
            url:basePath + 'org/chainOrgUpdate',
            data:JSON.stringify(data),
            success:function (res) {
                $("#navTab").load('org/chainOrg');
                console.log(res)
            }
        })
    })
})
</script>
</body>
</html>