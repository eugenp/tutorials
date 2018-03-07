package mapper;

import entity.StudentEntity;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper {

    StudentEntity getStudentById(int id);

    int addStudent(StudentEntity student);

    int updateStudentName(@Param("name") String name, @Param("id") int id);

    StudentEntity getStudentByIdWithClassInfo(int id);
}
