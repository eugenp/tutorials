<%@page import="org.baeldung.persistence.model.ActiveUser"%>
<%@page import="org.baeldung.web.cache.ActiveUserCache"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"> -->
	<link rel="stylesheet" href="resources/bootstrap.css">
	<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script> -->
	<script type="text/javascript" src="resources/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	function getAllLoggedInUsers() {
		$.ajax({
			url : "getAllLoggedInUsers",
			async : true,
			method : "GET",
			success : function(data) {
				if (data) {
					var tbody = $('div.module-content table.table-striped tbody');
					data = JSON.parse(data);
					
					var user;
					for (var i in data) {
						user = data[i];
						
						var tr = document.createElement('tr');
						tbody.append(tr);
						
						var td = document.createElement('td');
						td.appendChild(document.createTextNode(user.email));
						tr.appendChild(td);
						
						td = document.createElement('td');
						td.appendChild(document.createTextNode(new Date(user.activeTime)));
						tr.appendChild(td);
						
						td = document.createElement('td');
						td.appendChild(document.createTextNode(user.ipAddress));
						tr.appendChild(td);
					}
				}
				else {
					alert('No data found.');
				}
			}
		});	
	}
	
	$(document).ready(getAllLoggedInUsers);
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12">
			<div class="module">
				<div class="heading">
					<h2>List of Logged-in users</h2>
				</div>
				<div class="module-content">
					<div class="table-wrap">
						<table class="table table-striped activeUsers">
							<thead>
								<tr>
									<th>UserName</th>
									<th>Logged-in time</th>
									<th>Logged-in IpAddress</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>