package conf;

import java.util.List;
import ninja.Filter;

public class Filters implements ninja.application.ApplicationFilters {

    @Override
    public void addFilters(List<Class<? extends Filter>> filters) {
        // Add your application - wide filters here
        // filters.add(MyFilter.class);
    }
}
