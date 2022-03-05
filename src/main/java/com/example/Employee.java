package com.example;

import com.fasterxml.jackson.annotation.JsonMerge;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Comparable<Employee> {
    private long id;

    @NotBlank
    @Pattern(regexp="^[\\w'\\-,.][^0-9_!¡?÷¿/\\\\+=@#$%ˆ&*(){}|~<>;:\\[\\]]{2,}$")
    private String name;

    @Min(500)
    @Max(5000)
    private int wage;

    @NotBlank
    private String position;

    @JsonMerge
    @Valid
    private Map<Long, Employee> colleagues;

    @Override
    public int compareTo(Employee employee) {
        return name.compareTo(employee.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
