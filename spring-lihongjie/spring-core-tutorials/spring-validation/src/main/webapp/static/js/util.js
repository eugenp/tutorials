function enableSubmitForm() {
	
	$('.form-btn').click(function() {
		var data = {
			username : ""	
		};
		$.ajax({
			data : data,
			url : "../users/2",
			method : "post",
			success : function() {
				
			},
			error : function(xhr) {
				debugger;
				alert(1);
			}
		});
	})
}


$(document).ready(function(e) {
	enableSubmitForm();
});