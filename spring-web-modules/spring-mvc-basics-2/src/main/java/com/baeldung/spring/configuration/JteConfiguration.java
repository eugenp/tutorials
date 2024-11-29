package com.baeldung.spring.configuration;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.output.PrintWriterOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.util.Map;

@Configuration
@EnableWebMvc
class JteConfiguration implements WebMvcConfigurer {

    @Bean
    TemplateEngine templateEngine(ServletContext context) {
        String root = context.getRealPath("/WEB-INF/views/");
        DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(Path.of(root));
        String classesPath = context.getRealPath("/WEB-INF/classes/");
        return TemplateEngine.create(codeResolver, Path.of(classesPath), ContentType.Html, this.getClass().getClassLoader());
    }

    @Bean
    ViewResolver viewResolver(TemplateEngine templateEngine) {
        return (viewName, locale) -> (model, request, response) -> {
            templateEngine.render(viewName + ".jte", (Map<String, Object>) model, new PrintWriterOutput(response.getWriter()));
        };
    }


}
