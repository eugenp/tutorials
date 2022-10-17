package com.baeldung.vavr.either;

import java.util.HashMap;
import java.util.Map;

import io.vavr.control.Either;

public class EitherDemo {

	public static Object[] computeWithoutEitherUsingArray(int marks) {
		Object[] results = new Object[2];
		if (marks < 85) {
			results[0] = "Marks not acceptable";
		} else {
			results[1] = marks;
		}
		return results;
	}

	public static Map<String, Object> computeWithoutEitherUsingMap(int marks) {
		Map<String, Object> results = new HashMap<>();
		if (marks < 85) {
			results.put("FAILURE", "Marks not acceptable");
		} else {
			results.put("SUCCESS", marks);
		}
		return results;
	}

	static Either<String, Integer> computeWithEither(int marks) {
		if (marks < 85) {
			return Either.left("Marks not acceptable");
		} else {
			return Either.right(marks);
		}
	}
}
