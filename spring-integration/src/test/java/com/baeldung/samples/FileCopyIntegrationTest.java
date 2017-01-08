/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baeldung.samples;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class FileCopyIntegrationTest {

    //

    @Test
    public void whenFileCopyConfiguration_thanFileCopiedSuccessfully() throws InterruptedException {
        final AbstractApplicationContext context = new AnnotationConfigApplicationContext(FileCopyConfig.class.getCanonicalName());
        context.registerShutdownHook();
        Thread.sleep(5000);
    }

    @Test
    public void publish() throws InterruptedException {
        final AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:META-INF/spring/integration/spring-integration-file-publish-context.xml");

        Thread.sleep(15000);
    }

}
