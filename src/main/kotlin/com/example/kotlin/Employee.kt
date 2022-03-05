package com.example.kotlin

import com.fasterxml.jackson.annotation.JsonMerge
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import java.util.*

class Employee(
        var id: Long = -1, var name: String = "", var wage: Int = 0,
        var position: String = "",
//        @get:JsonSetter(nulls = Nulls.SKIP) @JsonMerge var colleagues: Map<Long, Employee>? = null
        @get:JsonSetter(nulls = Nulls.SKIP) @JsonMerge var colleagues: Map<Long, Employee> = emptyMap()
    ) : Comparable<Employee> {

   // constructor() : this(0, "", 0, "", null)

    override operator fun compareTo(other: Employee): Int {
        return id.compareTo(other.id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val employee = other as Employee
        return id == employee.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}
