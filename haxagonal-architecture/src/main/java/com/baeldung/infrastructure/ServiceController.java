package com.baeldung.infrastructure;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.application.CryptoServiceAdapter;

@RestController
public class ServiceController {

	@RequestMapping(path = "/encrypt", method = RequestMethod.GET)
	public String encryptEndpoint(@RequestParam(value = "text", defaultValue = "Some Text") String text) {
		CryptoServiceAdapter cryptoService = new CryptoServiceAdapter();
		String encryptedString = cryptoService.encryptString(text);
		return encryptedString;
	}

	@RequestMapping(path = "/decrypt", method = RequestMethod.GET)
	public String decryptEndpoint(@RequestParam(value = "text", defaultValue = "rcfXZqBYF97LcS+y52K1wg==") String text) {
		CryptoServiceAdapter cryptoService = new CryptoServiceAdapter();
		String decryptedString = cryptoService.decryptString(text);
		return decryptedString;
	}
}
