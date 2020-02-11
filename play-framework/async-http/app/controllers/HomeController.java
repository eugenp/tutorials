package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.Map;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

/**
 * This controller contains an action to handle HTTP requests to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message. The configuration in the
     * <code>routes</code> file means that this method will be called when the application receives
     * a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index(Http.Request request) throws JsonProcessingException {
        return ok(printStats(request));
    }

    private String printStats(Http.Request request) throws JsonProcessingException {
        Map<String, String[]> stringMap = request.body()
                                                 .asFormUrlEncoded();
        Map<String, Object> map = ImmutableMap.of(
          "Result", "ok",
          "GetParams", request.queryString(),
          "PostParams", stringMap == null ? Collections.emptyMap() : stringMap,
          "Headers", request.getHeaders().toMap()
        );
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map);
    }
}
