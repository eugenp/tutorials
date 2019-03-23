package com.baeldung.testloglevel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.testlog.TestLog;

@RestController
public class TestLogLevelController {

	Logger LOG = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/testLogLevel")
	public String testLogLevel() {
		LOG.trace("This is a TRACE log");
		LOG.debug("This is a DEBUG log");
		LOG.info("This is an INFO log");
		LOG.error("This is an ERROR log");

		new TestLog().trace("This is a TRACE log in another package");
		new TestLog().info("This is an INFO log in another package");
		new TestLog().error("This is an ERROR log in another package");

		return "Added some log output to console...";
	}

}