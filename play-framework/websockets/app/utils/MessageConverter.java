package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.MessageDTO;
import dto.RequestDTO;

public class MessageConverter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static MessageDTO jsonNodeToMessage(JsonNode jsonNode) {
        return OBJECT_MAPPER.convertValue(jsonNode, MessageDTO.class);
    }

    public static JsonNode messageToJsonNode(MessageDTO messageDTO) {
        return OBJECT_MAPPER.convertValue(messageDTO, JsonNode.class);
    }
    public static RequestDTO jsonNodeToRequest(JsonNode jsonNode) {
        return OBJECT_MAPPER.convertValue(jsonNode, RequestDTO.class);
    }

    public static JsonNode requestToJsonNode(RequestDTO requestDTO) {
        return OBJECT_MAPPER.convertValue(requestDTO, JsonNode.class);
    }
}
