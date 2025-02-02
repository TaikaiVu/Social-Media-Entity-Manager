**Social Media Entity Manager**

A backend microservice that is designed to handle interactions among entities in a social media application, providing a robust and secure foundation for managing social media entities.

Each microservices are designed to interact with each other and act as the backbone of the application:

- **ApiService:** Microservice that acts as an API Gateway using Spring Cloud Gateway, Eureka Service Discovery, and OAuth2 (Keycloak) to route, authenticate, and load-balance requests across microservices
- **CommentEntity:** Microservice that acts as a Service Registry using Eureka Server. Its main purpose is to manage and track all microservices in your system, enabling service discovery and dynamic load balancing.
- **DiscoveryService:** Microservice that acts as a central part of an inventory management system, which manages product batches and types, handling everything from record creation to monitoring inventory levels.
- **FriendShipEntity:** Microservice responsible for real-time event-driven notifications using Kafka and Server-Sent Events (SSE), handling the logic of alerting user for login activity, the change in stock, end-of-day report.
- **PostEntity:** Microservice that provides user authentication and authorization functionality using JWT (JSON Web Tokens) and manages user security features. 
- **PostService:** Microservice that is responsible for managing sales transactions, allowing users to record, update, delete, and retrieve sold item records. Additionally, it integrates with Inventory Service, using Kafka messaging to update stock levels and synchronize data.
- **UserEntity:** Microservice that is responsible for managing sales transactions, allowing users to record, update, delete, and retrieve sold item records. Additionally, it integrates with Inventory Service, using Kafka messaging to update stock levels and synchronize data.

  
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
