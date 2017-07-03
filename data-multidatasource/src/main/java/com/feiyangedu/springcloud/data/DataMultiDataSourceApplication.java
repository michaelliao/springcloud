package com.feiyangedu.springcloud.data;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.huobi.RWRoutingDataSource;

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
@Import(DataSourceConfiguration.class)
public class DataMultiDataSourceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DataMultiDataSourceApplication.class, args);
	}

	@Resource
	@Qualifier("primaryDataSource")
	DataSource primary;

	@Bean
	@Primary
	public DataSource ds() {
		if(primary==null){
			throw new RuntimeException("NULL");
		}
		RWRoutingDataSource ds = new RWRoutingDataSource();
		ds.setDefaultTargetDataSource(primary);
		Map<Object, Object> map = new HashMap<>();
		map.put("", primary);
		ds.setTargetDataSources(map);
		return ds;
	}

	/**
	 * Create primary (default) JdbcTemplate from primary DataSource.
	 */
	@Bean
	@Primary
	public JdbcTemplate primaryJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	/**
	 * Create second JdbcTemplate from second DataSource.
	 */
	@Bean(name = "secondJdbcTemplate")
	public JdbcTemplate secondJdbcTemplate(@Qualifier("secondDatasource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.regex("^/api/.*$")).build();
	}
}
