package com.example;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PojoValidator {
	private final static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private final static Validator validator = factory.getValidator();

	public static <T> void validate(T pojo) {
		try {
			final var violations = validator.validate(pojo);
			if (violations.size() > 0) {
				System.out.println("POJO " + pojo + "\ncontains errors: ");
				violations.forEach(u -> System.out.println("  '" + u.getPropertyPath().toString() + "'" + " " + u.getMessage()));
				System.out.println("****************************************\n");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
