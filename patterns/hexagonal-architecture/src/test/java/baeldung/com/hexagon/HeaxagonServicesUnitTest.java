package baeldung.com.hexagon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import baeldung.com.hexagon.domain.ToDo;
import baeldung.com.hexagon.repository.RepositoryService;

@SpringBootTest
class HeaxagonServicesUnitTest {


	@Autowired
	@Qualifier("RepositoryServiceMap")
	RepositoryService repositoryServiceMap;

	@Autowired
	@Qualifier("RepositoryServiceJpa")
	RepositoryService repositoryServiceJpa;

	@Test
	void contextLoads() {
		assertEquals(repositoryServiceMap.getMyName(), "RepositoryServiceMap");
		assertEquals(repositoryServiceJpa.getMyName(), "RepositoryServiceJpa");
	}
	
	@Test
	void list() {
		Iterable<ToDo> content = repositoryServiceMap.getList();
		for(ToDo next : content) {
			System.out.println(next);
		}
	}
	
	@Test
	void create() {
		ToDo entity = new ToDo("have some fun");
		try {
			repositoryServiceMap.create(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterable<ToDo> content = repositoryServiceMap.getList();
		for(ToDo next : content) {
			System.out.println(next);
		}
	}
	
	@Test
	void update() {
		ToDo entity = new ToDo(2l, "join an updated metal band");
		try {
			repositoryServiceMap.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterable<ToDo> content = repositoryServiceMap.getList();
		for(ToDo next : content) {
			System.out.println(next);
		}
	}
	
	@Test
	void delete() {
		Long entityId = 2L;
		try {
			repositoryServiceMap.delete(entityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterable<ToDo> content = repositoryServiceMap.getList();
		for(ToDo next : content) {
			System.out.println(next);
		}
	}
	
}
