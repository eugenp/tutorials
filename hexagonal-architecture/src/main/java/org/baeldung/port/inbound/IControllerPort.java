package org.baeldung.port.inbound;

import org.baeldung.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface IControllerPort {

    @RequestMapping(method = RequestMethod.POST)
    public Student create(@RequestBody Student student);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Optional<Student> view(@PathVariable(value = "id") Long id) throws IOException;

    @RequestMapping(method = RequestMethod.GET)
    public List<Student> getAll() throws IOException;

}
