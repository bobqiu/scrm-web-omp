<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row" style="margin-top:-20px;margin-bottom:-20px">
	<div class="text-center">
		<ul class="pagination">
			<li><a href="javascript:firstPage()" title="首页">&laquo;</a></li>
			<li><a href="javascript:prevPage()" title="上一页">&lt;</a></li>
			<c:forEach begin="1" end="${page.totalPage}" step="1" var="v">
			<li class='${v == page.pageNum ? "active" : ""}'><a href="javascript:goPage(${v})">${v}</a></li>
			</c:forEach>
			<li><a href="javascript:nextPage()" title="下一页">&gt;</a></li>
			<li><a href="javascript:lastPage()" title="末页">&raquo;</a></li>
		</ul>
		<script type="text/javascript">
			var curPageNum = ${page.pageNum};
			var totalPage = ${page.totalPage};
			function goPage(n) {
				//$("form input[name=numPerPage]").val(20);
				$("form input[name=pageNum]").val(n);
				$("form [data-form-id]").click();
			}
			function firstPage() {
				goPage(1);
			}
			function prevPage() {
				if (curPageNum > 1) {
					goPage(curPageNum - 1);
				}
			}
			function nextPage() {
				if (curPageNum < totalPage) {
					goPage(curPageNum + 1);
				}
			}
			function lastPage() {
				goPage(totalPage);
			}
		</script>
	</div>
</div>