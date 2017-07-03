package com.feiyangedu.springcloud.data;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfiguration {

	/**
	 * Create primary (default) DataSource.
	 */
	@Bean("primaryDataSource")
	@ConfigurationProperties(prefix = "spring.primary-datasource")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * Create second DataSource and named "secondDatasource".
	 */
	@Bean("secondDatasource")
	@ConfigurationProperties(prefix = "spring.second-datasource")
	public DataSource secondDataSource() {
		return DataSourceBuilder.create().build();
	}

}
