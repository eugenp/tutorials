package com.baeldung.hexagonal_architecture.core;

import java.util.Map;

public interface ObtainFacts {
	//getFacts from the datastore
		public Map<Integer,String> getFacts();
}
