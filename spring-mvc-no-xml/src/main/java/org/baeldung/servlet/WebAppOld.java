package org.baeldung.servlet;

// public class WebApp implements WebApplicationInitializer {
//
// public WebApp() {
// super();
// }
//
// // API
//
// @Override
// public void onStartup(final ServletContext servletContext) throws ServletException {
// final AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
// root.setServletContext(servletContext);
// root.scan("org.baeldung.spring");
// root.refresh();
//
// final Dynamic servlet = servletContext.addServlet("mvc", new DispatcherServlet(root));
// servlet.setLoadOnStartup(1);
// servlet.addMapping("/");
// }
//
// }
