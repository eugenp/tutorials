package org.baeldung.servlet;

//
//public class NewWebApp extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//    public NewWebApp() {
//        super();
//    }
//
//    // API
//
//    @Override
//    public void onStartup(final ServletContext servletContext) throws ServletException {
//        final AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
//        root.setServletContext(servletContext);
//        root.scan("org.baeldung.spring");
//        root.refresh();
//
//        final Dynamic servlet = servletContext.addServlet("mvc", new DispatcherServlet(root));
//        servlet.setLoadOnStartup(1);
//        servlet.addMapping("/*");
//    }
//
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return null;
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class<?>[] { ClientWebConfig.class };
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[] { "/*" };
//    }
//
//}

