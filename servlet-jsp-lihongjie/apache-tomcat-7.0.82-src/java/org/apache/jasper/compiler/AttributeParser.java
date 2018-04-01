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

/**
 * Converts a JSP attribute value into the unquoted equivalent. The attribute
 * may contain EL expressions, in which case care needs to be taken to avoid any
 * ambiguities. For example, consider the attribute values "${1+1}" and
 * "\${1+1}". After unquoting, both appear as "${1+1}" but the first should
 * evaluate to "2" and the second to "${1+1}". Literal \, $ and # need special
 * treatment to ensure there is no ambiguity. The JSP attribute unquoting
 * covers \\, \", \', \$, \#, %\&gt;, &lt;\%, &amp;apos; and &amp;quot;
 */
public class AttributeParser {

    /* System property that controls if the strict quoting rules are applied. */ 
    private static final boolean STRICT_QUOTE_ESCAPING = Boolean.parseBoolean(
            System.getProperty(
                    "org.apache.jasper.compiler.Parser.STRICT_QUOTE_ESCAPING",
                    "true"));

    /**
     * Parses the provided input String as a JSP attribute and returns an
     * unquoted value.
     * 
     * @param input         The input.
     * @param quote         The quote character for the attribute or 0 for
     *                      scripting expressions.
     * @param isELIgnored   Is expression language being ignored on the page
     *                      where the JSP attribute is defined.
     * @param isDeferredSyntaxAllowedAsLiteral
     *                      Are deferred expressions treated as literals?
     * @param quoteAttributeEL Should the rules of JSP.1.6 for escaping in
     *                      attributes be applied to EL in attribute values?
     * @return              An unquoted JSP attribute that, if it contains
     *                      expression language can be safely passed to the EL
     *                      processor without fear of ambiguity.
     */
    public static String getUnquoted(String input, char quote,
            boolean isELIgnored, boolean isDeferredSyntaxAllowedAsLiteral,
            boolean quoteAttributeEL) {
        return (new AttributeParser(input, quote, isELIgnored,
                isDeferredSyntaxAllowedAsLiteral,
                STRICT_QUOTE_ESCAPING, quoteAttributeEL)).getUnquoted();
    }

    /**
     * Provided solely for unit test purposes and allows per call overriding of
     * the STRICT_QUOTE_ESCAPING system property.
     * 
     * @param input         The input.
     * @param quote         The quote character for the attribute or 0 for
     *                      scripting expressions.
     * @param isELIgnored   Is expression language being ignored on the page
     *                      where the JSP attribute is defined.
     * @param isDeferredSyntaxAllowedAsLiteral
     *                      Are deferred expressions treated as literals?
     * @param strict        The value to use for STRICT_QUOTE_ESCAPING.
     * @param quoteAttributeEL Should the rules of JSP.1.6 for escaping in
     *                      attributes be applied to EL in attribute values?
     * @return              An unquoted JSP attribute that, if it contains
     *                      expression language can be safely passed to the EL
     *                      processor without fear of ambiguity.
     */
    protected static String getUnquoted(String input, char quote,
            boolean isELIgnored, boolean isDeferredSyntaxAllowedAsLiteral,
            boolean strict, boolean quoteAttributeEL) {
        return (new AttributeParser(input, quote, isELIgnored,
                isDeferredSyntaxAllowedAsLiteral, strict, quoteAttributeEL)).getUnquoted();
    }

    /* The quoted input string. */
    private final String input;
    
    /* The quote used for the attribute - null for scripting expressions. */
    private final char quote;
    
    /* Is expression language being ignored - affects unquoting. \$ and \# are
     * treated as literals rather than quoted values. */
    private final boolean isELIgnored;
    
    /* Are deferred expression treated as literals */
    private final boolean isDeferredSyntaxAllowedAsLiteral;
    
    /* Overrides the STRICT_QUOTE_ESCAPING. Used for Unit tests only. */
    private final boolean strict;
    
    private final boolean quoteAttributeEL;

    /* The type ($ or #) of expression. Literals have a type of null. */
    private char type;
    
    /* The length of the quoted input string. */
    private final int size;
    
    /* Tracks the current position of the parser in the input String. */
    private int i = 0;
    
    /* Indicates if the last character returned by nextChar() was escaped. */
    private boolean lastChEscaped = false;
    
    /* The unquoted result. */
    private StringBuilder result;


    /**
     * For test purposes.
     * @param input
     * @param quote
     * @param strict
     */
    private AttributeParser(String input, char quote,
            boolean isELIgnored, boolean isDeferredSyntaxAllowedAsLiteral,
            boolean strict, boolean quoteAttributeEL) {
        this.input = input;
        this.quote = quote;
        this.isELIgnored = isELIgnored;
        this.isDeferredSyntaxAllowedAsLiteral =
            isDeferredSyntaxAllowedAsLiteral;
        this.strict = strict;
        this.quoteAttributeEL = quoteAttributeEL;
        this.type = getType(input);
        this.size = input.length();
        result = new StringBuilder(size);
    }

    /*
     * Work through input looking for literals and expressions until the input
     * has all been read.
     */
    private String getUnquoted() {
        while (i < size) {
            parseLiteral();
            parseEL();
        }
        return result.toString();
    }

    /*
     * This method gets the next unquoted character and looks for
     * - literals that need to be converted for EL processing
     *   \ -> type{'\\'}
     *   $ -> type{'$'}
     *   # -> type{'#'}
     * - start of EL
     *   ${
     *   #{
     * Note all the examples above *do not* include the escaping required to use
     * the values in Java code.
     */
    private void parseLiteral() {
        boolean foundEL = false;
        while (i < size && !foundEL) {
            char ch = nextChar();
            if (!isELIgnored && ch == '\\') {
                if (type == 0) {
                    result.append("\\");
                } else {
                    result.append(type);
                    result.append("{'\\\\'}");
                }
            } else if (!isELIgnored && ch == '$' && lastChEscaped){
                if (type == 0) {
                    result.append("\\$");
                } else {
                    result.append(type);
                    result.append("{'$'}");
                }
            } else if (!isELIgnored && ch == '#' && lastChEscaped){
                // Note if isDeferredSyntaxAllowedAsLiteral==true, \# will
                // not be treated as an escape
                if (type == 0) {
                    result.append("\\#");
                } else {
                    result.append(type);
                    result.append("{'#'}");
                }
            } else if (ch == type){
                if (i < size) {
                    char next = input.charAt(i);
                    if (next == '{') {
                        foundEL = true;
                        // Move back to start of EL
                        i--;
                    } else {
                        result.append(ch);
                    }
                } else {
                    result.append(ch);
                }
            } else {
                result.append(ch);
            }
        }
    }

    /*
     * Once inside EL, no need to unquote or convert anything. The EL is
     * terminated by '}'. The only other valid location for '}' is inside a
     * StringLiteral. The literals are delimited by '\'' or '\"'. The only other
     * valid location for '\'' or '\"' is also inside a StringLiteral. A quote
     * character inside a StringLiteral must be escaped if the same quote
     * character is used to delimit the StringLiteral.
     */
    private void parseEL() {
        boolean endEL = false;
        boolean insideLiteral = false;
        char literalQuote = 0;
        while (i < size && !endEL) {
            char ch;
            if (quoteAttributeEL) {
                ch = nextChar();
            } else {
                ch = input.charAt(i++);
            }
            if (ch == '\'' || ch == '\"') {
                if (insideLiteral) {
                    if (literalQuote == ch) {
                        insideLiteral = false;
                    }
                } else {
                    insideLiteral = true;
                    literalQuote = ch;
                }
                result.append(ch);
            } else if (ch == '\\') {
                result.append(ch);
                if (insideLiteral && size < i) {
                    if (quoteAttributeEL) {
                        ch = nextChar();
                    } else {
                        ch = input.charAt(i++);
                    }
                    result.append(ch);
                }
            } else if (ch == '}') {
                if (!insideLiteral) {
                    endEL = true;
                }
                result.append(ch);
            } else {
                result.append(ch);
            }
        }
    }

    /*
     * Returns the next unquoted character and sets the lastChEscaped flag to
     * indicate if it was quoted/escaped or not.
     * &apos; is always unquoted to '
     * &quot; is always unquoted to "
     * \" is always unquoted to "
     * \' is always unquoted to '
     * \\ is always unquoted to \
     * \$ is unquoted to $ if EL is not being ignored
     * \# is unquoted to # if EL is not being ignored
     * <\% is always unquoted to <%
     * %\> is always unquoted to %>
     */
    private char nextChar() {
        lastChEscaped = false;
        char ch = input.charAt(i);
        
        if (ch == '&') {
            if (i + 5 < size && input.charAt(i + 1) == 'a' &&
                    input.charAt(i + 2) == 'p' && input.charAt(i + 3) == 'o' &&
                    input.charAt(i + 4) == 's' && input.charAt(i + 5) == ';') {
                ch = '\'';
                i += 6;
            } else if (i + 5 < size && input.charAt(i + 1) == 'q' &&
                    input.charAt(i + 2) == 'u' && input.charAt(i + 3) == 'o' &&
                    input.charAt(i + 4) == 't' && input.charAt(i + 5) == ';') {
                ch = '\"';
                i += 6;
            } else {
                ++i;
            }
        } else if (ch == '\\' && i + 1 < size) {
            ch = input.charAt(i + 1);
            if (ch == '\\' || ch == '\"' || ch == '\'' ||
                    (!isELIgnored &&
                            (ch == '$' ||
                                    (!isDeferredSyntaxAllowedAsLiteral &&
                                            ch == '#')))) {
                i += 2;
                lastChEscaped = true;
            } else {
                ch = '\\';
                ++i;
            }
        } else if (ch == '<' && (i + 2 < size) && input.charAt(i + 1) == '\\' &&
                input.charAt(i + 2) == '%') {
            // Note this is a hack since nextChar only returns a single char
            // It is safe since <% does not require special treatment for EL
            // or for literals
            result.append('<');
            i+=3;
            return '%';
        } else if (ch == '%' && i + 2 < size && input.charAt(i + 1) == '\\' &&
                input.charAt(i + 2) == '>') {
            // Note this is a hack since nextChar only returns a single char
            // It is safe since %> does not require special treatment for EL
            // or for literals
            result.append('%');
            i+=3;
            return '>';
        } else if (ch == quote && strict) {
            String msg = Localizer.getMessage("jsp.error.attribute.noescape",
                    input, ""+ quote);
            throw new IllegalArgumentException(msg);
        } else {
            ++i;
        }

        return ch;
    }

    /*
     * Determines the type of expression by looking for the first unquoted ${
     * or #{.
     */
    private char getType(String value) {
        if (value == null) {
            return 0;
        }

        if (isELIgnored) {
            return 0;
        }

        int j = 0;
        int len = value.length();
        char current;

        while (j < len) {
            current = value.charAt(j);
            if (current == '\\') {
                // Escape character - skip a character
                j++;
            } else if (current == '#' && !isDeferredSyntaxAllowedAsLiteral) {
                if (j < (len -1) && value.charAt(j + 1) == '{') {
                    return '#';
                }
            } else if (current == '$') {
                if (j < (len - 1) && value.charAt(j + 1) == '{') {
                    return '$';
                }
            }
            j++;
        }
        return 0;
    }
}
