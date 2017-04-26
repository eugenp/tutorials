package com.baeldung.spring.drools;


import com.baeldung.spring.drools.model.Applicant;
import com.baeldung.spring.drools.model.Product;
import com.baeldung.spring.drools.model.SuggestedRole;
import com.baeldung.spring.drools.service.ApplicantService;
import com.baeldung.spring.drools.service.ProductService;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        ApplicantService applicantService=(ApplicantService)ctx.getBean("applicantService");
        applicantService.suggestARoleForApplicant(new Applicant("Baljeet",37,1200000.0,8),new SuggestedRole());

        ProductService productService=(ProductService)ctx.getBean("productService");
        Product returnedProduct=productService.applyLabelToProduct(new Product("Microwave","Book"));

    }

    @Bean
    public ApplicantService getApplicantService(KieContainer kieContainer){
        return new ApplicantService(kieContainer);
    }

    @Bean
    public ProductService getProductService(KieContainer kieContainer){
        return new ProductService(kieContainer);
    }

    @Bean
    public KieContainer kieContainer() {
        return KieServices.Factory.get().getKieClasspathContainer();
    }

}
