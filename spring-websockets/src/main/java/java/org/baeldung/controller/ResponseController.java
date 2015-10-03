package java.org.baeldung.controller;

import java.org.baeldung.model.Response;
import java.org.baeldung.model.SimpleMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ResponseController {

	@MessageMapping("/welcome")
	@SendTo("/client/response")
	
	public Response welcomeMsg(SimpleMessage message) throws Exception {
		return new Response("Hi, " + message.getName() + "!");
	}

}
