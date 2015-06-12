package com.baeldung.multitenancy.implementation;

import org.hibernate.tool.hbm2ddl.SchemaExport;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vtajzich
 * Date: 8/21/13
 */
public class SessionFactoryBean extends org.springframework.orm.hibernate4.LocalSessionFactoryBean {

    private Map<String, DataSource> dataSourceMap;

    @Override
    public void afterPropertiesSet() throws IOException {
        super.afterPropertiesSet();

        for (Map.Entry<String, DataSource> entry : dataSourceMap.entrySet()) {

            Connection connection = null;

            try {

                connection = entry.getValue().getConnection();

                SchemaExport export = new SchemaExport(getConfiguration(), connection);

                export.setOutputFile(entry.getKey() + "-schema.sql");
                export.setDelimiter(";");

                // define your rules, here.
                //The below code allows creation, but does not allow dropping entries
                export.execute(true, true, false, true);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setDataSourceMap(Map<String, DataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }
}
