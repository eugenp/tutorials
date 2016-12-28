package org.baeldung.web.service;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService {

	private static final Logger log = Logger.getLogger(AsyncService.class);

	@Async
	@Override
	public void asyncCall() {
		log.info("Inside the @Async logic: " + SecurityContextHolder.getContext());
	}

}
