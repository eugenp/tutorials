package com.baeldung.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LexerRegex implements Lexer {

    private static final String NUMBER_REGEX = "\\d+";
    private static final String OPERATOR_REGEX = "[+\\-*/]";
    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String VALID_EXPRESSION_REGEX = "^(" + NUMBER_REGEX + "(" + OPERATOR_REGEX + NUMBER_REGEX + ")*|" + NUMBER_REGEX + " )$";

    private static final Pattern TOKEN_PATTERN = Pattern.compile(NUMBER_REGEX + "|" + OPERATOR_REGEX + "|" + WHITESPACE_REGEX);

    private LexerRegex() {
    }

    public List<Token> tokenize(Expression expression) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(expression.getValue());

        if (!expression.getValue()
            .matches(VALID_EXPRESSION_REGEX)) {
            throw new InvalidExpressionException(String.format(MESSAGE_ERROR, expression));
        }

        while (matcher.find()) {
            String match = matcher.group();
            createToken(match).ifPresent(tokens::add);
        }

        return tokens;
    }

    private static Optional<Token> createToken(String match) {
        if (match.matches(NUMBER_REGEX)) {
            return Optional.of(new TokenNumber(match));
        } else if (match.matches(OPERATOR_REGEX)) {
            return Optional.of(new TokenOperator(match));
        } else if (match.matches(WHITESPACE_REGEX)) {
            return Optional.empty();
        } else {
            throw new InvalidExpressionException(String.format(MESSAGE_ERROR, match));
        }
    }
}
