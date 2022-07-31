package com.baeldung.app.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.baeldung.app.api.MessageApi;
import com.baeldung.domain.model.Message;
import com.baeldung.domain.service.MessageService;
import com.baeldung.domain.util.MessageMatcher;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerUnitTest {

	@Mock
	private MessageService messageService;

	@InjectMocks
	private MessageController messageController;

	@Test
	public void givenMsg_whenVerifyUsingAnyMatcher_thenOk() {
		MessageApi messageApi = new MessageApi();
		messageApi.setFrom("me");
		messageApi.setTo("you");
		messageApi.setText("Hello, you!");

		messageController.createMessage(messageApi);

		verify(messageService, times(1)).deliverMessage(any(Message.class));
	}

	@Test
	public void givenMsg_whenVerifyUsingMessageMatcher_thenOk() {
		MessageApi messageApi = new MessageApi();
		messageApi.setFrom("me");
		messageApi.setTo("you");
		messageApi.setText("Hello, you!");

		messageController.createMessage(messageApi);

		Message message = new Message();
		message.setFrom("me");
		message.setTo("you");
		message.setText("Hello, you!");

		verify(messageService, times(1)).deliverMessage(argThat(new MessageMatcher(message)));
	}
}
