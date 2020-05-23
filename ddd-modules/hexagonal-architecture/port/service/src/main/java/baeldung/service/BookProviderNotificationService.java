package baeldung.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookProviderNotificationService {

    private static final String BOOK_PROVIDER_NOTIFICATION_QUEUE = "book-provider-queue";
    private final JmsTemplate jmsTemplate;

    @Autowired
    public BookProviderNotificationService(ApplicationContext applicationContext) {
        this.jmsTemplate = applicationContext.getBean(JmsTemplate.class);
    }

    public void notifyMissingBook(String query) {
        jmsTemplate.convertAndSend(
                BOOK_PROVIDER_NOTIFICATION_QUEUE,
                "Readers looking for " + query + " but nothing found. Send something soon");
    }

    public void notifyNotEnoughBooks(String query, Integer numberOfCurrentBooks) {
        if (numberOfCurrentBooks < 10) {
            jmsTemplate.convertAndSend(BOOK_PROVIDER_NOTIFICATION_QUEUE,
                    "Send notification to provider: Running out of " + query + ". Send more.");
        }
    }

}
