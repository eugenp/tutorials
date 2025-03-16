package com.baeldung.lexer;

public abstract class Token {

    private final String value;
    private final TokenType type;

    protected Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

}
