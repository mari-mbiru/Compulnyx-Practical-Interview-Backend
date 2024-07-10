create table
    accounts (
        account_balance bigint not null,
        account_id uuid not null,
        customer_internal_id_fk uuid unique,
        primary key (account_id)
    );

create table
    customers (
        account_id uuid unique,
        internal_id uuid not null,
        customer_id varchar(255) not null unique,
        customer_pin varchar(255) not null,
        email varchar(255) not null,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        customer_type enum ('ADMIN', 'CUSTOMER') not null,
        primary key (internal_id)
    );

create table
    transactions (
        transaction_amount bigint not null check (transaction_amount >= 0),
        account_id_fk uuid,
        transaction_id uuid not null,
        transfer_id uuid,
        transaction_type enum ('CREDIT', 'DEBIT') not null,
        primary key (transaction_id)
    );

alter table if exists accounts add constraint foreign key (customer_internal_id_fk) references customers;

alter table if exists customers add constraint foreign key (account_id) references accounts;

alter table if exists transactions add constraint foreign key (account_id_fk) references accounts;

alter table if exists transactions add constraint foreign key (transaction_id) references accounts;