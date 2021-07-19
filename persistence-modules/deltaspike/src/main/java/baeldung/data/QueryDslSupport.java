package baeldung.data;

import com.mysema.query.jpa.impl.JPAQuery;

public interface QueryDslSupport {
    JPAQuery jpaQuery();
}
