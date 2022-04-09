package com.baeldung.graphql.data;

public class Response {

    private Data data;

    public Response() {

    }

    public Response(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

}
