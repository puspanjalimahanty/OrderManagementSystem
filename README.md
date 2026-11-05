# Spring Boot Order Management System

## Scenario

An organization requires a centralized system to manage customers, products, and orders. The Order Management System is developed using Spring Boot to provide REST APIs for managing customer and product information and creating orders.

The application follows a layered architecture and uses Spring Data JPA with an H2 database for persistence. It also incorporates validation, exception handling, security, logging, API documentation, and monitoring.

## Technologies Used

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- Spring Security
- Bean Validation
- Swagger / OpenAPI
- Spring Boot Actuator
- Maven
- Postman

## Application Architecture

```text
Client (Postman / Swagger UI)
             |
             v
      Spring Security
             |
             v
      REST Controller
             |
             v
       Service Layer
             |
       +-----+------+
       |            |
       v            v
Customer        Product
Repository      Repository
       |            |
       +-----+------+
             |
             v
       OrderRepository
             |
             v
     Spring Data JPA
             |
             v
       Hibernate
             |
             v
        H2 Database
```

## Project Structure

```text
SpringBootOrderManagement/
├── src/main/java/
│   └── com.example.ordermanagement/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── entity/
│       ├── dto/
│       ├── exception/
│       └── config/
│
├── src/main/resources/
│   ├── application.properties
│   ├── application-dev.properties
│   └── application-test.properties
│
├── src/test/
├── pom.xml
└── README.md
```

## Core Modules

### 1. Customer Management

Provides REST APIs to create, retrieve, update, and delete customer records.

### 2. Product Management

Provides REST APIs to create products and retrieve product information.

### 3. Order Management

Creates orders using existing customer and product information. The application validates the request, calculates the total amount, assigns an order status, and saves the order.

## REST API Endpoints

| Operation | HTTP Method | Endpoint |
|---|---|---|
| Create Customer | POST | `/api/customers` |
| Get All Customers | GET | `/api/customers` |
| Get Customer by ID | GET | `/api/customers/{id}` |
| Update Customer | PUT | `/api/customers/{id}` |
| Delete Customer | DELETE | `/api/customers/{id}` |
| Create Product | POST | `/api/products` |
| Get All Products | GET | `/api/products` |
| Create Order | POST | `/api/orders` |

## Order Processing Flow

1. The client sends an order request containing `customerId`, `productId`, and `quantity`.
2. Spring Security authenticates the request.
3. The controller receives and validates the request DTO.
4. The service checks whether the customer exists.
5. The service checks whether the product exists.
6. The product price is retrieved.
7. The total amount is calculated using product price × quantity.
8. The order date and default order status are assigned.
9. The order is saved using Spring Data JPA.
10. The saved order is converted into a response DTO and returned to the client.

### Sample Order Request

```json
{
  "customerId": 1,
  "productId": 1,
  "quantity": 2
}
```

### Expected Order Response

```json
{
  "id": 1,
  "customerId": 1,
  "productId": 1,
  "quantity": 2,
  "totalAmount": 2000.0,
  "status": "PLACED"
}
```

*Example values; the actual response depends on the saved customer and product details.*

## Key Implementations

### 1. Layered Architecture

Separated application responsibilities into controller, service, repository, entity, and DTO layers.

### 2. DTOs and Validation

Used request and response DTOs to control the API contract and avoid directly exposing database entities.

Implemented validation using:

- `@NotBlank` – Ensures required text fields are not empty.
- `@Email` – Validates email format.
- `@Positive` – Ensures quantity and other applicable numeric values are positive.
- `@Valid` – Triggers validation on incoming requests.

### 3. Spring Data JPA

Used `JpaRepository` to perform database operations without manually writing SQL queries for basic CRUD functionality.

### 4. Business Logic

Implemented order creation logic to validate customer and product existence, calculate the order total, assign the order status, and persist the order.

### 5. Exception Handling

Implemented a custom `ResourceNotFoundException` and global exception handling using `@RestControllerAdvice` to return meaningful HTTP error responses.

### 6. Logging

Implemented application logging using SLF4J to record important business events and errors.

### 7. Configuration Management

Externalized configurable values such as the default order status and server port using `application.properties`.

### 8. Spring Profiles

Configured development and testing environments using:

- `application.properties`
- `application-dev.properties`
- `application-test.properties`

### 9. Spring Security

Implemented Basic Authentication with an in-memory user to protect the application APIs.

### 10. Swagger / OpenAPI

Integrated Swagger UI to automatically document and test REST APIs through a browser interface.

### 11. Spring Boot Actuator

Integrated Actuator endpoints to monitor application health, information, and metrics.

## Database Configuration

The application uses an H2 in-memory database.

```properties
spring.datasource.url=jdbc:h2:mem:ordermanagement-dev
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
```

H2 is lightweight and suitable for local development and testing. Since it is an in-memory database, data may be lost when the application restarts.

## How to Run

1. Clone the repository.

```bash
git clone <your-repository-url>
```

2. Open the project in Eclipse or IntelliJ IDEA.

3. Configure the required application properties.

4. Run the Spring Boot application.

5. The application starts on port `8081`.

6. Test the APIs using Postman or Swagger UI.

### Swagger UI

```text
http://localhost:8081/swagger-ui/index.html
```

### H2 Console

```text
http://localhost:8081/h2-console
```

### Actuator Endpoints

```text
http://localhost:8081/actuator/health
http://localhost:8081/actuator/info
http://localhost:8081/actuator/metrics
```

## Expected Outcomes

- Customers can be created, retrieved, updated, and deleted.
- Products can be created and retrieved.
- Orders are created using existing customers and products.
- Order totals are calculated automatically.
- Invalid requests are rejected through validation.
- Missing resources return meaningful error responses.
- APIs are protected using Basic Authentication.
- Swagger UI provides interactive API documentation.
- Actuator provides application health and metrics.

## Learning Outcomes

- Building REST APIs using Spring Boot.
- Implementing layered architecture.
- Using DTOs and Bean Validation.
- Integrating Spring Data JPA and Hibernate.
- Implementing business logic and exception handling.
- Configuring Spring Security.
- Managing environment-specific configuration using profiles.
- Documenting APIs using Swagger / OpenAPI.
- Monitoring applications using Spring Boot Actuator.

## Author

**Puspanjali Mahanty**

GitHub: [puspanjalimahanty](https://github.com/puspanjalimahanty)
