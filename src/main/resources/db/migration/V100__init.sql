drop table if exists "USER" CASCADE;
drop table if exists "BANK_ACCOUNT" CASCADE;
drop table if exists "WALLET" CASCADE;
drop table if exists "TRANSACTION" CASCADE;
drop table if exists "PAYMENT_ACCOUNT" CASCADE;

create sequence IF NOT EXISTS hibernate_sequence start with 4 increment by 1;

CREATE TABLE "USER"(
                       "USER_ID" BIGINT AUTO_INCREMENT PRIMARY KEY,
                       "NAME" VARCHAR(255) NOT NULL,
                       "SURNAME" VARCHAR(255) NOT NULL,
                       "NATIONAL_ID" VARCHAR(255) NOT NULL
);

CREATE TABLE "BANK_ACCOUNT" (
                                "BANK_ACCOUNT_ID" BIGINT AUTO_INCREMENT PRIMARY KEY,
                                "ROUTING_NUMBER" VARCHAR(255) NOT NULL,
                                "ACCOUNT_NUMBER" VARCHAR(255) NOT NULL,
                                "BANK_NAME" VARCHAR(255) NOT NULL,
                                "USER_ID" BIGINT NOT NULL,
                                FOREIGN KEY ("USER_ID") REFERENCES "USER"("USER_ID")

);
/*

CREATE TABLE "WALLET" (
    "WALLET_ID" BIGINT AUTO_INCREMENT PRIMARY KEY,
    "BALANCE" DECIMAL(10,2) NOT NULL,
    "USER_ID" BIGINT NOT NULL,
    FOREIGN KEY ("USER_ID") REFERENCES "USER"("USER_ID")
);

CREATE TABLE "TRANSACTION" (
    "TRANSACTION_ID" BIGINT AUTO_INCREMENT PRIMARY KEY,
    "AMOUNT" DECIMAL(10,2) NOT NULL,
    "FEE" DECIMAL(10,2) NOT NULL,
    "BALANCE" DECIMAL(10,2) NOT NULL,
    STATUS VARCHAR(255) NOT NULL,
    CREATE_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    USER_ID BIGINT NOT NULL,
    FOREIGN KEY ("USER_ID") REFERENCES "USER"("USER_ID")
);

*/

//insert into "BANK_ACCOUNT" ("BANK_ACCOUNT_ID", "ROUTING_NUMBER", "ACCOUNT_NUMBER", "BANK_NAME") values (1, '1234', '123400', 'BANK OF AMERICA');
insert into "USER" ("USER_ID", "NAME", "SURNAME", "NATIONAL_ID") values (1000, 'DIANA', 'CRUZ', '05049073-4');
insert into "BANK_ACCOUNT" ("BANK_ACCOUNT_ID", "ROUTING_NUMBER", "ACCOUNT_NUMBER", "BANK_NAME", "USER_ID") values (1, '1234', '123400', 'BANK OF AMERICA', 1000);

//insert into "WALLET" ("WALLET_ID", "BALANCE", "USER_ID") values (1, 30.00, 1);
//insert into "TRANSACTION" ("TRANSACTION_ID", "AMOUNT", "FEE", "STATUS", "CREATE_AT", "USER_ID") values (1, 20.00, 2.0, 'FAILED', CURRENT_TIMESTAMP(), 1);