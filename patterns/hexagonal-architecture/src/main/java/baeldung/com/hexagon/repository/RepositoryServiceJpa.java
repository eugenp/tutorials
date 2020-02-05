package baeldung.com.hexagon.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import baeldung.com.hexagon.domain.ToDo;

@Component("RepositoryServiceJpa")
public class RepositoryServiceJpa implements RepositoryService {
	
	@Autowired
	private ToDoJpaRepository repository;

	@Override
	public String getMyName() {
		return "RepositoryServiceJpa";
	}
	
	@Override
	public Iterable<ToDo> getList() {
		return repository.findAll();
	}

	@Override
	public ToDo create(ToDo entity) throws Exception {
		if(entity.getWhat().length() < 3) {
			throw new Exception("What should be at least 3 chars");
		}
		return repository.save(entity);
	}

	@Override
	public ToDo update(ToDo entity) throws Exception {
		if(entity.getWhat().length() < 3) {
			throw new Exception("What should be at least 3 chars");
		}
		return repository.save(entity);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
