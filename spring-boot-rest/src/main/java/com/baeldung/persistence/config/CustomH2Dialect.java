package com.baeldung.persistence.config;

import org.hibernate.dialect.H2Dialect;

/**
 * Since H2 1.4.200. the behavior of the drop table commands has changed.
 * Tables are not dropped in a correct order.
 * Until this is properly fixed directly in Hibernate project,
 * let's use this custom H2Dialect class to solve this issue.
 *
 * @see <a href="https://hibernate.atlassian.net/browse/HHH-13711">https://hibernate.atlassian.net/browse/HHH-13711</a>
 * @see <a href="https://github.com/hibernate/hibernate-orm/pull/3093">https://github.com/hibernate/hibernate-orm/pull/3093</a>
 */
public class CustomH2Dialect extends H2Dialect {

    @Override
    public boolean dropConstraints() {
        return true;
    }

    @Override
    public boolean supportsIfExistsAfterAlterTable() {
        return true;
    }

}
