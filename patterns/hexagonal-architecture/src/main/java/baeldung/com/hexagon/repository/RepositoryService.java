package baeldung.com.hexagon.repository;

import baeldung.com.hexagon.domain.ToDo;

public interface RepositoryService {
	public String getMyName();

	void delete(Long id);

	ToDo update(ToDo entity) throws Exception;

	ToDo create(ToDo entity) throws Exception;

	Iterable<ToDo> getList();

}
