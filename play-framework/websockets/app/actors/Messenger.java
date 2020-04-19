package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
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
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ThreadLocalRandom;

public class Messenger extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private ActorRef out;

    public Messenger(ActorRef out) {
        this.out = out;
    }

    public static Props props(ActorRef out) {
        return Props.create(Messenger.class, () -> new Messenger(out));
    }

    @Override
    public void preStart() throws Exception {
        log.info("Messenger actor started at {}",
          OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @Override
    public void postStop() throws Exception {
        log.info("Messenger actor stopped at {}",
          OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    private void onSendMessage(JsonNode jsonNode) {
        RequestDTO requestDTO = MessageConverter.jsonNodeToRequest(jsonNode);
        String message = requestDTO.getMessage().toLowerCase();
        if("stop".equals(message)) {
            MessageDTO messageDTO = createMessageDTO("1", "1", "Stop", "Stopping actor");
            out.tell(MessageConverter.messageToJsonNode(messageDTO), getSelf());
            self().tell(PoisonPill.getInstance(), getSelf());
        } else {
            log.info("Actor received. {}", requestDTO);
            processMessage(requestDTO);
        }
    }

    private MessageDTO createMessageDTO(String userId, String id, String title, String message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setUserId(UUID.randomUUID().toString());
        messageDTO.setId(UUID.randomUUID().toString());
        messageDTO.setTitle("Self Kill");
        messageDTO.setBody("Stopping actor");
        return messageDTO;
    }

    private void processMessage(RequestDTO requestDTO) {
        CompletionStage<HttpResponse> responseFuture = getRandomMessage();
        responseFuture.thenCompose(this::consumeHttpResponse)
          .thenAccept(messageDTO ->
            out.tell(MessageConverter.messageToJsonNode(messageDTO), getSelf()));
    }

    private CompletionStage<HttpResponse> getRandomMessage() {
        int postId = ThreadLocalRandom.current().nextInt(0, 100);
        return Http.get(getContext().getSystem()).singleRequest(
          HttpRequest.create("https://jsonplaceholder.typicode.com/posts/" + postId)
        );
    }

    private void discardEntity(HttpResponse httpResponse, Materializer materializer) {
        httpResponse.discardEntityBytes(materializer)
          .completionStage()
          .whenComplete((done, ex) -> log.info("Entity discarded completely!"));
    }

    private CompletionStage<MessageDTO> consumeHttpResponse(HttpResponse httpResponse) {
        Materializer materializer = Materializer.matFromSystem(getContext().getSystem());
        return Jackson.unmarshaller(MessageDTO.class)
          .unmarshal(httpResponse.entity(), materializer)
          .thenApply(messageDTO -> {
              log.info("Received message: {}", messageDTO);
              discardEntity(httpResponse, materializer);
              return messageDTO;
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
