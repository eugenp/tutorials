package de.samples.quarkus.todos.domain;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import de.samples.quarkus.interceptor.LogMethodCall;
import de.samples.quarkus.todos.persistence.TodoEntityMapper;
import de.samples.quarkus.todos.persistence.TodosRepository;
import lombok.var;

@ApplicationScoped
//@MethodValidated
public class TodosService {

	private final TodosRepository repo;
	private final TodoEntityMapper mapper;
	
	public TodosService(TodosRepository repo, TodoEntityMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	public Collection<Todo> findAll() {
		return repo.listAll()
				.stream()
				.map(mapper::map)
				.collect(Collectors.toList());

	}

	public Optional<Todo> findById(Long id) {
		return repo.findByIdOptional(id)
				.map(mapper::map);
	}

	@LogMethodCall
	public void add(@Valid Todo todo) {
		var entity = mapper.map(todo);
		repo.persist(entity);
		todo.setId(entity.getId());
	}

}
