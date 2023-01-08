package com.baeldung.spring.data.persistence.search;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	Student findFirstByOrderByScoreDesc();
	List<Student> findFirst3ByOrderByScoreDesc();
	
	Student findFirstBy(Sort sort);
	Student findFirstByNameLike(String name, Sort sort);
	
	List<Student> findFirst2ByScoreGreaterThan(int score, Sort sort);
	List<Student> findFirst2ByScoreLessThan(int score, Sort sort);
	
	List<Student> findFirst2ByScoreBetween(int startScore, int endScore, Sort sort);
	
	
	
	Student findTopByOrderByScoreDesc();
	List<Student> findTop3ByOrderByScoreDesc();
	
	
}
