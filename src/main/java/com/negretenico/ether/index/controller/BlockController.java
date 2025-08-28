package com.negretenico.ether.index.controller;

import com.common.functionico.evaluation.Result;
import com.negretenico.ether.index.model.BlockData;
import com.negretenico.ether.index.persistence.BlockDataService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequestMapping("/api/v1/blocks")
@RestController
public class BlockController {
	private final BlockDataService blockDataService;

	public BlockController(BlockDataService blockDataService) {
		this.blockDataService = blockDataService;
	}
	@GetMapping(value ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> get(@PathVariable String id){
		return Result.of(()->blockDataService.get(id))
				.map(Result::get)
				.map(block->{
					if(Objects.isNull(block)){
						return  ResponseEntity.notFound().build();
					}
					return ResponseEntity.ok(block);
				})
				.get();
	}
}
