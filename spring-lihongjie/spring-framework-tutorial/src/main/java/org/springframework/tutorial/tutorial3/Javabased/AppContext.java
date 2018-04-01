package org.springframework.tutorial.tutorial3.Javabased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 包含bean定义的AppContext配置类
 * @author lihongjie
 *
 */

@Configuration
public class AppContext {
	
	@Bean
	public Course course() {
		Course course = new Course();
		course.setModule(module());
		return course;
	}
	
	@Bean
	public Module module() {
		Module module = new Module();
		module.setAssignment(assignment());
		return module;
	}
	
	@Bean
	public Assignment assignment() {
		return new Assignment();
	}
}
