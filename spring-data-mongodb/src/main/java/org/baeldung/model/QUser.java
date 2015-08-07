package org.baeldung.model;

import javax.annotation.Generated;

import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.StringPath;

@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1L;

    public static final QUser user = new QUser("user");

    public final StringPath name = createString("name");

    public QUser(String variable) {
        super(User.class, variable);
    }
}
