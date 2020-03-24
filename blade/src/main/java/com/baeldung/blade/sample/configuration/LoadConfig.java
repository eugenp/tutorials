package com.baeldung.blade.sample.configuration;

import com.blade.Blade;
import com.blade.ioc.annotation.Bean;
import com.blade.loader.BladeLoader;
import com.blade.mvc.WebContext;

@Bean
public class LoadConfig implements BladeLoader {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoadConfig.class);

    @Override
    public void load(Blade blade) {
        String version = WebContext.blade()
            .env("app.version")
            .orElse("N/D");
        String authors = WebContext.blade()
            .env("app.authors", "Unknown authors");

        log.info("[LoadConfig] loaded 'app.version' (" + version + ") and 'app.authors' (" + authors + ") in a configuration bean");
    }
}