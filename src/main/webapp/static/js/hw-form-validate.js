/**
 * Created by huangwei on 2016/12/20.
 * isnull:不能为空
 * checkEsAndZhAndNum_1_50:1-50位英文、数字、汉字及组合
 **/

    function validateHorizentalForm(status,obj){
        if(status<3){
            var thisVal = $(obj).val().trim();
            var checknull = $(obj).attr("checknull");
            var checkEsAndZhAndNum_1_50 = $(obj).attr("checkEsAndZhAndNum_1_50");
            if(checknull){
                if(status<=1){
                    $(obj).next().html("&nbsp;");
                    $(obj).next().css("visibility","hidden");
                    if(thisVal==null || ""==thisVal){
                        $(obj).next().css("visibility","visible");
                        $(obj).next().html(checknull);
                        return false;
                    }
                }else{
                    $(obj).next().css("visibility","hidden");
                    $(obj).next().html("&nbsp;");
                }

            }

            if(checkEsAndZhAndNum_1_50){
                var  regex =  /^[\u4e00-\u9fa50-9A-Za-z,\.!:""<>\(\)，。！：、《》（）“”]{1,50}$/;
                if(status<=1){
                    $(obj).next().html("&nbsp;");
                    $(obj).next().css("visibility","hidden");
                    if(!regex.test(thisVal)){
                        $(obj).next().css("visibility","visible");
                        $(obj).next().html(checkEsAndZhAndNum_1_50);
                        return false;
                    }
                }else{
                    $(obj).next().css("visibility","hidden");
                    $(obj).next().html("&nbsp;");
                }
            }
            return true;
        }else{
            var flag = true;
            $(".isValidata").each(function () {
                var isNext =true;
                var thisVal = $(this).val().trim();
                if(!$(this).prop("disabled")){
                    var checknull = $(this).attr("checknull");
                    var checkEsAndZhAndNum_1_50 = $(this).attr("checkEsAndZhAndNum_1_50");
                    if(checknull){
                        if(thisVal==null || ""==thisVal){
                            $(this).next().css("visibility","visible");
                            $(this).next().html(checknull);
                            flag = false;
                            isNext =false;
                        }else{
                            $(this).next().html("&nbsp;");
                            $(this).next().css("visibility","hidden");
                        }
                    }
                    
                    if(isNext && checkEsAndZhAndNum_1_50){
                        var  regex =  /^[\u4e00-\u9fa50-9A-Za-z,\.!:""<>\(\)，。！：、《》（）“”]{1,50}$/
                        if(!regex.test(thisVal)){
                            $(this).next().css("visibility","visible");
                            $(this).next().html(checkEsAndZhAndNum_1_50);
                            flag = false;
                            isNext = false;
                        }else{
                            $(this).next().html("&nbsp;");
                            $(this).next().css("visibility","hidden");
                        }
                    }
                }

            })
            return flag;
        }
    }
