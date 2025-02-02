**Social Media Entity Manager**

A backend microservice that is designed to handle interactions among entities in a social media application, providing a robust and secure foundation for managing social media entities.

Each microservices are designed to interact with each other and act as the backbone of the application:

- **ApiService:** Microservice that acts as an API Gateway using Spring Cloud Gateway, Eureka Service Discovery, and OAuth2 (Keycloak) to route, authenticate, and load-balance requests across microservices
- **CommentEntity:** Microservice that manages user comments on posts using JPA, Eureka Service Discovery, WebClient for user data fetching, ensuring reliable comment creation, retrieval, updating, and deletion.
- **DiscoveryService:** Microservice that provides service discovery for all microservices, using Spring Security for basic authentication, enabling seamless service registration and discovery within the architecture.
- **FriendShipEntity:** Microservice that manages user friendships using Spring Actuator for monitoring, providing APIs to add, remove, and retrieve friends asynchronously.
- **PostService:** Microservice that is responsible for managing social media posts, including creation, retrieval, updating, and deletion, using WebFlux for async user fetching via WebClient
- **UserEntity:** Microservice that handles user management functions, including registration, authentication, profile retrieval, updates, and deletion. It also integrates with a friendship service to fetch a user's friends list

  
**Tech Stack:**

1. Backend
- Java
- Spring Boot

2. Database
- PostgreSQL
  
3. Security
- Spring Security
- JWT Authentication with Keycloak serving as the issuer URI: Ensures secure user authentication, adding an extra layer of protection to the application.

4. Infrastructure
- Docker

5. Testing
- Manual Testing and JUnit 5 Integration: Combining manual testing methods with JUnit 5 for automated testing, ensuring thorough regression testing and maintains robust code quality through effective test methodologies.
