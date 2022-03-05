package com.example.kotlin

import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException
import java.io.StringWriter
import java.net.URISyntaxException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

internal class ReadWriteMergeTestKotlin {
    private val om = ObjectMapper(YAMLFactory()).registerKotlinModule()

    @Test
    @Throws(IOException::class)
    fun testRead() {
        val davidFile = File(Objects.requireNonNull(javaClass.getResource("/david.yaml")).file)
        val david = om.readValue(davidFile, Employee::class.java)
        PojoValidator.validate(david)
        assertEquals(1000, david.id)
        assertEquals("David", david.name)
        assertEquals(1500, david.wage)
        assertEquals("Developer", david.position)
        assertNotNull(david.colleagues)
        //assertEquals(2, david.colleagues?.size ?: 0)
        assertEquals(2, david.colleagues.size)
    }

    @Test
    @Throws(IOException::class)
    fun testReadInvalid() {
        val davidFile = File(Objects.requireNonNull(javaClass.getResource("/david.yaml")).file)
        val david = om.readValue(davidFile, Employee::class.java)
        david.wage = 15000
 //       val mary = david.colleagues?.get(1002L)
        val mary = david.colleagues[1002L]
        assertNotNull(mary)
        mary!!.wage = 10000
        PojoValidator.validate(david)
    }

    @Test
    @Throws(IOException::class, URISyntaxException::class)
    fun testWrite() {
        val colleagues = TreeMap<Long, Employee>()
        colleagues[1001L] = Employee(1001, "Jane", 1200, "Developer", emptyMap())
        colleagues[1002L] = Employee(1002, "Mary", 1500, "Developer", emptyMap())
        val david = Employee(1000, "David", 1500, "Developer", colleagues)
        val result = StringWriter()
        om.writeValue(result, david)
        val expected = String(
            Files.readAllBytes(Paths.get(Objects.requireNonNull(javaClass.getResource("/david.yaml")).toURI())), Charsets.UTF_8)
        assertEquals(expected, result.toString())
    }

    @Test
    @Throws(IOException::class)
    fun testMerge() {
        val davidFile = File(Objects.requireNonNull(javaClass.getResource("/david.yaml")).file)
        val david = om.readValue(davidFile, Employee::class.java)
        PojoValidator.validate(david)
        assertEquals("David", david.name)
        assertEquals(1500, david.wage)
        assertEquals("Developer", david.position)
        assertNotNull(david.colleagues)
//        assertEquals(2, david.colleagues?.size ?: 0)
        assertEquals(2, david.colleagues.size)
        //val mary = david.colleagues?.get(1002L)
        val mary = david.colleagues[1002L]
        assertNotNull(mary)
        assertEquals("Mary", mary!!.name)
        assertEquals(1500, mary.wage)
        assertEquals("Developer", mary.position)
        val davidUpdateFile = File(Objects.requireNonNull(javaClass.getResource("/david-update.yaml")).file)
        val merger = om.readerForUpdating(david)
        val davidMerged = merger.readValue(davidUpdateFile, Employee::class.java)
        PojoValidator.validate(davidMerged)
//        assertEquals(3, davidMerged.colleagues?.size ?: 0, "colleagues are not properly merged using JSonMerge")
        assertEquals(3, davidMerged.colleagues.size)
        //val maryUpdated = david.colleagues?.get(1002L)
        val maryUpdated = david.colleagues[1002L]
        assertNotNull(maryUpdated)
        assertEquals(2000, maryUpdated?.wage ?: 0)
        assertEquals("Boss", maryUpdated?.position)
    }
}
