Social Media Entity Manager

A backend microservice that is designed to handle interactions among entities in a social media application. This project was developed using Spring, MongoDB, and Docker, which aims to provide a robust and secure foundation for managing social media entities.

Features:

Entity Interaction: The microservice is designed to efficiently orchestrate interactions among various entities within the social media application: 
1. Add friend request/accept add request
2. User make a like/comment on a post
3. Users receive notifications for interactions: Add firend request, new post, like and comment.

JWT Authentication with Keycloak: Implemented JWT (JSON Web Token) authentication, with Keycloak serving as the issuer URI. This ensures secure user authentication, adding an extra layer of protection to the application.

Resilience Patterns with Resilience4j: Configured resilience patterns using Resilience4j to enhance fault tolerance, ensuring the microservice's reliability in challenging conditions.

Distributed Tracing with Zipkin: Integrated Zipkin for distributed tracing, providing comprehensive monitoring insights into request flows. This feature aids in understanding the microservice's performance and identifying areas for optimization.

Manual Testing and JUnit 5 Integration: Rigorous testing practices are incorporated, combining manual testing methods with JUnit 5 for automated testing. This approach ensures thorough regression testing and maintains robust code quality through effective test methodologies.
