package org.baeldung.web.controller;

import java.util.concurrent.Callable;

import org.baeldung.web.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AsyncController {
	
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
	public Boolean checkIfContextPropagated() throws Exception{
		return asyncService.checkIfPrincipalPropagated().call() && asyncService.checkIfContextPropagated(SecurityContextHolder.getContext());
	}

}
