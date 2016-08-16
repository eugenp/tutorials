<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Company Data</title>
		<script src="https://code.jquery.com/jquery-3.1.0.js"
			integrity="sha256-slogkvB1K3VOkzAI8QITxV3VzpOnkeNVsKvtkYLMjfk="
			crossorigin="anonymous"></script>
		<script>
			$(document).ready(function(){
				$('#ResponseBody-button').click(function() {
				   $.ajax({
				      url: 'http://localhost:8080/spring-mvc-java/companyResponseBody?callback=getCompanyData',
				      data: {
				         format: 'json'
				      },
					  type: 'GET',
					  jsonpCallback:'getCompanyData',
					  dataType: 'jsonp',
				      error: function() {
				         $('#infoResponseBody').html('<p>An error has occurred</p>');
				      },
				      success: function(data) {
				        console.log("sucess");
				      }
				   });
				});
				$('#ResponseEntity-button').click(function() {
				console.log("ResponseEntity");
				   $.ajax({
				      url: 'http://localhost:8080/spring-mvc-java/companyResponseEntity?callback=getCompanyData',
				      data: {
				         format: 'json'
				      },
					  type: 'GET',
					  jsonpCallback:'getCompanyData',
					  dataType: 'jsonp',
				      error: function() {
				         $('#infoResponseEntity').html('<p>An error has occurred</p>');
				      },
				      success: function(data) {
				        console.log("sucess");
				      }
				   });
				});	
			});
		
		    function getCompanyData(data) {
				$("#response").append("<b>ID:</b> "+data.id+"<br/>");
				$("#response").append("<b>NAME:</b> "+data.name+"<br/>");
				$("#response").append("<br/>");
		     }
	     </script>
	</head>
	<body>
	
		<!--Using @ResponseBody -->
		<button id="ResponseBody-button">Send AJAX JSON-P request!</button>

		<div id="response"></div>
	

	</body>
</html>