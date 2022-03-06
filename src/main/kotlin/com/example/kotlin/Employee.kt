package com.example.kotlin

import com.fasterxml.jackson.annotation.JsonMerge
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class Employee(
    @field:Min(0)
    var id: Long = -1,

    @field:NotBlank
    @field:Pattern(regexp = "^[\\w'\\-,.][^0-9_!¡?÷¿/\\\\+=@#$%ˆ&*(){}|~<>;:\\[\\]]{2,}$")
    var name: String = "",

    @field:Min(500)
    @field:Max(5000)
    var wage: Int = 0,

    @field:NotBlank
    var position: String = "",

    @JsonMerge
    @field:Valid
    var colleagues: Map<Long, Employee> = emptyMap()
)
