package baeldung.com.hexagon.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import baeldung.com.hexagon.domain.ToDo;
import baeldung.com.hexagon.services.ToDoService;

//@RestController("/")
public class ToDoRestController {

	@Autowired
	private ToDoService toDoService;

	@GetMapping("")
	public Iterable<ToDo> retreive(Model model) {
		Iterable<ToDo> todoList = toDoService.getList();
		return todoList;
	}

	@PostMapping("")
	public ResponseEntity<ToDo> create(@RequestBody ToDo entity) {
		try {
			ToDo createdEntity = toDoService.create(entity);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
					.buildAndExpand(createdEntity.getId()).toUri();
			return ResponseEntity.created(uri).body(createdEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.unprocessableEntity().build();
	}

	@PutMapping("")
	public ResponseEntity<ToDo> update(@RequestBody ToDo entity) {
		try {
			ToDo createdEntity = toDoService.update(entity);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
					.buildAndExpand(createdEntity.getId()).toUri();
			return ResponseEntity.created(uri).body(createdEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.unprocessableEntity().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
		toDoService.delete(id);
	    return ResponseEntity.noContent().build();
	}
}
