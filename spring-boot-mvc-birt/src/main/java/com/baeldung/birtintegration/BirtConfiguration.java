package com.baeldung.birtintegration;


import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class BirtConfiguration implements ApplicationContextAware, DisposableBean {

    private ApplicationContext applicationContext;
    private IReportEngine birtEngine;

    @Bean
    public IReportEngine getObject(){
        Logger logger = LoggerFactory.getLogger(IReportEngine.class);
        EngineConfig config = new EngineConfig();

        //This line injects the Spring Context into the BIRT Context
        config.getAppContext().put("spring", this.applicationContext );
       // config.setLogConfig(logger. , this.logLevel);
        try {
            Platform.startup( config );
        }
        catch ( BirtException e ) {
            throw new RuntimeException ( "Could not start the Birt engine!", e) ;
        }

        IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
        IReportEngine be = factory.createReportEngine( config );
        this.birtEngine = be ;


        return be ;
    }

    @Override public void destroy() throws Exception {
        birtEngine.destroy();
        Platform.shutdown() ;
    }

    @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
