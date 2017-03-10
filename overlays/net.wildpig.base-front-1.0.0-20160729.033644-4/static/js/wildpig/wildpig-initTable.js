(function(){
	var $btForm = $('form[data-bt-form]');
	var $btTable = $('table[data-bt-table]');
	$btTable.data('queryParams', function(params){
		var ps = {};
		$.each($btForm.serializeArray(), function(){
			ps[this.name] = this.value;
		});
		return $.extend(params, ps);
	})
	$btTable.bootstrapTable();
	$btForm.submit(function(){
		$btTable.bootstrapTable('refresh');
		return false;
	});
})()