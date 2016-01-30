package org.baeldung.spring.data.redis.repo;

import org.baeldung.spring.data.redis.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private static final String KEY = "Student";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveStudent(final Student student) {
        redisTemplate.opsForHash().put(KEY, student.getId(), student);
    }

    public void updateStudent(final Student student) {
        redisTemplate.opsForHash().put(KEY, student.getId(), student);
    }

    public Student findStudent(final String id) {
        return (Student) redisTemplate.opsForHash().get(KEY, id);
    }

    public Map<Object, Object> findAllStudents() {
        return redisTemplate.opsForHash().entries(KEY);
    }

    public void deleteStudent(final String id) {
        this.redisTemplate.opsForHash().delete(KEY, id);
    }
}
