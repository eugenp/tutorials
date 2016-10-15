package com.baeldung.spring.web.config;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MainWebAppInitializer implements WebApplicationInitializer {

	private static final String TMP_FOLDER = "/tmp";
	private static final int MAX_UPLOAD_SIZE = 5 * 1024 * 1024; // 5 MB

	/**
	 * Register and configure all Servlet container components necessary to power the web application.
	 */
	public void onStartupOrig(final ServletContext sc) throws ServletException {

		// Create the 'root' Spring application context
		final AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
		root.scan("com.baeldung.spring.web.config");
		// root.getEnvironment().setDefaultProfiles("embedded");

		// Manages the lifecycle of the root application context
		sc.addListener(new ContextLoaderListener(root));

		// Handles requests into the application
		final ServletRegistration.Dynamic appServlet = sc.addServlet("mvc", new DispatcherServlet(new GenericWebApplicationContext()));
		appServlet.setLoadOnStartup(1);

		// final MultipartConfigElement multipartConfigElement = new
		// MultipartConfigElement(TMP_FOLDER, MAX_UPLOAD_SIZE,
		// MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);
		//
		// appServlet.setMultipartConfig(multipartConfigElement);

		final Set<String> mappingConflicts = appServlet.addMapping("/");
		if (!mappingConflicts.isEmpty()) {
			throw new IllegalStateException("'appServlet' could not be mapped to '/' due " + "to an existing mapping. This is a known issue under Tomcat versions " + "<= 7.0.14; see https://issues.apache.org/bugzilla/show_bug.cgi?id=51278");
		}
	}

	@Override
	public void onStartup(final ServletContext sc) throws ServletException {
		// Create the 'root' Spring application context
		final AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.setConfigLocation("com.baeldung.spring.web.config.root");

		// Manages the lifecycle of the root application context
		sc.addListener(new ContextLoaderListener(rootContext));

		final AnnotationConfigWebApplicationContext mainBuildingContext = new AnnotationConfigWebApplicationContext();
		mainBuildingContext.setConfigLocation("com.baeldung.spring.web.config.mainBuilding");
		final ServletRegistration.Dynamic mainBuildingServlet = sc.addServlet("MainBuilding",
				new DispatcherServlet(mainBuildingContext));
		mainBuildingServlet.setLoadOnStartup(1);
		mainBuildingServlet.addMapping("/MainBuilding/*");

		final AnnotationConfigWebApplicationContext secondaryBuildingContext = new AnnotationConfigWebApplicationContext();
		secondaryBuildingContext.setConfigLocation("com.baeldung.spring.web.config.secondaryBuilding");
		final ServletRegistration.Dynamic secondaryBuildingServlet = sc.addServlet("SecondaryBuilding",
				new DispatcherServlet(secondaryBuildingContext));
		secondaryBuildingServlet.setLoadOnStartup(1);
		secondaryBuildingServlet.addMapping("/SecondaryBuilding/*");
	}

}
