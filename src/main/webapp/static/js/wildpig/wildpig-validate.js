
$.validator.setDefaults({
    //errorElement: "p",
    //errorClass:"text-danger col-sm-3 p-error",
    errorPlacement: function(error, element) {                             //错误信息位置设置方法
    	//error.appendTo( element.parent().parent());                            //这里的element是录入数据的对象
    	if ($(error).text() != '') {
    		$(element).tips({
    			side : 1,
    			msg : $(error).text(),
    			bg : '#FF8C00',
    			time : 3
    		});
    	}
    },
    highlight : function(element) {
        $(element).closest('.form-group').addClass('has-error');
    },
    success : function(label) {
    	// 获取焦点input
    	$(document.activeElement).closest('.form-group').removeClass('has-error');
    	/*label.closest('.form-group').removeClass('has-error');
    	label.remove();*/
    },
    submitHandler: function(form) {
    	var flag =validateForm(3);
    	if(!flag){
    		return false;
		}
        flag =validateHorizentalForm(3);
        if(!flag){
            return false;
        }

    	var ajaxBefore = $(form).data("ajaxBefore");
		var $resultMsg = $(form).find("div").eq(0);
    	if (ajaxBefore) {
    		var result = eval(ajaxBefore + "()");
    		if (!result && !result.status) {
    			dialogMng.msg('submit_ajax', result.msg);
    			return false;
    		}
        }
	    $(form).ajaxSubmit({
	        type: "post",
	        dataType: "json",
	        success: function(responseText,statusText){
				$("#resultMsg").remove();
	            if (responseText.status == "1") {
	                $(form).clearForm();
	                $(form).find(".close[data-dismiss=modal]").click();
	                var ajaxAfter = $(form).data("ajaxAfter");
	                if (ajaxAfter == 'refresh') {
	                	$("table[data-bt-table]").bootstrapTable('refresh');
	                } else if (!ajaxAfter) {
	                	eval(ajaxAfter + "()");
	                } else {
	                	$("#navTab").load(ajaxAfter);
	                	
	                }
	                dialogMng.msg('submit_ajax',responseText.msg || dialog_message_success);
	            } else{
					if($(form).attr("isDialog")=="true"){
						$resultMsg.append("<div id='resultMsg'  align='center' style='color:red;margin-top:-20px;'>"+(responseText.msg || dialog_message_failure)+"!</div>");
					}else{
						dialogMng.msg('submit_ajax',responseText.msg || dialog_message_failure);
					}

	            }
	        },
	        error:function(xhr, ajaxOptions, thrownError){
				if($(form).attr("isDialog")=="true"){
					$resultMsg.append("<div id='resultMsg'  align='center' style='color:red;margin-top:-20px;'>"+(responseText.msg)+"!</div>");
				}else{
					dialogMng.msg('submit_ajax_error',responseText.msg);
				}
	        }
	    });
    }
});
jQuery.extend(jQuery.validator.messages, {
    required: validate_message_required,
    remote: validate_message_remote,
    email: validate_message_email,
    url: validate_message_url,
    date: validate_message_date,
    dateISO: validate_message_dateiso,
    number: validate_message_number,
    digits: validate_message_digits,
    creditcard: validate_message_creditcard,
    equalTo: validate_message_equalto,
    accept: validate_message_accept,
    maxlength: jQuery.validator.format(validate_message_maxlength),
    minlength: jQuery.validator.format(validate_message_minlength),
    rangelength: jQuery.validator.format(validate_message_rangelength),
    range: jQuery.validator.format(validate_message_range),
    max: jQuery.validator.format(validate_message_max),
    min: jQuery.validator.format(validate_message_min)
});

jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    return this.optional(element) || length == 11 && /^1[358]\d{9}$/.test(value)
}, validate_message_ismobile);

jQuery.validator.addMethod("commonString", function(value, element) {       
	return this.optional(element) || /^[a-zA-Z0-9_@]+$/.test(value);       
}, validate_message_commonstring); 

jQuery.validator.addMethod("stringCheck", function(value, element) {       
	return this.optional(element) || /^[a-zA-Z0-9\u4e00-\u9fa5-_@]+$/.test(value);       
}, validate_message_stringcheck);

jQuery.validator.addMethod("stringEnNumCheck", function(value, element) {
	return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
}, validate_message_englishNumber);

jQuery.validator.addMethod("checkString", function(value, element) {       
	return this.optional(element) || /^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value);       
}, validate_message_checkstring);