package com.negretenico.ether.index.blockNumber;

import com.negretenico.ether.index.model.EthereumBlockResponse;
import com.negretenico.ether.index.model.events.BlockFetchedEvent;
import com.negretenico.ether.index.model.events.NewBlockHeadEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class BlockNumberService {
	private final WebClient blockNumberWebClient;
	private final ApplicationEventPublisher eventPublisher;
	public BlockNumberService(WebClient blockNumberWebClient, ApplicationEventPublisher eventPublisher) {
		this.blockNumberWebClient = blockNumberWebClient;
		this.eventPublisher = eventPublisher;
	}
	@EventListener(NewBlockHeadEvent.class)
	@Async
	public void getBlockByNumber(NewBlockHeadEvent event){
		String blockHex = event.getBlockHex();
		String payload = """
        {
          "jsonrpc":"2.0",
          "method":"eth_getBlockByNumber",
          "params":["%s", true],
          "id":1
        }
        """.formatted(blockHex);
		log.info("We are processing block {}",blockHex);
		log.info("We are making the request with payload {}",payload);
		 blockNumberWebClient.post()
				 .bodyValue(payload)
				 .retrieve()
				.bodyToMono(EthereumBlockResponse.class)
				 .doOnSuccess(s->{
					 log.info("Publishing Event {}", s.result().getHash());
					 eventPublisher.publishEvent(BlockFetchedEvent.of(this,s));
				 })
				 .doOnError(e->log.error("We got an error while performing the fetch ",e))
				 .subscribe();
	}
}
