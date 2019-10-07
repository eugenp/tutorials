package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.MessageDTO;
import dto.RequestDTO;

/**
 * Created by alfred on 07 Jun 2019
 */
public class MessageConverter {
    public static MessageDTO jsonNodeToMessage(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        final MessageDTO messageDTO = mapper.convertValue(jsonNode, MessageDTO.class);
        return messageDTO;
    }

    public static JsonNode messageToJsonNode(MessageDTO messageDTO) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonData = mapper.convertValue(messageDTO, JsonNode.class);
        return jsonData;
    }
    public static RequestDTO jsonNodeToRequest(JsonNode jsonNode) {
        ObjectMapper mapper = new ObjectMapper();
        final RequestDTO requestDTO = mapper.convertValue(jsonNode, RequestDTO.class);
        return requestDTO;
    }

    public static JsonNode requestToJsonNode(RequestDTO requestDTO) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonData = mapper.convertValue(requestDTO, JsonNode.class);
        return jsonData;
    }
}
