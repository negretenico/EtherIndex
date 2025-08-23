package com.negretenico.ether.index.persistence;

import com.negretenico.ether.index.model.BlockData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Service
@Slf4j
public class BatchPersistenceService {
	private final BlockingQueue<BlockData> queue;
	private final PersistenceService persistenceService;

	public BatchPersistenceService(BlockingQueue<BlockData> queue,
																	 PersistenceService persistenceService) {
		this.queue = queue;
		this.persistenceService = persistenceService;
	}

	@Scheduled(fixedRate = 1000) // every 1s
	public void processBatch() {
		List<BlockData> batch = new ArrayList<>();
		queue.drainTo(batch, 100);

		if (batch.isEmpty()) {
			log.warn("BatchPersistenceService: Queue is empty, doing nothing");
			return;
		};
		log.info("Processing batch of size {}", batch.size());
		batch.forEach(persistenceService::save);
	}
}