package com.negretenico.ether.index.persistence;

import com.negretenico.ether.index.model.BlockData;
import com.negretenico.ether.index.model.events.BlockFetchedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

@Service
@Slf4j
public class PersistenceListener {
	private final BlockingQueue<BlockData> blockQueue;
	public PersistenceListener( BlockingQueue<BlockData> blockQueue) {
		this.blockQueue = blockQueue;
	}
	@EventListener(BlockFetchedEvent.class)
	public void subscribe(BlockFetchedEvent blockFetchedEvent){
		log.info("PersistenceListener: Initiating save");
		if(Objects.isNull(blockFetchedEvent) || Objects.isNull(blockFetchedEvent.getEthereumBlockResponse())){
			log.warn("PersistenceListener: Invalid data Require no null values");
			return;
		}
		if(Objects.isNull(blockFetchedEvent.getEthereumBlockResponse().result())){
			log.warn("PersistenceListener: Invalid data, require no null values -> blockFetchedEvent.getEthereumBlockResponse().result()");
			return;
		}
		BlockData bd = blockFetchedEvent.getEthereumBlockResponse().result();
		boolean offered = blockQueue.offer(bd);
		if(!offered){
			log.warn("PersistenceListener: Queue is full dropping {}",bd.getHash());
			return;
		}
		log.info("PersistenceListener: Finished");
	}
}
