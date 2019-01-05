package com.feiyangedu.springcloud.es;

import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feiyangedu.springcloud.es.domain.News;
import com.feiyangedu.springcloud.es.repository.NewsRepository;
import com.feiyangedu.springcloud.es.util.Phrase;
import com.feiyangedu.springcloud.es.util.Span;
import com.feiyangedu.springcloud.es.util.SplitUtil;
import com.feiyangedu.springcloud.es.util.Word;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Boot Application using Spring Data Jdbc.
 * 
 * @author Michael Liao
 */
@SpringBootApplication
@EnableSwagger2
@RestController
@EnableElasticsearchRepositories
public class DataESApplication {

	final Log log = LogFactory.getLog(getClass());

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DataESApplication.class, args);
	}

	@Autowired
	NewsRepository newsRepository;

	@Autowired
	ElasticsearchTemplate esTemplate;

	@PostConstruct
	public void init() throws Exception {
		// load rss from http://rss.sina.com.cn/tech/rollnews.xml
		SyndFeed feed = null;
		String url = "http://rss.sina.com.cn/tech/rollnews.xml";
		log.info("Read rss from " + url + "...");
		SyndFeedInput input = new SyndFeedInput();
		try (XmlReader reader = new XmlReader(new URL(url))) {
			feed = input.build(reader);
		}
		List<SyndEntry> entries = feed.getEntries();
		log.info("Read " + entries.size() + " entries.");
		List<News> newsList = entries.stream().map(this::fromEntry).collect(Collectors.toList());
		for (News news : newsList) {
			System.out.println(news);
		}
		newsRepository.saveAll(newsList);
	}

	@GetMapping("/api/title")
	public List<News> getNews(@RequestParam("q") String q) {
		return newsRepository.findByTitleLike(q);
	}

	@GetMapping("/api/search")
	public List<News> search(@RequestParam("q") String q) {
		Span[] spans = SplitUtil.split(q);
		if (spans.length == 0) {
			return null;
		}
		QueryBuilder queryBuilder = null;
		if (spans.length == 1) {
			queryBuilder = createQueryBuilder(spans[0]);
		} else {
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			for (Span span : spans) {
				boolQueryBuilder.should(createQueryBuilder(span));
			}
			queryBuilder = boolQueryBuilder;
		}
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
		return esTemplate.queryForList(query, News.class);
	}

	QueryBuilder createQueryBuilder(Span span) {
		if (span instanceof Word) {
			return QueryBuilders.termQuery("_all", span.text);
		}
		if (span instanceof Phrase) {
			if (span.text.length() > 3) {
				return QueryBuilders.multiMatchQuery(span.text, "_all").minimumShouldMatch("75%");
			} else {
				return QueryBuilders.matchPhraseQuery("_all", span.text);
			}
		}
		throw new IllegalArgumentException("Unsupported type of Span: " + span.getClass().getName());
	}

	public News fromEntry(SyndEntry entry) {
		News news = new News();
		news.id = hash256(entry.getLink().trim());
		news.link = entry.getLink().trim();
		news.title = entry.getTitle().trim();
		news.description = entry.getDescription().getValue().trim();
		news.pubDate = entry.getPublishedDate().getTime();
		return news;
	}

	String hash256(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(s.getBytes(StandardCharsets.UTF_8));
			return String.format("%064x", new BigInteger(1, hash));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.regex("^/api/.*$")).build();
	}

}
