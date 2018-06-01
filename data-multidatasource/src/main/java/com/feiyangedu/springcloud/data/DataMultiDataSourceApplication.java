package com.feiyangedu.springcloud.data;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Boot Application using Spring Data Jdbc.
 * 
 * @author Michael Liao
 */
@EnableSwagger2
@SpringBootApplication
public class DataMultiDataSourceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DataMultiDataSourceApplication.class, args);
	}

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties primaryDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean("secondDataSourceProperties")
	@ConfigurationProperties("spring.second-datasource")
	public DataSourceProperties secondDataSourceProperties() {
		return new DataSourceProperties();
	}

	/**
	 * Create primary (default) DataSource.
	 */
	@Bean
	@Primary
	public DataSource primaryDataSource(@Autowired DataSourceProperties props) {
		return props.initializeDataSourceBuilder().build();
	}

	/**
	 * Create second DataSource and named "secondDatasource".
	 */
	@Bean("secondDatasource")
	public DataSource secondDataSource(@Autowired @Qualifier("secondDataSourceProperties") DataSourceProperties props) {
		return props.initializeDataSourceBuilder().build();
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
	public JdbcTemplate secondJdbcTemplate(@Autowired @Qualifier("secondDatasource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.regex("^/api/.*$")).build();
	}
}
