package com.baeldung.jte;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JteTemplateTest {

    @Test
    public void givenArticle_whenHtmlCreated_thenArticleViewIsRendered() {
        ArticleView articleView = new ArticleView();

        String output = articleView.createHtml(
            "article.jte",
            new Article("Java Template Engine", "Baeldung", "Helpful article", 42)
        );

        assertEquals("""
        <html>
            <body>
                <h1>Java Template Engine</h1>
                <h2>Baeldung</h2>
                <p>Helpful article</p>
                <p>42</p>
            </body>
        </html>""",
        output);
    }

}
