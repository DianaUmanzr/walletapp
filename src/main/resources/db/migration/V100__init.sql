
drop table if exists USER CASCADE;
drop table if exists BANK_ACCOUNT CASCADE;
drop table if exists WALLET CASCADE;
drop table if exists TRANSACTION CASCADE;
drop table if exists PAYMENT_ACCOUNT CASCADE;

create sequence IF NOT EXISTS hibernate_sequence start with 4 increment by 1;

CREATE TABLE IF NOT EXISTS USER(
                                   USER_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   NAME VARCHAR(255) NOT NULL,
                                   SURNAME VARCHAR(255) NOT NULL,
                                   NATIONAL_ID VARCHAR(255) NOT NULL
);

insert into USER (USER_ID, NAME, SURNAME, NATIONAL_ID) values (1000, 'DIANA', 'CRUZ', '05049073-4');