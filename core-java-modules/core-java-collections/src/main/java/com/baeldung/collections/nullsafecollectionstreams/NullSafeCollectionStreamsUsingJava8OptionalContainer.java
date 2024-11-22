package com.baeldung.collections.nullsafecollectionstreams;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class NullSafeCollectionStreamsUsingJava8OptionalContainer {

    /**
     * This method shows how to make a null safe stream from a collection  through the use of
     * Java SE 8’s Optional Container
     *
     * @param collection The collection that is to be converted into a stream
     * @return The stream that has been created from the collection or an empty stream if the collection is null
     */
    public Stream<String> collectionAsStream(Collection<String> collection) {
        return Optional.ofNullable(collection)
                .map(Collection::stream)
                .orElseGet(Stream::empty);
    }
}