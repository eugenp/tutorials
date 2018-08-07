package com.baeldung.nullsafecollectionstreams;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

public class NullSafeCollectionStreams {

    /**
    * Test making created collection streams null safe through the use of a check to prevent null 
    * dereferences
    * 
     * @param collection The collection that is to be converted into a stream
     * @return The stream that has been created from the collection or an empty stream if the collection is null
    */
    public Stream<String> collectionAsStream1(Collection<String> collection) {
        if (collection == null) {
            return Stream.empty();
        }
        return collection.stream();
    }

    /**
    * Test making created collection streams null safe through the use of emptyIfNull() method from 
    * Apache Commons CollectionUtils library
    * 
     * @param collection The collection that is to be converted into a stream
     * @return The stream that has been created from the collection or an empty stream if the collection is null
    */
    public Stream<String> collectionAsStream2(Collection<String> collection) {
        Stream<String> streamOfCollection = emptyIfNull(collection).stream();
        return streamOfCollection;
    }

    /**
    * Test making created collection streams null safe through the use of Java SE 8’s Optional Container
    * 
     * @param collection The collection that is to be converted into a stream
     * @return The stream that has been created from the collection or an empty stream if the collection is null
    */
    public Stream<String> collectionAsStream3(Collection<String> collection) {
        return Optional.ofNullable(collection)
            .map(Collection::stream)
            .orElseGet(Stream::empty);
    }
}
