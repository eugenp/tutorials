package com.baeldung.jte;

import gg.jte.CodeResolver;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import gg.jte.resolve.DirectoryCodeResolver;

import java.nio.file.Path;

import static gg.jte.ContentType.Html;

public class ArticleView {

    public String createHtml(String template, Article article) {
        CodeResolver codeResolver = new DirectoryCodeResolver(Path.of("src/main/resources/jte"));
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, Html);

        TemplateOutput output = new StringOutput();
        templateEngine.render(template, article, output);

        return output.toString();
    }
}
