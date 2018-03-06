$(document).ready(function() {
	
	$("#updateModal").on("click", ".update_plus_btn", function() {
		var dataType = $("#updateModal_dataType").val();
		switch(dataType) {
		case 'STRING':
			break;
		case 'LIST':
			var ctrLine = $(this).parent().parent().parent().parent().clone();
			var tbody = $(this).parent().parent().parent().parent().parent();
			$(ctrLine).find("input").each(function() {
				$(this).val("");
			});
			$(tbody).append($(ctrLine));
			break;
		case 'SET':
			var ctrLine = $(this).parent().parent().parent().parent().clone();
			var tbody = $(this).parent().parent().parent().parent().parent();
			$(ctrLine).find("input").each(function() {
				$(this).val("");
			});
			$(tbody).append($(ctrLine));
			break;
		case 'ZSET':
			var ctrLine = $(this).parent().parent().parent().parent().clone();
			var tbody = $(this).parent().parent().parent().parent().parent();
			$(ctrLine).find("input").each(function() {
				$(this).val("");
				$(this).attr("readonly", false);
			});
			$(tbody).append($(ctrLine));
			break;
		case 'HASH':
			var ctrLine = $(this).parent().parent().parent().parent().clone();
			var tbody = $(this).parent().parent().parent().parent().parent();
			$(ctrLine).find("input").each(function() {
				$(this).val("");
				$(this).attr("readonly", false);
			});
			$(tbody).append($(ctrLine));
			break;
		};
		
	});
	
	$("#updateModal").on("click", ".update_minus_btn", function() {
		
		var serverName = $("#updateModal_serverName").val();
		var dbIndex = $("#updateModal_dbIndex").val();
		var key = $("#updateModal_key").val();
		var dataType = $("#updateModal_dataType").val();
		
		switch(dataType) {
		case 'STRING':
			var ctrLine = $(this).parent().parent().parent().parent();
			$.ajax({
				type: "post",
				url: basePath +'/string/delValue',
				dataType: "json",
				data: {
					serverName: serverName,
					dbIndex: dbIndex,
					key: key,
					dataType: dataType,
				},
				success : function(data) {
					$(ctrLine).remove();
					modelAlert(data);
				}
			});
			break;
		case 'LIST':
			var ctrLine = $(this).parent().parent().parent().parent();
			$.ajax({
				type: "post",
				url: basePath +'/list/delListValue',
				dataType: "json",
				data: {
					serverName: serverName,
					dbIndex: dbIndex,
					key: key,
					dataType: dataType,
				},
				success : function(data) {
					$(ctrLine).remove();
					modelAlert(data);
				}
			});
			break;
		case 'SET':
			var ctrLine = $(this).parent().parent().parent().parent();
			var value = $(ctrLine).find("input[name='value']").val();
			$.ajax({
				type: "post",
				url: basePath +'/set/delSetValue',
				dataType: "json",
				data: {
					serverName: serverName,
					dbIndex: dbIndex,
					key: key,
					dataType: dataType,
					value: value,
				},
				success : function(data) {
					$(ctrLine).remove();
					modelAlert(data);
				}
			});
			break;
		case 'ZSET':
			var ctrLine = $(this).parent().parent().parent().parent();
			var member = $(ctrLine).find("input[name='value']").val();
			$.ajax({
				type: "post",
				url: basePath +'/zset/delZSetValue',
				dataType: "json",
				data: {
					serverName: serverName,
					dbIndex: dbIndex,
					key: key,
					dataType: dataType,
					member: member,
				},
				success : function(data) {
					$(ctrLine).remove();
					modelAlert(data);
				}
			});
			break;
		case 'HASH':
			var ctrLine = $(this).parent().parent().parent().parent();
			var field = $(ctrLine).find("input[name='field']").val();
			$.ajax({
				type: "post",
				url: basePath +'/hash/delHashField',
				dataType: "json",
				data: {
					serverName: serverName,
					dbIndex: dbIndex,
					key: key,
					dataType: dataType,
					field: field,
				},
				success : function(data) {
					$(ctrLine).remove();
					modelAlert(data);
				}
			});
			break;
		};
		
	});
	
	$("#updateModal").on("click", ".update_redis_btn", function() {
		
		var serverName = $("#updateModal_serverName").val();
		var dbIndex = $("#updateModal_dbIndex").val();
		var key = $("#updateModal_key").val();
		var dataType = $("#updateModal_dataType").val();
		
		switch(dataType) {
		case 'STRING':
			var ctrLine = $(this).parent().parent();
			var value = $(ctrLine).find("input[name='value']").val();
			$.ajax({
				type: "post",
				url: basePath +'/string/updateValue',
				dataType: "json",
				data: {
					serverName: serverName,
					dbIndex: dbIndex,
					key: key,
					dataType: dataType,
					value: value,
				},
				success : function(data) {
					modelAlert(data);
				}
			});
			break;
		case 'LIST':
			var ctrLine = $(this).parent().parent();
			var value = $(ctrLine).find("input[name='value']").val();
			$.ajax({
				type: "post",
				url: basePath +'/list/updateListValue',
				dataType: "json",
				data: {
					serverName: serverName,
					dbIndex: dbIndex,
					key: key,
					dataType: dataType,
					value: value,
				},
				success : function(data) {
					modelAlert(data);
				}
			});
			break;
		case 'SET':
			var ctrLine = $(this).parent().parent();
			var value = $(ctrLine).find("input[name='value']").val();
			$.ajax({
				type: "post",
				url: basePath +'/set/updateSetValue',
				dataType: "json",
				data: {
					serverName: serverName,
					dbIndex: dbIndex,
					key: key,
					dataType: dataType,
					value: value,
				},
				success : function(data) {
					modelAlert(data);
				}
			});
			break;
		case 'ZSET':
			var ctrLine = $(this).parent().parent();
			var score = $(ctrLine).find("input[name='score']").val();
			var member = $(ctrLine).find("input[name='value']").val();
			$.ajax({
				type: "post",
				url: basePath +'/zset/updateZSetValue',
				dataType: "json",
				data: {
					serverName: serverName,
					dbIndex: dbIndex,
					key: key,
					dataType: dataType,
					score: score,
					member: member,
				},
				success : function(data) {
					modelAlert(data);
				}
			});
			break;
		case 'HASH':
			var ctrLine = $(this).parent().parent();
			var field = $(ctrLine).find("input[name='field']").val();
			var value = $(ctrLine).find("input[name='value']").val();
			$.ajax({
				type: "post",
				url: basePath +'/hash/updateHashField',
				dataType: "json",
				data: {
					serverName: serverName,
					dbIndex: dbIndex,
					key: key,
					dataType: dataType,
					field: field,
					value: value,
				},
				success : function(data) {
					modelAlert(data);
				}
			});
			break;
		};
	});
	
	$(".edit_btn").on("click", function() {
		
		var checkedNum = $("#listTable").find("input:checkbox[name='redisKey']:checked").length;
		if(checkedNum > 1||checkedNum<=0) {
			$("#model_title").text("warning");
			$("#model_content").text("please choose one to edit");
			$('#myModal').modal();
			return ;
		}
		
		var key = $("#listTable").find("input:checkbox[name='redisKey']:checked").attr("value1");
		var dataType = $("#listTable").find("input:checkbox[name='redisKey']:checked").attr("value2");
		
		$("#updateModal_serverName").val(serverName);
		$("#updateModal_dbIndex").val(dbIndex);
		$("#updateModal_key").val(key);
		$("#updateModal_dataType").val(dataType);
		
		$.ajax({
			type: "get",
			url: basePath +'/redis/KV',
			dataType: "json",
			data: {
				serverName: serverName,
				dbIndex: dbIndex,
				key: key,
				dataType: dataType,
			},
			success : function(data) {
				if(dataType=='NONE') {
					dataType = data.data.dataType;
					$("#updateModal_dataType").val(dataType);
				}
				$('#'+ dataType + 'FormTable').tableData(data.data.values); 
				$("#updateModal").modal('show');
			}
		});
	});
	
	/*$(".update_btn").on("click", function() {
		
		var serverName = $("#updateModal_serverName").val();
		var dbIndex = $("#updateModal_dbIndex").val();
		var key = $("#updateModal_key").val();
		var dataType = $("#updateModal_dataType").val();
		
		var updateForm = $("#" + dataType + "Form");
		alert($(updateForm).attr("value1"));
		var updateFormParam = $(updateForm).formSerialize();
		updateFormParam = updateFormParam.substring(updateFormParam.indexOf("&")+1);
		var url = basePath + '/redis/KV';
		$.ajax({
			type: "post",
			url: url,
			dataType: "json",
			data: updateFormParam + "&serverName=" + serverName
			+ "&dbIndex=" + dbIndex+ "&key=" + key
			+ "&dataType=" + dataType,
			success: function(data) {
				modelAlert(data);
			}
		});
	});*/
	
	$(".redisKey").on("dblclick", function() {
		
		var key = $(this).find("input:checkbox[name='redisKey']").attr("value1");
		var dataType = $(this).find("input:checkbox[name='redisKey']").attr("value2");
		
		$("#updateModal_serverName").val(serverName);
		$("#updateModal_dbIndex").val(dbIndex);
		$("#updateModal_key").val(key);
		$("#updateModal_dataType").val(dataType);
		
		$.ajax({
			type: "get",
			url: basePath + '/redis/KV',
			dataType: "json",
			data: {
				serverName: serverName,
				dbIndex: dbIndex,
				key: key,
				dataType: dataType,
			},
			success : function(data) {
				if(dataType=='NONE') {
					dataType = data.data.dataType;
					$("#updateModal_dataType").val(dataType);
				}
				$('#'+ dataType + 'FormTable').tableData(data.data.values); 
				$("#updateModal").modal('show');
			}
		});
	});
	
})