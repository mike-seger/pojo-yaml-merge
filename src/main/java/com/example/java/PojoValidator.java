package com.example.java;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static java.lang.System.out;

class PojoValidator {
	private final static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private final static Validator validator = factory.getValidator();

	public static <T> void validate(T pojo) {
		try {
			final var violations = validator.validate(pojo);
			if (violations.size() > 0) {
				out.printf("POJO  %s\ncontains errors:\n", pojo);
				violations.forEach(u -> System.out.printf("  '%s' %s\n", u.getPropertyPath().toString(), u.getMessage()));
				out.println("****************************************\n");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
