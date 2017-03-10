/**
 * Created by fangyh on 2015/4/20.
 */
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

$(function(){
	$("body").delegate("*[data-target='navTab']","click",function(){
	
		console.log("============navTab"); 
		var url = $(this).attr("href") || $(this).data("url");
		$("#navTab").load(url);
		return false;
	});
	
    $("body").delegate("*[data-model='dialog']","click",function(){
        var targetid = $(this).data("targetid");
        var method = $(this).data("method") || "GET";
        var url = $(this).data("url");
        if(!url){
        	url = $(this).attr("href");
        }
        window.top.dialogMng.dialog(targetid,url,{'method':method});
        return true;
    });
    
    $("body").delegate("*[data-model='ajaxToDo']","click",function(){
    	// 改操作分单条/批量数据操作
        var targetid = $(this).data("targetid");
        console.log("targetid:"+targetid);
        var url = $(this).data("url");
        var msg = $(this).data("msg");
        var $btTable = $("table[data-bt-table]");
        var ids = "";
        var batchAction = $(this).data("batchAction");
        if (batchAction == true) {
        	ids = getCheckboxItem($btTable);
        	if (!ids || ids == "") {
    			dialogMng.msg(targetid+"nodata", dialog_message_selectdata);
    			return false;
    		}
        }
        dialogMng.comfirm({
        	targetid: targetid,
        	msg: msg,
        	callback: function(){
        		$.ajax({
                    url: url,
                    data: {"ids" : ids},
                    dataType: 'json',
                    success: function(data){
                    	$('#'+targetid).modal("hide");
                        if(data.status) {
                            if ($btTable.length > 0) {
                            	$btTable.bootstrapTable('refresh');
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
        });
    });
})
