package com.baeldung.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import scala.concurrent.duration.Duration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static akka.pattern.PatternsCS.ask;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AkkaActorsUnitTest {

    private static ActorSystem system = null;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("test-system");
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system, Duration.apply(1000, TimeUnit.MILLISECONDS), true);
        system = null;
    }

    @Test
    public void givenAnActor_sendHimAMessageUsingTell() {

        final TestKit probe = new TestKit(system);
        ActorRef myActorRef = probe.childActorOf(Props.create(MyActor.class));
        myActorRef.tell("printit", probe.testActor());

        probe.expectMsg("Got Message");
    }

    @Test
    public void givenAnActor_sendHimAMessageUsingAsk() throws ExecutionException, InterruptedException {

        final TestKit probe = new TestKit(system);
        ActorRef wordCounterActorRef = probe.childActorOf(Props.create(WordCounterActor.class));

        CompletableFuture<Object> future =
                ask(wordCounterActorRef, new WordCounterActor.CountWords("this is a text"), 1000).toCompletableFuture();

        Integer numberOfWords = (Integer) future.get();
        assertTrue("The actor should count 4 words", 4 == numberOfWords);
    }

    @Test
    public void givenAnActor_whenTheMessageIsNull_respondWithException() {
        final TestKit probe = new TestKit(system);
        ActorRef wordCounterActorRef = probe.childActorOf(Props.create(WordCounterActor.class));

        CompletableFuture<Object> future =
                ask(wordCounterActorRef, new WordCounterActor.CountWords(null), 1000).toCompletableFuture();

        try {
            future.get(1000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            assertTrue("Invalid error message", e.getMessage().contains("The text to process can't be null!"));
        } catch (InterruptedException | TimeoutException e) {
            fail("Actor should respond with an exception instead of timing out !");
        }
    }
    
    @Test
    public void givenAnAkkaSystem_countTheWordsInAText() {
    	ActorSystem system = ActorSystem.create("test-system");
        ActorRef myActorRef = system.actorOf(Props.create(MyActor.class), "my-actor");
        myActorRef.tell("printit", null);
//        system.stop(myActorRef);
//        myActorRef.tell(PoisonPill.getInstance(), ActorRef.noSender());
//        myActorRef.tell(Kill.getInstance(), ActorRef.noSender());

        ActorRef readingActorRef = system.actorOf(ReadingActor.props(TEXT), "readingActor");
        readingActorRef.tell(new ReadingActor.ReadLines(), ActorRef.noSender());    //ActorRef.noSender() means the sender ref is akka://test-system/deadLetters

//        Future<Terminated> terminateResponse = system.terminate();
    }
    
    private static String TEXT = "Lorem Ipsum is simply dummy text\n" +
            "of the printing and typesetting industry.\n" +
            "Lorem Ipsum has been the industry's standard dummy text\n" +
            "ever since the 1500s, when an unknown printer took a galley\n" +
            "of type and scrambled it to make a type specimen book.\n" +
            " It has survived not only five centuries, but also the leap\n" +
            "into electronic typesetting, remaining essentially unchanged.\n" +
            " It was popularised in the 1960s with the release of Letraset\n" +
            " sheets containing Lorem Ipsum passages, and more recently with\n" +
            " desktop publishing software like Aldus PageMaker including\n" +
            "versions of Lorem Ipsum.";

}
