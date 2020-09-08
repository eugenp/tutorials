/**
 * 
 */
package com.baeldung.collections.mapfirstpair;

import java.util.Map;

/**
 * @author ASHWINI
 *
 */
public interface MapFirstPairService {

    <K, V> Map.Entry<K, V> getFirstPairUsingIterator(Map<K, V> map);

    <K, V> Map.Entry<K, V> getFirstPairUsingStream(Map<K, V> map);
}
