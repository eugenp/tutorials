package com.baeldung.networking.cookies;

import java.net.*;
import java.util.List;

public class PersistentCookieStore implements CookieStore, Runnable {
    CookieStore store;

    public PersistentCookieStore() {
        store = new CookieManager().getCookieStore();
        // deserialize cookies into store
        Runtime.getRuntime()
            .addShutdownHook(new Thread(this));
    }

    @Override
    public void run() {
        // serialize cookies to persistent storage
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
