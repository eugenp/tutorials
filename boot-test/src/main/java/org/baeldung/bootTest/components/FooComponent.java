package org.baeldung.bootTest.components;

import org.baeldung.bootTest.exceptions.CommonException;
import org.baeldung.bootTest.exceptions.FooNotFoundException;
import org.baeldung.bootTest.model.Foo;
import org.baeldung.bootTest.repository.FooRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

@Component
public class FooComponent {

    @Autowired
    private FooRepository fooRepository;
    
    private final static Logger logger = org.slf4j.LoggerFactory.getLogger(FooComponent.class);

    public Foo getFooWithId(Long id) {

        Foo foo = null;

        try {
            foo = fooRepository.findOne(id);
            Preconditions.checkNotNull(foo);
            logger.info("Foo with Id {} found",id);
        }catch(NullPointerException ex){
            logger.error("Foo with Id {} was not found",id);
            throw new FooNotFoundException("The given foo Id was not found");
        } catch (Exception ex) {
            logger.error("Error while retrieving Foo with Id {} found",id,ex);
            throw new CommonException("Error while retrieving foo");
        }

        return foo;
    }
    
    public Foo getFooWithName(String name) {

        Foo foo = null;

        try {
            foo = fooRepository.findOneByName(name);
            Preconditions.checkNotNull(foo);
            logger.info("Foo with name {} found",name);
        }catch(NullPointerException ex){
            logger.error("Foo with name {} was not found",name);
            throw new FooNotFoundException("The given foo name was not found");
        } catch (Exception ex) {
            logger.error("Error while retrieving Foo with name {} found",name,ex);
            throw new CommonException("Error while retrieving foo");
        }

        return foo;
    }
}