package com.baeldung.pathvariable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class PathVariableAnnotationController {
	@GetMapping("/api/employees/{id}")
	@ResponseBody
	public String getEmployeesById(@PathVariable String id) {
		return "ID: " + id;
	}

	@GetMapping("/api/employeeswithvariable/{id}")
	@ResponseBody
	public String getEmployeesByIdWithVariableName(@PathVariable("id") String employeeId) {
		return "ID: " + employeeId;
	}

	@GetMapping("/api/employees/{id}/{name}")
	@ResponseBody
	public String getEmployeesByIdAndName(@PathVariable String id, @PathVariable String name) {
		return "ID: " + id + ", name: " + name;
	}

	@GetMapping("/api/employeeswithmapvariable/{id}/{name}")
	@ResponseBody
	public String getEmployeesByIdAndNameWithMapVariable(@PathVariable Map<String, String> pathVarsMap) {
		String id = pathVarsMap.get("id");
		String name = pathVarsMap.get("name");
		if (id != null && name != null) {
			return "ID: " + id + ", name: " + name;
		} else {
			return "Missing Parameters";
		}
	}

	@GetMapping(value = { "/api/employeeswithrequired", "/api/employeeswithrequired/{id}" })
	@ResponseBody
	public String getEmployeesByIdWithRequired(@PathVariable String id) {
		return "ID: " + id;
	}

	@GetMapping(value = { "/api/employeeswithrequiredfalse", "/api/employeeswithrequiredfalse/{id}" })
	@ResponseBody
	public String getEmployeesByIdWithRequiredFalse(@PathVariable(required = false) String id) {
		if (id != null) {
			return "ID: " + id;
		} else {
			return "ID missing";
		}
	}

	@GetMapping(value = { "/api/employeeswithoptional", "/api/employeeswithoptional/{id}" })
	@ResponseBody
	public String getEmployeesByIdWithOptional(@PathVariable Optional<String> id) {
		if (id.isPresent()) {
			return "ID: " + id.get();
		} else {
			return "ID missing";
		}
	}

	@GetMapping(value = { "/api/employeeswithmap/{id}", "/api/employeeswithmap" })
	@ResponseBody
	public String getEmployeesByIdWithMap(@PathVariable Map<String, String> pathVarsMap) {
		String id = pathVarsMap.get("id");
		if (id != null) {
			return "ID: " + id;
		} else {
			return "ID missing";
		}
	}
}
