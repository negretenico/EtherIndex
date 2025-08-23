package com.negretenico.ether.index.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EthereumBlockResponse(int id, String jsonrpc, BlockData result) {
}
