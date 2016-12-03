package org.baeldung.web.controller;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AsyncController {

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public Callable<Boolean> processUpload(final MultipartFile file) {

		return new Callable<Boolean>() {
			public Boolean call() throws Exception {
				// ...
				return true;
			}
		};
	}

}
