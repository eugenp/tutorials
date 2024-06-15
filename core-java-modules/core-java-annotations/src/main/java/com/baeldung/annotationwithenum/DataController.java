package com.baeldung.annotationwithenum;

public class DataController {

    private static final int REQUEST_TYPE = SimulatedRequestContentType.JSON;
    private static final String EXTENDED_REQUEST_TYPE = ExtendedRequestContentType.Constants.XML_VALUE;


    @PostRequest(contentType = RequestContentType.JSON, intContentType = REQUEST_TYPE, extendedContentType = EXTENDED_REQUEST_TYPE)
    public String createData(){
        return "CREATED";
    }

    @PutRequest(contentType = RequestContentType.JSON, intContentType = REQUEST_TYPE, extendedContentType = EXTENDED_REQUEST_TYPE)
    public String updateData(){
        return "UPDATED";
    }
}
