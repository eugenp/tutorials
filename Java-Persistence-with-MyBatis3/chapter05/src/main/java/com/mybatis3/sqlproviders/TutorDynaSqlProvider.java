/**
 * 
 */
package com.mybatis3.sqlproviders;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.mybatis3.domain.Tutor;
/**
 * @author Siva
 *
 */
public class TutorDynaSqlProvider 
{

	public String findAllTutorsSql() 
	{
		return new SQL() {{
		SELECT("tutor_id as tutorId, name, email");
	    FROM("tutors");
		}}.toString();
	}
	
	public String findTutorByIdSql(final int tutorId) 
	{
		return new SQL() {{
		SELECT("tutor_id as tutorId, name, email");
	    FROM("tutors");
	    WHERE("tutor_id="+tutorId);
	    }}.toString();
	}
	
	
	public String findTutorByNameAndEmailSql(Map<String, Object> map) 
	{
		//String name = (String) map.get("name");
		//String email = (String) map.get("email");
		//System.err.println(name+":"+email);
		return new SQL() {{
		SELECT("tutor_id as tutorId, name, email");
	    FROM("tutors");
	    WHERE("name=#{name} AND email=#{email}");
	    }}.toString();
	}
	
	public String insertTutor(final Tutor tutor) {
		return new SQL() {{
        INSERT_INTO("TUTORS");
        
        if (tutor.getName() != null) {
            VALUES("NAME", "#{name}");
        }
        
        if (tutor.getEmail() != null) {
            VALUES("EMAIL", "#{email}");
        }
        }}.toString();
	}
	
	public String updateTutor(final Tutor tutor) 
	{
		return new SQL() {{
		UPDATE("TUTORS");
        
        if (tutor.getName() != null) {
        	SET("NAME = #{name}");
        }
        
        if (tutor.getEmail() != null) {
        	SET("EMAIL = #{email}");
        }
        WHERE("TUTOR_ID = #{tutorId}");
        }}.toString();
	}
	
	public String deleteTutor(int tutorId) 
	{
		return new SQL() {{
		DELETE_FROM("TUTORS");
        WHERE("TUTOR_ID = #{tutorId}");
        }}.toString();
	}
	
	public String selectTutorById() 
	{		
		return new SQL() {{
		SELECT("t.tutor_id, t.name as tutor_name, email, a.addr_id, street, city, state, zip, country,course_id, c.name as course_name, description, start_date, end_date");
		FROM("TUTORS t");
		LEFT_OUTER_JOIN("addresses a on t.addr_id=a.addr_id");
		LEFT_OUTER_JOIN("courses c on t.tutor_id=c.tutor_id");
        WHERE("t.TUTOR_ID = #{tutorId}");
        }}.toString();		
	}
}
