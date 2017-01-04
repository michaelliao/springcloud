package com.feiyangedu.springcloud.es.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.feiyangedu.springcloud.es.domain.News;

public interface NewsRepository extends ElasticsearchCrudRepository<News, String> {

	List<News> findByTitleLike(String q);
}
