**Social Media Entity Manager**

A backend microservice that is designed to handle interactions among entities in a social media application, providing a robust and secure foundation for managing social media entities.

Each microservices are designed to interact with each other and act as the backbone of the application:



Features:

Entity Interaction: The microservice is designed to efficiently orchestrate interactions among various entities within the social media application: 
1. Add friend request/accept add request
2. User make a like/comment on a post
3. Users receive notifications for interactions: Add firend request, new post, like and comment.



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
