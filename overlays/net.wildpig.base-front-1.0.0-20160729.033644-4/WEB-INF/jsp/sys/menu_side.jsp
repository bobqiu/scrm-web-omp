<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="panel-group" role="tablist" id="accordion">
	<c:forEach items="${menuList}" var="menu" varStatus="status">
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="heading">
				<h4 class="panel-title" data-toggle="collapse" data-parent="#accordion" 
				 data-target="#mainmenu${status.index }"  style="cursor:pointer;">
					<a href="javascript:">
						<i class="glyphicon glyphicon-cog"></i>${menu.menuName }
						<i class="glyphicon glyphicon-triangle-left pull-right"></i>
					</a>
				</h4>
			</div>
			<div id="mainmenu${status.index }" class="panel-collapse collapse">
				<ul class="list-group submenu">
					<c:forEach items="${menu.subMenu}" var="sub">
								<li class="list-group-item">
					<a href="${sub.menuUrl != null ? sub.menuUrl : 'javascript:'}" data-target="navTab">
						<i class="glyphicon glyphicon-triangle-right"></i>
						${sub.menuName }
					</a>
				</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	</c:forEach>
</div>
<script type="text/javascript">
(function(){
	$('div[id^=mainmenu]').on('show.bs.collapse', function(){
		$('[data-target="#' + this.id + '"] i:last-child').removeClass('glyphicon-triangle-left').addClass('glyphicon-triangle-bottom');
	}).on('hide.bs.collapse', function(){
		$('[data-target="#' + this.id + '"] i:last-child').removeClass('glyphicon-triangle-bottom').addClass('glyphicon-triangle-left');
	});
})()
</script>