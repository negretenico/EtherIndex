package com.negretenico.ether.index.config;

import com.negretenico.ether.index.model.BlockData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

@Configuration
public class QueueConfig {
	@Bean
	public BlockingQueue<BlockData> blockQueue(){
		return new LinkedBlockingQueue<>(10_000);
	}
}
