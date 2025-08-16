package org.learnings.aimodels.sentiments.web.api;

import ai.djl.huggingface.tokenizers.Encoding;
import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import com.google.protobuf.ByteString;
import inference.GrpcPredictV2.InferTensorContents;
import inference.GrpcPredictV2.ModelInferRequest;
import inference.GrpcPredictV2.ModelInferResponse;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SentimentsRoute extends RouteBuilder {

    private static final Logger log = LoggerFactory.getLogger(SentimentsRoute.class);
    private final HuggingFaceTokenizer tokenizer = HuggingFaceTokenizer.newInstance("distilbert-base-uncased");

    @Override
    public void configure() {
        // Configure REST via Undertow
        restConfiguration()
          .component("undertow")
          .host("0.0.0.0")
          .port(8080)
          .bindingMode(RestBindingMode.off);

        // REST GET endpoint
        rest("/sentiments")
          .get()
          .param().name("sentence").required(true).type(RestParamType.query).endParam()
          .outType(String[].class)
          .responseMessage().code(200).message("the sentence is.. ").endResponseMessage()
          .to("direct:classify");

        // Main route
        from("direct:classify")
          .routeId("sentiment-inference")
          .setBody(this::createRequest)
          .setHeader("Content-Type", constant("application/json"))
          .to("kserve:infer?modelName=sentiment&target=host.docker.internal:8001")
          //                .to("kserve:infer?modelName=sentiment&target=localhost:8001")
          .process(this::postProcess);
    }

    private ModelInferRequest createRequest(Exchange exchange) {
        String sentence = exchange.getIn().getHeader("sentence", String.class);
        Encoding encoding = tokenizer.encode(sentence);
        List<Long> inputIds = Arrays.stream(encoding.getIds()).boxed().collect(Collectors.toList());
        List<Long> attentionMask = Arrays.stream(encoding.getAttentionMask()).boxed().collect(Collectors.toList());

        var content0 = InferTensorContents.newBuilder().addAllInt64Contents(inputIds);
        var input0 = ModelInferRequest.InferInputTensor.newBuilder()
          .setName("input_ids").setDatatype("INT64").addShape(1).addShape(inputIds.size())
          .setContents(content0);

        var content1 = InferTensorContents.newBuilder().addAllInt64Contents(attentionMask);
        var input1 = ModelInferRequest.InferInputTensor.newBuilder()
          .setName("attention_mask").setDatatype("INT64").addShape(1).addShape(attentionMask.size())
          .setContents(content1);

        ModelInferRequest requestBody = ModelInferRequest.newBuilder()
          .addInputs(0, input0).addInputs(1, input1)
          .build();
        log.debug("-- payload: [{}]", requestBody);

        return requestBody;
    }

    private void postProcess(Exchange exchange) {
        log.debug("-- in response");
        ModelInferResponse response = exchange.getMessage().getBody(ModelInferResponse.class);

        List<List<Float>> logits = response.getRawOutputContentsList().stream()
          .map(ByteString::asReadOnlyByteBuffer)
          .map(buf -> buf.order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer())
          .map(buf -> {
              List<Float> longs = new ArrayList<>(buf.remaining());
              while (buf.hasRemaining()) {
                  longs.add(buf.get());
              }
              return longs;
          })
          .toList();

        log.debug("-- logits: [{}]", logits);
        String result = Math.abs(logits.getFirst().getFirst()) < logits.getFirst().getLast() ? "good" : "bad";

        exchange.getMessage().setBody(result);
    }
}
