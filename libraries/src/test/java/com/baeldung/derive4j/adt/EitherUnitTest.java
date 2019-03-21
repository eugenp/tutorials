package com.baeldung.derive4j.adt;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.function.Function;
@RunWith(MockitoJUnitRunner.class)
public class EitherUnitTest {
    @Test
    public void testEitherIsCreatedFromRight() {
       Either<Exception, String> either = Eithers.right("Okay");
       Optional<Exception> leftOptional = Eithers.getLeft(either);
       Optional<String> rightOptional = Eithers.getRight(either);
       Assertions.assertThat(leftOptional).isEmpty();
       Assertions.assertThat(rightOptional).hasValue("Okay");

    }

    @Test
    public void testEitherIsMatchedWithRight() {
        Either<Exception, String> either = Eithers.right("Okay");
        Function<Exception, String> leftFunction = Mockito.mock(Function.class);
        Function<String, String> rightFunction = Mockito.mock(Function.class);
        either.match(leftFunction, rightFunction);
        Mockito.verify(rightFunction, Mockito.times(1)).apply("Okay");
        Mockito.verify(leftFunction, Mockito.times(0)).apply(Mockito.any(Exception.class));
    }

}
