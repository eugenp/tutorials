package com.baeldung.crawler4j;

import java.io.File;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class ImageCrawler extends WebCrawler {
    private final static Pattern EXCLUSIONS = Pattern.compile(".*(\\.(css|js|xml|gif|png|mp3|mp4|zip|gz|pdf))$");

    private static final Pattern IMG_PATTERNS = Pattern.compile(".*(\\.(jpg|jpeg))$");

    private File saveDir;

    public ImageCrawler(File saveDir) {
        this.saveDir = saveDir;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL().toLowerCase();
        if (EXCLUSIONS.matcher(urlString).matches()) {
            return false;
        }

        if (IMG_PATTERNS.matcher(urlString).matches() 
            || urlString.startsWith("https://www.baeldung.com/")) {
            return true;
        }

        return false;
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        if (IMG_PATTERNS.matcher(url).matches() 
            && page.getParseData() instanceof BinaryParseData) {
            String extension = url.substring(url.lastIndexOf("."));
            int contentLength = page.getContentData().length;

            System.out.printf("Extension is '%s' with content length %d %n", extension, contentLength);
        }
    }

}
