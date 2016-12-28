package org.baeldung.web.controller;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.baeldung.web.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AsyncController {

	private static final Logger log = Logger.getLogger(AsyncService.class);

	@Autowired
	private AsyncService asyncService;

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public Callable<Boolean> processUpload(final MultipartFile file) {

		return new Callable<Boolean>() {
			public Boolean call() throws Exception {
				// ...
				return true;
			}
		};
	}

	@RequestMapping(method = RequestMethod.GET, value = "/async")
	@ResponseBody
	public SecurityContext standardProcessing() throws Exception {
		log.info("Outside the @Async logic - before the async call: " + SecurityContextHolder.getContext());
		asyncService.asyncCall();
		log.info("Inside the @Async logic - after the async call: " + SecurityContextHolder.getContext());
		return SecurityContextHolder.getContext();
	}

}
