package org.baeldung.web.service;

import java.util.List;

import org.baeldung.mock.MockStudentData;
import org.baeldung.web.exception.MyResourceNotFoundException;
import org.baeldung.web.vo.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

	private List<Student> mockDataStudent = MockStudentData.getMockDataStudents();
	
	@Override
	public Page<Student> findPaginated(int page, int size){
		Page<Student> studentPage = getPage(page, size);
		return studentPage;
	}

	private Page<Student> getPage(int page, int size) {
		page = page != 0?page - 1:page;
		int from = Math.max(0, page * size);
		int to = Math.min(mockDataStudent.size(), (page + 1) * size);
		if(from > to){
			throw new MyResourceNotFoundException("page number is higher than total pages.");
		}
		return new PageImpl<Student>(mockDataStudent.subList(from, to),
									new PageRequest(page,size),
									mockDataStudent.size());
	}

}
