package com.baeldung.spring.mybatis.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.baeldung.spring.mybatis.model.Student;

public interface StudentMapper {
    @Insert("INSERT INTO student(userName, password, name) VALUES" + "(#{userName},#{password}, #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", flushCache = true, keyColumn = "id")
    public void insertStudent(Student student);

    @Select("SELECT USERNAME as userName, PASSWORD as password, NAME as name " + "FROM student WHERE userName = #{userName}")
    public Student getStudentByUserName(String userName);
    
    @Select("SELECT USERNAME as userName, PASSWORD as password, NAME as name " + "FROM student WHERE userName = #{userName} and password=#{password}")
    public Student getStudentByLogin(Student student);

}