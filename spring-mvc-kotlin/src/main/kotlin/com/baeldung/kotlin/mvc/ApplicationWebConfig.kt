package com.baeldung.kotlin.mvc

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.view.InternalResourceViewResolver
import org.springframework.web.servlet.view.JstlView





@EnableWebMvc
@Configuration
open class ApplicationWebConfig: WebMvcConfigurerAdapter() {

    override fun addViewControllers(registry: ViewControllerRegistry?) {
        super.addViewControllers(registry)

        registry!!.addViewController("/welcome.html")
    }

    @Bean
    open fun viewResolver(): ViewResolver {
        val bean = InternalResourceViewResolver()

        bean.setViewClass(JstlView::class.java)
        bean.setPrefix("/WEB-INF/view/")
        bean.setSuffix(".jsp")

        return bean
    }

}