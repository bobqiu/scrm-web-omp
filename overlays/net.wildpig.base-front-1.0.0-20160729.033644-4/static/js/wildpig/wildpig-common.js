DateTools = {
	formatDate : function(longdate) {
		if (!longdate) {
			return "";
		}
		var d = new Date(longdate);
		var fullYear = d.getFullYear();
		var fullMonth = (d.getMonth() >= 9 ? '' : '0') + (d.getMonth() + 1);
		var fullDate = (d.getDate() > 9 ? '' : '0') + d.getDate();
		var fullHour = (d.getHours() > 9 ? '' : '0') + d.getHours();
		var fullMinutes = (d.getMinutes() > 9 ? '' : '0') + d.getMinutes();
		var fullSeconds = (d.getSeconds() > 9 ? '' : '0') + d.getSeconds();
		var formatdate = fullYear + "-" + fullMonth + "-" + fullDate + " " + fullHour + ":" + fullMinutes + ":" + fullSeconds;
		return formatdate;
	}
}

//获得选中项
function getCheckboxItem($btTable) {
	var allSel = [];
	if ($btTable.length > 0) {
		var selections = $btTable.bootstrapTable('getSelections');
		var uniqueId = $btTable.data("uniqueId") || "id";
		$.each(selections, function(){
			allSel.push(this[uniqueId]);
		});
	}
	return allSel.join(',');
}
