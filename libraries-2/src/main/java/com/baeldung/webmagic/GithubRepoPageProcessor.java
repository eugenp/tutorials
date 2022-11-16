package com.baeldung.webmagic;

import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(0);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+)").all());
        GithubRepo githubRepo = new GithubRepo();
        githubRepo.setAuthor(page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        githubRepo.setName(page.getHtml().xpath("//h1[contains(@class, 'entry-title') and contains(@class, 'public')]/strong/a/text()").toString());
        githubRepo.setReadme(page.getHtml().xpath("//div[@id='readme']/tidyText()").toString());
        if (githubRepo.getName() == null) {
            //skip this page
            page.setSkip(true);
        } else {
            page.putField("repo", githubRepo);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft/webmagic").thread(5).run();
    }
}
