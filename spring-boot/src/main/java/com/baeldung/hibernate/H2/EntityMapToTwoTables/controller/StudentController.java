
import com.baeldung.hibernate.H2.EntityMapToTwoTables.entity.StudentEntity;
import com.baeldung.hibernate.H2.EntityMapToTwoTables.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController{
    Logger LOG = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentRepository studentRepository;

    @RequestMapping("/add")
    public int addTransaction(@RequestBody StudentEntity studentEntity) {
        LOG.info("Got request to add student : {}",studentEntity);
        return studentRepository.saveAndFlush(studentEntity).getId();
    }

}

