/**
 * 
 */
package com.mybatis3.mappers;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.mybatis3.domain.Course;
import com.mybatis3.domain.Tutor;
import com.mybatis3.sqlproviders.TutorDynaSqlProvider;


/**
 * @author Siva
 *
 */

public interface TutorMapper 
{
	
	@Select("select * from courses where tutor_id=#{tutorId}")
	@ResultMap("com.mybatis3.mappers.TutorMapper.CourseResult")
	List<Course> selectCoursesByTutorId(int tutorId);
	
	@Select("SELECT tutor_id, t.name as tutor_name, email, addr_id FROM tutors t where t.tutor_id=#{tutorId}")
	@Results({
		@Result(id=true, column="tutor_id", property="tutorId"),
		@Result(column="tutor_name", property="name"),
		@Result(column="email", property="email"),
		@Result(property="address", column="addr_id",
				one=@One(select="com.mybatis3.mappers.AddressMapper.selectAddressById")),		
		@Result(property="courses", column="tutor_id",
				many=@Many(select="com.mybatis3.mappers.TutorMapper.selectCoursesByTutorId"))		
	})
	Tutor selectTutorWithCoursesById(int tutorId);
	
	@SelectProvider(type=TutorDynaSqlProvider.class, method="findAllTutorsSql")
	List<Tutor> findAllTutors();
	
	@SelectProvider(type=TutorDynaSqlProvider.class, method="findTutorByIdSql")
	Tutor findTutorById(int tutorId);
	
	@SelectProvider(type=TutorDynaSqlProvider.class, method="findTutorByNameAndEmailSql")
	Tutor findTutorByNameAndEmail(@Param("name")String name, @Param("email")String email);
	
	@InsertProvider(type=TutorDynaSqlProvider.class, method="insertTutor")
	@Options(useGeneratedKeys=true, keyProperty="tutorId")
	int insertTutor(Tutor tutor);
	
	@UpdateProvider(type=TutorDynaSqlProvider.class, method="updateTutor")
	int updateTutor(Tutor tutor);
	
	@DeleteProvider(type=TutorDynaSqlProvider.class, method="deleteTutor")
	int deleteTutor(int tutorId);
		
	@SelectProvider(type=TutorDynaSqlProvider.class, method="selectTutorById")
	@ResultMap("com.mybatis3.mappers.TutorMapper.TutorResult")
	Tutor selectTutorById(int tutorId);
	
	
}
