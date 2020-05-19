package com.baeldung.collections.nullsafecollectionstreams;

import java.util.Collection;
import java.util.stream.Stream;

public class NullSafeCollectionStreamsUsingNullDereferenceCheck {

    /**
     * This method shows how to make a null safe stream from a collection through the use of a check
     * to prevent null dereferences
     *
     * @param collection The collection that is to be converted into a stream
     * @return The stream that has been created from the collection or an empty stream if the collection is null
     */
    public Stream<String> collectionAsStream(Collection<String> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    }

}
