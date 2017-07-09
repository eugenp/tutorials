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

	public static Either<String, Integer> computeWithEither(int marks) {
		if (marks < 85) {
			return Either.left("Marks not acceptable");
		} else {
			return Either.right(marks);
		}
	}

	public static String getError(Either<String, Integer> result) {
		return result.getLeft();
	}

	public static int getMarks(Either<String, Integer> result) {
		return result.get();
	}

	public static int getModifiedMarks(Either<String, Integer> result) {
		result = result.right().map(i -> i * 2).toEither();
		return result.get();
	}

	public void utilities() {

		String error;
		int marks;

		Either<String, Integer> result = computeWithEither(100);

		result.toArray();
		result.toCharSeq();
		result.toLinkedSet();
		result.toList();
		result.toOption();
		result.toPriorityQueue();
		result.iterator();
		result.toVector();
		result.toTree();
		result.toStream();

		result.toJavaArray();
		result.toJavaList();
		result.toJavaOptional();
		result.toJavaParallelStream();
		result.toJavaSet();
		result.toJavaStream();
		result.toJavaList();

		Either.RightProjection<String, Integer> projection = computeWithEither(9).right();

		result.contains(800);
		result.isLeft();
		result.isRight();

		if (result.isLeft()) {
			error = result.getLeft();
		} else {
			marks = result.get();
		}
	}

}
