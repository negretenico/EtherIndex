package com.negretenico.ether.index.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.negretenico.ether.index.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "blocks")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockData {

	@Id
	private String hash;

	@Column(name = "baseFeePerGas")
	private String baseFeePerGas;

	@Column(name = "blobGasUsed")
	private String blobGasUsed;

	@Column(name = "difficulty")
	private String difficulty;

	@Column(name = "excessBlobGas")
	private String excessBlobGas;

	@Column(name = "extraData", length = 1000)
	private String extraData;

	@Column(name = "gasLimit")
	private String gasLimit;

	@Column(name = "gasUsed")
	private String gasUsed;

	@Column(name = "logsBloom", length = 1000)
	private String logsBloom;

	@Column(name = "miner")
	private String miner;

	@Column(name = "mixHash")
	private String mixHash;

	@Column(name = "nonce")
	private String nonce;

	@Column(name = "number")
	private String number;

	@Column(name = "parentBeaconBlockRoot")
	private String parentBeaconBlockRoot;

	@Column(name = "parentHash")
	private String parentHash;

	@Column(name = "receiptsRoot")
	@JsonProperty("receiptsRoot")
	private String receiptsRoot;

	@Column(name = "sha3Uncles")
	@JsonProperty("sha3Uncles")
	private String sha3Uncles;

	@Column(name = "size")
	private String size;

	@Column(name = "stateRoot")
	private String stateRoot;

	@Column(name = "timestamp")
	private String timestamp;

	@Column(name = "transactionsRoot")
	private String transactionsRoot;

	@Column(name = "withdrawalsRoot")
	private String withdrawalsRoot;

	@OneToMany(mappedBy = "blockHash", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonProperty("transactions")
	private List<Transaction> transactions = new ArrayList<>();

	// Store uncles and withdrawals as JSON strings for simplicity
	@Column(name = "uncles", columnDefinition = "TEXT")
	@JsonProperty("uncles")
	@Convert(converter = StringListConverter.class)
	private List<String> uncles = new ArrayList<>();

	@Column(name = "withdrawals", columnDefinition = "TEXT")
	@JsonProperty("withdrawals")
	@Convert(converter = StringListConverter.class)
	private List<String> withdrawals = new ArrayList<>();

	@CreationTimestamp
	@Column(name = "createdAt")
	private LocalDateTime createdAt;

}