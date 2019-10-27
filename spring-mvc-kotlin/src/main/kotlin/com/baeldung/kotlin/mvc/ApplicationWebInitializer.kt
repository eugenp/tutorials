package com.baeldung.kotlin.mvc

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class ApplicationWebInitializer : AbstractAnnotationConfigDispatcherServletInitializer() {

    override fun getRootConfigClasses(): Array<Class<*>>? {
        return null
    }

    override fun getServletMappings(): Array<String> {
        return arrayOf("/")
    }

    override fun getServletConfigClasses(): Array<Class<*>> {
        return arrayOf(ApplicationWebConfig::class.java)
    }
}