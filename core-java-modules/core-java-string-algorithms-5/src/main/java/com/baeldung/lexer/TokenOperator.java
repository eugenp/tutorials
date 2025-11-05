package com.baeldung.lexer;

public class TokenOperator extends Token {

    protected TokenOperator(String value) {
        super(TokenType.OPERATOR, value);
    }
}
