package com.negretenico.ether.index.model;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Validated
@ConfigurationProperties(prefix = "alchemy")
public record AlchemyProperties(@NotBlank(message = "Alchemy API key is " +
		"required")String apiKey,	@NotBlank(message = "Alchemy base URL is " +
		"required") String host) {
	public String getWebSocketUrl() {
		if (Objects.isNull(apiKey)|| apiKey.trim().isEmpty()) {
			throw new IllegalStateException("Alchemy API key is not configured");
		}
		return 		UriComponentsBuilder.newInstance()
				.scheme("wss")
				.host(host)
				.pathSegment("v2",apiKey)
				.build()
				.toUriString();
	}
	public String getHTTPUrl(){
		if (Objects.isNull(apiKey)|| apiKey.trim().isEmpty()) {
			throw new IllegalStateException("Alchemy API key is not configured");
		}
		return 		UriComponentsBuilder.newInstance()
				.scheme("https")
				.host(host)
				.pathSegment("v2",apiKey)
				.build()
				.toUriString();
	}
	@PostConstruct
	void init(){
		System.out.printf("These are the values, %s %s ",host,apiKey);
	}
}