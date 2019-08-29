package com.baeldung.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SaxParserMain {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        BaeldungHandler baeldungHandler = new BaeldungHandler();
        saxParser.parse("xml\\src\\main\\resources\\sax\\baeldung.xml", baeldungHandler);
        System.out.println(baeldungHandler.getWebsite());
    }

    public static class BaeldungHandler extends DefaultHandler {
        private Baeldung website;
        private String elementValue;

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            elementValue = new String(ch, start, length);
        }

        @Override
        public void startDocument() throws SAXException {
            website = new Baeldung();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            switch (qName) {
                case ARTICLES:
                    website.setArticleList(new ArrayList<>());
                    break;
                case ARTICLE:
                    website.getArticleList().add(new BaeldungArticle());
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            switch (qName) {
                case TITLE:
                    latestArticle().setTitle(elementValue);
                    break;
                case CONTENT:
                    latestArticle().setContent(elementValue);
                    break;
            }
        }

        private BaeldungArticle latestArticle() {
            List<BaeldungArticle> articleList = website.getArticleList();
            int latestArticleIndex = articleList.size() - 1;
            return articleList.get(latestArticleIndex);
        }

        public Baeldung getWebsite() {
            return website;
        }

        @Override
        public String toString() {
            return "BaeldungHandler{" +
                    "website=" + website +
                    ", elementValue='" + elementValue + '\'' +
                    '}';
        }

        private static final String ARTICLES = "articles";
        private static final String ARTICLE = "article";
        private static final String TITLE = "title";
        private static final String CONTENT = "content";
    }


    public static class Baeldung {
        private List<BaeldungArticle> articleList;

        public List<BaeldungArticle> getArticleList() {
            return articleList;
        }

        public void setArticleList(List<BaeldungArticle> articleList) {
            this.articleList = articleList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Baeldung baeldung = (Baeldung) o;
            return Objects.equals(articleList, baeldung.articleList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(articleList);
        }

        @Override
        public String toString() {
            return "Baeldung{" +
                    "articleList=" + articleList +
                    '}';
        }
    }

    public static class BaeldungArticle {
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BaeldungArticle that = (BaeldungArticle) o;
            return Objects.equals(title, that.title) &&
                    Objects.equals(content, that.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, content);
        }

        @Override
        public String toString() {
            return "BaeldungArticle{" +
                    "title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
