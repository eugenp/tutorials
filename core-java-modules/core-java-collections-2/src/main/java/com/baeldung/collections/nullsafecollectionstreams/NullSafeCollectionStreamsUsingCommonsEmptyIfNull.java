package com.baeldung.collections.nullsafecollectionstreams;

import java.util.Collection;
import java.util.stream.Stream;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

public class NullSafeCollectionStreamsUsingCommonsEmptyIfNull {

    /**
     * This method shows how to make a null safe stream from a collection through the use of
     * emptyIfNull() method from Apache Commons CollectionUtils library
     *
     * @param collection The collection that is to be converted into a stream
     * @return The stream that has been created from the collection or an empty stream if the collection is null
     */
    public Stream<String> collectionAsStream(Collection<String> collection) {
        return emptyIfNull(collection).stream();
    }
}
