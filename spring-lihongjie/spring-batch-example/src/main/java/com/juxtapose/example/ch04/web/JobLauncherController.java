/**
 * 
 */
package com.juxtapose.example.ch04.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2013-3-20下午09:18:12
 */
@Controller
public class JobLauncherController {
	
	private static final String JOB_NAME = "jobName";
	private JobLauncher jobLauncher;
	private JobRegistry jobRegistry;
	
	public JobLauncherController(JobLauncher jobLauncher, JobRegistry jobRegistry) {
		this.jobLauncher = jobLauncher;
		this.jobRegistry = jobRegistry;
	}

	@RequestMapping(value="executeJob",method=RequestMethod.GET)
	public void launch(@RequestParam String jobName,HttpServletRequest request) 
			throws Exception {
		JobParameters jobParameters = bulidParameters(request);
		jobLauncher.run( jobRegistry.getJob(jobName),jobParameters);
	}

	private JobParameters bulidParameters(HttpServletRequest request) {
		JobParametersBuilder builder = new JobParametersBuilder();
		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			if(!JOB_NAME.equals(paramName)) {
				builder.addString(paramName,request.getParameter(paramName));
			}
		}
		return builder.toJobParameters();
	}
}
