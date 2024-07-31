package app.config;

import app.controllers.ProductsController;
import org.javalite.activeweb.AbstractControllerConfig;
import org.javalite.activeweb.AppContext;
import org.javalite.activeweb.controller_filters.DBConnectionFilter;
import org.javalite.activeweb.controller_filters.TimingFilter;

public class AppControllerConfig extends AbstractControllerConfig {
    @Override
    public void init(AppContext appContext) {
        addGlobalFilters(new TimingFilter());
        add(new DBConnectionFilter()).to(ProductsController.class);
    }
}
