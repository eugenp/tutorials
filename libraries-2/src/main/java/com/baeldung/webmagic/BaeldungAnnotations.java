package com.baeldung.webmagic;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://baeldung.com/\\w+/\\w+")
@HelpUrl("https://baeldung.com/\\w+")
public class BaeldungAnnotations {

    @ExtractByUrl("https://baeldung\\.com/(\\w+)/w+")
    private String article_category;

    @ExtractByUrl("https://baeldung\\.com/\\w+/(w+)")
    private String article_name;
    
    public static void main(String[] args) {
        OOSpider.create(Site.me().setSleepTime(1000)
                        , new ConsolePageModelPipeline(), Baeldung.class)
                .addUrl("https://www.baeldung.com/cs/cdn").thread(5).run();
    }
}

