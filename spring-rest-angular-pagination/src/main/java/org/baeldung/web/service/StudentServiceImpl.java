package org.baeldung.web.service;

import javax.transaction.Transactional;

import org.baeldung.web.dao.StudentRepository;
import org.baeldung.web.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository dao;
	
	@Override
	public Page<Student> findPaginated(int page, int size) {
		return dao.findAll(new PageRequest(page,size));
	}

}
