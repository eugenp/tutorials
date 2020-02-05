package baeldung.com.hexagon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import baeldung.com.hexagon.domain.ToDo;
import baeldung.com.hexagon.repository.ToDoJpaRepository;

@SpringBootTest
class HeaxagonDataUnitTest {

	@Test
	void contextLoads() {
	}
	
	@Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private ToDoJpaRepository todoRepository;

    @Test
    void injectedComponentsAreNotNull(){
         assertThat(dataSource).isNotNull();
         assertThat(jdbcTemplate).isNotNull();
         assertThat(entityManager).isNotNull();
         assertThat(todoRepository).isNotNull();
    }
    
    @Test
    public void testSaveToDo() {

        ToDo todo = new ToDo("have breakfast");
        todoRepository.save(todo);
    }
    
    @Test
    public void testSaveAndLoadToDo() {
        todoRepository.deleteAll();
        ToDo todo = new ToDo("go to work");
        todoRepository.save(todo);

        Iterable<ToDo> todoIt = todoRepository.findAll();
    	System.out.println("print..");
    	todoIt.forEach(action->{
        	System.out.println(action);
        	});

        Optional<ToDo> todoAfter = todoRepository.findById(4l);
        assertTrue(todoAfter.isPresent());
        assertEquals(todoAfter.get().getWhat(), "go to work");
        System.out.println(todo);
    }
    
    @Test
    public void testSaveAndLoadAllToDo() {
        todoRepository.deleteAll();
        ToDo todo = new ToDo("write some code");
        todoRepository.save(todo);
        ToDo todoOther = new ToDo("ride the lightning");
        todoRepository.save(todoOther);
        Iterable<ToDo> todoIt = todoRepository.findAll();
    	System.out.println("print..");
    	todoIt.forEach(action->{
        	System.out.println(action);
        	});
        }
}
