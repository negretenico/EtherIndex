package com.negretenico.ether.index.controller;

import com.common.functionico.evaluation.Result;
import com.negretenico.ether.index.model.Transaction;
import com.negretenico.ether.index.persistence.TransactionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequestMapping("/api/v1/tx")
@RestController
public class TransactionController {
	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	@GetMapping(value="/{hash}",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getTransaction(@PathVariable String hash){
		return Result.of(()->transactionService.get(hash))
				.map(Result::get)
				.map(tx->{
					if(Objects.isNull(tx)){
						return ResponseEntity.notFound().build();
					}
					return ResponseEntity.ok(tx);
				}).get();
	}
	@GetMapping(value="/{address}/transactions",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Transaction>> getByAddress(@PathVariable String address,
																				@RequestParam String from ,
																				@RequestParam String to,
																				@RequestParam(defaultValue = "9") int start,
																				@RequestParam(defaultValue = "50") int end){
		return  Result.of(()->transactionService.findByFromEqualsOrToEqualsOrderByHashAsc(from,to,PageRequest.of(start,end)))
				.map(Result::get)
				.map(ResponseEntity::ok)
				.get();
	}
}
