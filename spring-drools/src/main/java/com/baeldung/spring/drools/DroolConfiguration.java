package com.baeldung.spring.drools;

import com.baeldung.spring.drools.service.ApplicantService;
import com.baeldung.spring.drools.service.ProductService;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@Configuration
public class DroolConfiguration {

    private static final String RULES_PATH = "com/baeldung/spring/drools/rules/";

    @Bean
    public KieFileSystem kieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
        for (Resource file : getRuleFiles()) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
        }
        return kieFileSystem;
    }

    private Resource[] getRuleFiles() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        return resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
    }

    @Bean
    public KieContainer kieContainer() throws IOException {
        final KieRepository kieRepository = getKieServices().getRepository();

        kieRepository.addKieModule(new KieModule() {
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });

        KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();

        return getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());
    }

    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    public KieBase kieBase() throws IOException {
        return kieContainer().getKieBase();
    }

    @Bean
    public KieSession kieSession() throws IOException {
        return kieContainer().newKieSession();
    }

    @Bean
    public ApplicantService getApplicantService(){
        return new ApplicantService();
    }

    @Bean
    public ProductService getProductService(KieContainer kieContainer){
        return new ProductService();
    }

    @Bean
    public KModuleBeanFactoryPostProcessor kiePostProcessor() {
        return new KModuleBeanFactoryPostProcessor();
    }
}