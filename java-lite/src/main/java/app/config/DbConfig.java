package app.config;

import org.javalite.activeweb.AbstractDBConfig;
import org.javalite.activeweb.AppContext;

public class DbConfig extends AbstractDBConfig {
    @Override
    public void init(AppContext appContext) {
        this.configFile("/database.properties");
    }
}
