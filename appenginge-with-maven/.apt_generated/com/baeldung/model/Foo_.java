package com.baeldung.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2015-11-30T08:17:35.137-0500")
@StaticMetamodel(Foo.class)
public class Foo_ {
	public static volatile SingularAttribute<Foo, Long> id;
	public static volatile SingularAttribute<Foo, String> name;
	public static volatile SingularAttribute<Foo, String> user;
	public static volatile SingularAttribute<Foo, String> description;
}
