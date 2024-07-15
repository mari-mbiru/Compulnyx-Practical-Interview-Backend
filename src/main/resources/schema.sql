create table accounts
(
    account_balance  bigint not null,
    customer_uuid_fk uuid unique,
    uuid             uuid   not null,
    primary key (uuid)
);
create table customers
(
    uuid       uuid         not null,
    email      varchar(255) not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    user_id    varchar(255) not null unique,
    user_pin   varchar(255) not null,
    primary key (uuid)
);
create table tokens
(
    expired                 boolean not null,
    revoked                 boolean not null,
    customer_internal_id_fk uuid,
    uuid                    uuid    not null,
    token                   varchar(255) unique,
    token_type              enum ('BEARER'),
    primary key (uuid)
);
create table transactions
(
    date_created           timestamp(6) not null,
    transaction_amount     bigint       not null check (transaction_amount >= 0),
    account_id_fk          uuid,
    related_transaction_id uuid,
    transfer_id            uuid,
    uuid                   uuid         not null,
    transaction_type       enum ('CREDIT','DEBIT') not null,
    primary key (uuid)
);
alter table if exists accounts add constraint FK4kyfev3oiaaqid8q53go67m47 foreign key (customer_uuid_fk) references customers;
alter table if exists tokens add constraint FKg2k4cnglno3jfmu7tmajsofjj foreign key (customer_internal_id_fk) references customers;
alter table if exists transactions add constraint FK2u5qob0pq8gruyu9d74ti0m1d foreign key (account_id_fk) references accounts;
alter table if exists transactions add constraint FKgq8fytmk4xbr4pohsnu63lydq foreign key (related_transaction_id) references transactions;
