package com.baeldung.networking.cookies;

import java.net.*;
import java.util.List;

/**
 * @author zn.wang
 */
public class PersistentCookieStore implements CookieStore, Runnable {
    CookieStore store;

    public PersistentCookieStore() {
        store = new CookieManager().getCookieStore();
        // deserialize cookies into store(将cookie反序列化到存储中)
        Runtime.getRuntime()
            .addShutdownHook(new Thread(this));
    }

    @Override
    public void run() {
        // serialize cookies to persistent storage(将cookie序列化到持久存储)
    }

    @Override
    public void add(URI uri, HttpCookie cookie) {
        store.add(uri, cookie);

    }

    @Override
    public List<HttpCookie> get(URI uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<HttpCookie> getCookies() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<URI> getURIs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeAll() {
        // TODO Auto-generated method stub
        return false;
    }

}
