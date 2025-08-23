package com.negretenico.ether.index.persistence;

import com.common.functionico.evaluation.Result;
import com.negretenico.ether.index.model.Transaction;
import com.negretenico.ether.index.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	public Result<Transaction> save(Transaction transaction){
		return  Result.of(()->transactionRepository.save(transaction))
				.onSuccess(t->log.info("TransactionService: Successfully " +
						"save {}",t.getBlockHash()))
				.onFailure(e -> log.error("TransactionService: Failed to save",e));
	}
}
