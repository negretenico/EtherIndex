package com.negretenico.ether.index.persistence;

import com.common.functionico.evaluation.Result;
import com.negretenico.ether.index.model.BlockData;
import com.negretenico.ether.index.repository.BlockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BlockDataService {
	private final BlockRepository blockRepository;

	public BlockDataService(BlockRepository blockRepository) {
		this.blockRepository = blockRepository;
	}

	public Result<BlockData> save(BlockData blockData) {
		return Result.of(() -> blockRepository.save(blockData))
				.onSuccess(block ->
						log.info("BlockDataService: Successfully save {}", block.getHash()))
				.onFailure(e->log.error("BlockDataService: Failed to save",e));
	}
	public Result<BlockData> get(String id){
		return Result.of(()-> blockRepository.findById(id))
				.map(Optional::get);
	}
}
