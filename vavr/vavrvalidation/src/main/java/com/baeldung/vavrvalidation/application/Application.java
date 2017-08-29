package com.baeldung.vavrvalidation.application;

import com.baeldung.vavrvalidation.model.User;
import com.baeldung.vavrvalidation.validator.UserValidator;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import io.vavr.control.Validation.Invalid;

public class Application {

	public static void main(String[] args) {
    	
    	UserValidator userValidator = new UserValidator();
    	Validation<Seq<String>, User> validation = userValidator.validateUser(" ", " ");
    	
       /** Comment this out 
    	* for checking validation results
    	* using the Valid / Invalid instances
		if (validation instanceof Invalid) {
			validation.getError().forEach(System.out::println);
		}
		else {
			System.out.println(validation.get());
		}
		*/
    	
       /** Comment this out
    	* for checking validation results
    	* using the isValid() / isInvalid() methods
    	if (validation.isInvalid()) {
    		validation.getError().forEach(System.out::println);
    	}
    	else {
    		System.out.println(validation.get());
    	}
    	*/
    	
       /** Comment this out
    	* for using the toEither() method
    	System.out.println(validation.toEither());
    	*/
    	
       /** Comment this out
    	* for using the fold() method
    	int result = validation.fold(Seq::length, User::hashCode);
    	System.out.println(result);
    	*/
    }
}
