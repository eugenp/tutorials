package com.baeldung.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class DeferredResultController {

	private final static Logger LOG = LoggerFactory.getLogger(DeferredResultController.class);

	@GetMapping("/async-deferredresult")
	public DeferredResult<ResponseEntity<?>> handleReqDefResult(Model model) {
		LOG.info("Received async-deferredresult request");
		DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();
		new Thread(() -> {
			LOG.info("Processing in separate thread");
			output.setResult(ResponseEntity.ok("ok"));
		}).start();
		LOG.info("servlet thread freed");
		return output;
	}

	public DeferredResult<ResponseEntity<?>> handleReqWithTimeouts(Model model) {
		LOG.info("Received async request with a configured timeout");
		DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>(500l);
		deferredResult.onTimeout(new Runnable() {
			@Override
			public void run() {
				deferredResult.setErrorResult(
						ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout occurred."));
			}
		});

		new Thread(() -> {
			LOG.info("Processing in separate thread");
			try {
				Thread.sleep(600l);
				deferredResult.setResult(ResponseEntity.ok("ok"));
			} catch (InterruptedException e) {
				LOG.info("Request processing interrupted");
				deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("INTERNAL_SERVER_ERROR occurred."));
			}

		}).start();
		LOG.info("servlet thread freed");
		return deferredResult;
	}

	public DeferredResult<ResponseEntity<?>> handleAsyncFailedRequest(Model model) {
		DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>();
		new Thread(() -> {
			try {
				// Exception occurred in processing
				throw new Exception();
			} catch (Exception e) {
				LOG.info("Request processing failed");
				deferredResult.setErrorResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("INTERNAL_SERVER_ERROR occurred."));
			}
		}).start();
		return deferredResult;
	}

}
