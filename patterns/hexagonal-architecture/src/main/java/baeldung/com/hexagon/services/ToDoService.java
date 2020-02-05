package baeldung.com.hexagon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import baeldung.com.hexagon.domain.ToDo;
import baeldung.com.hexagon.repository.RepositoryService;

@Service
public class ToDoService {
	
	@Autowired
//	@Qualifier("RepositoryServiceMap")
	@Qualifier("RepositoryServiceJpa")	
	private RepositoryService repositoryService;

	public Iterable<ToDo>getList() {
		return repositoryService.getList();
	}

	public ToDo create(ToDo entity) throws Exception {
		if(entity.getWhat().length() < 3) {
			throw new Exception("'What' content should be at least 3 chars");
		}
		return repositoryService.create(entity);
	}

	public ToDo update(ToDo entity) throws Exception {
		if(entity.getWhat().length() < 3) {
			throw new Exception("'What' content should be at least 3 chars");
		}
		return repositoryService.update(entity);		
	}

	public void delete(Long id) {
		repositoryService.delete(id);
	}

}
