package com.baeldung.aggregateEx;

import com.baeldung.aggregateEx.entity.ExceptionAggregator;
import com.baeldung.aggregateEx.entity.Result;

import static org.junit.Assert.*;

import io.vavr.control.Either;
import io.vavr.control.Try;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AggregateExceptionHandlerUnitTest {

    private static RuntimeException process(String str) {
        try {
            Integer.parseInt(str);
            return null;
        } catch (NumberFormatException e) {
            return new RuntimeException(e);
        }
    }

    private static Object transform(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return new RuntimeException(e);
        }
    }

    @Test
    public void givenExtractedMethod_whenFoundNonInt_thenAggregateException() {
        String[] strings = {"1", "2", "3", "a", "b", "c"};
        RuntimeException runEx = Arrays.stream(strings)
                .map(AggregateExceptionHandlerUnitTest::process)
                .filter(Objects::nonNull)
                .reduce(new RuntimeException("Errors Occurred"), (o1, o2) -> {
                    o1.addSuppressed(o2);
                    return o1;
                });
        assertEquals("Errors Occurred", runEx.getMessage());
        assertEquals(3, runEx.getSuppressed().length);
    }

    @Test
    public void givenTryCatchInPipeline_whenFoundNonInts_thenAggregateException() {
        String[] strings = {"1", "2", "3", "a", "b", "c"};
        RuntimeException runEx = Arrays.stream(strings)
                .map(str -> {
                    try {
                        Integer.parseInt(str);
                        return null;
                    } catch (NumberFormatException e) {
                        return new RuntimeException(e);
                    }
                })
                .filter(Objects::nonNull)
                .reduce(new RuntimeException("Errors Occurred"), (o1, o2) -> {
                    o1.addSuppressed(o2);
                    return o1;
                });
        assertEquals("Errors Occurred", runEx.getMessage());
        assertEquals(3, runEx.getSuppressed().length);
    }

    @Test
    public void whenFoundNonInts_thenAggregateExceptionAndReturnOutput() {
        String[] strings = {"1", "2", "3", "a", "b", "c"};
        Map resultMap = Arrays.stream(strings)
                .map(AggregateExceptionHandlerUnitTest::transform)
                .collect(Collectors.partitioningBy(o -> o instanceof RuntimeException, Collectors.toList()));
        RuntimeException ex = null;
        if (resultMap.containsKey(Boolean.TRUE)) {
            List<RuntimeException> exs = (List<RuntimeException>) resultMap.get(Boolean.TRUE);
            ex = exs.stream()
                    .reduce(
                            new RuntimeException("Errors Occurred"), (o1, o2) -> {
                                o1.addSuppressed(o2);
                                return o1;
                            });
        }
        assertEquals("Errors Occurred", ex.getMessage());
        assertEquals(3, ex.getSuppressed().length);
    }

    @Test
    public void givenWrapFunction_whenFoundNonInts_thenAggregateException() throws ExceptionAggregator {
        String[] strings = {"1", "2", "3", "a", "b", "c"};
        Map<Boolean, List<Result<Integer>>> resultmap = Arrays.stream(strings)
                .map(CustomMapper.mapper(Integer::parseInt))
                .collect(Collectors.partitioningBy(r -> r.getException().isEmpty(), Collectors.toList()));

        if (resultmap.containsKey(Boolean.FALSE)) {
            List<Result<Integer>> resultList = resultmap.get(Boolean.FALSE);
            List<Exception> exceptionList = resultList.stream()
                    .map(opex -> opex.getException().get())
                    .collect(Collectors.toList());

            assertThrows(ExceptionAggregator.class, () -> handleExceptions(exceptionList));
        }
    }

    private void handleExceptions(List<Exception> exceptions) throws ExceptionAggregator {
        ExceptionAggregator exceptionAggregator = new ExceptionAggregator("Errors occurred");
        exceptionAggregator.addExceptions(exceptions);
        throw exceptionAggregator;
    }

    @Test
    public void givenExCollector_whenFoundNonInts_thenAggregateException() throws ExceptionAggregator {
        String[] strings = {"1", "2", "3", "a", "b", "c"};
        ExceptionCollector exCollector = Arrays.stream(strings)
                .collect(ExceptionCollector.of(Integer::parseInt));
        List<Integer> output = exCollector.getResults();
        List<RuntimeException> runEx = exCollector.getExceptions();
        assertEquals(3, runEx.size());
    }

    private static Either<RuntimeException, Integer> processAndReturnEither(String str) {
        Either<RuntimeException, Integer> either = null;
        try {
            either = Either.right(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            either = Either.left(new RuntimeException(e));
        }
        return either;
    }

    @Test
    public void givenVavrEither_whenFoundNonInts_thenAggregateException() {
        List<String> strings = List.of("1", "2", "3", "a", "b", "c");
        Map<Boolean, List<Either<RuntimeException, Integer>>> map = strings.stream()
                .map(str -> processAndReturnEither(str))
                .collect(Collectors.partitioningBy((t) -> t.isLeft(), Collectors.toList()));

        RuntimeException runEx = map.get(Boolean.TRUE)
                .stream().map(either -> either.getLeft())
                .reduce(new RuntimeException("Errors Occurred"), (o1, o2) -> {
                    o1.addSuppressed(o2);
                    return o1;
                });
        assertEquals(3, runEx.getSuppressed().length);
    }

    @Test
    public void givenVavrTry_whenFoundNonInts_thenAggregateException() {
        List<String> strings = List.of("1", "2", "3", "a", "b", "c");
        Map<Boolean, List<Try<Integer>>> map = strings.stream()
                .map(str -> Try.of(() -> Integer.parseInt(str)))
                .collect(Collectors.partitioningBy((t) -> t.isFailure(), Collectors.toList()));
        Throwable runEx = map.get(Boolean.TRUE).stream()
                .map(t -> t.getCause())
                .reduce(new RuntimeException("Errors Occurred"), (o1, o2) -> {
                    o1.addSuppressed(o2);
                    return o1;
                });
        assertEquals(3, runEx.getSuppressed().length);
    }

    @Test
    public void givenVavrEitherAndTry_whenFoundNonInts_thenAggregateException() {
        List<String> strings = List.of("1", "2", "3", "a", "b", "c");
        Map<Boolean, List<Either<Throwable, Integer>>> map = strings.stream()
                .map(str -> Try.of(() -> Integer.parseInt(str)).toEither())
                .collect(Collectors.partitioningBy(Either::isLeft, Collectors.toList()));
        Throwable runEx = map.get(Boolean.TRUE).stream()
                .map(either -> either.getLeft())
                .reduce(new RuntimeException("Errors Occurred"), (o1, o2) -> {
                    o1.addSuppressed(o2);
                    return o1;
                });
        assertEquals(3, runEx.getSuppressed().length);
    }
}
