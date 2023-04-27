# Wallet Application

## Content

* [Technology stack](#markdown-header-technology-stack)
* [Application Structure](#markdown-header-application-structure)
* [Configuration](#markdown-header-configuration)
* [How to test the software](#markdown-header-how-to-test-the-software)

## Technology stack

Wallet App is developed on Java 11 and Springboot 2

* Maven
* Java 11
* Spring boot 2.6.3
* Postgres


## Application Structure

The structure looks as follows :

```
wallet-app
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── wallet
│   │   │   │   ├── configuration
│   │   │   │   │   ├── Config.java
│   │   │   │   ├── controller
│   │   │   │   │   ├── BankAccountController.java
│   │   │   │   │   ├── PaymentController.java
│   │   │   │   │   ├── TransactionController.java
│   │   │   │   │   ├── WalletController.java
│   │   │   │   ├── dto
│   │   │   │   │   ├── request
│   │   │   │   │   │   ├── AccountDTO.java
│   │   │   │   │   │   ├── BankAccountRequestDTO.java
│   │   │   │   │   │   ├── BankInformationRequestDTO.java
│   │   │   │   │   │   ├── DestinationDTO.java
│   │   │   │   │   │   ├── PaymentOnTopRequestDto.java
│   │   │   │   │   │   ├── PaymentRequestDto.java
│   │   │   │   │   │   ├── SourceDTO.java
│   │   │   │   │   │   ├── SourceInformationDTO.java
│   │   │   │   │   │   ├── WalletInformationRequestDTO.java
│   │   │   │   │   │   ├── WalletTransactionRequestDto.java
│   │   │   │   │   ├── response
│   │   │   │   │   │   ├── PaymentInfoDTO.java
│   │   │   │   │   │   ├── PaymentResponseDto.java
│   │   │   │   │   │   ├── RequestInfoDTO.java
│   │   │   │   │   │   ├── TransactionDTO.java
│   │   │   │   │   │   ├── WalletTransactionResponseDto.java
│   │   │   │   │   │   ├── WalletWithdrawalResponseDto.java
│   │   │   │   ├── entity
│   │   │   │   │   ├── enums
│   │   │   │   │   │   ├── TransactionStatusType.java
│   │   │   │   │   ├── Audit.java
│   │   │   │   │   ├── Auditable.java
│   │   │   │   │   ├── AuditListener.java
│   │   │   │   │   ├── BankAccount.java
│   │   │   │   │   ├── Transaction.java
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Wallet.java
│   │   │   │   ├── repository
│   │   │   │   │   ├── Auditable.java
│   │   │   │   │   ├── BankAccountRepository.java
│   │   │   │   │   ├── TransactionRepository.java
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   ├── WalletRepository.java
│   │   │   │   ├── service
│   │   │   │   │   ├── BankAccountService.java
│   │   │   │   │   ├── PaymentBusinessDelegate.java
│   │   │   │   │   ├── PaymentService.java
│   │   │   │   │   ├── TransactionService.java
│   │   │   │   │   ├── WalletBusinessDelegate.java
│   │   │   │   │   ├── WalletWithdrawalService.java
│   │   │   │   │   ├── WalletService.java
│   │   │   │   ├── exception

```

### Configuration Project

### Local Configuration

To Compile the Wallet App:
```
mvn clean package
```

To run the application locally you need:

1 - Configure local Variables

Environment variable | Value
 --- | ---
`CONFIGURATION_URL` | http://localhost:30024;
`WEB_PORT` | 8081;
`EXTERNAL_API_URL` | http://mockoon.tools.getontop.com:3000;
`POSTGRES_DB_PASSWORD` | postgres;
`POSTGRES_DB_USERNAME` | postgres;
`POSTGRES_DB_URL`| jdbc:postgresql://192.168.1.26:5432/postgres;

2- Do an insert in the postgres db for create a new user

INSERT INTO public.userw (user_id, name, national_id, surname) VALUES (1000, 'DIANA', '05049073-4', 'CRUZ');

3- Run the application and the tables are created automatically.

### Run application with Docker

1-Compile the project and generate the application jar in target folder.
$ mvn clean install package

2 - Execute the following command to build docker image.
(the IP configured has to be your local ip)

docker build -t wallet --build-arg POSTGRES_DB_URL=jdbc:postgresql://192.168.1.26:5432/postgres --build-arg POSTGRES_DB_USERNAME=postgres --build-arg POSTGRES_DB_PASSWORD=postgres --build-arg WEB_PORT=8082 --build-arg EXTERNAL_API_URL=http://mockoon.tools.getontop.com:3000 .

3- Run docker image with the port configured in the step 2.
docker run -p 8887:8082 wallet:latest


## How to test the software

1- Open postman export the collection and create a bank account

POST - http://localhost:8081/accounts
```
{
"userId":"1000",
"firstName":"DIANA RAQUEL",
"lastName":"CRUZ UMANZOR",
"nationalId":"05049073-4",
"accountNumber":"0245253419",
"routingNumber":"98876",
"bankName":"BAC"
}
```

2- Transfer money to your wallet

POST - http://localhost:8081/wallets/credit-wallet/

```
{
  "amount": 10,
  "accountNumber": "0245253419",
  "userId": 1000
}
```

3- When the wallet has credit you can start doing payments according to the balance you have

POST - http://localhost:8081/payments
```
{
    "source": {
        "type": "COMPANY",
        "sourceInformation": {
            "name": "ONTOP INC"
        },
        "account": {
            "accountNumber": "0245253419",
            "currency": "USD",
            "routingNumber": "028444018"
        }
    },
    "destination": {
        "name": "TONY STARK",
        "account": {
            "accountNumber": "1885226711",
            "currency": "USD",
            "routingNumber": "211927207"
        }
    },
    "amount": 1.00,
    "userId": 1000
}
```

4- You can see all transactions made by a user sorted in descending order by creation date.

GET - http://localhost:8081/transactions?userId=1000&page=0&size=4