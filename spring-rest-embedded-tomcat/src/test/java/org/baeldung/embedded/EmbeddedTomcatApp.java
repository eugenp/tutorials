package org.baeldung.embedded;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class EmbeddedTomcatApp {

    private Tomcat tomcatInstance;
    private WebApplicationContext webApplicationContext;
    private CountDownLatch started = new CountDownLatch(1);

    public void start() throws Exception {
        tomcatInstance = new Tomcat();
        tomcatInstance.setBaseDir(new File(getClass().getResource(".")
            .toURI()).getAbsolutePath()); // Tomcat's temporary directory
        tomcatInstance.setPort(0);

        Context webapp = tomcatInstance.addWebapp("", new File("src/main/webapp/").getAbsolutePath());

        webapp.addLifecycleListener(event -> {
            if (event.getType()
                .equals("after_stop")) {
                started.countDown();
            } else if (event.getType()
                .equals("after_start")) {
                webApplicationContext = WebApplicationContextUtils
                    .findWebApplicationContext(webapp.getServletContext());

                ((ConfigurableListableBeanFactory) webApplicationContext
                    .getAutowireCapableBeanFactory()).registerSingleton("baseUrl", getBaseUrl());

                started.countDown();
            }
        });

        tomcatInstance.start();
        started.await();
    }

    public Tomcat getTomcatInstance() {
        return this.tomcatInstance;
    }

    public String getBaseUrl() {
        return String.format("http://localhost:%d%s", getLocalPort(), getWebApplicationContext().getServletContext()
            .getContextPath());
    }

    public int getLocalPort() {
        return tomcatInstance.getConnector()
            .getLocalPort();
    }

    public WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    public boolean isStarted() {
        return started.getCount() == 0;
    }

    public boolean isStartedSucessfully() {
        return webApplicationContext != null;
    }
}
