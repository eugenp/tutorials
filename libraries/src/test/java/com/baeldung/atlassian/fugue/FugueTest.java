package com.baeldung.atlassian.fugue;

import io.atlassian.fugue.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.*;
import static io.atlassian.fugue.Unit.Unit;

public class FugueTest {

    @Before
    public void setup() {
    }

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
        Option<Object> some = Option.some("value").map(x -> null);

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

}
