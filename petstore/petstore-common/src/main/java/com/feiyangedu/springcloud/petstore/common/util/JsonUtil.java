package com.feiyangedu.springcloud.petstore.common.util;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {

	public static final ObjectMapper objectMapper = createObjectMapper();

	static ObjectMapper createObjectMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		// ignored properties: Page.sort
		mapper.addMixIn(Page.class, PageMixIn.class);
		// disabled features:
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// add java8 time support:
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	public static String writeJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T readJson(String str, Class<T> clazz) {
		try {
			return objectMapper.readValue(str, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

abstract class PageMixIn {

	@JsonIgnore
	public abstract Sort getSort(); // we don't need it!

}
