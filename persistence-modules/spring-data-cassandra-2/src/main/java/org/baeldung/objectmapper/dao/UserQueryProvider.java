package org.baeldung.objectmapper.dao;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import org.baeldung.objectmapper.entity.User;

public class UserQueryProvider {

    private final CqlSession session;
    private final EntityHelper<User> userHelper;

    public UserQueryProvider(MapperContext context, EntityHelper<User> userHelper) {
        this.session = context.getSession();
        this.userHelper = userHelper;
    }

    public PagingIterable<User> getUsersOlderThanAge(String age) {
        SimpleStatement statement = QueryBuilder.selectFrom("user_profile")
          .all()
          .whereColumn("user_age")
          .isGreaterThan(QueryBuilder
                  .bindMarker(age))
          .build();
        PreparedStatement preparedSelectUser = session.prepare(statement);
        return session
          .execute(preparedSelectUser.getQuery())
          .map(result -> userHelper.get(result, true));
    }
}
