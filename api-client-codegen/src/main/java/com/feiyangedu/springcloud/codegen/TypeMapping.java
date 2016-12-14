package com.feiyangedu.springcloud.codegen;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TypeMapping {

	static final Map<String, String> MAPPING = new HashMap<String, String>() {
		{
			put("string", "String");
			put("boolean", "Boolean");
			put("integer", "Integer");
			put("int16", "Short");
			put("int32", "Integer");
			put("int64", "Long");
			put("date", "java.time.LocalDate");
			put("time", "java.time.LocalTime");
			put("date-time", "java.time.LocalDateTime");
		}
	};

	/**
	 * Input as: { "type": "string" }, { "type": "integer", "format": "int32" },
	 * {"schema": "$ref":"#/#/definitions/User"}
	 */
	@SuppressWarnings("unchecked")
	public static String getMapping(Map<String, Object> mapping) {
		String format = (String) mapping.getOrDefault("format", "");
		if (!format.isEmpty()) {
			return fromMap(format);
		}
		String type = (String) mapping.getOrDefault("type", "");
		if (MAPPING.containsKey(type)) {
			return fromMap(type);
		}
		if (type.equals("array")) {
			return "java.util.List<" + fromSchema((Map<String, Object>) mapping.get("items")) + ">";
		}
		if (mapping.containsKey("$ref")) {
			return fromSchema(mapping);
		}
		// try schema:
		return fromSchema((Map<String, Object>) mapping.get("schema"));
	}

	static String fromSchema(Map<String, Object> schema) {
		if (schema == null) {
			throw new NullPointerException("Missing schema");
		}
		String ref = (String) schema.get("$ref");
		if (ref != null) {
			if (ref.startsWith("#/definitions/")) {
				ref = ref.substring("#/definitions/".length());
			}
			return ref;
		}
		return getMapping(schema);
	}

	static String fromMap(String key) {
		return Objects.requireNonNull(MAPPING.get(key), "Key not found: " + key);
	}
}
