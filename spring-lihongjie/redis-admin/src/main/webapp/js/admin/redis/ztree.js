$(document).ready(function() {
	var url = basePath + '/redis/serverTree';
	initZTree(url);
	$(".refresh_btn").on("click", function() {
		var url = basePath + '/redis/refresh';
		refreshZTree(url);
	});
	
	$(".addServer_btn").on("click", function() {
		var url = basePath + '/redis/addServer';
		var formParam = $("#addServerModalForm").formSerialize();
		$.ajax({
			type: "post",
			url : url,
			dataType: "json",
			data: formParam,
			success : function(data) {
				window.location.href = basePath + '/redis/stringList/' + serverName + '/' + dbIndex;
			}
		})
	})
});

function initZTree(url) {
	$.ajax({
		type: "get",
		url: url,
		dataType: "json",
		data: {},
		success : function(data) {
			var setting = {
					callback: {
						onDblClick : function(event, treeId, treeNode) {
							var zTree = $.fn.zTree.getZTreeObj("treeDemo");
							if (treeNode.isParent) {
								//if(!treeNode.open)
								zTree.expandNode(treeNode);
								refreshPage(treeNode.attach.serverName, treeNode.attach.dbIndex, treeNode.attach.keyPrefixs);
								return true;
							} else {
								refreshPage(treeNode.attach.serverName, treeNode.attach.dbIndex, treeNode.attach.keyPrefixs);
								return true;
							} 
						}
				}
			};
			var zNodes = data;
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}
	});
}

function refreshZTree(url) {
	$.ajax({
		type: "get",
		url: url,
		dataType: "json",
		data: {},
		success : function(data) {
			if(data.returncode == "200") {
				refreshPage(serverName, dbIndex, []);
			} else {
				modelAlert(data);
			}
		}
	});
}

function refreshPage(serverName, dbIndex, arr) {
	if(dbIndex==-1) {
		return ;
	}
	var url = basePath + '/redis/stringList/' + serverName + '/' + dbIndex;
	for(var i=0;i<arr.length;i++){
		if(i==0) {
			url = url + '?keyPrefixs=' + arr[0];
			continue;
		}
		url = url + '&keyPrefixs=' + arr[i];
	}
	window.location.href = url;
}
