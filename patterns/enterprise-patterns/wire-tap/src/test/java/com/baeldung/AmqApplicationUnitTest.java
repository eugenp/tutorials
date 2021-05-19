package com.baeldung;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.junit5.params.Test;

public class AmqApplicationUnitTest {

	@Test
	public void testShallowCopyOfMessage() throws Exception {
		try (CamelContext context = new DefaultCamelContext()) {
			init(context);

			context.addRoutes(createRouteBuilder_shallowCopy(context));

			context.start();

			Thread.sleep(1000);
			context.stop();
		}
	}

	@Test
	public void testDeepCopyOfMessage() throws Exception {
		try (CamelContext context = new DefaultCamelContext()) {
			init(context);

			context.addRoutes(createRouteBuilder_deepCopy(context));

			context.start();

			Thread.sleep(1000);
			context.stop();
		}
	}

	private void init(CamelContext context) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"vm://localhost?broker.persistent=false");

		context.addComponent("direct", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
	}

	private RouteBuilder createRouteBuilder_shallowCopy(CamelContext context) {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start").log("Main route: Send '${body}' to tap router").wireTap("direct:tap")
						.log("Main route: Add 'two' to '${body}'").bean(MyBean.class, "addTwo")
						.log("Main route: Output '${body}'");

				from("direct:tap").log("Tap Wire route: received '${body}'")
						.log("Tap Wire route: Add 'three' to '${body}'").bean(MyBean.class, "addThree")
						.log("Tap Wire route: Output '${body}'");

				try (ProducerTemplate template = context.createProducerTemplate()) {
					context.start();
					template.sendBody("direct:start", "One");

					Thread.sleep(1000);
					context.stop();
				}
			}
		};
	}

	private RoutesBuilder createRouteBuilder_deepCopy(CamelContext context) {
		return new RouteBuilder() {
			public void configure() {
				from("direct:start").log("Send '${body}' to tap router").wireTap("direct:tap")
						.onPrepare(new MyPayloadClonePrepare()).end().delay(1000).log("Output of main '${body}'");

				from("direct:tap").log("Tap router received '${body}'").bean(MyBean.class, "addThree")
						.log("Output of tap '${body}'");
			}
		};
	}

}
