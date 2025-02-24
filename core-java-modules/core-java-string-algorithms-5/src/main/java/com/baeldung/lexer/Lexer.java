package com.baeldung.lexer;

import java.util.List;

public interface Lexer {

    String MESSAGE_ERROR = "Unexpected entry: '%s'";

    List<Token> tokenize(Expression expression);

}
