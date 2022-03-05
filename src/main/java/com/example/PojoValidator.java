package com.example;

import jakarta.validation.*;

import java.util.Set;

public class PojoValidator {
	private final static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private final static Validator validator = factory.getValidator();

	public static <T> void validate(@Valid T pojo) {
		try {
			if (pojo != null) {
				final Set<ConstraintViolation<T>> violations = validator.validate(pojo);
				if (violations.size() > 0) {
					System.out.println("POJO contains errors:");
					violations.forEach(u -> System.out.println("  \"" + u.getPropertyPath().toString() + "\"" + " " + u.getMessage()));
					System.out.println("****************************************");
				} else {
					System.out.println("No validation errors");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
