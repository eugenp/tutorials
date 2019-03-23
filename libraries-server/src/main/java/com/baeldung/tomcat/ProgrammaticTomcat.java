package com.baeldung.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.io.File;

/**
 * Created by adi on 1/10/18.
 */
public class ProgrammaticTomcat {

    private Tomcat tomcat = null;

    // uncomment for live test
    // public static void main(String[] args) throws LifecycleException, ServletException, URISyntaxException, IOException {
    // startTomcat();
    // }

    public void startTomcat() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setHostname("localhost");
        String appBase = ".";
        tomcat.getHost().setAppBase(appBase);

        File docBase = new File(System.getProperty("java.io.tmpdir"));
        Context context = tomcat.addContext("", docBase.getAbsolutePath());

        // add a servlet
        Class servletClass = MyServlet.class;
        Tomcat.addServlet(context, servletClass.getSimpleName(), servletClass.getName());
        context.addServletMappingDecoded("/my-servlet/*", servletClass.getSimpleName());

        // add a filter and filterMapping
        Class filterClass = MyFilter.class;
        FilterDef myFilterDef = new FilterDef();
        myFilterDef.setFilterClass(filterClass.getName());
        myFilterDef.setFilterName(filterClass.getSimpleName());
        context.addFilterDef(myFilterDef);

        FilterMap myFilterMap = new FilterMap();
        myFilterMap.setFilterName(filterClass.getSimpleName());
        myFilterMap.addURLPattern("/my-servlet/*");
        context.addFilterMap(myFilterMap);

        tomcat.start();
        // uncomment for live test
        // tomcat
        // .getServer()
        // .await();
    }

    public void stopTomcat() throws LifecycleException {
        tomcat.stop();
        tomcat.destroy();
    }
}
