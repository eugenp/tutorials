package com.baeldung.spring.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class JsonMessageConverter implements MessageConverter {

	private final JsonMapper jsonMapper = JsonMapper.builder()
			.build();

	@SneakyThrows
	@Override
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {

		var json = jsonMapper.writeValueAsString(object);
		var msg = session.createTextMessage(json);
		msg.setStringProperty("_type", object.getClass().getName());
		return msg;
	}

	@Override
	@SneakyThrows
	public Object fromMessage(Message message)
			throws JMSException, MessageConversionException {

		if (message instanceof TextMessage msg ) {
			var clazz = Class.forName(msg.getStringProperty("_type"));
			return jsonMapper.readValue(msg.getText(), clazz);
		}
		throw new MessageConversionException("Message is not of type TextMessage");
	}
}
