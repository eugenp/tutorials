package org.baeldung.web.service;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService {

	private static final Logger log = Logger.getLogger(AsyncService.class);

	@Override
	public Callable<Boolean> checkIfPrincipalPropagated() {
		Object before = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.info("Before new thread: " + before);
		return new Callable<Boolean>() {
			public Boolean call() throws Exception {
				Object after = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				log.info("New thread: " + after);
				return before == after;
			}
		};
	}

	@Async
	@Override
	public Boolean checkIfContextPropagated(Object context) {
		log.info("Before @Async: " + context);
		log.info("Inside @Async: " + SecurityContextHolder.getContext());
		return context == SecurityContextHolder.getContext();
	}

}
