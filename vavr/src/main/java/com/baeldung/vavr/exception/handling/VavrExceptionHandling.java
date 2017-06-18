package com.baeldung.vavr.exception.handling;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import io.vavr.Function1;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;

public class VavrExceptionHandling {
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	public static Integer readFromFile(Integer i) throws IOException {
        if (i == 0) {
            throw new IOException(); // mock IOException
        }
        return i * i;
    }
	
	public static Option<Integer> usingFunctionLifting(String s) {
		Function1<String, Integer> parsefn = i -> Integer.parseInt(i);

    	Function1<String, Option<Integer>> fn = Function1.lift(parsefn);
    	
    	return fn.apply(s);
	}
	
	public static Try<Integer> usingTryContainer(Integer i) {
		return Try.of(() -> readFromFile(i));
	}

    public static Validation<Seq<Throwable>, Boolean> validateBookingDates(String checkInDateString, String checkOutDateString){
    	
    	Validation.Builder<Throwable, LocalDate, LocalDate> builder = Validation.combine(parseDate(checkInDateString), parseDate(checkOutDateString));
    	
    	return builder.ap((checkInDate, checkOutDate) -> checkInDate.isBefore(checkOutDate));
    }

    private static Validation<Throwable, LocalDate> parseDate(String dateString) {
		Try<LocalDate> parsedDate = Try.of(() -> LocalDate.from(formatter.parse(dateString)));
    	
    	return parsedDate.isSuccess() ? Validation.valid(parsedDate.get()) : Validation.invalid(parsedDate.getCause());
    }

}