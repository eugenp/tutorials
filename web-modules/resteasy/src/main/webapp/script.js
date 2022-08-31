function call(url, type, data) {
	var request = $.ajax({
		url : url,
		method : "GET",
		data : (data) ? JSON.stringify(data) : "",
		dataType : type
	});

	request.done(function(resp) {
		console.log(resp);
	});

	request.fail(function(jqXHR, textStatus) {
		console.log("Request failed: " + textStatus);
	});
};