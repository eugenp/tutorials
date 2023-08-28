package com.baeldung.aggregateEx;

import com.baeldung.aggregateEx.entity.ExceptionAggregator;
import com.baeldung.aggregateEx.entity.Result;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


public class AggregateExceptionHandlerUnitTest {
    static Logger logger = LoggerFactory.getLogger(AggregateExceptionHandlerUnitTest.class);

    @Test
    public void givenExtractedMethod_whenFoundEx_thenSuppressExIntoRuntimeEx() {
        String[] strings = {"1", "2", "3", "a", "b", "c"};
        Throwable runEx = Arrays.stream(strings)
                .map(str -> callProcessThrowsExAndNoOutput(str))
                .filter(Objects::nonNull)
                .reduce(new RuntimeException("Errors Occurred"), (o1, o2) -> {
                    o1.addSuppressed(o2);
                    return o1;
                });
        processExceptions(runEx);
        assertEquals("Errors Occurred", runEx.getMessage());
        assertEquals(3, runEx.getSuppressed().length);
    }
    @Test
    public void givenTryCatchInPipeline_whenFoundEx_thenSuppressExIntoRuntimeEx() {
        String[] strings = {"1", "2", "3", "a", "b", "c"};
        RuntimeException runEx = Arrays.stream(strings).map(str -> {
                    try {
                        processThrowsExAndNoOutput(str);
                        return null;
                    } catch (RuntimeException e) {
                        return e;
                    }
                }).filter(Objects::nonNull)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    RuntimeException runtimeException = new RuntimeException("Errors Occurred");
                    list.forEach(runtimeException::addSuppressed);
                    return runtimeException;
                }));
        processExceptions(runEx);
        assertEquals("Errors Occurred", runEx.getMessage());
        assertEquals(3, runEx.getSuppressed().length);
    }

    @Test
    public void givenProcessMethod_whenStreamResultHasExAndOutput_thenHandleExceptionListAndOutputList() {
        List<String> strings = List.of("1", "2", "3", "a", "b", "c");
        Map map = strings.stream()
                .map(s -> processReturnsExAndOutput(s))
                .collect(Collectors.partitioningBy(o -> o instanceof RuntimeException, Collectors.toList()));
        assert(map.containsKey(Boolean.TRUE) && map.containsKey(Boolean.FALSE));
        handleExceptionsAndOutputs((List<RuntimeException>) map.get(Boolean.TRUE), (List<Integer>)map.get(Boolean.FALSE));
    }

    @Test
    public void givenCustomMapper_whenStreamResultHasExAndSuccess_thenHandleExceptionListAndOutputList() {
        List<String> strings = List.of("1", "2", "3", "a", "b", "c");
        strings.stream()
                .map(CustomMapper.mapper(Integer::parseInt))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> handleErrorsAndOutPutForResult(list)));
    }

    @Test
    public void givenCustomCollector_whenStreamResultHasExAndSuccess_thenHandleAggrExceptionAndResults() {
        String[] strings = {"1", "2", "3", "a", "b", "c"};
        Arrays.stream(strings)
                .collect(Collectors.collectingAndThen(CustomCollector.of(Integer::parseInt), col -> {
                    handleExAndResults(col.getExceptionAggregator(), col.getResults());
                    return col;
                }));
    }

    @Test
    public void givenVavrEitherAndTry_whenStreamResultHasExAndSuccess_thenHandleExceptionListAndOutputList() {
        List<String> strings = List.of("1", "2", "3", "a", "b", "c");
        strings.stream()
                .map(str -> Try.of(() -> Integer.parseInt(str)).toEither())
                .collect(Collectors.collectingAndThen(Collectors.partitioningBy(Either::isLeft, Collectors.toList()), map -> {
                    handleErrorsAndOutPutForEither(map);
                    return map;
                }));
    }

    private static void processThrowsExAndNoOutput(String input) {
        //return exception when input is "a", "b", "c"
        if (input.matches("[a-c]")) {
            throw new RuntimeException("Downstream method throws exception for " + input);
        }
    }
    private static Throwable callProcessThrowsExAndNoOutput(String input) {
        try {
            processThrowsExAndNoOutput(input);
            return null;
        } catch (RuntimeException e) {
            return e;
        }
    }

    private static Object processReturnsExAndOutput(String input) {
        logger.info("call a downstream method that returns an Integer");
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            return new RuntimeException("Exception in processWithReturnOutput for " + input, e);
        }
    }

    private static void processExceptions(Throwable throwable) {
        logger.error("Process Exception" + throwable.getMessage());
    }

    private static void handleExceptionsAndOutputs(List<RuntimeException> exs, List output) {
        logger.info("handle exceptions and output");
    }

    private static void handleExAndResults(ExceptionAggregator exAgg, List<Integer> results ) {
        logger.info("handle aggregated exceptions and results" + exAgg.getExceptions().size() + " " + results.size());
    }

    private static void handleErrorsAndOutPutForEither(Map<Boolean, List<Either<Throwable, Integer>>> map) {
        logger.info("handle errors and output");
        map.get(Boolean.TRUE).forEach(either -> logger.error("Process Exception " + either.getLeft()));

        map.get(Boolean.FALSE).forEach(either -> logger.info("Process Result " + either.get()));
    }

    private static String handleErrorsAndOutPutForResult(List<Result<Integer, Throwable>> successAndErrors) {
        logger.info("handle errors and output");
        successAndErrors.forEach(result -> {
            if (result.getException().isPresent()) {
                logger.error("Process Exception " + result.getException().get());
            } else {
                logger.info("Process Result" + result.getResult());
            }
        });
        return "Errors and Output Handled";
    }
}