package org.baeldung.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.baeldung.model.Message;
import org.baeldung.model.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChatController {

	@RequestMapping(value = "/showChat", method = RequestMethod.GET)
	public String displayChat() {
		return "chat";
	}

	@MessageMapping("/chat")
	@SendTo("/topic/")
	public OutputMessage send(final Message message) throws Exception {

		final String time = new SimpleDateFormat("HH:mm").format(new Date());
		return new OutputMessage(message.getText(), time);
	}

}
