package com.baeldung.multiple_bean_instantiation.solution3;

import java.util.Collection;
import java.util.List;

public interface MultiBeanFactory<T> {
	List<T> getObject(String name) throws Exception;
	  Class<?> getObjectType();
	/* Collection<String> getNames(); */
}
