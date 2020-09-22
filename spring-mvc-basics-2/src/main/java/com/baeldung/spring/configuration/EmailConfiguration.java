package com.baeldung.spring.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;

@ComponentScan(basePackages = { "com.baeldung.spring.mail" })
@PropertySource(value={"classpath:application.properties"})
public class EmailConfiguration {

	@Value("${spring.mail.host}")
	private String mailServerHost;

	@Value("${spring.mail.port}")
	private Integer mailServerPort;
	
	@Value("${spring.mail.username}")
	private String mailServerUsername;

	@Value("${spring.mail.password}")
	private String mailServerPassword;
	
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String mailServerAuth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String mailServerStartTls;
		
	@Value("${spring.mail.templates.external}")
	private boolean mailTemplatesExternal;

	@Value("${spring.mail.templates.path}")
	private String mailTemplatesPath;
    
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost(mailServerHost);
        mailSender.setPort(mailServerPort);
        
        mailSender.setUsername(mailServerUsername);
        mailSender.setPassword(mailServerPassword);
        
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", mailServerAuth);
        props.put("mail.smtp.starttls.enable", mailServerStartTls);
        props.put("mail.debug", "true");
        
        return mailSender;
    }
    
    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("This is the test email template for your email:\n%s\n");
        return message;
    }
    
    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
    	AbstractConfigurableTemplateResolver templateResolver;
    	templateResolver = mailTemplatesExternal ? new FileTemplateResolver() : new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(mailTemplatesPath + "/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }
    
    @Bean 
    public FreeMarkerConfigurer freemarkerConfig() throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
        if (mailTemplatesExternal) {
        	TemplateLoader templateLoader;
			templateLoader = new FileTemplateLoader(new File(mailTemplatesPath));
			configuration.setTemplateLoader(templateLoader);
        } else {
        	configuration.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/" + mailTemplatesPath));
        }
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setConfiguration(configuration);
        return freeMarkerConfigurer; 
    }
    
    @Bean 
    public FreeMarkerViewResolver freemarkerViewResolver() { 
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver(); 
        resolver.setCache(true); 
        resolver.setPrefix(""); 
        resolver.setSuffix(".ftl"); 
        return resolver; 
    }

    
    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("/mailMessages");
        return messageSource;
    }

}
