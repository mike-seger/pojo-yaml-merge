package com.example.kotlin

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import java.util.function.Consumer

object PojoValidator {
    private val factory = Validation.buildDefaultValidatorFactory()
    private val validator = factory.validator
    fun <T> validate(pojo: T) {
        try {
            val violations = validator.validate(pojo)
            if (violations.size > 0) {
                println("POJO $pojo\ncontains errors: ")
                violations.forEach(Consumer { u: ConstraintViolation<T> ->
                    println(
                        "  '" + u.propertyPath.toString() + "'" + " " + u.message
                    )
                })
                println("****************************************\n")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
