package com.baeldung.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class BaeldungPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://baeldung\\.com/\\w+/\\w+)").all());
        page.putField("article_category", page.getUrl().regex("https://baeldung\\.com/(\\w+)/w+").toString());
        page.putField("article_name", page.getHtml().regex("https://baeldung\\.com/\\w+/(w+)").toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new BaeldungPageProcessor()).addUrl("https://www.baeldung.com/cs/cdn").thread(1).run();
    }
    
}
