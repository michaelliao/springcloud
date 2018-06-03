package com.itranswarp.springcloud.data;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.itranswarp.springcloud.data.context.RoutingDataSource;
import com.itranswarp.springcloud.data.context.RoutingDataSourceContext;

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
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class DataRoutingDataSourceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DataRoutingDataSourceApplication.class, args);
	}

	@Bean("rwDataSourceProperties")
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties rwDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean("roDataSourceProperties")
	@ConfigurationProperties("spring.ro-datasource")
	public DataSourceProperties roDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public PlatformTransactionManager txManager(@Autowired DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * Create primary (default) routing DataSource.
	 */
	@Bean
	@Primary
	public DataSource primaryDataSource(@Autowired @Qualifier("rwDatasource") DataSource rw,
			@Autowired @Qualifier("roDatasource") DataSource ro) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(RoutingDataSourceContext.MASTER, rw);
		targetDataSources.put(RoutingDataSourceContext.SLAVE, ro);
		RoutingDataSource dataSource = new RoutingDataSource();
		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(rw);
		return dataSource;
	}

	/**
	 * Create rw DataSource and named "rwDatasource".
	 */
	@Bean("rwDatasource")
	public DataSource rwDataSource(@Autowired @Qualifier("rwDataSourceProperties") DataSourceProperties props) {
		return props.initializeDataSourceBuilder().build();
	}

	/**
	 * Create second DataSource and named "secondDatasource".
	 */
	@Bean("roDatasource")
	public DataSource roDataSource(@Autowired @Qualifier("roDataSourceProperties") DataSourceProperties props) {
		return props.initializeDataSourceBuilder().build();
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.regex("^/api/.*$")).build();
	}
}
