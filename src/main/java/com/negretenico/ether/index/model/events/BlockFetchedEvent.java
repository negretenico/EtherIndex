package com.negretenico.ether.index.model.events;

import com.negretenico.ether.index.model.EthereumBlockResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class BlockFetchedEvent extends ApplicationEvent {
	private  final  EthereumBlockResponse ethereumBlockResponse;
	private BlockFetchedEvent(Object source, EthereumBlockResponse ethereumBlockResponse) {
		super(source);
		this.ethereumBlockResponse = ethereumBlockResponse;
	}
	public static BlockFetchedEvent of(Object source,
																		 EthereumBlockResponse blockResponse){
		return new BlockFetchedEvent(source,blockResponse);
	}
}
