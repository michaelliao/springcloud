package com.feiyangedu.springcloud.codegen;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import io.swagger.util.Json;

public class TypeMappingTest {

	@Test
	public void testGetMapping() throws Exception {
		assertEquals("String", getType("{ \"type\": \"string\" }"));
		assertEquals("Integer", getType("{ \"type\": \"string\", \"format\": \"int32\" }"));
		assertEquals("Long", getType("{ \"type\": \"string\", \"format\": \"int64\" }"));
		assertEquals("java.time.LocalDate", getType("{ \"type\": \"string\", \"format\": \"date\" }"));
		assertEquals("java.time.LocalTime", getType("{ \"type\": \"string\", \"format\": \"time\" }"));
		assertEquals("java.time.LocalDateTime", getType("{ \"type\": \"string\", \"format\": \"date-time\" }"));
		assertEquals("java.util.List<User>",
				getType("{ \"type\": \"array\", \"items\": { \"$ref\": \"#/definitions/User\" } }"));
		assertEquals("User", getType("{ \"description\": \"OK\", \"schema\": { \"$ref\": \"#/definitions/User\" } }"));
		assertEquals("String", getType("{ \"description\": \"OK\", \"schema\": { \"type\": \"string\" } }"));
		assertEquals("Sort", getType("{ \"$ref\": \"#/definitions/Sort\" }"));
	}

	@SuppressWarnings("unchecked")
	String getType(String json) throws Exception {
		Map<String, Object> map = Json.mapper().readValue(json, Map.class);
		return TypeMapping.getMapping(map);
	}
}
