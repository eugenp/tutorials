package com.baeldung.evaluation.di;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.evaluation.di.config.Config;
import com.baeldung.evaluation.di.example.PaymentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class DependencyInjectionJavaConfigTest implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Test
	public void givenJavaConfig_whenComponentCreated_thenConstructorBasedDI() {
		PaymentService paymentService = applicationContext.getBean(PaymentService.class);
		assertNotNull("Failed: Constructor-Based Dependency injection, Java Config, PaymentService",
				paymentService.getPayment());
	}

	@Test
	public void givenJavaConfig_whenComponentCreated_thenSetterBasedDI() {
		PaymentService paymentService = applicationContext.getBean(PaymentService.class);
		assertNotNull("Failed: Setter-Based Dependency injection, Java Config, PaymentService",
				paymentService.getDetails());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
