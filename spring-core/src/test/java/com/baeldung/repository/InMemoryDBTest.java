package com.baeldung.repository;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.configuration.JpaConfig;
import com.baeldung.model.Foo;
import com.baeldung.repository.FooRepository;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
public class InMemoryDBTest {
    
    @Resource
    private FooRepository fooRepository;
    
    private static final long ID = 1;
    private static final String NAME="foo";
    
    @Test
    public void givenFoo_whenSave_thenGetOk(){
        Foo foo = new Foo(ID, "foo");
        fooRepository.save(foo);
        
        Foo foo2 = fooRepository.getOne(ID);
        assertEquals("name incorrect", NAME, foo2.getName());        
    }

}
