package org.baeldung.service.ports;

import org.baeldung.persistence.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Service
public interface IStudentUI {

    @RequestMapping(method = RequestMethod.POST)
    public Student create(@RequestBody Student student);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Optional<Student> view(@PathVariable(value = "id") Long id);
}
