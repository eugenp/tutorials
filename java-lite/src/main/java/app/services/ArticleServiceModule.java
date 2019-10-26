package app.services;

import com.google.inject.AbstractModule;

public class ArticleServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ArticleService.class).to(ArticleServiceImpl.class)
            .asEagerSingleton();
    }
}
