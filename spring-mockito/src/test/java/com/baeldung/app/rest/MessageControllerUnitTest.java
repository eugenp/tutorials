package com.baeldung.app.rest;

import com.baeldung.app.api.MessageApi;
import com.baeldung.domain.model.Message;
import com.baeldung.domain.service.MessageService;
import com.baeldung.domain.util.MessageMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerUnitTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    @Test
    public void createMessage_NewMessage_OK() {
        MessageApi messageApi = new MessageApi();
        messageApi.setFrom("me");
        messageApi.setTo("you");
        messageApi.setText("Hello, you!");

        messageController.createMessage(messageApi);

        Message message = new Message();
        message.setFrom("me");
        message.setTo("you");
        message.setText("Hello, you!");

        Mockito.verify(messageService, times(1)).deliverMessage(Matchers.argThat(new MessageMatcher(message)));
    }
}
