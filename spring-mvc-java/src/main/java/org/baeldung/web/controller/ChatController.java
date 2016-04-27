package org.baeldung.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.baeldung.model.Message;
import org.baeldung.model.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public OutputMessage send(final Message message) throws Exception {

		final String time = new SimpleDateFormat("HH:mm").format(new Date());
		return new OutputMessage(message.getText(), time);
	}

}
