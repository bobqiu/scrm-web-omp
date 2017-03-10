jQuery.extend(jQuery.validator.messages, {
  required: "必选字段",
  remote: "请修正该字段",
  email: "请输入正确格式的电子邮件",
  url: "请输入合法的网址",
  date: "请输入合法的日期",
  dateISO: "请输入合法的日期 (ISO).",
  number: "请输入合法的数字",
  digits: "只能输入整数",
  creditcard: "请输入合法的信用卡号",
  equalTo: "请再次输入相同的值",
  accept: "请输入拥有合法后缀名的字符串",
  maxlength: jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
  minlength: jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
  rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
  range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
  max: jQuery.validator.format("请输入一个最大为{0} 的值"),
  min: jQuery.validator.format("请输入一个最小为{0} 的值")
});
$.validator.setDefaults({
	highlight : function(element) { 
        $(element).closest('.form-group').addClass('has-error'); 
    }, 
    success : function(label) { 
        label.closest('.form-group').removeClass('has-error'); 
        label.remove(); 
    }, 
    errorPlacement : function(error, element) { 
		error.appendTo(element.parent());  
		error.addClass('control-label').css('padding-left','5px'); 
    },
    ignore: "",
});

$.validator.addMethod('earlyThan',function(value, element, param){
	$(param).closest('.form-group').removeClass('has-error');
	$(param).siblings('.control-label').last().remove();
	var startTime = Date.parse(new Date(value.replace(/-/g, "/"))) / 1000;
    var endTime = Date.parse(new Date($(param).val().replace(/-/g, "/"))) / 1000;
    if($(param).val()){
    	return startTime <= endTime;
    }else{
    	return true;
    }
},$.validator.format("开始时间必须小于结束时间"));
$.validator.addMethod("latterThan",function(value, element, param){
	$(param).closest('.form-group').removeClass('has-error');
	$(param).siblings('.control-label').last().remove();
	var startTime = Date.parse(new Date($(param).val().replace(/-/g, "/"))) / 1000;
    var endTime = Date.parse(new Date(value.replace(/-/g, "/"))) / 1000;
    if($(param).val()){
    	return startTime <= endTime;
    }else{
    	return true;
    }
},$.validator.format("结束时间必须大于开始时间"));
$.validator.addMethod('maxSelected',function(value,element,param){
    if (value != null && value.length > param) {
        return false;
    }
    return true;
}, $.validator.format("选择项不得超过{0}个"));
$.validator.addMethod('notDefaultValue',function(value,element,param){
    return element.value != param;
}, $.validator.format("必选字段"));
$.validator.addMethod('imageRequired',function(value,element,params){
    var cover = $(params).attr("data-src");
    if(cover === undefined || $.trim(cover) == "") {
        return false;
    }
    return true;
},$.validator.format("图片必须上传"));
$.validator.addMethod('originalRequired',function(value,element,params){
   var cover = $(params).find("input");
    if(cover.length==0) {
        return false;
    }
    return true;
},$.validator.format("封面图必须上传"));
$.validator.addMethod('sliderRequired',function(value,element,params){
    var cover = $(params).find("input");
    if(cover.length<5||cover.length>8) {
        return false;
    }
    return true;
},$.validator.format("轮播图为5~8张图"));
$.validator.addMethod('detailRequired',function(value,element,params){
    var cover = $(params).find("div>input");
    if(cover.length==0) {
        return false;
    }
    return true;
},$.validator.format("请上传详情图"));
$.validator.addMethod('originRequired',function(value,element,params){
		var prvince = $(params+" .dropbtn").data("namep");
		var prvinceId = $(params+" .dropbtn").data("codep");
		var city = $(params+" .dropbtn").data("namec");
		var cityId = $(params+" .dropbtn").data("codec");
		var $area = $(params+" .dropbtn").data("namea");
		var areaId = $(params+" .dropbtn").data("codea");
		if(!prvinceId||!cityId){
			return false;
		}
	return true;
},$.validator.format("请选择省/市"));
$.validator.addMethod('origins',function(value,element,params){
		var prvince = $(params+" .dropbtn").data("namep");
		var prvinceId = $(params+" .dropbtn").data("codep");
		var city = $(params+" .dropbtn").data("namec");
		var cityId = $(params+" .dropbtn").data("codec");
		var $area = $(params+" .dropbtn").data("namea");
		var areaId = $(params+" .dropbtn").data("codea");
		if(!prvinceId||!cityId||!areaId){
			return false;
		}
	return true;
},$.validator.format("请选择省/市/区"));
$.validator.addMethod('product',function(value,element,params){
		 var cover = $(params).find("input");
    if(cover.length==0) {
        return false;
    }
    return true;
},$.validator.format("请选择地区"));
$.validator.addMethod('price',function(value,element,params){
		 	var moneyRegx = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	   	if(!moneyRegx.test(value)) {
	        return false;
	    }
	    return true;
},$.validator.format("请输入正确的价格"));
$.validator.addMethod('suggestedRetailPrice',function(value,element,params){
		 	var price = $(element).parents(".main-arry").find("input[data-val='price']").val();
		 	if(price){
		 		 	price = parseFloat(price);
			 		value = parseFloat(value);
			   	if(value<price) {
			        return false;
			    }
		 	}  
	    return true;
},$.validator.format("零售价不能小于供应价"));
$.validator.addMethod('weight',function(value,element,params){
		 	var weight = /^\d+(\.\d+)?$/g;
	   	if(!weight.test(value)) {
	        return false;
	    }
	    return true;
},$.validator.format("请输入正确的重量单位KG"));
$.validator.addMethod('capacity',function(value,element,params){
			if(value){
					var moneyRegx = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
			   	if(!moneyRegx.test(value)) {
			        return false;
			    }
			}
	    return true;
},$.validator.format("只能输入数字"));
$.validator.addMethod('selectorRequired',function(value,element,params){
		var id = $(params[0]+" option:selected").attr("data-id");
		if(!id){
			return false;
		}
	return true;
},$.validator.format("请选择{1}"));
$.validator.addMethod('Tel',function(value,element,params){
		if(value.length!=11){
			return false;
		}
	return true;
},$.validator.format("请输入正确的手机号码"));