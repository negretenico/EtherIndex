create table blocks (
  number        bigint primary key,
  hash          bytea unique not null,
  parent_hash   bytea not null,
  timestamp     timestamptz not null,
  canonical     boolean not null default true
);

create table transactions (
  hash          bytea primary key,
  block_number  bigint references blocks(number),
  from_address  bytea not null,
  to_address    bytea,
  value_wei     numeric(78,0) not null,
  nonce         bigint not null,
  gas_limit     bigint not null,
  gas_price_wei numeric(78,0),
  input         bytea,
  status        smallint,         -- from receipt
  idx_in_block  int not null,
  unique (block_number, idx_in_block)
);

create table logs (
  id            bigserial primary key,
  tx_hash       bytea not null references transactions(hash),
  block_number  bigint not null,
  log_index     int not null,
  address       bytea not null,
  topics        bytea[] not null,
  data          bytea,
  unique (tx_hash, log_index)
);

-- ERC-20 Transfer(address indexed from, address indexed to, uint256 value)
create table erc20_transfers (
  id            bigserial primary key,
  contract      bytea not null,
  tx_hash       bytea not null references transactions(hash),
  block_number  bigint not null,
  log_index     int not null,
  from_address  bytea not null,
  to_address    bytea not null,
  value_wei     numeric(78,0) not null,
  unique (tx_hash, log_index)
);

create table cursors (
  name          text primary key,
  last_block    bigint not null
);
