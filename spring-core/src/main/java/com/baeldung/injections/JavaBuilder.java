package com.baeldung.injections;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("javaBuilder")
public class JavaBuilder implements CodeBuilder {

	private String stringProperty;
	private List<?> listProperty;
	private Map<?, ?> mapProperty;
}
