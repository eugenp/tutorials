<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- dialog -->
<div class="modal modal1 fade" id="myModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="back_btn close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="model_title">model_title</h4>
			</div>
			<div class="modal-body">
				<p id="model_content">model_content</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="back_btn btn btn-default" data-dismiss="modal">close</button>
				<button type="button" class="back_btn btn btn-default" data-dismiss="modal">back to list</button>
				<!-- <button type="button" class="btn btn-primary">Save changes</button> -->
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
	
	
	<script>
	function modelAlert(data) {
		if (data.returncode == "200") {
			$("#model_title").text(data.returnmsg);
			$("#model_content").text(data.returnmemo);
			$('#myModal').modal();
		} else {
			$("#model_title").text(data.returnmsg);
			$("#model_content").text(data.returnmemo);
			$('#myModal').modal();
		}
	}
	
	</script>
</div>
<!-- /.modal -->