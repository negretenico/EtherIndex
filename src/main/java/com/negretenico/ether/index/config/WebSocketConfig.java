package com.negretenico.ether.index.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.negretenico.ether.index.etherum.EthereumWssHandler;
import com.negretenico.ether.index.model.AlchemyProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
@EnableWebSocket
public class WebSocketConfig {

	@Bean
	public WebSocketConnectionManager webSocketConnectionManager(AlchemyProperties alchemyProperties,ApplicationEventPublisher eventPublisher) {
		WebSocketConnectionManager manager = new WebSocketConnectionManager(
				client(),
				ethereumWssHandler(eventPublisher),
				alchemyProperties.getWebSocketUrl()
		);
		manager.setAutoStartup(true);
		return manager;
	}

	@Bean
	public WebSocketClient client() {
		return new StandardWebSocketClient();
	}

	@Bean
	public EthereumWssHandler ethereumWssHandler(ApplicationEventPublisher eventPublisher) {
		return new EthereumWssHandler(new ObjectMapper(),eventPublisher);
	}
}