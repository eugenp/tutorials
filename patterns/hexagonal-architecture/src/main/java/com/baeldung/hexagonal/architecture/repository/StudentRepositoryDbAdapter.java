package com.baeldung.hexagonal.architecture.repository;

import com.baeldung.hexagonal.architecture.exception.BadRequestException;
import com.baeldung.hexagonal.architecture.models.Entity.StudentModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("studentRepositoryDbAdapter")
@Transactional
public class StudentRepositoryDbAdapter implements  StudentRepositoryPort {

    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    @PersistenceContext
    private EntityManager entityManager;

    public void createStudentData(String name, String email, String batch,Integer registartionNumber) throws BadRequestException{
        if(getStudentDataFromRegistartionNo(registartionNumber) != null)
            throw new BadRequestException("This registartion number is already used by some student:" ,HttpStatus.BAD_REQUEST);

            StudentModel student = new StudentModel();
            student.setName(name);
            student.setEmail(email);
            student.setBatch(batch);
            student.setRegistration_Number(registartionNumber);
            entityManager.persist(student);
    }

    public StudentModel getStudentData(Integer registartionNumber) {
        return (StudentModel) getSession().createQuery(
                "from StudentModel where registration_Number = :registartionNumber")
                .setParameter("registartionNumber", registartionNumber)
                .uniqueResult();

    }

    private StudentModel getStudentDataFromRegistartionNo(Integer registartionNumber){
        return (StudentModel) getSession().createQuery(
                "from StudentModel where registration_Number = :registartionNumber")
                .setParameter("registartionNumber", registartionNumber)
                .uniqueResult();
    }


}
