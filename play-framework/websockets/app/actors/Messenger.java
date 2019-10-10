package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.http.javadsl.Http;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpMessage;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.Materializer;
import com.fasterxml.jackson.databind.JsonNode;
import dto.MessageDTO;
import dto.RequestDTO;
import utils.MessageConverter;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by alfred on 02 Mar 2019
 */
public class Messenger extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ActorRef out;

    public Messenger(ActorRef out) {
        this.out = out;
    }

    public static Props props(ActorRef out) {
        return Props.create(Messenger.class, () -> new Messenger(out));
    }

    @Override
    public void preStart() throws Exception {
        log.info("Messenger actor started at {}", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @Override
    public void postStop() throws Exception {
        log.info("Messenger actor stopped at {}", OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    private void onSendMessage(JsonNode jsonNode) {
        final RequestDTO requestDTO = MessageConverter.jsonNodeToRequest(jsonNode);
        log.info("Actor received. {}", requestDTO);
        processMessage(requestDTO);
    }

    private void processMessage(RequestDTO requestDTO) {
        final int postId = ThreadLocalRandom.current().nextInt(0, 100);
        Materializer materializer = Materializer.matFromSystem(getContext().getSystem());

        //Request the post
        final CompletionStage<HttpResponse> responseFuture = Http.get(getContext().getSystem())
                        .singleRequest(HttpRequest.create("https://jsonplaceholder.typicode.com/posts/" + postId));
        responseFuture.thenCompose(httpResponse -> {

            //convert the post into a MessageDTO
            final CompletionStage<MessageDTO> unmarshal = Jackson.unmarshaller(MessageDTO.class)
                    .unmarshal(httpResponse.entity(), materializer);

            //Discard the body of the entity to avoid akka stream errors
            return unmarshal.thenApply(messageDTO -> {
                log.info("Received message: {}", messageDTO);
                final HttpMessage.DiscardedEntity discarded = httpResponse.discardEntityBytes(materializer);

                discarded.completionStage().whenComplete((done, ex) -> {
                    log.info("Entity discarded completely!");
                });
                return messageDTO;
            });
        }).thenAccept(messageDTO -> {
            log.info("Now responding with => {}", messageDTO);
            out.tell(MessageConverter.messageToJsonNode(messageDTO), getSelf());
        });
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(JsonNode.class, this::onSendMessage)
                .matchAny(o -> log.error("Received unknown message: {}", o.getClass()))
                .build();
    }
}
