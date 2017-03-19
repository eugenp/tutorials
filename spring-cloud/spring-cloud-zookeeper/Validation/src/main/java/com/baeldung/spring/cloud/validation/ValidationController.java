/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.spring.cloud.validation;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationController {

	@RequestMapping(path = "/validation/isvalid", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
	public Boolean isValid(@RequestBody(required = false) String email) {
		return email.contains("@");
	}

}
