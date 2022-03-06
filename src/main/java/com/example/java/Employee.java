package com.example.java;

import com.fasterxml.jackson.annotation.JsonMerge;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    public Employee(long id, String name, int wage, String position) {
        this(id, name, wage, position, Collections.emptyMap()); }

    @Min(0)
    long id;

    @NotBlank
    @Pattern(regexp="^[\\w'\\-,.][^0-9_!¡?÷¿/\\\\+=@#$%ˆ&*(){}|~<>;:\\[\\]]{2,}$")
    String name;

    @Min(500)
    @Max(5000)
    int wage;

    @NotBlank
    String position;

    @JsonMerge
    @Valid
    Map<Long, Employee> colleagues = new TreeMap<>();
}
