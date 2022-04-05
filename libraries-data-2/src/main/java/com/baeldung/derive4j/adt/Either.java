package com.baeldung.derive4j.adt;

import org.derive4j.Data;

import java.util.function.Function;

@Data
interface Either<A,B>{
    <X> X match(Function<A, X> left, Function<B, X> right);
}
