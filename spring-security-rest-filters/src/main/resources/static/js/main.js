"use strict"

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

ajax('GET', '/api/tasks').then(function(response) {	
	var taskList = document.querySelector('#taskList');
	
	response._embedded.tasks.forEach(function(task) {
		taskList.insertAdjacentHTML('afterbegin', '<li>' + task.description + ' [ ' + task.assignee  +' ]</li>')
	})
}).catch(function(err) {
	if(err == 403) {
		alert('Unauthorized');
	} else {
		console.log('error:', err);	
	}
})

document.querySelector('#logout').addEventListener('click', function() {
	window.location = "logout";
})


document.querySelector('#addTask').addEventListener('click', function() {		
	var description = document.querySelector('#description').value;
	var assignee 	= document.querySelector('#assignee').options[document.querySelector('#assignee').selectedIndex].value;
	
	if(description && assignee) {
	 	document.querySelector('#taskQueue ul')
		.insertAdjacentHTML('afterbegin', '<li>' + description + ' [ ' + assignee + ' ]</li>');
	}
})

document.querySelector('#clearAll').addEventListener('click', function() {		
	document.querySelector('#taskQueue ul').innerHTML = '';
})


document.querySelector('#saveAll').addEventListener('click', function() {
	var newTasks = [];
	
	document.querySelectorAll('#taskQueue li').forEach(function(task) { 
		var groups = /^(.*) \[ (.*) \]$/g.exec(task.textContent);
		
		console.log(task.textContent, groups);
		
		newTasks.push({
			description: groups[1],
			assignee: groups[2]
		})
		
	})
	
	ajax('POST', 'api/taskList', newTasks );
	
})