package com.baeldung.lexer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LexerTest {

    private final Lexer lexer = new LexerFsm();

    @ParameterizedTest
    @ValueSource(strings = { "1 + 2 $ 3", "1 - 2 #", "- 1 + 2", "+ 1 2" })
    void givenInputContainsInvalidCharacters_whenTokenize_thenExceptionThrown(String input) {
        assertThrows(Exception.class, () -> lexer.tokenize(new Expression(input)));
    }

    @Test
    void givenInputIsSimpleExpression_whenTokenize_thenValidTokensIsReturned() {
        String input = "3 + 50";
        List<Token> tokens = lexer.tokenize(new Expression(input));

        assertAll(() -> assertEquals(3, tokens.size(), "Token count mismatch"),

            () -> assertToken(tokens.get(0), TokenType.NUMBER, "3"),

            () -> assertToken(tokens.get(1), TokenType.OPERATOR, "+"),

            () -> assertToken(tokens.get(2), TokenType.NUMBER, "50"));
    }

    private void assertToken(Token token, TokenType expectedType, String expectedValue) {
        assertAll(() -> assertEquals(expectedType, token.getType(), "Token type mismatch"),
            () -> assertEquals(expectedValue, token.getValue(), "Token value mismatch"));
    }

    @Test
    void givenInputIsEmptyExpression_whenTokenize_thenEmptyListIsReturned() {
        String input = "";
        List<Token> tokens = lexer.tokenize(new Expression(input));

        assertTrue(tokens.isEmpty(), "Lexer should return an empty list when the input expression is empty");
    }

    @Test
    void givenInputIsNumber_whenTokenize_thenTokenNumberIsReturned() {
        String input = "1";
        List<Token> tokens = lexer.tokenize(new Expression(input));

        assertAll(() -> assertEquals(1, tokens.size(), "Token count mismatch"),

            () -> assertInstanceOf(TokenNumber.class, tokens.get(0)),

            () -> assertEquals(1, ((TokenNumber) tokens.get(0)).getValueAsInt()));
    }
}

