package org.baeldung.web.service;

import java.util.concurrent.Callable;

public interface AsyncService {
	
	Callable<Boolean> checkIfPrincipalPropagated();
	
	Boolean checkIfContextPropagated(Object context);

}
