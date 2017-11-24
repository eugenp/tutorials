package com.baeldung.injections;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("pythonBuilder")
public class PythonBuilder implements CodeBuilder {

	private List<BadPython> listProperty;
}
