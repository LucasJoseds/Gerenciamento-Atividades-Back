package com.lucas.gerenciamentoatividades.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	 //Spring Boot 2.3.9.RELEASE
	@Override
	public void addCorsMappings( CorsRegistry registry ) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		
		List<String> all = Arrays.asList("*");
		
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedMethods(all);
		config.setAllowedOriginPatterns(all);
		config.setAllowedHeaders(all);
		config.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**",  config);
		
		CorsFilter corFilter = new CorsFilter(source);
		
		FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<CorsFilter>(corFilter);
		filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
		
		return filter;
		
	}
	}
