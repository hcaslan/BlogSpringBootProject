# Blog Spring Boot Project

This is a sample blog project developed using Java and the Spring Boot framework. The project provides RESTful APIs for managing blog categories, posts, comments, and users.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-Endpoints)
- [Validation and Error Checking](#validation-and-error-checking)

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- Jakarta Bean Validation

## Project Structure

The Blog Spring Boot Project follows a standard project structure to organize its source code and resources. Here's an overview of the project structure:

- `src/main/java/org.hca.constant`: Contains constant values used throughout the application.
- `src/main/java/org.hca.controller`: Contains the application's controllers.
- `src/main/java/org.hca.dto`: Contains Data Transfer Objects (DTOs) to encapsulate data and transport it between the controller and service layers.
   - `.request`: Contains request DTOs represent data sent by the client in requests.
   - `.response`: Contains response DTOs represent data returned to the client in responses.
- `src/main/java/org.hca.entity`: Contains the entity classes representing the application's data model.
- `src/main/java/org.hca.exception`: Contains custom exceptions and exception handling logic.
- `src/main/java/org.hca.mapper`:  Contains mapper classes responsible for mapping between different object representations within the application.
   - `.customMapper`: Contains custom mapping logic for specific scenarios.
- `src/main/java/org.hca.repository`: Contains the interfaces for data access and repositories.
- `src/main/java/org.hca.rules`: Contain classes related to business rules and validation rules specific to the application domain.
- `src/main/java/org.hca.service`: Contains the services that implement the business logic.
- `src/main/java/org.hca.utility`: Within the sub-packages, contains custom annotation classes and its validators that are used throughout the application.
- `src/main/resources`: Contains configuration files and static resources used by the application.

## Getting Started

Follow the steps below to set up and run the project on your local machine:

1. Configure the database connection:

   - Open the `application.yml` file located in the `src/main/resources` directory.
   - Update the database connection details such as URL, username, and password according to your PostgreSQL configuration.
   
   ```yaml
   server:
     port: 9090
   spring:
     datasource:
       driver-class-name: org.postgresql.Driver
       url: jdbc:postgresql://localhost:5432/blogdb
       username: postgres
       password: 1234
     jpa:
       hibernate:
         ddl-auto: update
   ```
   - The application will start running on `http://localhost:9090`.

2. Access the API documentation:

   Once the application is running, you can access the API documentation by visiting `http://localhost:9090/swagger-ui.html` in your web browser. The API documentation provides detailed information about the available endpoints, request and response formats, and allows you to interact with the APIs.

## API Endpoints

The following API endpoints are available:

- #### Post associated endpoints:

| Endpoint Tag                     | Call   | Endpoint                                  | Description                                                        |
|----------------------------------|--------|-------------------------------------------|--------------------------------------------------------------------|
| CREATE                           | POST   | `/api/v7/posts`                           | Create a new post.                                                 |
| GET_ALL                          | GET    | `/api/v7/posts`                           | Get a list of all posts.                                           |
| FIND_BY_ID                       | GET    | `/api/v7/posts/{id}`                      | Get detailed information about a specific post.                    |
| UPDATE                           | PUT    | `/api/v7/posts/{id}`                      | Update a post.                                                     |
| DELETE                           | DELETE | `/api/v7/posts/{id}`                      | Delete a post.                                                     |
| LIKE                             | POST   | `/api/v7/posts/like`                      | Like a post.                                                       |
| UNLIKE                           | POST   | `/api/v7/posts/unlike`                    | Unlike a liked post.                                               |
| GET_POSTS_BY_USER_ID             | GET    | `/api/v7/posts/user/{userId}`             | Get a list of all posts published by a specific user.              |
| GET_POSTS_BY_CATEGORY_ID         | GET    | `/api/v7/posts/category/{categoryId}`     | Get a list of all posts in a specific category.                    |
| GET_POSTS_IN_CHRONOLOGICAL_ORDER | GET    | `/api/v7/posts/sortPostsByPublication`    | Get the posts as an ordered list from newest to oldest.            |
| SEARCH                           | GET    | `/api/v7/posts/searchByKeyword`           | Get posts based on a specific keyword.                             |
| GET_POSTS_BY_CATEGORY_NAME       | GET    | `/api/v7/posts/user/searchByCategoryName` | Get a list of all posts in a specific category (by category name). |

- #### User associated endpoints:

| Endpoint Tag    | Call   |              Endpoint               | Description                                                           |
|-----------------|--------|:-----------------------------------:|-----------------------------------------------------------------------|
| CREATE          | POST   |           `/api/v7/users`           | Create a new user.                                                    |
| GET_ALL         | GET    |           `/api/v7/users`           | Get a list of all users.                                              |
| FIND_BY_ID      | GET    |        `/api/v7/users/{id}`         | Get detailed information about a specific user (by user id).          |
| UPDATE          | PUT    |        `/api/v7/users/{id}`         | Update a user.                                                        |
| DELETE          | DELETE |        `/api/v7/users/{id}`         | Marks the user as deleted and prevents access to that user.           |
| REACTIVATE_USER | POST   | `/api/v7/users/reActivateUser/{id}` | Removes the deleted mark from the user and makes the user accessible. |

- #### Category associated endpoints:

| Endpoint Tag | Call   |             Endpoint              | Description                                                            |
|--------------|--------|:---------------------------------:|------------------------------------------------------------------------|
| CREATE       | POST   |       `/api/v7/categories`        | Create a new category.                                                 |
| GET_ALL      | GET    |       `/api/v7/categories`        | Get a list of all categories.                                          |
| FIND_BY_ID   | GET    |     `/api/v7/categories/{id}`     | Get detailed information about a specific category (by category id).   |
| FIND_BY_NAME | GET    | `/api/v7/categories/categoryName` | Get detailed information about a specific category (by category name). |
| UPDATE       | PUT    |     `/api/v7/categories/{id}`     | Update a category.                                                     |
| DELETE       | DELETE |     `/api/v7/categories/{id}`     | Marks the category as deleted and prevents access to that category.    |

- #### Comment associated endpoints:

| Endpoint Tag | Call   |       Endpoint       | Description                                       |
|--------------|--------|:--------------------:|---------------------------------------------------|
| CREATE       | POST   |   `/api/comments`    | Create a new comment                              |
| GET_ALL      | GET    |   `/api/comments`    | Get a list of all comments                        |
| FIND_BY_ID   | GET    | `/api/comments/{id}` | Get detailed information about a specific comment |
| UPDATE       | PUT    | `/api/comments/{id}` | Update a comment                                  |
| DELETE       | DELETE | `/api/comments/{id}` | Delete a comment                                  |


## Validation and Error Checking

The project incorporates validation and error checking to ensure the integrity of the data and provide meaningful error messages.

- #### Length Validation: 

 [Jakarta Bean Validation](https://beanvalidation.org/) is a validation model that can add constraints to the beans with annotations placed on fields, methods, or classes.

**MaxLength**: `@MaxLength` is a custom validation annotation. With its corresponding validator `MaxLengthValidator`,  ensures that the length of the annotated string doesn't exceed the specified maximum length.

**MinLength**: `@MinLength` is a custom validation annotation. With its corresponding validator `MinLengthValidator`,  ensures that the length of the annotated string fulfil the required minimum length.

With the `validateFieldLength` method, the lengths of the fields are checked and a meaningful error is returned.

- #### Email Validation: 

[Apache Commons Validator](https://commons.apache.org/proper/commons-validator/) provides various utility methods for validating common data types, including email addresses.

In particular, `EmailValidator` is designed to validate whether a given string conforms to the standard format of an email address. It checks for basic syntactical correctness according to the rules defined in the email RFCs (Request for Comments), ensuring that the email address contains an "@" symbol, has a valid domain name, and adheres to other criteria.

   ```java
    public void checkIfEmailValid(String email) {
           EmailValidator validator = EmailValidator.getInstance();
           if(!validator.isValid(email)) throw new ValidationException(ErrorType.EMAIL_NOT_VALID);
       }
   ```

- #### BusinessRules Classes: 

The project utilizes BusinessRules classes to encapsulate validation logic and enforce business rules. These classes provide a structured approach to validate entities and perform custom validation checks specific to the project's requirements.

---

This project was developed for Java programming courses. [Bilge Adam](https://www.bilgeadam.com/).

by [hcaslan](https://github.com/hcaslan).