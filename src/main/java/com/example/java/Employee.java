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
import java.util.Objects;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Comparable<Employee> {
    public Employee(long id, String name, int wage, String position) {
        this(id, name, wage, position, Collections.emptyMap()); }

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
    private Map<Long, Employee> colleagues = new TreeMap<>();

    @Override
    public int compareTo(Employee employee) {
        if(id == employee.id) return 0;
        if(id < employee.id) return -1;
        return 1;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Employee employee = (Employee) other;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
