package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReadWriteMergeTest {
	final private ObjectMapper om = new ObjectMapper(new YAMLFactory());

	@Test
	void testRead() throws IOException {
		var davidFile = new File(Objects.requireNonNull(getClass().getResource("david.yaml")).getFile());
		var david = om.readValue(davidFile, Employee.class);
		PojoValidator.validate(david);
		assertEquals(1000, david.getId());
		assertEquals("David", david.getName());
		assertEquals(1500, david.getWage());
		assertEquals("Developer", david.getPosition());
		assertNotNull(david.getColleagues());
		assertEquals(2, david.getColleagues().size());
	}


	@Test
	void testReadInvalid() throws IOException {
		var davidFile = new File(Objects.requireNonNull(getClass().getResource("david.yaml")).getFile());
		var david = om.readValue(davidFile, Employee.class);
		david.setWage(15000);
		var mary = david.getColleagues().get(1002L);
		assertNotNull(mary);
		mary.setWage(10000);
		PojoValidator.validate(david);
	}

	@Test
	void testWrite() throws IOException, URISyntaxException {
		var colleagues = new TreeMap<Long, Employee>();
		colleagues.put(1001L, new Employee(1001, "Jane", 1200, "Developer", null));
		colleagues.put(1002L, new Employee(1002, "Mary", 1500, "Developer", null));
		var david = new Employee(1000, "David", 1500, "Developer", colleagues);
		var result = new StringWriter();
		om.writeValue(result, david);
		var expected = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(
			getClass().getResource("david.yaml")).toURI())), StandardCharsets.UTF_8.name());
		assertEquals(expected, result.toString());
	}

	@Test
	void testMerge() throws IOException {
		var davidFile = new File(Objects.requireNonNull(getClass().getResource("david.yaml")).getFile());
		var david = om.readValue(davidFile, Employee.class);
		PojoValidator.validate(david);
		assertEquals("David", david.getName());
		assertEquals(1500, david.getWage());
		assertEquals("Developer", david.getPosition());
		assertNotNull(david.getColleagues());
		assertEquals(2, david.getColleagues().size());

		var mary = david.getColleagues().get(1002L);
		assertNotNull(mary);
		assertEquals("Mary", mary.getName());
		assertEquals(1500, mary.getWage());
		assertEquals("Developer", mary.getPosition());

		var davidUpdateFile = new File(Objects.requireNonNull(getClass().getResource("david-update.yaml")).getFile());
		ObjectReader merger = om.readerForUpdating(david);

		var davidMerged = merger.readValue(davidUpdateFile, Employee.class);
		PojoValidator.validate(davidMerged);
		assertEquals(3, davidMerged.getColleagues().size());
		assertNotNull(david.getColleagues().get(1002L));

		assertEquals(2000, mary.getWage());
		assertEquals("Boss", mary.getPosition());
	}
}
