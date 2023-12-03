
# OnlineStoreAPI

OnlineStoreAPI is a simple API developed using Spring Boot (Java 11) with an H2 in-memory database. The primary goal of this project is to demonstrate how to create a basic online store API and to implement unit tests and integration tests.

## Technology Stack

- Java 11
- Spring Boot
- H2 Database (in-memory)

## Getting Started

Follow these instructions to set up and run the project locally for development and testing purposes.

 ```
- git clone https://github.com/your-username/OnlineStoreAPI.git
- cd OnlineStoreAPI
- mvn clean install
- mvn spring-boot:run
 ```
 The application will be accessible at http://localhost:8081.

### Prerequisites
- Java 11 or higher installed
- Maven installed (if not using an IDE with built-in Maven support)

## API Endpoints
 
 ```
Checkout: POST /checkout
List Categories: GET /categories
Deals of the day: GET /deals_of_the_day/{number_of_products}
List products by category: GET /products
 ```
## Testing

 ```
mvn test
 ```
