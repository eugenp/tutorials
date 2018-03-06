<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<c:url value="/users/records" var="recordsUrl"/>
<c:url value="/users/create" var="addUrl"/>
<c:url value="/users/update" var="editUrl"/>
<c:url value="/users/delete" var="deleteUrl"/>

<html>
<head>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>

	<title>User Records</title>
	
	<script type='text/javascript'>
	$(function() {
		// init
		urlHolder.records = '${recordsUrl}';
		urlHolder.add = '${addUrl}';
		urlHolder.edit = '${editUrl}';
		urlHolder.del = '${deleteUrl}';
		loadTable();
		
		$('#newBtn').click(function() { 
			toggleForms('new');
			toggleCrudButtons('hide');
		});
		
		$('#editBtn').click(function() { 
			if (hasSelected()) {
				toggleForms('edit');
				toggleCrudButtons('hide');
				fillEditForm();
			}
		});
		
		$('#reloadBtn').click(function() { 
			loadTable();
		});

		$('#deleteBtn').click(function() {
			if (hasSelected()) { 
				submitDeleteRecord();
			}
		});
		
		$('#newForm').submit(function() {
			event.preventDefault();
			submitNewRecord();
		});
		
		$('#editForm').submit(function() {
			event.preventDefault();
			submitUpdateRecord();
		});

		$('#closeNewForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});
		
		$('#closeEditForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});
	});
	</script>
</head>

<body>
	<h1 id='banner'>Record System</h1>
	<hr/>
	
	<table id='tableUsers'>
		<caption></caption>
		<thead>
			<tr>
				<th></th>
				<th>Username</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Role</th>
			</tr>
		</thead>
	</table>
	
	<div id='controlBar'>
		<input type='button' value='New' id='newBtn' />
		<input type='button' value='Delete' id='deleteBtn' />
		<input type='button' value='Edit' id='editBtn' />
		<input type='button' value='Reload' id='reloadBtn' />
	</div>
	
	<div id='newForm'>
		<form>
  			<fieldset>
				<legend>Create New Record</legend>
				<label for='newUsername'>Username</label><input type='text' id='newUsername'/><br/>
				<label for='newPassword'>Password</label><input type='password' id='newPassword'/><br/>
				<label for='newFirstName'>First Name</label><input type='text' id='newFirstName'/><br/>
				<label for='newLastName'>Last Name</label><input type='text' id='newLastName'/><br/>
				<label for='newRole'>Role</label>
					<select id='newRole'>
						<option value='1'>Admin</option>
						<option value='2' selected='selected'>Regular</option>
					</select>
  			</fieldset>
			<input type='button' value='Close' id='closeNewForm' />
			<input type='submit' value='Submit'/>
		</form>
	</div>
	
	<div id='editForm'>
		<form>
  			<fieldset>
				<legend>Edit Record</legend>
				<input type='hidden' id='editUsername'/>
				<label for='editFirstName'>First Name</label><input type='text' id='editFirstName'/><br/>
				<label for='editLastName'>Last Name</label><input type='text' id='editLastName'/><br/>
				<label for='editRole'>Role</label>
					<select id='editRole'>
						<option value='1'>Admin</option>
						<option value='2' selected='selected'>Regular</option>
					</select>
			</fieldset>
			<input type='button' value='Close' id='closeEditForm' />
			<input type='submit' value='Submit'/>
		</form>
	</div>
	
</body>
</html>