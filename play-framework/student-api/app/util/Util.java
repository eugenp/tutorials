package util;
import com.fasterxml.jackson.databind.node.ObjectNode;           
import play.libs.Json;
import play.libs.Json.*;
import com.fasterxml.jackson.databind.JsonNode;           

public class Util{
	public static ObjectNode createResponse(Object response,boolean ok){
		ObjectNode result = Json.newObject();
		result.put("isSuccessfull", ok);
		if(response instanceof String)
        result.put("body",(String)response);
		else result.put("body",(JsonNode)response);

		return result;
	}
}