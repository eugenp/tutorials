package com.baeldung;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmqApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AmqApplication.class, args);

		try (CamelContext context = new DefaultCamelContext()) {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					"vm://localhost?broker.persistent=false");

			context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

			context.addRoutes(new RouteBuilder() {
				public void configure() {

					from("file:src/data?noop=true").to("jms:incomingOrders");

					from("jms:incomingOrders").wireTap("jms:orderAudit").choice()
							.when(header("CamelFileName").endsWith(".xml")).to("jms:xmlOrders")
							.when(header("CamelFileName").regex("^.*(csv|csl)$")).to("jms:csvOrders");

					from("jms:xmlOrders").log("Received XML order: ${header.CamelFileName}").to("mock:xml");

					from("jms:csvOrders").log("Received CSV order: ${header.CamelFileName}").to("mock:csv");

					from("jms:orderAudit").log("Wire tap received order: ${header.CamelFileName}").to("mock:wiretap");
				}
			});

			context.start();

			Thread.sleep(1000);
			context.stop();
		}
	}

}
