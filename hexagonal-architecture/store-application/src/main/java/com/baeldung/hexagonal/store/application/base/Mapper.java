package com.baeldung.hexagonal.store.application.base;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<F, T> {
    T map(F source);

    default List<T> mapList(List<F> sourceList) {
        return sourceList.stream().map(this::map).collect(Collectors.toList());
    }
}
