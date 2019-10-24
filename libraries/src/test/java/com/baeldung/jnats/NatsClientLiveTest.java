package com.baeldung.jnats;

import io.nats.client.Message;
import io.nats.client.SyncSubscription;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NatsClientLiveTest {

    @Test
    public void givenMessageExchange_MessagesReceived() throws Exception {

        NatsClient client = connectClient();

        SyncSubscription fooSubscription = client.subscribeSync("foo.bar");
        SyncSubscription barSubscription = client.subscribeSync("bar.foo");
        client.publishMessage("foo.bar", "bar.foo", "hello there");

        Message message = fooSubscription.nextMessage(200);
        assertNotNull("No message!", message);
        assertEquals("hello there", new String(message.getData()));

        client.publishMessage(message.getReplyTo(), message.getSubject(), "hello back");

        message = barSubscription.nextMessage(200);
        assertNotNull("No message!", message);
        assertEquals("hello back", new String(message.getData()));
    }


    private NatsClient connectClient() {
        return new NatsClient();
    }

    @Test
    public void whenWildCardSubscription_andMatchTopic_MessageReceived() throws Exception {

        NatsClient client = connectClient();

        SyncSubscription fooSubscription = client.subscribeSync("foo.*");

        client.publishMessage("foo.bar", "bar.foo", "hello there");

        Message message = fooSubscription.nextMessage(200);
        assertNotNull("No message!", message);
        assertEquals("hello there", new String(message.getData()));
    }

    @Test
    public void whenWildCardSubscription_andNotMatchTopic_NoMessageReceived() throws Exception {

        NatsClient client = connectClient();

        SyncSubscription fooSubscription = client.subscribeSync("foo.*");

        client.publishMessage("foo.bar.plop", "bar.foo", "hello there");

        Message message = fooSubscription.nextMessage(200);
        assertNull("Got message!", message);


        SyncSubscription barSubscription = client.subscribeSync("foo.>");

        client.publishMessage("foo.bar.plop", "bar.foo", "hello there");

        message = barSubscription.nextMessage(200);
        assertNotNull("No message!", message);
        assertEquals("hello there", new String(message.getData()));


    }


    @Test
    public void givenRequest_ReplyReceived() {

        NatsClient client = connectClient();
        client.installReply("salary.requests", "denied!");

        Message reply = client.makeRequest("salary.requests", "I need a raise.");
        assertNotNull("No message!", reply);
        assertEquals("denied!", new String(reply.getData()));

    }

    @Test
    public void givenQueueMessage_OnlyOneReceived() throws Exception {

        NatsClient client = connectClient();

        SyncSubscription queue1 = client.joinQueueGroup("foo.bar.requests", "queue1");
        SyncSubscription queue2 = client.joinQueueGroup("foo.bar.requests", "queue1");

        client.publishMessage("foo.bar.requests", "queuerequestor", "foobar");

        List<Message> messages = new ArrayList<>();

        Message message = queue1.nextMessage(200);

        if (message != null) messages.add(message);
        message = queue2.nextMessage(200);

        if (message != null) messages.add(message);
        assertEquals(1, messages.size());

    }

}
