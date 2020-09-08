/**
 * 
 */
package com.baeldung.collections.mapfirstpair;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author ASHWINI
 *
 */
public class MapFirstPairServiceImpl implements MapFirstPairService {

    public <K, V> Map.Entry<K, V> getFirstPairUsingIterator(Map<K, V> map) {
        if (map == null || map.size() == 0)
            return null;

        Iterator<Map.Entry<K, V>> iterator = map.entrySet()
            .iterator();

        if (iterator.hasNext())
            return iterator.next();

        return null;
    }

    public <K, V> Map.Entry<K, V> getFirstPairUsingStream(Map<K, V> map) {
        if (map == null || map.size() == 0)
            return null;

        Set<Map.Entry<K, V>> entrySet = map.entrySet();

        return entrySet.stream()
            .findFirst()
            .get();
    }

}