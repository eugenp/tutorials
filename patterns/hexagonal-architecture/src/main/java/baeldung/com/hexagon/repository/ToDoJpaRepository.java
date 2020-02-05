package baeldung.com.hexagon.repository;

import org.springframework.data.repository.CrudRepository;
import baeldung.com.hexagon.domain.ToDo;

public interface ToDoJpaRepository extends CrudRepository<ToDo, Long> {

}
