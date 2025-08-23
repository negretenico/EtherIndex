package com.negretenico.ether.index.persistence;

import com.negretenico.ether.index.model.BlockData;
import com.negretenico.ether.index.model.events.BlockFetchedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class PersistenceListener {
	private final PersistenceService persistenceService;

	public PersistenceListener(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
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
		persistenceService.save(bd);
		log.info("PersistenceListener: Finished save");
	}
}
