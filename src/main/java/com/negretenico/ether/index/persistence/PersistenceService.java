package com.negretenico.ether.index.persistence;

import com.common.functionico.evaluation.Result;
import com.negretenico.ether.index.model.BlockData;
import com.negretenico.ether.index.model.Transaction;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersistenceService {
	private final TransactionService transactionService;
	private final BlockDataService blockDataService;

	public PersistenceService(TransactionService transactionService, BlockDataService blockDataService) {
		this.transactionService = transactionService;
		this.blockDataService = blockDataService;
	}

	@Transactional
	public void save(BlockData blockData) {
		blockDataService.save(blockData)
				.onSuccess(bData -> {
					log.info("PersistenceService: Successfully saved block {}", bData.getHash());
					bData.getTransactions().forEach(transaction->{
						transaction.setBlockHash(bData.getHash());
						transactionService.save(transaction)
								.onSuccess(tx -> log.info("PersistenceService: Successfully save " +
										"transaction {}", tx.getTransactionIndex()))
								.onFailure(e ->{
									log.error("PersistenceService: Could not save " +
											"transaction", e);
									throw new PersistenceFailureException(e.getMessage());
								});
					});
				}).onFailure(e -> {
					log.error("PersistenceService: Could not save " +
							"block", e);
					throw new PersistenceFailureException(e.getMessage());
				});
	}
}
