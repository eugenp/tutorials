package com.baeldung.reactive.webfluxapplication.model;


public class WebFluxEvent {
    private String token;
    private long time;
    
    public WebFluxEvent() { }

    public WebFluxEvent(String token, long time) {
        super();
        this.token = token;
        this.time = time;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "WebFluxEvent [token=" + token + ", time=" + time + "]";
    }
}
