package com.negretenico.ether.index.etherum;

import com.common.functionico.either.Either;
import com.common.functionico.evaluation.Result;
import com.common.functionico.risky.Try;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.negretenico.ether.index.model.events.NewBlockHeadEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.function.ThrowingSupplier;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;

@Slf4j
public class EthereumWssHandler extends TextWebSocketHandler {
	ObjectMapper objectMapper;
	WebSocketSession session;
	ApplicationEventPublisher applicationEventPublisher;
	public EthereumWssHandler(ObjectMapper objectMapper,
														ApplicationEventPublisher applicationEventPublisher){
		 this.objectMapper= objectMapper;
	this.applicationEventPublisher=applicationEventPublisher;
	}
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		this.session = session;
		String subscribeMessage = """
					{
							"id": 1,
							"method": "eth_subscribe",
							"params": ["newHeads"]
					}
				""";
		log.info("Connected to Alchemy WebSocket");
		ThrowingSupplier<Either<Void,Throwable>> supplier = ()->{
			session.sendMessage(new TextMessage(subscribeMessage));
			return Either.left(null);
		};
		 Try.of(supplier)
				.onFailure((e) -> log.error("Error sending subscription message", e));
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		this.session = session;
		log.info("Received message: {}", message.getPayload());
		Result.of(()->objectMapper.readTree(message.getPayload()))
				.onSuccess(node->{
					if(node.has("result")){
						log.info("Subscription confirmed: {}", node.get("result").asText());
						return;
					}
					if(!node.has("method")){
						log.info("Node does not have key `method`");
						return;
					}
					if(!"eth_subscription".equals(node.get("method").asText())){
						log.info("Node has key `method` but it does not equal " +
								"`eth_subscription` it equals {}",node.get("method").asText());
						return;
					}
					JsonNode params = node.get("params");
					if(Objects.isNull(params) || !params.has("result")){
						log.info("Node is missing key `params` or `result`");
						return;
					}
					handleBlockHeader(params.get("result"));
				})
				.onFailure(e->log.error("Error parsing message", e));
	}

	private void handleBlockHeader(JsonNode blockHeader) {
		log.info("New block: {}", blockHeader.get("number").asText());
		// Process the new block header as needed
		applicationEventPublisher.publishEvent(NewBlockHeadEvent.of(this,
				blockHeader.get("number").asText()));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		log.error("WebSocket transport error", exception);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		log.info("WebSocket connection closed: {}", status);
	}
	// Method to send custom messages
	public void sendMessage(String message) {
		if(Objects.isNull(session) || !session.isOpen()){
			log.info("Session is not ready to send messages");
			return;
		}
		ThrowingSupplier<Void> supplier= ()->{
			session.sendMessage(new TextMessage(message));
			return null;
		};
		Try.of(supplier).onFailure(e -> log.error("Error sending message",e));
	}
}