package com.negretenico.ether.index.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transactions")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

	@Id
	private String hash;

	@Column(name = "blockHash")
	private String blockHash;

	@Column(name = "blockNumber")
	private String blockNumber;

	@Column(name = "fromAddress")
	@JsonProperty("from")
	private String from;

	@Column(name = "gas")
	private String gas;

	@Column(name = "gasPrice")
	private String gasPrice;

	@Column(name = "input", columnDefinition = "TEXT")
	private String input;

	@Column(name = "nonce")
	private String nonce;

	@Column(name = "toAddress")
	private String to;

	@Column(name = "transactionIndex")
	private String transactionIndex;

	@Column(name = "transactionValue")
	@JsonProperty("value")
	private String value;

	@Column(name = "transactionType")
	@JsonProperty("type")
	private String type;

	@Column(name = "v")
	private String v;

	@Column(name = "r")
	private String r;

	@Column(name = "s")
	private String s;

	@Column(name = "sourceHash")
	private String sourceHash;

	@Column(name = "mint")
	private String mint;

	@Column(name = "depositReceiptVersion")
	private String depositReceiptVersion;

	@CreationTimestamp
	@Column(name = "createdAt")
	private LocalDateTime createdAt;

}