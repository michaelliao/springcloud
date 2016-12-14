package com.feiyangedu.springcloud.codegen;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

public class TemplateBuilder {

	final PebbleEngine engine;

	public TemplateBuilder() {
		engine = new PebbleEngine.Builder().extension(new CustomPebbleExtension()).autoEscaping(false).build();
	}

	public String render(String templateName, Map<String, Object> data) {
		try (StringWriter writer = new StringWriter()) {
			PebbleTemplate t = engine.getTemplate(templateName);
			t.evaluate(writer, data);
			return writer.toString();
		} catch (PebbleException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}

class CustomPebbleExtension extends AbstractExtension {

	@Override
	public Map<String, Filter> getFilters() {
		Map<String, Filter> map = new HashMap<>();
		map.put("firstUpper", new FirstUpperFilter());
		map.put("firstLower", new FirstLowerFilter());
		map.put("getter", new GetterFilter());
		map.put("setter", new SetterFilter());
		return map;
	}

}

class GetterFilter implements Filter {

	@Override
	public List<String> getArgumentNames() {
		return Arrays.asList("type");
	}

	@Override
	public Object apply(Object input, Map<String, Object> args) {
		String name = (String) input;
		String type = (String) args.get("type");
		if ("Boolean".equals(type)) {
			return "is" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
		}
		return "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}
}

class SetterFilter implements Filter {

	@Override
	public List<String> getArgumentNames() {
		return null;
	}

	@Override
	public Object apply(Object input, Map<String, Object> args) {
		String name = (String) input;
		return "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}
}

class FirstLowerFilter implements Filter {

	@Override
	public List<String> getArgumentNames() {
		return null;
	}

	@Override
	public Object apply(Object input, Map<String, Object> args) {
		String s = (String) input;
		return Character.toLowerCase(s.charAt(0)) + s.substring(1);
	}
}

class FirstUpperFilter implements Filter {

	@Override
	public List<String> getArgumentNames() {
		return null;
	}

	@Override
	public Object apply(Object input, Map<String, Object> args) {
		String s = (String) input;
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}
}
