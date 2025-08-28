package com.negretenico.ether.index.repository;

import com.negretenico.ether.index.model.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,String> {
	List<Transaction> findByFromOrTo(String from, String to);

	List<Transaction> findByBlockHash(String blockHash);

	List<Transaction> findByBlockNumber(String blockNumber);

	List<Transaction> findTop100ByFromOrToOrderByBlockNumberDesc(String from, String to);
	List<Transaction> findByFromEqualsOrToEqualsOrderByHashAsc(String fromValue, String toValue, Pageable page);
	long countByFromOrTo(String from, String to);

}
