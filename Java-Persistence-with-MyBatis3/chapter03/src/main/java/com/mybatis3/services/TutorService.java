package com.mybatis3.services;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybatis3.domain.Tutor;
import com.mybatis3.mappers.TutorMapper;
import com.mybatis3.util.MyBatisUtil;


public class TutorService 
{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	public Tutor findTutorById(int tutorId) {
		logger.debug("findTutorById :"+tutorId);
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			TutorMapper mapper = sqlSession.getMapper(TutorMapper.class);
			return mapper.selectTutorById(tutorId);
		} 
		
		finally {
			sqlSession.close();
		}
	}

	
}
