/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jasper.compiler;

import org.apache.jasper.JasperException;
import org.apache.jasper.compiler.ELNode.ELText;
import org.apache.jasper.compiler.ELNode.Function;
import org.apache.jasper.compiler.ELNode.Root;
import org.apache.jasper.compiler.ELNode.Text;

/**
 * This class implements a parser for EL expressions.
 * 
 * It takes strings of the form xxx${..}yyy${..}zzz etc, and turn it into a
 * ELNode.Nodes.
 * 
 * Currently, it only handles text outside ${..} and functions in ${ ..}.
 * 
 * @author Kin-man Chung
 */

public class ELParser {

    private Token curToken;  // current token
    private Token prevToken; // previous token
    private String whiteSpace = "";

    private final ELNode.Nodes expr;

    private ELNode.Nodes ELexpr;

    private int index; // Current index of the expression

    private final String expression; // The EL expression
    
    private char type;

    private final boolean isDeferredSyntaxAllowedAsLiteral;

    private static final String reservedWords[] = { "and", "div", "empty",
            "eq", "false", "ge", "gt", "instanceof", "le", "lt", "mod", "ne",
            "not", "null", "or", "true" };

    public ELParser(String expression, boolean isDeferredSyntaxAllowedAsLiteral) {
        index = 0;
        this.expression = expression;
        this.isDeferredSyntaxAllowedAsLiteral = isDeferredSyntaxAllowedAsLiteral;
        expr = new ELNode.Nodes();
    }

    /**
     * Parse an EL expression
     * 
     * @param expression
     *            The input expression string of the form Char* ('${' Char*
     *            '}')* Char*
     * @param isDeferredSyntaxAllowedAsLiteral
     *                      Are deferred expressions treated as literals?
     * @return Parsed EL expression in ELNode.Nodes
     */
    public static ELNode.Nodes parse(String expression,
            boolean isDeferredSyntaxAllowedAsLiteral) {
        ELParser parser = new ELParser(expression,
                isDeferredSyntaxAllowedAsLiteral);
        while (parser.hasNextChar()) {
            String text = parser.skipUntilEL();
            if (text.length() > 0) {
                parser.expr.add(new ELNode.Text(text));
            }
            ELNode.Nodes elexpr = parser.parseEL();
            if (!elexpr.isEmpty()) {
                parser.expr.add(new ELNode.Root(elexpr, parser.type));
            }
        }
        return parser.expr;
    }

    /**
     * Parse an EL expression string '${...}'. Currently only separates the EL
     * into functions and everything else.
     * 
     * @return An ELNode.Nodes representing the EL expression
     * 
     * Note: This can not be refactored to use the standard EL implementation as
     *       the EL API does not provide the level of access required to the
     *       parsed expression.
     */
    private ELNode.Nodes parseEL() {

        StringBuilder buf = new StringBuilder();
        ELexpr = new ELNode.Nodes();
        curToken = null;
        prevToken = null;
        while (hasNext()) {
            curToken = nextToken();
            if (curToken instanceof Char) {
                if (curToken.toChar() == '}') {
                    break;
                }
                buf.append(curToken.toString());
            } else {
                // Output whatever is in buffer
                if (buf.length() > 0) {
                    ELexpr.add(new ELNode.ELText(buf.toString()));
                    buf.setLength(0);
                }
                if (!parseFunction()) {
                    ELexpr.add(new ELNode.ELText(curToken.toString()));
                }
            }
        }
        if (curToken != null) {
            buf.append(curToken.getWhiteSpace());
        }
        if (buf.length() > 0) {
            ELexpr.add(new ELNode.ELText(buf.toString()));
        }

        return ELexpr;
    }

    /**
     * Parse for a function FunctionInvokation ::= (identifier ':')? identifier
     * '(' (Expression (,Expression)*)? ')' Note: currently we don't parse
     * arguments
     */
    private boolean parseFunction() {
        if (!(curToken instanceof Id) || isELReserved(curToken.toTrimmedString()) ||
                prevToken instanceof Char && prevToken.toChar() == '.') {
            return false;
        }
        String s1 = null; // Function prefix
        String s2 = curToken.toTrimmedString(); // Function name
        int start = index - curToken.toString().length();
        Token original = curToken;
        if (hasNext()) {
            int mark = getIndex() - whiteSpace.length();
            curToken = nextToken();
            if (curToken.toChar() == ':') {
                if (hasNext()) {
                    Token t2 = nextToken();
                    if (t2 instanceof Id) {
                        s1 = s2;
                        s2 = t2.toTrimmedString();
                        if (hasNext()) {
                            curToken = nextToken();
                        }
                    }
                }
            }
            if (curToken.toChar() == '(') {
                ELexpr.add(new ELNode.Function(s1, s2, expression.substring(start, index - 1)));
                return true;
            }
            curToken = original;
            setIndex(mark);
        }
        return false;
    }

    /**
     * Test if an id is a reserved word in EL
     */
    private boolean isELReserved(String id) {
        int i = 0;
        int j = reservedWords.length;
        while (i < j) {
            int k = (i + j) / 2;
            int result = reservedWords[k].compareTo(id);
            if (result == 0) {
                return true;
            }
            if (result < 0) {
                i = k + 1;
            } else {
                j = k;
            }
        }
        return false;
    }

    /**
     * Skip until an EL expression ('${' || '#{') is reached, allowing escape
     * sequences '\${' and '\#{'.
     * 
     * @return The text string up to the EL expression
     */
    private String skipUntilEL() {
        StringBuilder buf = new StringBuilder();
        while (hasNextChar()) {
            char ch = nextChar();
            if (ch == '\\') {
                // Is this the start of a "\$" or "\#" escape sequence?
                char p0 = peek(0);
                if (p0 == '$' || (p0 == '#' && !isDeferredSyntaxAllowedAsLiteral)) {
                    buf.append(nextChar());
                } else {
                    buf.append(ch);
                }
            } else if ((ch == '$' || (ch == '#' && !isDeferredSyntaxAllowedAsLiteral)) &&
                    peek(0) == '{') {
                this.type = ch;
                nextChar();
                break;
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }


    /**
     * Escape '$' and '#', inverting the unescaping performed in
     * {@link #skipUntilEL()} but only for ${ and #{ sequences since escaping
     * for $ and # is optional.
     *
     * @param input Non-EL input to be escaped
     * @param isDeferredSyntaxAllowedAsLiteral
     *
     * @return The escaped version of the input
     */
    static String escapeLiteralExpression(String input,
            boolean isDeferredSyntaxAllowedAsLiteral) {
        int len = input.length();
        int lastAppend = 0;
        StringBuilder output = null;
        for (int i = 0; i < len; i++) {
            char ch = input.charAt(i);
            if (ch =='$' || (!isDeferredSyntaxAllowedAsLiteral && ch == '#')) {
                if (i + 1 < len && input.charAt(i + 1) == '{') {
                    if (output == null) {
                        output = new StringBuilder(len + 20);
                    }
                    output.append(input.substring(lastAppend, i));
                    lastAppend = i + 1;
                    output.append('\\');
                    output.append(ch);
                }
            }
        }
        if (output == null) {
            return input;
        } else {
            output.append(input.substring(lastAppend, len));
            return output.toString();
        }
    }


    /**
     * Escape '\\', '\'' and '\"', inverting the unescaping performed in
     * {@link #skipUntilEL()}.
     *
     * @param input Non-EL input to be escaped
     * @param isDeferredSyntaxAllowedAsLiteral
     *
     * @return The escaped version of the input
     */
    private static String escapeELText(String input) {
        int len = input.length();
        char quote = 0;
        int lastAppend = 0;
        int start = 0;
        int end = len;

        // Look to see if the value is quoted
        String trimmed = input.trim();
        int trimmedLen = trimmed.length();
        if (trimmedLen > 1) {
            // Might be quoted
            quote = trimmed.charAt(0);
            if (quote == '\'' || quote == '\"') {
                if (trimmed.charAt(trimmedLen - 1) != quote) {
                    throw new IllegalArgumentException(Localizer.getMessage(
                            "org.apache.jasper.compiler.ELParser.invalidQuotesForStringLiteral",
                            input));
                }
                start = input.indexOf(quote) + 1;
                end = start + trimmedLen - 2;
            } else {
                quote = 0;
            }
        }

        StringBuilder output = null;
        for (int i = start; i < end; i++) {
            char ch = input.charAt(i);
            if (ch == '\\' || ch == quote) {
                if (output == null) {
                    output = new StringBuilder(len + 20);
                }
                output.append(input.substring(lastAppend, i));
                lastAppend = i + 1;
                output.append('\\');
                output.append(ch);
            }
        }
        if (output == null) {
            return input;
        } else {
            output.append(input.substring(lastAppend, len));
            return output.toString();
        }
    }


    /*
     * @return true if there is something left in EL expression buffer other
     * than white spaces.
     */
    private boolean hasNext() {
        skipSpaces();
        return hasNextChar();
    }

    private String getAndResetWhiteSpace() {
        String result = whiteSpace;
        whiteSpace = "";
        return result;
    }

    /*
     * Implementation note: This method assumes that it is always preceded by a
     * call to hasNext() in order for whitespace handling to be correct.
     *
     * @return The next token in the EL expression buffer.
     */
    private Token nextToken() {
        prevToken = curToken;
        if (hasNextChar()) {
            char ch = nextChar();
            if (Character.isJavaIdentifierStart(ch)) {
                int start = index - 1;
                while (index < expression.length() &&
                        Character.isJavaIdentifierPart(
                                ch = expression.charAt(index))) {
                    nextChar();
                }
                return new Id(getAndResetWhiteSpace(), expression.substring(start, index));
            }

            if (ch == '\'' || ch == '"') {
                return parseQuotedChars(ch);
            } else {
                // For now...
                return new Char(getAndResetWhiteSpace(), ch);
            }
        }
        return null;
    }

    /*
     * Parse a string in single or double quotes, allowing for escape sequences
     * '\\', '\"' and "\'"
     */
    private Token parseQuotedChars(char quote) {
        StringBuilder buf = new StringBuilder();
        buf.append(quote);
        while (hasNextChar()) {
            char ch = nextChar();
            if (ch == '\\') {
                ch = nextChar();
                if (ch == '\\' || ch == '\'' || ch == '\"') {
                    buf.append(ch);
                } else {
                    throw new IllegalArgumentException(Localizer.getMessage(
                            "org.apache.jasper.compiler.ELParser.invalidQuoting",
                            expression));
                }
            } else if (ch == quote) {
                buf.append(ch);
                break;
            } else {
                buf.append(ch);
            }
        }
        return new QuotedString(getAndResetWhiteSpace(), buf.toString());
    }

    /*
     * A collection of low level parse methods dealing with character in the EL
     * expression buffer.
     */

    private void skipSpaces() {
        int start = index;
        while (hasNextChar()) {
            char c = expression.charAt(index);
            if (c > ' ')
                break;
            index++;
        }
        whiteSpace = expression.substring(start, index);
    }

    private boolean hasNextChar() {
        return index < expression.length();
    }

    private char nextChar() {
        if (index >= expression.length()) {
            return (char) -1;
        }
        return expression.charAt(index++);
    }

    private char peek(int advance) {
        int target = index + advance;
        if (target >= expression.length()) {
            return (char) -1;
        }
        return expression.charAt(target);
    }

    private int getIndex() {
        return index;
    }

    private void setIndex(int i) {
        index = i;
    }

    /*
     * Represents a token in EL expression string
     */
    private static class Token {

        protected final String whiteSpace;

        Token(String whiteSpace) {
            this.whiteSpace = whiteSpace;
        }

        char toChar() {
            return 0;
        }

        @Override
        public String toString() {
            return whiteSpace;
        }

        String toTrimmedString() {
            return "";
        }

        String getWhiteSpace() {
            return whiteSpace;
        }
    }

    /*
     * Represents an ID token in EL
     */
    private static class Id extends Token {
        String id;

        Id(String whiteSpace, String id) {
            super(whiteSpace);
            this.id = id;
        }

        @Override
        public String toString() {
            return whiteSpace + id;
        }

        @Override
        String toTrimmedString() {
            return id;
        }
    }

    /*
     * Represents a character token in EL
     */
    private static class Char extends Token {

        private char ch;

        Char(String whiteSpace, char ch) {
            super(whiteSpace);
            this.ch = ch;
        }

        @Override
        char toChar() {
            return ch;
        }

        @Override
        public String toString() {
            return whiteSpace + ch;
        }

        @Override
        String toTrimmedString() {
            return "" + ch;
        }
    }

    /*
     * Represents a quoted (single or double) string token in EL
     */
    private static class QuotedString extends Token {

        private String value;

        QuotedString(String whiteSpace, String v) {
            super(whiteSpace);
            this.value = v;
        }

        @Override
        public String toString() {
            return whiteSpace + value;
        }

        @Override
        String toTrimmedString() {
            return value;
        }
    }

    public char getType() {
        return type;
    }


    static class TextBuilder extends ELNode.Visitor {

        protected final boolean isDeferredSyntaxAllowedAsLiteral;
        protected final StringBuilder output = new StringBuilder();

        protected TextBuilder(boolean isDeferredSyntaxAllowedAsLiteral) {
            this.isDeferredSyntaxAllowedAsLiteral = isDeferredSyntaxAllowedAsLiteral;
        }

        public String getText() {
            return output.toString();
        }

        @Override
        public void visit(Root n) throws JasperException {
            output.append(n.getType());
            output.append('{');
            n.getExpression().visit(this);
            output.append('}');
        }

        @Override
        public void visit(Function n) throws JasperException {
            output.append(escapeLiteralExpression(n.getOriginalText(), isDeferredSyntaxAllowedAsLiteral));
            output.append('(');
        }

        @Override
        public void visit(Text n) throws JasperException {
            output.append(escapeLiteralExpression(n.getText(),isDeferredSyntaxAllowedAsLiteral));
        }

        @Override
        public void visit(ELText n) throws JasperException {
            output.append(escapeELText(n.getText()));
        }
    }
}
