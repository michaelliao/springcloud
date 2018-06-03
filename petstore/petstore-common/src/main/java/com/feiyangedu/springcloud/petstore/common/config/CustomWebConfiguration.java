package com.feiyangedu.springcloud.petstore.common.config;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feiyangedu.springcloud.petstore.common.context.UserContext;
import com.feiyangedu.springcloud.petstore.common.context.UserInfo;
import com.feiyangedu.springcloud.petstore.common.exception.APIException;
import com.feiyangedu.springcloud.petstore.common.filter.UserContextFilter;
import com.feiyangedu.springcloud.petstore.common.rest.RestExceptionHandler;
import com.feiyangedu.springcloud.petstore.common.util.JsonUtil;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Aspect
@EnableSwagger2
@EnableDiscoveryClient
@Configuration
public class CustomWebConfiguration {

	final Log log = LogFactory.getLog(getClass());

	@Bean
	public Docket createSwaggerDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.paths(PathSelectors.regex("^/api/.*$")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Petstore")
				.contact(new Contact("Liao Xuefeng", "http://www.liaoxuefeng.com", "askxuefeng@gmail.com"))
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.description("Petstore Sample Application on Spring Cloud").build();
	}

	/**
	 * Convert HystrixBadRequestException to APIException using AspectJ.
	 */
	@Around("execution(* com..*FeignClient.*(..))")
	public Object processFeignClientResult(ProceedingJoinPoint pjp) throws Throwable {
		log.info("Invoke feign client...");
		try {
			return pjp.proceed();
		} catch (HystrixBadRequestException e) {
			Throwable t = e.getCause();
			if (t instanceof APIException) {
				log.warn("Convert HystrixBadRequestException to APIException.");
				throw t;
			}
			log.warn("HystrixBadRequestException", e);
			throw e;
		} catch (Exception e) {
			log.warn(e.getClass(), e);
			throw e;
		}
	}

	@Bean
	public RestExceptionHandler restExceptionHandler() {
		return new RestExceptionHandler();
	}

	@Value("${filter.userContext.urlPattern:/api/*}")
	String userContextFilterPattern;

	@Bean
	public FilterRegistrationBean userContextFilterRegistration() {
		UserContextFilter userContextFilter = new UserContextFilter();
		userContextFilter.setObjectMapper(objectMapper);
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(userContextFilter);
		registration.addUrlPatterns(userContextFilterPattern);
		registration.setName("userContextFilter");
		registration.setOrder(1);
		return registration;
	}

	@Bean
	public RequestInterceptor feignRequestInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate templ) {
				UserInfo user = UserContext.getCurrentUserInfo();
				if (user != null) {
					String s = null;
					try {
						s = objectMapper.writeValueAsString(user);
					} catch (JsonProcessingException e) {
						throw new RuntimeException(e);
					}
					templ.header("Authorization",
							"Bearer " + Base64.getUrlEncoder().encodeToString(s.getBytes(StandardCharsets.UTF_8)));
				}
			}
		};
	}

	/**
	 * Customized JSON ObjectMapper.
	 */
	@Bean
	public ObjectMapper objectMapper() {
		return JsonUtil.objectMapper;
	}

	@Autowired
	ObjectMapper objectMapper;

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurerAdapter() {
			/**
			 * Spring默认把静态资源文件/static/abc.js映射到/abc.js，不利于配置反向代理。配置为保留/static/前缀
			 */
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				super.addResourceHandlers(registry);
				registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
			}

			/**
			 * Json默认序列化设置，增加Java8 Time支持
			 */
			@Override
			public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
				final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
				converter.setObjectMapper(objectMapper);
				converters.add(converter);
				super.configureMessageConverters(converters);
			}
		};
	}
}
