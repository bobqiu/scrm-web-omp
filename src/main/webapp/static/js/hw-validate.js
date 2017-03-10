/**
 * Created by huangwei on 2016/12/20.
 * isnull:不能为空
 * isEsAndNum_6_12:6-12位数字、英文及组合
 * isEsAndZhAndNum_1_6:1-6位英文、数字、汉字及组合
 * isEsAndZhAndNum_1_8:1-8位英文、数字、汉字及组合
 * isEsAndNum_1_20:1-20位数字、英文及组合
 * isZh_2_10:2-10位汉字
 * isPhone:手机号码格式是否正确
 * equalID:根据ID查找文本比较俩个值是否相等
 * isKeysorkds_1_15:判断输入的关键字大于了15了
 */

    function validateForm(status,obj){
        var isKeysorkds_1_15_validte_function=function (isKeysorkds_1_15,num) {
            if(num > 15){
                $("#isKeysorkds_1_15_validte_function").remove();
                $(this).parent().parent().after("<span id='isKeysorkds_1_15_validte_function' style='color:red;padding-left:10px;padding-top:7px;float:left;'>" + isKeysorkds_1_15 + "</span>");
                return false;
            }
            else if(num == 0){
                $("#isKeysorkds_1_15_validte_function").remove();
                $(this).parent().parent().after("<span id='isKeysorkds_1_15_validte_function' style='color:red;padding-left:10px;padding-top:7px;float:left;'>" + "关键字至少要有一个" + "</span>");
                return false;
            }
            else {
                $("#isKeysorkds_1_15_validte_function").remove();
                return true;
            }
            return true;
        }

        if(status<3){
            var thisVal = $(obj).val().trim();
            var isnull = $(obj).attr("isnull");
            var isEsAndZhAndNum_1_6 = $(obj).attr("isEsAndZhAndNum_1_6");
            var isEsAndZhAndNum_1_8 = $(obj).attr("isEsAndZhAndNum_1_8");
            var isEsAndNum_1_20 = $(obj).attr("isEsAndNum_1_20");
            var isZh_2_10 = $(obj).attr("isZh_2_10");
            var isPhone = $(obj).attr("isPhone");
            var isEsAndNum_6_12 = $(obj).attr("isEsAndNum_6_12");
            var equalID = $(obj).attr("equalID");
            var isKeysorkds_1_15 = $(this).attr("isKeysorkds_1_15");
            if(isnull){
                if(status<=1){
                    $(obj).next().remove();
                    if(thisVal==null || ""==thisVal){
                        $(obj).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>"+isnull+"</span>");
                        return false;
                    }
                }else{
                    $(obj).next().remove();
                }

            }
            if(isEsAndZhAndNum_1_6){
                var  regex =  /^[\u4e00-\u9fa50-9A-Za-z]{1,6}$/;
                if(status<=1){
                    $(obj).next().remove();
                    if(!regex.test(thisVal)){
                        $(obj).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>"+isEsAndZhAndNum_1_6+"</span>");
                        return false;
                    }
                }else{
                    $(obj).next().remove();
                }
            }

            if(isEsAndZhAndNum_1_8){
                var  regex =  /^[\u4e00-\u9fa50-9A-Za-z]{1,8}$/;
                if(status<=1){
                    $(obj).next().remove();
                    if(!regex.test(thisVal)){
                        $(obj).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>"+isEsAndZhAndNum_1_8+"</span>");
                        return false;
                    }
                }else{
                    $(obj).next().remove();
                }
            }

            if(isZh_2_10){
                var  regex =  /^[\u4e00-\u9fa5]{2,10}$/;
                if(status<=1){
                    $(obj).next().remove();
                    if(!regex.test(thisVal)){
                        $(obj).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>"+isZh_2_10+"</span>");
                        return false;
                    }
                }else{
                    $(obj).next().remove();
                }
            }

            if(isPhone){
                var  regex =  /^1[358]\d{9}$/;
                if(status<=1){
                    $(obj).next().remove();
                    if(!regex.test(thisVal) || thisVal.length!=11){
                        $(obj).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>"+isPhone+"</span>");
                        return false;
                    }
                }else{
                    $(obj).next().remove();
                }
            }


            if(isEsAndNum_1_20){
                var  regex =  /^[A-Za-z0-9]{1,20}$/;
                if(status<=1){
                    $(obj).next().remove();
                    if(!regex.test(thisVal)){
                        $(obj).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>"+isEsAndNum_1_20+"</span>");
                        return false;
                    }
                }else{
                    $(obj).next().remove();
                }
            }

            if(isEsAndNum_6_12){
                var  regex =  /^[A-Za-z0-9]{6,12}$/;
                if(status<=1){
                    $(obj).next().remove();
                    if(thisVal !='' && !regex.test(thisVal)){
                        $(obj).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>"+isEsAndNum_6_12+"</span>");
                        return false;
                    }
                }else{
                    $(obj).next().remove();
                }
            }

            if(equalID){
                var  beforeVal =  $(equalID).val();
                if(status<=1){
                    $(obj).next().remove();
                    if(thisVal!=beforeVal){
                        $(obj).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>"+$(obj).attr("equalMsg")+"</span>");
                        return false;
                    }
                }else{
                    $(obj).next().remove();
                }
            }
            if (isKeysorkds_1_15){
                if (!isKeysorkds_1_15_validte_function.call(this,isKeysorkds_1_15,$(".tag").length) ){
                    return false;
                }
            }
            return true;
        }else{
            var flag = true;
            $(".isValidata").each(function () {
                var isNext =true;
                var thisVal = $(this).val().trim();
                if(!$(this).prop("disabled")) {
                    var isnull = $(this).attr("isnull");
                    var isEsAndZhAndNum_1_6 = $(this).attr("isEsAndZhAndNum_1_6");
                    var isEsAndZhAndNum_1_8 = $(this).attr("isEsAndZhAndNum_1_8");
                    var isEsAndNum_1_20 = $(this).attr("isEsAndNum_1_20");
                    var isEsAndNum_6_12 = $(this).attr("isEsAndNum_6_12");
                    var isZh_2_10 = $(this).attr("isZh_2_10");
                    var isPhone = $(this).attr("isPhone")
                    var equalID = $(this).attr("equalID");
                    var isKeysorkds_1_15 = $(this).attr("isKeysorkds_1_15");
                    if (isnull) {
                        if (thisVal == null || "" == thisVal) {
                            $(this).next().remove();
                            $(this).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>" + isnull + "</span>");
                            flag = false;
                            isNext = false;
                        } else {
                            $(this).next().remove();
                        }
                    }

                    if (isNext && isEsAndZhAndNum_1_6) {
                        var regex = /^[\u4e00-\u9fa50-9A-Za-z]{1,6}$/
                        if (!regex.test(thisVal)) {
                            $(this).next().remove();
                            $(this).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>" + isEsAndZhAndNum_1_6 + "</span>");
                            flag = false;
                            isNext = false;
                        } else {
                            $(this).next().remove();
                        }
                    }

                    if (isNext && isEsAndZhAndNum_1_8) {
                        var regex = /^[\u4e00-\u9fa50-9A-Za-z]{1,8}$/
                        if (!regex.test(thisVal)) {
                            $(this).next().remove();
                            $(this).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>" + isEsAndZhAndNum_1_8 + "</span>");
                            flag = false;
                            isNext = false;
                        } else {
                            $(this).next().remove();
                        }
                    }

                    if (isNext && isZh_2_10) {
                        var regex = /^[\u4e00-\u9fa5]{2,10}$/
                        if (!regex.test(thisVal)) {
                            $(this).next().remove();
                            $(this).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>" + isZh_2_10 + "</span>");
                            flag = false;
                            isNext = false;
                        } else {
                            $(this).next().remove();
                        }
                    }

                    if (isNext && isPhone) {
                        var regex = /^1[358]\d{9}$/
                        if (!regex.test(thisVal) || thisVal.length != 11) {
                            $(this).next().remove();
                            $(this).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>" + isPhone + "</span>");
                            flag = false;
                            isNext = false;
                        } else {
                            $(this).next().remove();
                        }
                    }


                    if (isNext && isEsAndNum_1_20) {
                        var regex = /^[A-Za-z0-9]{1,20}$/
                        if (!regex.test(thisVal)) {
                            $(this).next().remove();
                            $(this).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>" + isEsAndNum_1_20 + "</span>");
                            flag = false;
                            isNext = false;
                        } else {
                            $(this).next().remove();
                        }
                    }

                    if (isNext && isEsAndNum_6_12) {
                        var regex = /^[A-Za-z0-9]{6,12}$/
                        if (thisVal != '' && !regex.test(thisVal)) {
                            $(this).next().remove();
                            $(this).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>" + isEsAndNum_6_12 + "</span>");
                            flag = false;
                            isNext = false;
                        } else {
                            $(this).next().remove();
                        }
                    }


                    if (isNext && equalID) {
                        var beforeVal = $(equalID).val();
                        if (thisVal != beforeVal) {
                            $(this).next().remove();
                            $(this).after("<span style='color:red;padding-left:10px;padding-top:7px;float:left;'>" + $(this).attr("equalMsg") + "</span>");
                            flag = false;
                            isNext = false;
                        } else {
                            $(this).next().remove();
                        }
                    }

                    if (isNext && isKeysorkds_1_15 && !isKeysorkds_1_15_validte_function.call(this, isKeysorkds_1_15, thisVal == "" ? 0 : thisVal.split(",").length)) {
                        flag = false;
                        isNext = false;
                    }
                }
            })
            return flag;
        }
    }
    function focusValidata() {
        $(".focusValidata").each(function () {
            var thisObj = this;
            $(this).blur(function(){
                validateForm.call(this,1,thisObj);
            });
            $(this).focus(function () {
                validateForm.call(this,2,thisObj);
            });
        });
    }

