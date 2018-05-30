package app.config;

import org.javalite.activeweb.AppContext;
import org.javalite.activeweb.Bootstrap;

import com.google.inject.Guice;
import com.google.inject.Injector;

import app.services.ArticleServiceModule;

public class AppBootstrap extends Bootstrap {
    public void init(AppContext context) {
    }
    public Injector getInjector() {
        return Guice.createInjector(new ArticleServiceModule());
    }
}
