package com.feiyangedu.springcloud.es.domain;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "es")
public class News {

	public String id;
	public String link;
	public String title;
	public String description;
	public long pubDate;

	@Override
	public String toString() {
		return new StringBuilder(128).append("News(title=").append(title).append(", description=")
				.append(description.length() >= 20 ? description.substring(0, 20) + "..." : description).append(")")
				.toString();
	}
}
