package com.baeldung.easymock;

import java.util.List;

public class BaeldungReader {

    private ArticleReader articleReader;
    private IArticleWriter articleWriter;

    public BaeldungReader() {
    }

    ;

    public BaeldungReader(ArticleReader articleReader) {
        this.articleReader = articleReader;
    }

    public BaeldungReader(IArticleWriter writer) {
        this.articleWriter = writer;
    }

    public BaeldungReader(ArticleReader articleReader, IArticleWriter writer) {
        this.articleReader = articleReader;
        this.articleWriter = writer;
    }

    public BaeldungArticle readNext() {
        return articleReader.next();
    }

    public List<BaeldungArticle> readTopic(String topic) {
        return articleReader.ofTopic(topic);
    }

    public String write(String title, String content) {
        return articleWriter.write(title, content);
    }

}