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

import com.baeldung.app.api.MessageDTO;
import com.baeldung.domain.model.Message;
import com.baeldung.domain.service.MessageService;
import com.baeldung.domain.util.MessageMatcher;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerUnitTest {

        @InjectMocks
        private MessageController messageController;
    
	@Mock
	private MessageService messageService;

	@Test
	public void givenMsg_whenVerifyUsingAnyMatcher_thenOk() {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setFrom("me");
		messageDTO.setTo("you");
		messageDTO.setText("Hello, you!");

		messageController.createMessage(messageDTO);

		verify(messageService, times(1)).deliverMessage(any(Message.class));
	}

	@Test
	public void givenMsg_whenVerifyUsingMessageMatcher_thenOk() {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setFrom("me");
		messageDTO.setTo("you");
		messageDTO.setText("Hello, you!");

		messageController.createMessage(messageDTO);

		Message message = new Message();
		message.setFrom("me");
		message.setTo("you");
		message.setText("Hello, you!");

		verify(messageService, times(1)).deliverMessage(argThat(new MessageMatcher(message)));
	}
}
