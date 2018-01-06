package com.baeldung.beaninjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean(name = "pdfFileChecker")
    public FileChecker pdfFileChecker() {
        return new PdfFileChecker();
    }

    @Bean(name = "fileUploaderSetterInjection")
    public FileUploaderSetterInjection fileUploaderSetterInjection() {
        FileUploaderSetterInjection bean = new FileUploaderSetterInjection();
        bean.setFileChecker(pdfFileChecker());

        return bean;
    }

    @Bean(name = "fileUploaderConstructorInjection")
    public FileUploaderConstructorInjection fileUploaderConstructorInjection() {
        return new FileUploaderConstructorInjection(pdfFileChecker());
    }

}
