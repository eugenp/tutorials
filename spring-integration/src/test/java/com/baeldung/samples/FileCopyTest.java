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

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;


/**
 * Starts the Spring Context and will initialize the Spring Integration routes.
 *
 * @author Baeldung
 * @since 1.0
 *
 */
public final class FileCopyTest {

	private static final Logger LOGGER = Logger.getLogger(FileCopyTest.class);

	@Test
	public void test() throws InterruptedException {


		final AbstractApplicationContext context =
				new ClassPathXmlApplicationContext("classpath:META-INF/spring/integration/spring-integration-file-copy-context.xml");

		Thread.sleep(5000);


	}
}
