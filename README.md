# Take home assessment with BRC

Using Java 21.

## Quickstart for Linux or Mac
For first start, run this command: ./mvnw clean 
Start the HTTP server with the command: ./mvnw spring-boot:run

## Quickstart for Windows
For first start, run this command: mvnw.cmd clean
Start the HTTP server with the command: mvnw.cmd spring-boot:run

## Architecture
Developed a REST web-service for a simple banking application in Spring.

Implemented a three-layer design with a controller to handle HTTP requests, a service layer to perform business logic, and a repository layer to store data.

Outlined interfaces for the services and repository. Provided implementation for thread-safe services and in-memory data storage.

Controller expects request data transfer objects and returns response data transfer objects.

Makes use of domain events for logging key actions that change the state of accounts, which can be extended to add additional behaviour such as sending notifications.

## Testing
Can test account creation with this request:

curl -X POST http://localhost:8080/accounts/create \
     -H "Content-Type: application/json" \
     -d '{"initialBalance": 1000.00}' \
     -w "\n"


curl -X POST http://localhost:8080/accounts/create \
     -H "Content-Type: application/json" \
     -d '{"initialBalance": 1000.00}' \
     -w "\n"

Can test transferring between the two created accounts above with this:

curl -X POST http://localhost:8080/accounts/transfer \
     -H "Content-Type: application/json" \
     -d '{"senderId": 0, "recipientId": 1, "value": 200.00}' \
     -w "\n"

Can test getting account history for an account with this:

curl -X POST http://localhost:8080/accounts/transactions \
     -H "Content-Type: application/json" \
     -d '{"id": 0}' \
     -w "\n"

To run unit tests, run: ./mvnw test