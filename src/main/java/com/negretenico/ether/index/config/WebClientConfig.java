package com.negretenico.ether.index.config;

import com.negretenico.ether.index.model.AlchemyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	@Bean
	public WebClient blockNumberWebClient(AlchemyProperties alchemyProperties){
		return  WebClient.builder()
				.baseUrl(alchemyProperties.getHTTPUrl())
				.build();
	}
}
