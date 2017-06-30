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
		Map<String, Object> results = new HashMap<String, Object>();
		if (marks < 85) {
			results.put("FAILURE", "Marks not acceptable");
		} else {
			results.put("SUCCESS", marks);
		}
		return results;
	}

	private static Either<String, Integer> computeWithEither(int marks) {
		if (marks < 85) {
			return Either.left("Marks not acceptable");
		} else {
			return Either.right(marks);
		}
	}

	public static void main(String[] args) {

		Map<String, Object> results = computeWithoutEitherUsingMap(8);

		String error = (String) results.get("FAILURE");
		int marks = (int) results.get("SUCCESS");

		Either<String, Integer> result = computeWithEither(80);
		
		computeWithEither(80).right().map(i -> i * 2).toEither();

		error = result.getLeft();
		marks = result.get();

	}

}
