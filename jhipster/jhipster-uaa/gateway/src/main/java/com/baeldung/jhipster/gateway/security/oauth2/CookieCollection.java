package com.baeldung.jhipster.gateway.security.oauth2;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A Collection of Cookies that allows modification - unlike a mere array.
 * <p>
 * Since {@link Cookie} doesn't implement <code>hashCode</code> nor <code>equals</code>,
 * we cannot simply put it into a <code>HashSet</code>.
 */
public class CookieCollection implements Collection<Cookie> {
    private final Map<String, Cookie> cookieMap;

    public CookieCollection() {
        cookieMap = new HashMap<>();
    }

    public CookieCollection(Cookie... cookies) {
        this(Arrays.asList(cookies));
    }

    public CookieCollection(Collection<? extends Cookie> cookies) {
        cookieMap = new HashMap<>(cookies.size());
        addAll(cookies);
    }

    @Override
    public int size() {
        return cookieMap.size();
    }

    @Override
    public boolean isEmpty() {
        return cookieMap.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof String) {
            return cookieMap.containsKey(o);
        }
        if (o instanceof Cookie) {
            return cookieMap.containsValue(o);
        }
        return false;
    }

    @Override
    public Iterator<Cookie> iterator() {
        return cookieMap.values().iterator();
    }

    public Cookie[] toArray() {
        Cookie[] cookies = new Cookie[cookieMap.size()];
        return toArray(cookies);
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return cookieMap.values().toArray(ts);
    }

    @Override
    public boolean add(Cookie cookie) {
        if (cookie == null) {
            return false;
        }
        cookieMap.put(cookie.getName(), cookie);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof String) {
            return cookieMap.remove((String)o) != null;
        }
        if (o instanceof Cookie) {
            Cookie c = (Cookie)o;
            return cookieMap.remove(c.getName()) != null;
        }
        return false;
    }

    public Cookie get(String name) {
        return cookieMap.get(name);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for(Object o : collection) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Cookie> collection) {
        boolean result = false;
        for(Cookie cookie : collection) {
            result|= add(cookie);
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean result = false;
        for(Object cookie : collection) {
            result|= remove(cookie);
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean result = false;
        Iterator<Map.Entry<String, Cookie>> it = cookieMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, Cookie> e = it.next();
            if (!collection.contains(e.getKey()) && !collection.contains(e.getValue())) {
                it.remove();
                result = true;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        cookieMap.clear();
    }
}
