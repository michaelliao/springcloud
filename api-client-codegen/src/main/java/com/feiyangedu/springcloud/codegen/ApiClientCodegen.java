package com.feiyangedu.springcloud.codegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;

public class ApiClientCodegen {

	TemplateBuilder builder = new TemplateBuilder();
	Map<String, Object> config;
	Map<String, Object> swaggerData;

	@SuppressWarnings("unchecked")
	ApiClientCodegen(List<String> args) throws Exception {
		String name = parse("--name=", args);
		// read schema data:
		String url = parse("--url=", args);
		Swagger swagger = new SwaggerParser().read(url);
		String json = Json.pretty().writeValueAsString(swagger).replace('«', '<').replace('»', '>');
		this.swaggerData = Json.mapper().readValue(json, Map.class);

		// add default args:
		List<String> list = new ArrayList<>(args);
		list.add("--groupId=io.swagger.api.client");
		list.add("--version=0.0.1-SNAPSHOT");
		list.add("--publicField=false");
		list.add("--springVersion=4.3.4.RELEASE");
		list.add("--output=" + name + "-client");
		this.config = getConfig(list);
		// build client:
		String output = parse("--output=", list);
		buildClient(output);
		System.out.println("Generated in " + output + ".");
	}

	@SuppressWarnings("unchecked")
	void buildClient(String path) throws Exception {
		File root = new File(path);
		if (!root.isDirectory()) {
			if (!root.mkdirs()) {
				throw new RuntimeException("Mkdir failed: " + path);
			}
		}
		// create pom.xml:
		Map<String, Object> pomContext = new HashMap<>();
		pomContext.put("config", this.config);
		createByTemplate("pom.txt", pomContext, new File(root, "pom.xml"), null);
		// mkdirs:
		String name = (String) this.config.get("name");
		String maxPackage = (String) this.config.get("basePackage") + ".model";
		String[] pkgs = maxPackage.split("\\.");
		List<String> subdirs = new ArrayList<>();
		subdirs.add("src");
		subdirs.add("main");
		subdirs.add("java");
		subdirs.addAll(Arrays.asList(pkgs));
		File modelPath = mkdirs(root, subdirs.toArray(new String[0]));
		// basePackage dir:
		File clientPath = modelPath.getParentFile();
		// generate XxxClient.java:
		Map<String, Object> clientContext = new HashMap<>();
		clientContext.put("config", this.config);
		clientContext.put("swagger", swaggerData);
		clientContext.put("util", new Util());
		String javaFilename = Character.toUpperCase(name.charAt(0)) + name.substring(1) + "Client.java";
		createByTemplate("client.txt", clientContext, new File(clientPath, javaFilename), null);
		// generate models:
		Map<String, Object> definitions = (Map<String, Object>) this.swaggerData.getOrDefault("definitions",
				Collections.emptyMap());
		for (String key : definitions.keySet()) {
			Map<String, Object> modelContext = new HashMap<>();
			String genericType = "";
			Matcher matcher = Pattern.compile("^(\\w+)(\\<\\w+\\>)$").matcher(key);
			if (matcher.matches()) {
				genericType = matcher.group(2);
			}
			modelContext.put("name", key);
			modelContext.put("swagger", this.swaggerData);
			modelContext.put("config", this.config);
			modelContext.put("util", new Util());
			String javaModelFilename = Character.toUpperCase(key.charAt(0))
					+ key.substring(1, key.length() - genericType.length()) + ".java";
			createByTemplate("bean.txt", modelContext, new File(modelPath, javaModelFilename), genericType);
		}
	}

	void createByTemplate(String templateName, Map<String, Object> context, File output, String genericType)
			throws Exception {
		String str = this.builder.render(templateName, context);
		if (genericType != null && !genericType.isEmpty()) {
			str = str.replace(genericType, "<T>");
		}
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"))) {
			writer.write(str);
		}
	}

	File mkdirs(File root, String... children) throws Exception {
		File p = new File(root, String.join(File.separator, children));
		if (!p.isDirectory()) {
			p.mkdirs();
		}
		if (!p.isDirectory()) {
			throw new RuntimeException("Mkdirs failed: " + p.getAbsolutePath());
		}
		return p;
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("Usage:");
			System.out.println("java -jar xxx.jar --name=<app-name> --url=http://host/path/to/api-docs");
			System.exit(0);
		}
		new ApiClientCodegen(Arrays.asList(args));
	}

	static Map<String, Object> getConfig(List<String> args) {
		String name = parse("--name=", args);
		boolean publicField = "true".equals(parse("--publicField", args));
		String groupId = parse("--groupId=", args);
		String version = parse("--version=", args);
		String springVersion = parse("--springVersion=", args);

		Map<String, Object> cfg = new HashMap<>();
		cfg.put("groupId", groupId);
		cfg.put("version", version);
		cfg.put("basePackage", groupId + "." + name);
		cfg.put("name", name);
		cfg.put("publicField", publicField);
		cfg.put("springVersion", springVersion);
		return cfg;
	}

	static String parse(String prefix, List<String> args) {
		for (String arg : args) {
			if (arg.startsWith(prefix)) {
				return arg.substring(prefix.length());
			}
		}
		System.err.println("Missing argument: " + prefix);
		System.exit(1);
		throw new RuntimeException("Missing argument: " + prefix);
	}
}
