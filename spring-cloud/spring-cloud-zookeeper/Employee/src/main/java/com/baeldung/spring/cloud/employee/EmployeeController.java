/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.spring.cloud.employee;

import com.baeldung.spring.cloud.employee.model.Employee;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	@Autowired
	private ValidationClient validationClient;

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Employee> create(@RequestBody Employee content) throws IOException {

		if (validationClient.isValid(content.getemailAddress())) {
			content.setvalidRecord(Boolean.TRUE);
		} else {
			content.setvalidRecord(Boolean.FALSE);
		}

		return new ResponseEntity<>(content, HttpStatus.OK);
	}

}
