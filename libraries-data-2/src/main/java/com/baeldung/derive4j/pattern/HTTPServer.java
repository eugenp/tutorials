package com.baeldung.derive4j.pattern;


public class HTTPServer {
    public static String GET_RESPONSE_BODY = "Success!";
    public static String PUT_RESPONSE_BODY = "Resource Created!";
    public static String POST_RESPONSE_BODY = "Resource Updated!";
    public static String DELETE_RESPONSE_BODY = "Resource Deleted!";

    public HTTPResponse acceptRequest(HTTPRequest request) {
       return HTTPRequests.caseOf(request)
                .GET((path) -> new HTTPResponse(200, GET_RESPONSE_BODY))
                .POST((path,body) -> new HTTPResponse(201, POST_RESPONSE_BODY))
                .PUT((path,body) -> new HTTPResponse(200, PUT_RESPONSE_BODY))
                .DELETE(path -> new HTTPResponse(200, DELETE_RESPONSE_BODY));
    }
}
