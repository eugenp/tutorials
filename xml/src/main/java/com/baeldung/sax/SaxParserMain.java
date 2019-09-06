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

public class SaxParserMain {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        BaeldungHandler baeldungHandler = new BaeldungHandler();
        saxParser.parse("xml/src/main/resources/sax/baeldung.xml", baeldungHandler);
        System.out.println(baeldungHandler.getWebsite());
    }

    //@ToString
    public static class BaeldungHandler extends DefaultHandler {
        private static final String ARTICLES = "articles";
        private static final String ARTICLE = "article";
        private static final String TITLE = "title";
        private static final String CONTENT = "content";

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
    }

    //@Data
    public static class Baeldung {
        private List<BaeldungArticle> articleList;

        public void setArticleList(List<BaeldungArticle> articleList) {
            this.articleList = articleList;
        }

        public List<BaeldungArticle> getArticleList() {
            return this.articleList;
        }
    }

    //@Data
    public static class BaeldungArticle {
        private String title;
        private String content;

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return this.content;
        }
    }
}
