**Social Media Entity Manager**

A backend microservice that is designed to handle interactions among entities in a social media application, providing a robust and secure foundation for managing social media entities.

Each microservices are designed to interact with each other and act as the backbone of the application:



 acts as an API Gateway using Spring Cloud Gateway, Eureka Service Discovery, and OAuth2 (Keycloak) to route, authenticate, and load-balance requests across microservices



**Tech Stack:**

1. Backend
- Java
- Spring Boot

2. Database
- MongoDB
  
3. Security
- JWT Authentication with Keycloak serving as the issuer URI: Ensures secure user authentication, adding an extra layer of protection to the application.
- Resilience Patterns with Resilience4j: Enhance fault tolerance, ensuring the microservice's reliability in challenging conditions.
- Distributed Tracing with Zipkin: Providing comprehensive monitoring insights into request flows, aiding in understanding the microservice's performance and identifying areas for optimization.

4. Infrastructure
- Docker

5. Testing
- Manual Testing and JUnit 5 Integration: Combining manual testing methods with JUnit 5 for automated testing, ensuring thorough regression testing and maintains robust code quality through effective test methodologies.
