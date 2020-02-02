package com.baeldung.hexagonalarchitecture.application.boundary;

import com.baeldung.hexagonalarchitecture.application.boundary.drivenports.IObtainGreetings;
import com.baeldung.hexagonalarchitecture.domain.command.RequestGreeting;
import com.baeldung.hexagonalarchitecture.infrastructutre.drivenadapter.GreetingsPrinterStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoundaryUnitTest {

        private static final String ENGLISH_GREETING = "hello";
        private static final String SPANISH_GREETING = "hola";

        private GreetingsPrinterStub printGreetings;

        private IObtainGreetings greetingsObtainer;

        private Boundary boundary;

        @BeforeEach void setUp() {
                //given
                greetingsObtainer = mock(IObtainGreetings.class);
                when(greetingsObtainer.getGreetingsForLanguage("rr")).thenReturn(new String[] { "hello" });
                when(greetingsObtainer.getGreetingsForLanguage("en")).thenReturn(new String[] { "hello" });
                when(greetingsObtainer.getGreetingsForLanguage("sp")).thenReturn(new String[] { "hola" });
                printGreetings = new GreetingsPrinterStub();
                boundary = new Boundary(greetingsObtainer, printGreetings);

        }

        @Test public void whenAskedEnglishLanguage_thenReturnEnglishGreeting() throws Exception {
                boundary.reactTo(new RequestGreeting("en"));
                String[] greetings = printGreetings.getGreetings();
                assertEquals(ENGLISH_GREETING, greetings[0]);
        }

        @Test public void whenAskedUnknownLanguage_thenReturnEnglishGreeting() throws Exception {
                boundary.reactTo(new RequestGreeting("rr"));
                String[] greetings = printGreetings.getGreetings();
                assertEquals(ENGLISH_GREETING, greetings[0]);
        }

        @Test public void whenAskedFrenchLanguage_thenReturnFrenchGreeting() throws Exception {
                boundary.reactTo(new RequestGreeting("sp"));
                String[] greetings = printGreetings.getGreetings();
                assertEquals(SPANISH_GREETING, greetings[0]);
        }

}