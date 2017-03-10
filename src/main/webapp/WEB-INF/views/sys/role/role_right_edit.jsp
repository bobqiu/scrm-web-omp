<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="/WEB-INF/tld/shiro.tld"%>
<form class="form-horizontal" action="role/editRight" method="post" id="roleResEditForm">
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <h4 class="modal-title">编辑权限</h4>
</div>
<div class="modal-body">
    <div class="row ">
        <div class="col-lg-4" style="width: 100%;height: 480px;overflow: auto;">
            <ul id="tree_res" class="ztree"></ul>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" title="取消" class="btn btn-default" data-dismiss="modal"><i class="glyphicon  glyphicon-remove"></i>取消</button>
    <shiro:hasPermission name="role/editRight">
        <button type="button" title="保存" class="btn btn-primary" onclick="submitForm()"><i class="glyphicon  glyphicon-ok"></i>保存</button>
    </shiro:hasPermission>
</div>
</form>
<script type="text/javascript">
	$(document).ready(function(){
		var setting = {
	        view: {
	            dblClickExpand: false,
	            showLine: true,
	            selectedMulti: false
	        },
	        check: {
	                chkboxType: {"Y": "ps", "N": "s"},
	                chkStyle: "checkbox",
	                enable: true,
	                autoCheckTrigger: true
	        },
	        data: {
	            simpleData: {
	                enable:true,
	                idKey: "id",
	                pIdKey: "pId",
	                rootPId: ""
	            }
	        }
	    };
		
		$.post("role/resNodes?roleId=${pd.roleId}", function(zNodes){
			$.fn.zTree.init($("#tree_res"), setting, zNodes);
			//首页不可编辑
			$("#tree_res li").find("a[title='首页']").prev().removeAttr("treenode_check");
		}, "json");
	});
	
	function submitForm(){
		var treeObj = $.fn.zTree.getZTreeObj("tree_res");
		var nodes = treeObj.getCheckedNodes(true);
		var selRes = "";
		for (var i = 0; i < nodes.length; i++) {
			selRes += nodes[i].resFlag + ",";
		}
		if (selRes.length > 1) {
			selRes = selRes.substring(0, selRes.length - 1);
		}
		$.ajax({
			url: "role/editRight",
			type: "post",
			data: {'roleId': ${pd.roleId}, 'selRes': selRes},
			dataType: "json",
			success: function(data){
				$("#roleResEditForm .close").click();
				if (data.status) {
					dialogMng.msg("my_modal_ok", data.msg || "编辑权限成功");
				} else {
					dialogMng.msg("my_modal_fail", data.msg || "编辑权限失败");
				}
			}
		});
	}
</script>