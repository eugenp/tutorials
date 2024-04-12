package com.baeldung;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
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
			connectionFactory.setTrustAllPackages(true);
			context.addComponent("direct", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
			addRoute(context);

			try (ProducerTemplate template = context.createProducerTemplate()) {
				context.start();

				MyPayload payload = new MyPayload("One");
				template.sendBody("direct:source", payload);
				Thread.sleep(10000);
			} finally {
				context.stop();
			}
		}
	}

	private static void addRoute(CamelContext context) throws Exception {
		context.addRoutes(newExchangeRoute());
	}

	static RoutesBuilder traditionalWireTapRoute() {
		return new RouteBuilder() {
			public void configure() {

				from("direct:source").log("Main route: Send '${body}' to tap router").wireTap("direct:tap").delay(1000)
						.log("Main route: Add 'two' to '${body}'").bean(MyBean.class, "addTwo").to("direct:destination")
						.log("Main route: Output '${body}'");

				from("direct:tap").log("Tap Wire route: received '${body}'")
						.log("Tap Wire route: Add 'three' to '${body}'").bean(MyBean.class, "addThree")
						.log("Tap Wire route: Output '${body}'");

				from("direct:destination").log("Output at destination: '${body}'");
			}
		};
	}

	static RoutesBuilder newExchangeRoute() throws Exception {
		return new RouteBuilder() {
			public void configure() throws Exception {

				from("direct:source").wireTap("direct:tap").onPrepare(new MyPayloadClonePrepare()).end().delay(1000);

				from("direct:tap").bean(MyBean.class, "addThree");
			}
		};
	}

}
