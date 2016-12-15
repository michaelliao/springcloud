package com.feiyangedu.springcloud.petstore.common.rest;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feiyangedu.springcloud.petstore.common.exception.APIException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class RestErrorDecoder implements ErrorDecoder {

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() == 400) {
			Collection<String> values = response.headers().get("Content-Type");
			if (values != null && !values.isEmpty()) {
				String contentType = values.iterator().next();
				if (contentType != null && contentType.startsWith("application/json")) {
					try (Reader reader = response.body().asReader()) {
						APIErrorResponse resp = objectMapper.readValue(reader, APIErrorResponse.class);
						return new APIException(resp.code, resp.message);
					} catch (IOException e) {
						return new RuntimeException(e);
					}
				}
			}
		}
		return new RuntimeException("Unknown exception: " + response.status());
	}

}
