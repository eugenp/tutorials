package com.baeldung.streamex;

import java.util.Collection;
import java.util.List;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

public class StreamUtil {

    public StreamEx<?> getEmptyStream() {
        return StreamEx.empty();
    }

    public StreamEx<?> getStream(Collection<?> collection) {
        return StreamEx.of(collection);
    }

    public StreamEx<?> sort(Collection<?> collection) {
        return StreamEx.of(collection).sorted();
    }

    public StreamEx<?> reverse(List<?> list) {
        return StreamEx.ofReversed(list);
    }

    public StreamEx<?> reverseSorted(Collection<?> collection) {
        return StreamEx.of(collection).reverseSorted();
    }

    public EntryStream<?, ?> zip(Collection<?> colOne, Collection<?> colTwo) {
        return StreamEx.of(colOne).zipWith(StreamEx.of(colTwo));
    }

    public StreamEx<?> getNotNull(Collection<?> collection) {
        return StreamEx.of(collection).nonNull();
    }

    public boolean has(List<String> list, String searchParam) {
        return StreamEx.of(list).has(searchParam);
    }

}
