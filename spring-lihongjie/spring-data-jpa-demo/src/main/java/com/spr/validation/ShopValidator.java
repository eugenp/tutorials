package com.spr.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spr.model.Shop;

@Component
public class ShopValidator implements Validator {
	
	private final static String EMPLOYEES_NUMBER = "emplNumber";

	@Override
	public boolean supports(Class<?> clazz) {
		return Shop.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Shop shop = (Shop) target;
		
		Integer emplNumber = shop.getEmplNumber();
		
		ValidationUtils.rejectIfEmpty(errors, "name", "shop.name.empty");
		ValidationUtils.rejectIfEmpty(errors, EMPLOYEES_NUMBER, "shop.emplNumber.empty");
		
		if (emplNumber != null && emplNumber < 1)
			errors.rejectValue(EMPLOYEES_NUMBER, "shop.emplNumber.lessThenOne");

	}

}
