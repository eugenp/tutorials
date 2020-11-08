package com.baeldung.fugue;

import io.atlassian.fugue.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;

import static org.junit.Assert.*;
import static io.atlassian.fugue.Unit.Unit;

public class FugueUnitTest {

    @Test
    public void whenSome_thenDefined() {
        Option<String> some = Option.some("value");

        assertTrue(some.isDefined());
        assertEquals("value", some.get());
    }

    @Test
    public void whenNone_thenNotDefined() {
        Option<Object> none = Option.none();

        assertFalse(none.isDefined());
        assertEquals(1, none.getOrElse(1));
    }

    @Test
    public void whenSomeNull_thenException() {
        try {
            Option.some(null);

            fail("some(null) should throw");
        } catch (NullPointerException e) {
            //Expected
        }
    }

    @Test
    public void whenNullOption_thenSome() {
        Option<String> some = Option.some("value") .map(String::toUpperCase);

        assertEquals("VALUE", some.get());

        some = some.map(x -> null);

        assertNull(some.get());
        some.forEach(Assert::assertNull);
        for(Object value : some) {
            assertNull(value);
        }
        assertEquals(1, some.toStream().count());

        Option<Object> none = Option.some("value").flatMap(x -> Option.none());

        assertFalse(none.isDefined());

        none = Option.some("value").flatMap(Options.nullSafe(x -> null));

        assertFalse(none.isDefined());
    }

    @Test
    public void whenNone_thenEmptyOptional() {
        Optional<Object> optional = Option.none().toOptional();

        assertFalse(optional.isPresent());
        assertTrue(Option.fromOptional(optional).isEmpty());
    }

    @Test
    public void whenOption_thenIterable() {
        Option<String> some = Option.some("value");
        Iterable<String> strings = Iterables.concat(some, Arrays.asList("a", "b", "c"));
        List<String> stringList = new ArrayList<>();
        Iterables.addAll(stringList, strings);

        assertEquals(4, stringList.size());
    }

    @Test
    public void whenOption_thenStream() {
        assertEquals(0, Option.none().toStream().count());
        assertEquals(1, Option.some("value").toStream().count());
    }

    @Test
    public void whenLift_thenPartialFunction() {
        Function<Integer, Integer> f = (Integer x) -> x > 0 ? x + 1 : null;
        Function<Option<Integer>, Option<Integer>> lifted = Options.lift(f);

        assertEquals(2, (long) lifted.apply(Option.some(1)).get());
        assertTrue(lifted.apply(Option.none()).isEmpty());
        assertEquals(null, lifted.apply(Option.some(0)).get());
    }

    @Test

    public void whenLeft_thenEither() {
        Either<Integer, String> right = Either.right("value");
        Either<Integer, String> left = Either.left(-1);
        if(right.isLeft()) {
            fail();
        }
        if(left.isRight()) {
            fail();
        }

        String s = right.map(String::toUpperCase).getOrNull();

        assertEquals("VALUE", s);

        Either<String, String> either = right.left().map(x -> decodeSQLErrorCode(x));

        assertTrue(either.isRight());
        assertEquals("value", either.right().get());

        either.right().forEach(x -> assertEquals("value", x));
    }

    private static String decodeSQLErrorCode(Integer x) {
        return "error";
    }

    @Test
    public void whenTryIsFailure_thenIsFailureReturnsTrue() {
        assertTrue(Try.failure(new Exception("Fail!")).isFailure());
    }

    @Test
    public void whenTryIsSuccess_thenIsSuccessReturnsTrue() {
        assertTrue(Try.successful("OK").isSuccess());
    }

    @Test
    public void givenFunctionReturning_whenCheckedOf_thenSuccess() {
        assertTrue(Checked.of(() -> "ok").isSuccess());
    }

    @Test
    public void givenFunctionThrowing_whenCheckedOf_thenFailure() {
        assertTrue(Checked.of(() -> { throw new Exception("ko"); }).isFailure());
    }

    @Test
    public void givenFunctionThrowing_whenCheckedLift_thenFailure() {
        Checked.Function<String, Object, Exception> throwException = (String x) -> {
            throw new Exception(x);
        };

        assertTrue(Checked.lift(throwException).apply("ko").isFailure());
    }

    @Test
    public void whenRecover_thenSuccessfulTry() {
        Try<Object> recover = Try.
                failure(new Exception("boo!")).
                recover((Exception e) -> e.getMessage() + " recovered.");

        assertTrue(recover.isSuccess());
        assertEquals("boo! recovered.", recover.getOrElse(() -> null));

        recover = Try.
                failure(new Exception("boo!")).
                recoverWith((Exception e) -> Try.successful("recovered again!"));

        assertTrue(recover.isSuccess());
        assertEquals("recovered again!", recover.getOrElse(() -> null));
    }


    @Test
    public void whenFailure_thenMapNotCalled() {
        Try<Object> recover = Try.failure(new Exception("boo!")).map(x -> {
            fail("Oh, no!");
            return null;
        }).recover(Function.identity());
        Exception exception = (Exception) recover.toOption().get();

        assertTrue(recover.isSuccess());
        assertEquals("boo!", exception.getMessage());
    }

    @Test
    public void whenException_thenTryThrows() {
        Try<Object> checked = Checked.of(() -> {
            throw new Exception("Aaargh!");
        });
        Either<Exception, Object> either = checked.toEither();

        assertTrue(checked.isFailure());
        assertTrue(either.isLeft());
        assertEquals(42, checked.getOrElse(() -> 42));

        try {
            checked.getOrElse(() -> {
                throw new NoSuchElementException("fail");
            });

            fail("Was expecting exception");
        } catch (Exception e) {
            assertEquals("fail", e.getMessage());
        }
    }

    @Test
    public void whenRecoverThrows_thenFailure() {
        Try<Object> failure = Try.failure(new Exception("boo!")).recover(x -> {
            throw new RuntimeException(x);
        });

        assertTrue(failure.isFailure());
    }

    Unit doSomething() {
        System.out.println("Hello! Side effect");
        return Unit();
    }

    @Test
    public void whenPair_thenLeftAndRight() {
        Pair<Integer, String> pair = Pair.pair(1, "a");

        assertEquals(1, (int) pair.left());
        assertEquals("a", pair.right());
    }

}
