# Ledger

Stack: Java 11, Maven, Spring boot 2.7.18, H2, Junit5, Mockito

Steps of setup 

1- git clone https://github.com/LeandroMartinez044/ledger.git

2- mvn install

3- mvn spring-boot:run

Note: 

it's not neccesary to connect to a database. It's all configurated to use H2. I've decided to use H2 for practical purposes.

There are 3 wallets loaded in com.martinez.ledger.config.LedgerConfig. The total supply is the 100.00

### Transfer Curl

curl to transfer between 2 wallets

```bash
curl --location 'http://localhost:8080/api/v1/ledger/transfer' \
--header 'Content-Type: application/json' \
--data '{
    "walletNumberSource": "walletNumberA",
    "walletNumberTarget": "walletNumberB",
    "amount": 30.00
}'
```
Additional endpoint to see the current wallet balances: http://localhost:8080/api/v1/wallet


### Proof of dev using JMeter for load testing(Throughput: 1003.5/sec):


[Screencast from 2025-12-14 15-47-10.webm](https://github.com/user-attachments/assets/c33f9821-fac8-4149-83b9-d600fc92b71e)


### Events:

When the transactions are completed, the service publishes the events. There are 3 services listening the event, FraudDetectionService, NotificationService, RewardsService. When these services receive the event only log a confirmation message to the console.


### pseudo-code:

```
// Function Transfer(move money)
// 1- @Transactional to rollback in case a database problem and to make sure integrity 

// 2- Check business validations before going into to the critical zone

// 3-order by id to prevent Deadlock.

Wallet Lock sources -> min between id Wallet 1 and id Wallet 2
Wallet Lock target -> max between id Wallet 1 and id Wallet 2
We are avoiding the mutual block, it means that both resources waits the same resource.

// 4- Synchronized: the thread takes over of resources of the wallet source and wallet target until the code in Synchronized finishes.

// 5- Transfer between wallets.

// 6- create a new transaction to insert the record. it uses UUID for Ids to identify the transaction. 

// 7- Persist new transaction and in case that the persistence fails, it rollbacks the math operations and it will go back the last balances.

// 8- creates and publishes the event.

```
