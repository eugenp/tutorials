"use strict"


//Helper function to make ajax calls
var ajax = function(method, url, json) {
	return new Promise(function(resolve, reject) {
		var xhr = new XMLHttpRequest();
		
		xhr.open(method, url);
		
		if(method == 'POST') {
			xhr.setRequestHeader('Content-type', 'application/json');
		}
		
		xhr.onload = function() {
			if(xhr.status == 200) {
				resolve(JSON.parse(xhr.responseText));
			} else {
				reject(xhr.status);
			}
		}
		
		xhr.send(JSON.stringify(json));
	})
}

// Add tasks to list
function addTasks(tasks) {
	var taskList = document.querySelector('#taskList');
	
	tasks.forEach(function(task) {
		taskList.insertAdjacentHTML('afterbegin', '<li>' + task.description + ' [ ' + task.assignee  +' ]</li>')
	})	
}

// Load tasks
ajax('GET', '/api/tasks').then(function(data) {	
	addTasks(data);
}).catch(function(err) {
	if(err == 403) {
		alert('Unauthorized');
	} else {
		console.log('error:', err);	
	}
})

// Click event on logout buton
document.querySelector('#logout').addEventListener('click', function() {
	window.location = "logout";
})

// Click event on addTask button
document.querySelector('#addTask').addEventListener('click', function() {		
	var description = document.querySelector('#description');
	var assignee 	= document.querySelector('#assignee').options[document.querySelector('#assignee').selectedIndex].value;
	
	if(description && assignee) {
	 	document.querySelector('#taskQueue ul')
	 			.insertAdjacentHTML('afterbegin', '<li>' + description.value + ' [ ' + assignee + ' ]</li>');
	 	// Clear description input field
	 	description.value = '';
	 	
	}
})

// Click event on clearAll button
document.querySelector('#clearAll').addEventListener('click', function() {		
	document.querySelector('#taskQueue ul').innerHTML = '';
})

// Click event on saveAll button
document.querySelector('#saveAll').addEventListener('click', function() {
	var newTasks = [];
	var taskQueueList = document.querySelector('#taskQueue ul');
	
	// Read tasks from queue and build list of new tasks
	taskQueueList.querySelectorAll('li').forEach(function(task) { 
		var groups = /^(.*) \[ (.*) \]$/g.exec(task.textContent);
		
		newTasks.push({
			description: groups[1],
			assignee: groups[2]
		})
		
	})
	
	// POST new tasks to the server
	ajax('POST', 'api/tasks', newTasks ).then(function(data) {
		addTasks(data);
		// Clear queue list after successful post
		taskQueueList.innerHTML = '';
	});
	
})