package org.baeldung.adapter.primary;

import org.baeldung.entity.Student;
import org.baeldung.port.inbound.IControllerPort;
import org.baeldung.port.outbound.IRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "student")
public class StudentControllerAdapter implements IControllerPort {

    @Autowired
    private IRepositoryPort iRepositoryPort;

    @Override
    public Student create(Student student) {
        return iRepositoryPort.create(student);
    }

    @Override
    public Optional<Student> view(Long id) throws IOException {
        return iRepositoryPort.findStudentById(id);
    }

    @Override
    public List<Student> getAll() throws IOException {
        return iRepositoryPort.getAll();
    }

}
