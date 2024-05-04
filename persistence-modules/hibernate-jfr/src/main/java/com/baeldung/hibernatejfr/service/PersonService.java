package com.baeldung.hibernatejfr.service;

import com.baeldung.hibernatejfr.dao.PersonDao;
import com.baeldung.hibernatejfr.entity.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public PersonEntity findPersonById(Long id){
        Optional<PersonEntity> person = personDao.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public PersonEntity updatePerson(Long id, PersonEntity person) {
        PersonEntity foundPerson = findPersonById(id);
        foundPerson.setDesignation(person.getDesignation());
        foundPerson.setMobile(person.getMobile());
        foundPerson.setName(person.getName());
        return personDao.save(foundPerson);
    }

    @Transactional
    public void createPersons(List<PersonEntity> personList){
        personDao.saveAll(personList);
    }

    @Transactional
    public List<PersonEntity> listPersons(){
        return personDao.findAll();
    }

}
