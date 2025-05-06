package com.baeldung.lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LexerFsm implements Lexer {

    public List<Token> tokenize(Expression expression) {
        State state = State.INITIAL;
        StringBuilder currentToken = new StringBuilder();
        ArrayList<Token> tokens = new ArrayList<>();

        while (expression.hasNext()) {
            final Character currentChar = getValidNextCharacter(expression);

            switch (state) {
                case INITIAL:
                    if (Grammar.isWhitespace(currentChar)) {
                        break;
                    } else if (Grammar.isDigit(currentChar)) {
                        state = State.NUMBER;
                        currentToken.append(currentChar);
                    } else if (Grammar.isOperator(currentChar) && !tokens.isEmpty()) { // to ensure there are no expressions starting with an OPERATOR
                        state = State.OPERATOR;
                        currentToken.append(currentChar);
                    } else {
                        state = State.INVALID;
                        currentToken.append(currentChar);
                    }
                    break;

                case NUMBER:
                    if (Grammar.isDigit(currentChar)) {
                        currentToken.append(currentChar);
                    } else {
                        tokens.add(new TokenNumber(currentToken.toString()));
                        currentToken.setLength(0);
                        state = State.INITIAL;
                    }
                    break;

                case OPERATOR:
                    tokens.add(new TokenOperator(currentToken.toString()));
                    currentToken.setLength(0);
                    state = State.INITIAL;
                    continue;

                case INVALID:
                    throw new InvalidExpressionException(String.format(MESSAGE_ERROR, currentToken));
            }
        }

        finalizeToken(state, currentToken, tokens);

        return tokens;
    }

    private static Character getValidNextCharacter(Expression expression) {
        final Optional<Character> currentChar = expression.next();

        if (!currentChar.isPresent() || !Grammar.isValidSymbol(currentChar.get())) {
            throw new InvalidExpressionException(String.format(MESSAGE_ERROR, currentChar));
        }

        return currentChar.get();
    }

    private static void finalizeToken(State state, StringBuilder currentToken, ArrayList<Token> tokens) {
        if (State.INVALID == state || State.OPERATOR == state) {
            throw new InvalidExpressionException(String.format(MESSAGE_ERROR, currentToken));
        } else if (State.NUMBER == state) {
            tokens.add(new TokenNumber(currentToken.toString()));
        }
    }

    private enum State {
        INITIAL,
        NUMBER,
        OPERATOR,
        INVALID
    }

    private enum Grammar {
        ADDITION('+'),
        SUBTRACTION('-'),
        MULTIPLICATION('*'),
        DIVISION('/');

        private final char symbol;

        Grammar(char symbol) {
            this.symbol = symbol;
        }

        public static boolean isOperator(char character) {
            return Arrays.stream(Grammar.values())
                .anyMatch(grammar -> grammar.symbol == character);
        }

        public static boolean isDigit(char character) {
            return Character.isDigit(character);
        }

        public static boolean isWhitespace(char character) {
            return Character.isWhitespace(character);
        }

        public static boolean isValidSymbol(char character) {
            return isOperator(character) || isWhitespace(character) || isDigit(character);
        }

    }

}

