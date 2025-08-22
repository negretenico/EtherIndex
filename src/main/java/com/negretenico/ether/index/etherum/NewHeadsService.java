package com.negretenico.ether.index.etherum;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;

@Service
public class NewHeadsService {
	private final EthereumWssHandler ethereumWssHandler;

	public NewHeadsService(EthereumWssHandler ethereumWssHandler) {
		this.ethereumWssHandler = ethereumWssHandler;
	}
	@EventListener(ApplicationReadyEvent.class)
	public void subscribe(){
		String message = """
				{
				  "id": 1,
				  "jsonrpc": "2.0",
				  "method": "eth_subscribe",
				  "params": ["newHeads"]
				}
				""";
		ethereumWssHandler.sendMessage(message);
	}
}
