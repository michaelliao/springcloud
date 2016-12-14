package com.feiyangedu.springcloud.codegen;

import java.util.Map;

public class Util {

	public String getJavaType(Map<String, Object> input) {
		return TypeMapping.getMapping(input);
	}

}
