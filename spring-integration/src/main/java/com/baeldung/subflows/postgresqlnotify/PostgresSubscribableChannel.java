package com.baeldung.subflows.postgresqlnotify;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.sql.DataSource;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.integration.channel.AbstractSubscribableChannel;
import org.springframework.integration.dispatcher.MessageDispatcher;
import org.springframework.integration.dispatcher.UnicastingDispatcher;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * <p>This is a simplified backport of the version available on Spring Integration 6.x. for illustration purposes only.</p>
 * <p>In particular, this implementation <b>does not persist messages</b> as the full-fledged version does.</p>
 * 
 * @see https://github.com/spring-projects/spring-integration/blob/6.0.x/spring-integration-jdbc/src/main/java/org/springframework/integration/jdbc/channel/PostgresSubscribableChannel.java
 * 
 */
public class PostgresSubscribableChannel extends AbstractSubscribableChannel {
    
    private static Logger log = LoggerFactory.getLogger(PostgresSubscribableChannel.class);

    private static final String HEADER_FIELD = "h";
    private static final String BODY_FIELD = "b";

    private final Supplier<Connection> connectionProvider;
    private final String channelName;
    private final MessageDispatcher dispatcher = new UnicastingDispatcher();
    private final DataSource ds;
    private CountDownLatch startLatch;
    private Executor executor;
    private ObjectMapper om;
    private NotifierTask notifierTask;

    public PostgresSubscribableChannel(String channelName, Supplier<Connection> connectionProvider, DataSource ds, ObjectMapper om) {

        this.connectionProvider = connectionProvider;
        this.channelName = channelName;
        this.ds = ds;
        this.executor = new SimpleAsyncTaskExecutor("posgres-subscriber-" + channelName);
        this.om = om;

    }

    @Override
    protected MessageDispatcher getDispatcher() {
        return this.dispatcher;
    }

    @Override
    public boolean subscribe(MessageHandler handler) {
        boolean r = super.subscribe(handler);
        if (r && super.getSubscriberCount() == 1) {
            log.info("subscribe: starting listener thread...");
            startListenerThread();
        }
        return r;
    }

    @Override
    public boolean unsubscribe(MessageHandler handle) {
        boolean r = super.unsubscribe(handle);
        if (r && super.getSubscriberCount() == 0) {
            log.info("unsubscribe: stopping listener thread...");
            stopListenerThread();
        }

        return r;
    }

    private void startListenerThread() {

        startLatch = new CountDownLatch(1);
        notifierTask = new NotifierTask(connectionProvider.get());
        executor.execute(notifierTask);
        try {
            startLatch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException iex) {
            throw new RuntimeException(iex);
        }
    }

    private void stopListenerThread() {
        notifierTask.kill();
    }

    @Override
    protected boolean doSend(Message<?> message, long timeout) {
        try {
            String msg = prepareNotifyPayload(message);
            
            try( Connection c = ds.getConnection()) {
                log.debug("doSend:sending message: channel={}", channelName);
                c.createStatement().execute("NOTIFY " + channelName + ", '" + msg + "'");
            }
            
            return true;
        } catch (Exception ex) {
            throw new MessageDeliveryException(message, "Unable to deliver message: " + ex.getMessage(),ex);
        }
    }
    
    protected String prepareNotifyPayload(Message<?> message) throws JsonProcessingException {
        Map<String, Object> rawMap = new HashMap<>();
        rawMap.putAll(message.getHeaders());
        JsonNode headerData = om.valueToTree(rawMap);
        JsonNode bodyData = om.valueToTree(message.getPayload());

        ObjectNode msg = om.getNodeFactory()
          .objectNode();
        msg.set(HEADER_FIELD, headerData);
        msg.set(BODY_FIELD, bodyData);
        return om.writeValueAsString(msg);
    }

    // Inner class that listens for notifications and dispatches them to subscribers
    class NotifierTask implements Runnable {

        private final Connection conn;
        private final CountDownLatch stopLatch = new CountDownLatch(1);

        NotifierTask(Connection conn) {
            this.conn = conn;
        }

        void kill() {
            try {
                this.conn.close();
                stopLatch.await(10, TimeUnit.SECONDS);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public void run() {

            startLatch.countDown();

            try (Statement st = conn.createStatement()) {
                log.debug("notifierTask: enabling notifications for channel {}", channelName);
                st.execute("LISTEN " + channelName);

                PGConnection pgConn = conn.unwrap(PGConnection.class);

                while (!Thread.currentThread()
                    .isInterrupted()) {
                    log.debug("notifierTask: wainting for notifications. channel={}", channelName);
                    PGNotification[] nts = pgConn.getNotifications();
                    log.debug("notifierTask: processing {} notification(s)", nts.length);

                    for (PGNotification n : nts) {
                        Message<?> msg = convertNotification(n);
                        getDispatcher().dispatch(msg);
                    }
                }
            } catch (SQLException sex) {
                // TODO: Handle exceptions
            } finally {
                stopLatch.countDown();
            }
        }

        @SuppressWarnings("unchecked")
        private Message<?> convertNotification(PGNotification n) {
            String payload = n.getParameter();
            try {
                JsonNode root = om.readTree(payload);

                if (!root.isObject()) {
                    return new ErrorMessage(new IllegalArgumentException("Message is not a JSON Object"));
                }

                Map<String, Object> hdr;
                JsonNode headers = root.path(HEADER_FIELD);

                if (headers.isObject()) {
                    hdr = om.treeToValue(headers, Map.class);
                } else {
                    hdr = Collections.emptyMap();
                }

                JsonNode body = root.path(BODY_FIELD);
                return MessageBuilder
                  .withPayload(body.isTextual()?body.asText():body)
                  .copyHeaders(hdr)
                  .build();
            } catch (Exception ex) {
                return new ErrorMessage(ex);
            }
        }
    }
}
