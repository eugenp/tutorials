package com.baeldung.bootique;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import com.baeldung.bootique.service.HelloService;

import io.bootique.BQRuntime;
import io.bootique.test.junit.BQDaemonTestFactory;
import io.bootique.test.junit.BQTestFactory;

public class AppTest {

	@Rule
	public BQTestFactory bqTestFactory = new BQTestFactory();

	@Rule
	public BQDaemonTestFactory bqDaemonTestFactory = new BQDaemonTestFactory();

	@Test
	public void givenService_expectBoolen() {
		BQRuntime runtime = bqTestFactory.app("--server").autoLoadModules().createRuntime();
		HelloService service = runtime.getInstance(HelloService.class);
		assertEquals(true, service.save());
	}

}
