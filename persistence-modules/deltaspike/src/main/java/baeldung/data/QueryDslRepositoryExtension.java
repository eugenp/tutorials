package baeldung.data;

import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.deltaspike.data.spi.DelegateQueryHandler;
import org.apache.deltaspike.data.spi.QueryInvocationContext;

import javax.inject.Inject;

public class QueryDslRepositoryExtension<E> implements QueryDslSupport, DelegateQueryHandler {

    @Inject private QueryInvocationContext context;

    @Override
    public JPAQuery jpaQuery() {
        return new JPAQuery(context.getEntityManager());
    }
}
