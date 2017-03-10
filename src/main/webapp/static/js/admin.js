
dialogMng = {
    htmlContent:{
        outHtml:"<div class='modal fade' id='#dataid'><div class='modal-dialog'><div class='modal-content'>#datacontent</div></div></div>",
        contentHtml:"<div class='modal-header'><button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button><h4 class='modal-title'>#datatitle</h4></div><div class='modal-body'>#databody</div><div class='modal-footer'>#databutton</div>",
        closeButtonHtml:"<button type='button' class='btn btn-default' data-dismiss='modal'>" + button_close + "</button>",
        submitButtonHtml:"<button type='button' data-type='comfirm' class='btn btn-primary'>" + button_submit + "</button>"
    },
    dialogHtml:function(targetId){
            return "<div class=\"modal fade\" id=\""+targetId+"\">"
                +"<div class=\"modal-dialog\">"
                +"<div class=\"modal-content\"></div>"
                +"</div></div>"
    },
    basedialog:function(targetid,url,data){
        var html = this.dialogHtml(targetid);
        $("body").append(html)
        $.ajax({
        	'type':data.method || "GET",
        	'url':url,
        	'data':data,
        	'async':false,
        	'success':function(data,status){
        		if(status!='success'){
            		$('#'+targetid).modal('hide')
            		$('#'+targetid).remove();
            		$("body").removeAttr("style")
            		dialogMng.msg("dialogMsg", dialog_message_nofund);
            		return;
            	}else{
            		$("#"+targetid).find(".modal-content").html(data);
            		 $('#'+targetid).modal({
            	            keyboard:false
            	            ,backdrop :'static'
            	        })
            	}
        	},
        	'error':function(){
        		$('#'+targetid).modal('hide')
        		$('#'+targetid).remove();
        		$("body").removeAttr("style")
        		dialogMng.msg("dialogMsg", dialog_message_nofund);
        		return;
        	}
        });
//        $("#"+targetid).find(".modal-content").load(url,data,function(response,status){
//        	if(status!='success'){
//        		$('#'+targetid).modal('hide')
//        		$('#'+targetid).remove();
//        		$("body").removeAttr("style")
//        		dialogMng.msg("dialogMsg", dialog_message_nofund);
//        		return;
//        	}else{
//        		 $('#'+targetid).modal({
//        	            keyboard:false
//        	            ,backdrop :'static'
//        	        })
//        	}
//        });
//        $('#'+targetid).modal({
//            keyboard:false
//            ,backdrop :'static'
//        })
        $('#'+targetid).on('hide.bs.modal', function () {
            $(this).remove();
        })
        $("body").removeAttr("style")
    },
    basecomfirm: function (obj) {
        var contentHtml = this.htmlContent.contentHtml
        var datatitle = dialog_message_datatitle;
        var databody = obj.msg;
        var databutton = this.htmlContent.closeButtonHtml+this.htmlContent.submitButtonHtml;
        var htmlText = this.htmlContent.outHtml.replace("#dataid",obj.targetid);
        htmlText = htmlText.replace("#datacontent",contentHtml)
        htmlText = htmlText.replace("#datatitle",datatitle)
        htmlText = htmlText.replace("#databody",databody)
        htmlText = htmlText.replace("#databutton",databutton)
        $("body").append(htmlText)
        $('#'+obj.targetid).modal({
            keyboard:false
            ,backdrop :'static'
        })
        $('#'+obj.targetid).on('hide.bs.modal', function () {
            $(this).remove();
        });
        $('#'+obj.targetid).find("button[data-type='comfirm']").click(function(){
            if(obj.callback)
            {
                obj.callback();
            }
        });
        $("body").removeAttr("style")
    },
    basemsg:function(targetid,msg){
        var contentHtml = this.htmlContent.contentHtml
        var datatitle = dialog_message_datatitle;
        var databody = msg;
        var databutton = this.htmlContent.closeButtonHtml;
        var htmlText = this.htmlContent.outHtml.replace("#dataid",targetid);
        htmlText = htmlText.replace("#datacontent",contentHtml)
        htmlText = htmlText.replace("#datatitle",datatitle)
        htmlText = htmlText.replace("#databody",databody)
        htmlText = htmlText.replace("#databutton",databutton)
        $("body").append(htmlText)
        $('#'+targetid).modal({
            keyboard:false
            ,backdrop :'static'
        })
        $('#'+targetid).on('hide.bs.modal', function () {
           $(this).remove();
        })
        $("body").removeAttr("style")
    },
    dialog:function(targetid,url,data){
    	if(window.top!=window){
    		window.top.dialogMng.basedialog(targetid,url,data);
    	}else{
    		dialogMng.basedialog(targetid,url,data);
    	}
    },
    comfirm:function(obj){
    	window.top.dialogMng.basecomfirm(obj);
    },
    msg:function(targetid,msg){
    	window.top.dialogMng.basemsg(targetid,msg);
    }
}

format={
		formatDate:function(longdate){
			 var d = new Date(longdate);
			 var fullYear = d.getFullYear();
			 var fullMonth = (d.getMonth()+1)>9?(d.getMonth()+1)+'':'0'+(d.getMonth()+1);
			 var fullDate = d.getDate()>9?d.getDate()+'':'0'+d.getDate();
			 var fullHour = d.getHours()>9?d.getHours()+'':'0'+d.getHours();
			 var fullMinutes = d.getMinutes()>9?d.getMinutes()+'':'0'+d.getMinutes();
			 var fullSeconds = d.getSeconds()>9?d.getSeconds()+'':'0'+d.getSeconds();
	         var formatdate= fullYear+"-"+fullMonth+"-"+fullDate+" "+fullHour+":"+fullMinutes+":"+fullSeconds;
	          return formatdate;
		}
}
$(function(){
	$("body").delegate("a[data-target='navTab']","click",function(){
		var url = $(this).attr("href");
		var md = $(this).attr("md");
		if(md=='ajax'){
			
		}else{
			$("#navTab").load(url);
		}
		return false;
	});
	$("body").delegate("*[data-form-id]","click",function(){
		var formId = $(this).data("formId");
		var form = document.getElementById(formId);
		var url = form.action;
		var data = $(form).serialize();
		$("#navTab").load(url, data);
		return false;
	});
    $("body").delegate("*[data-model='dialog']","click",function(){
        var targetid = $(this).data("targetid");
        var method = $(this).data("method") || "GET";
        var url = $(this).data("url");
        if(!url){
        	url = $(this).attr("href")
        }
        window.top.dialogMng.dialog(targetid,url,{'method':method});
        return true;
    });
    $("body").delegate("*[data-model='ajaxToDo']","click",function(){
    	// 改操作分单条/批量数据操作
        var targetid = $(this).data("targetid");
        var url = $(this).data("url");
        var msg = $(this).data("msg");
        var formBtn = $(this).data("formBtn");// from表单ID，重刷新table
        var checkboxName = $(this).data("checkboxName"); // 多选框NAME，批量操作时才会有值
        
        var ids = "";
        if (null != checkboxName && checkboxName != 'undefined') {
        	// 批量操作
        	ids = getCheckboxItem(checkboxName);
    		if(ids == ""){
    			dialogMng.msg(targetid+"nodata", dialog_message_selectdata);
    			return false;
    		}
        } else {
        	// 单条数据操作
        }
        
        var obj = {
            targetid:targetid,
            msg:msg
        }
        obj.callback=function(){
            $.ajax({
                url:url,
                data : {
                	"ids" : ids
                },
                dataType:'json',
                success:function(data){
                	$('#'+targetid).modal("hide");
                    if(data.status) {
                        if (null != formBtn && formBtn != 'undefined') {
	                    	$("#"+formBtn).click();
	                    }
                        dialogMng.msg(targetid+"ok",data.msg || dialog_message_success);
                    }else{
                        dialogMng.msg(targetid+"fail",data.msg || dialog_message_failure);
                    }
                },
                error:function(){
                    $('#'+targetid).modal("hide");
                    dialogMng.msg(targetid+"fail", dialog_message_failure);
                }
            });
        }
        dialogMng.comfirm(obj);
    });
    $("body").delegate("input:checkbox[data-aim='reselect']","change",function(){
    	var name = $(this).data("name");
    	ReSel(name);
		return true;
	});
})

$.fn.gvTables = function(obj){
    $(this).dataTable( {
        "bLengthChange": false,
        "iDisplayLength": 10,
        "bFilter": false,
        "bSort": false,
        "scrollY": "360px",
         "bServerSide": true,
        "scrollCollapse": "true",
        "bProcessing": true,//进度
        "pagingType":   "full_numbers",
        "createdRow": function ( row, data, index ) {
            $('td', row).eq(4).css('font-weight',"bold").css("color","red");
        },
        /*"columns": [
            { "data": "name", "sWidth": "10%" },
            { "data": "age" },
            { "data": "salary", "sWidth": "20%" },
            { "data": "tel" },
            { "data": "desc" },
            { "data": "id"}
        ],
        "columnDefs": [{
            "render": function(data, type, row) {
                return '<a href="#"><i title="详情" class="glyphicon glyphicon-zoom-in"></i></a><a href="'+row['id']+'"><i title="修改" class="glyphicon glyphicon-pencil"></i></a><a href="#"><i title="删除" class="glyphicon glyphicon-trash"></i></a>';
            },
            "targets": 5
        }],*/
        "sAjaxSource": obj.url+"?" + new Date().getTime(),
        "fnServerData": function (sSource, aDataSet, fnCallback) {
            $.ajax({
                "dataType": 'json',
                "type": "POST",
                "url": sSource,
                "data": aDataSet,
                "success": fnCallback
            });
        }/*,
        "oLanguage": {
            "sProcessing": datatable_processing,
            "sLengthMenu": datatable_lengthmenu,
            "sZeroRecords": datatable_zerorecords,
            "sEmptyTable": datatable_emptytable,
            "sInfo": datatable_info,
            "sInfoEmpty": datatable_infoempty,
            "sInfoFiltered": datatable_infofiltered,
            "sSearch": datatable_search,
            "oPaginate": {
                "sFirst": datatable_first,
                "sPrevious": datatable_previous,
                "sNext": datatable_next,
                "sLast": datatable_last
            }
        }*/
    } );
}