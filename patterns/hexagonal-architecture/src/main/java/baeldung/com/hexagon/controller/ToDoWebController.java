package baeldung.com.hexagon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import baeldung.com.hexagon.domain.ToDo;
import baeldung.com.hexagon.services.ToDoService;

@Controller("/")
public class ToDoWebController {

	@Autowired
	private ToDoService toDoService;
    @GetMapping("")
    public String retreive(Model model) {
    	Iterable<ToDo> todoList = toDoService.getList();
		model.addAttribute("todoList", todoList);
		model.addAttribute("toDo", new ToDo(""));
		return "index";
    }
    

	@PostMapping("")
    public String post(Model model, ToDo entity) {
		try {
			if(entity.getId() == null || entity.getId() == ToDo.INVALID_ID) {
				toDoService.create(entity);				
			} else {
				if(entity.getWhat().isEmpty()) {
					toDoService.delete(entity.getId());	
				} else {
					toDoService.update(entity);					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
		}
		Iterable<ToDo> todoList = toDoService.getList();
		model.addAttribute("todoList", todoList);
		model.addAttribute("toDo", new ToDo());
        return "index";
    }
}
