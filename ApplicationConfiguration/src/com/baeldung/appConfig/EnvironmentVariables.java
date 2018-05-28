package com.baeldung.appConfig;

import java.util.Map;

public class EnvironmentVariables {

	public static void main(String[] args) {
		Map<String, String> envVars = System.getenv();
		envVars.forEach((k, v) -> {
			System.out.println(k + " : " + v);
		});
	}
}
