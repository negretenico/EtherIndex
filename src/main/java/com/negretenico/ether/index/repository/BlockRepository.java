package com.negretenico.ether.index.repository;

import com.negretenico.ether.index.model.BlockData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<BlockData,String > {
	Optional<BlockData> findByNumber(String number);

	Optional<BlockData> findTopByOrderByNumberDesc();

	List<BlockData> findByMiner(String miner);

	long countByMiner(String miner);
}
