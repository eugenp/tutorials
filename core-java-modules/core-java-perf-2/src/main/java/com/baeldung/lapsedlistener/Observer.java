package com.baeldung.lapsedlistener;

public interface Observer {
    void update(String latestNews);

    void subscribe(Subject subject);

    void unsubscribe(Subject subject);
}
