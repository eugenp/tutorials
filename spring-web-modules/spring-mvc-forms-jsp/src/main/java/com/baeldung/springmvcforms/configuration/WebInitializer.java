package com.baeldung.springmvcforms.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext container) throws ServletException {

		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(ApplicationConfiguration.class);
		ctx.setServletContext(container);

		// Manage the lifecycle of the root application context
		container.addListener(new ContextLoaderListener(ctx));

		ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(ctx));

		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");

	}
//	@Override
//	public void onStartup(ServletContext container) {
//		// Create the 'root' Spring application context
//		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//		rootContext.register(ServiceConfig.class, JPAConfig.class, SecurityConfig.class);
//
//		// Manage the lifecycle of the root application context
//		container.addListener(new ContextLoaderListener(rootContext));
//
//		// Create the dispatcher servlet's Spring application context
//		AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
//		dispatcherServlet.register(MvcConfig.class);
//
//		// Register and map the dispatcher servlet
//		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
//		dispatcher.setLoadOnStartup(1);
//		dispatcher.addMapping("/");
//
//	}
}
