package com.negretenico.ether.index.persistence;

import com.common.functionico.evaluation.Result;
import com.negretenico.ether.index.model.Transaction;
import com.negretenico.ether.index.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

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
	public Result<Transaction> get(String hash){
		return  Result.of(()->transactionRepository.findById(hash))
				.map(Optional::get);
	}

	public Result<List<Transaction>> findByFromEqualsOrToEqualsOrderByHashAsc(String from, String to, Pageable page){
		return Result.of(()->transactionRepository.findByFromEqualsOrToEqualsOrderByHashAsc(from,to,page));
	}
}
