package com.baeldung.java14.textblocks;

public class TextBlocks13 {
    public String getBlockOfHtml() {
        return """
                <html>

                    <body>
                        <p>example text</p>
                    </body>
                </html>""";
    }

    public String getNonStandardIndent() {
        return """
                    Indent
                """;
    }

    public String getQuery() {
        return """
                select "id", "user"
                from "table"
                """;
    }

    public String getTextWithCarriageReturns() {
        return """
                separated with\r
                carriage returns""";
    }

    public String getTextWithEscapes() {
        return """
                fun with\n
                whitespace\t\r
                and other escapes \"""
                """;
    }

    public String getFormattedText(String parameter) {
        return """
                Some parameter: %s
                """.formatted(parameter);
    }
}
