package com.feiyangedu.springcloud.petstore.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanUtil {

	public static void copyPublicFields(Object source, Object target) {
		// copy public field:
		Map<String, Field> sourceFields = getPublicFields(source.getClass());
		Map<String, Field> targetFields = getPublicFields(target.getClass());
		try {
			for (Map.Entry<String, Field> sourceEntry : sourceFields.entrySet()) {
				Field targetField = targetFields.get(sourceEntry.getKey());
				if (targetField != null) {
					// copy:
					targetField.set(target, sourceEntry.getValue().get(source));
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private static Map<String, Field> getPublicFields(Class<?> clazz) {
		Map<String, Field> map = cache.get(clazz);
		if (map == null) {
			map = new HashMap<>();
			for (Field f : clazz.getFields()) {
				map.put(f.getName(), f);
			}
			cache.put(clazz, map);
		}
		return map;
	}

	static Map<Class<?>, Map<String, Field>> cache = new ConcurrentHashMap<>();
}
