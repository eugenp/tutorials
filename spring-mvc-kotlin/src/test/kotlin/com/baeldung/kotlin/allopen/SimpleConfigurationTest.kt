package com.baeldung.kotlin.allopen

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.support.AnnotationConfigContextLoader

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader::class,
        classes = arrayOf(SimpleConfiguration::class))
class SimpleConfigurationTest {

    @Test
    fun contextLoads() {
    }

}