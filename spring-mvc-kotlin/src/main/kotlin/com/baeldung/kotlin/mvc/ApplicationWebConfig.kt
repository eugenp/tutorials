package com.baeldung.kotlin.mvc

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.thymeleaf.spring4.SpringTemplateEngine
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring4.view.ThymeleafViewResolver
import org.thymeleaf.templatemode.TemplateMode

@EnableWebMvc
@Configuration
open class ApplicationWebConfig : WebMvcConfigurerAdapter(), ApplicationContextAware {

    private var applicationContext: ApplicationContext? = null

    override fun setApplicationContext(applicationContext: ApplicationContext?) {
        this.applicationContext = applicationContext
    }

    override fun addViewControllers(registry: ViewControllerRegistry?) {
        super.addViewControllers(registry)

        registry!!.addViewController("/welcome.html")
    }

    @Bean
    open fun templateResolver(): SpringResourceTemplateResolver {
        return SpringResourceTemplateResolver()
                .apply { prefix = "/WEB-INF/view/" }
                .apply { suffix = ".html"}
                .apply { templateMode = TemplateMode.HTML }
                .apply { setApplicationContext(applicationContext) }
    }

    @Bean
    open fun templateEngine(): SpringTemplateEngine {
        return SpringTemplateEngine()
                .apply { setTemplateResolver(templateResolver()) }
    }

    @Bean
    open fun viewResolver(): ThymeleafViewResolver {
        return ThymeleafViewResolver()
                .apply { templateEngine = templateEngine() }
                .apply { order = 1 }
    }
}