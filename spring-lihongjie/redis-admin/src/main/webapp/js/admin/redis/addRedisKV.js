$(document).ready(function() {
$('#addModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget);
			var operator = button.data('whatever'); 
			if(operator == 'add') {
				$("#addModalLabel").text("Add redis-data");
			}
		});
		
		
		$('#addModal').on("click", ".plus_btn", function() {
			var dataType = $(this).attr("value1");
			
			var formEle;
			var addHtml = '';
			switch(dataType) {
			case 'STRING':
				//never go here
				break;
			case 'LIST':
				formEle = $(this).parent().parent().parent();
				addHtml = listHtml;
				break;
			case 'SET':
				formEle = $(this).parent().parent().parent();
				addHtml = setHtml;
				break;
			case 'ZSET':
				formEle = $(this).parent().parent().parent().parent().parent().parent();
				addHtml = zsetHtml;
				break;
			case 'HASH':
				formEle = $(this).parent().parent().parent().parent().parent().parent();
				addHtml = hashHtml;
				break;
			}
			$(formEle).append(clearLineHtml);
			$(formEle).append(addHtml);
		});
		
		$('#addModal').on("click", ".minus_btn", function() {
			var dataType = $(this).attr("value1");
			
			var inputEle;
			var addHtml = '';
			switch(dataType) {
			case 'STRING':
				//never go here
				break;
			case 'LIST':
				inputEle = $(this).parent().parent();
				break;
			case 'SET':
				inputEle = $(this).parent().parent();
				break;
			case 'ZSET':
				inputEle = $(this).parent().parent().parent().parent().parent();
				break;
			case 'HASH':
				inputEle = $(this).parent().parent().parent().parent().parent();
				break;
			}
			$(inputEle).remove();
		});
		
		
		$("#addModal_dateType").on("change", function() {
			var dataType = $(this).val();
			var addModalForm = $("#addModalForm");
			
			var addHtml = '';
			switch(dataType) {
			case 'STRING':
				addHtml = stringHtml;
				break;
			case 'LIST':
				addHtml = listHtml;
				break;
			case 'SET':
				addHtml = setHtml;
				break;
			case 'ZSET':
				addHtml = zsetHtml;
				break;
			case 'HASH':
				addHtml = hashHtml;
				break;
			}
			$("#addModalForm").find(".input_div").remove();
			$("#addModalForm>span:last-child").text(dataType + ' values');
			$(addModalForm).append(addHtml);
		})
		
		$(".add_KV_btn").on("click", function() {
			var addFormParam = $("#addModalForm").formSerialize();
			
			var url = basePath + '/redis/KV';
			$.ajax({
				type: "post",
				url: url,
				dataType: "json",
				data: addFormParam,
				success: function(data) {
					modelAlert(data);
				}
			})
			
		})
});