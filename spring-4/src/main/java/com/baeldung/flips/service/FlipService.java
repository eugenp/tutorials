package com.baeldung.flips.service;

import com.baeldung.flips.model.Foo;
import org.flips.annotation.FlipBean;
import org.flips.annotation.FlipOnSpringExpression;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlipService {

    private final List<Foo> foos;

    public FlipService() {
        foos = new ArrayList<>();
        foos.add(new Foo("Foo1", 1));
        foos.add(new Foo("Foo2", 2));
        foos.add(new Foo("Foo3", 3));
        foos.add(new Foo("Foo4", 4));
        foos.add(new Foo("Foo5", 5));
        foos.add(new Foo("Foo6", 6));

    }

    public List<Foo> getAllFoos() {
        return foos;
    }

    public Optional<Foo> getFooById(int id) {
        return foos.stream().filter(foo -> (foo.getId() == id)).findFirst();
    }

    @FlipBean(with = NewFlipService.class)
    @FlipOnSpringExpression(expression = "(2 + 2) == 4")
    public Foo getNewFoo() {
        return new Foo("New Foo!", 99);
    }

    public Foo getLastFoo() {
        return foos.get(foos.size() - 1);
    }

    public Foo getFirstFoo() {
        return foos.get(0);
    }

}