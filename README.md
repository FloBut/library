

# Hi, I'm Florica! 👋

Here you can find the documentation of the Library project
## 🚀 About Me
💻Dedicated back-end software developer | 👨‍💻Motivated to work for companies to build great back-ends | Java, Spring Boot | Passionate about solving problems using technology | 💼 Actively looking for a job | 10️⃣ personal projects


## 🛠 Skills
Back-end development, Software development, Web development, Java, Spring framework, Spring boot, Data structures, Algorithms, OOP, MySQL, Relational databases, SQL, Git, Web services, Rest APIs, Unit Testing

## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://github.com/FloBut)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/florica-butuc/)


# Exercise for Technical Assessment (Backend Software Engineer)
This project is a Library Management System. The API should allow
users to manage books and authors, including the ability to add, update, delete, and
retrieve information
## Features

Add a new book: Admins can add a new book with a title, description, isbn and an author.

Update an existing book: Admins can update an existing book from the library.

Delete a book: The book with indicated id will be deleted from database.

Retrieve a list of all the books: Admins can retrieve a list with all books from library.

Retrieve details of a single book by its id: Admins can retrieve a detail of a book by indicated the id.

Add a new author: Admin can add an author by adding a name and a bio.

Update an existing author: Admins can update an existing author from the library.

Delete a book: The author with indicated id will be deleted from database.

Retrieve a list of all the authors: Admins can retrieve a list with all authors from library.

Retrieve details of a single author by its id: Admins can retrieve a detail of an author by indicated the id.

The application integrates with API Documentation: Swagger.

Basic Authentication & Authorization: Keycloak is integrated into the Docker setup for authentication and authorization purposes, ensuring secure access to the application.
## Built with

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)

## Technical solution explanation

I created the following diagram to show the architecture of the application:
![App Screenshot](https://imgur.com/a/VtT9B7X)

I used a layered architecture. Each layer uses the layers beneath it, with these dependencies being injected through Spring's Dependency Injection mechanism.
The Business Logic layer implements the functionality logic, while the Persistence layer ensures the connection with the database.
Using Hibernate ORM, which is included in Spring Data JPA, the mapping between database tables and Java entities is achieved. Spring Data JPA is also used for performing CRUD operations with the relational database MySQL.
In the Presentation layer, all endpoints of the REST API are defined. The data format that will be received and sent to a client is JSON.

Now I will explain some of my technical decisions, though not necessarily in any specific order.

**Exception handling:**
I used a global exception handler to have a single point for handling exceptions and to maintain a uniform format in error messages.
Also, I reduced code duplication and decoupled error handling logic from business logic.

**DTO Pattern:**
Even if the application is small, I used DTO pattern to separate the presentation layer from the service layer.
So that changes in the database model will not affect data sent to the client. The API will remain stable.
This time, I implemented my own mappers and didn't use a framework.

**Docker**:
I chose to run the application in Docker containers so that it can be deployed in various environments.
Additionally, the application is isolated, easy to scale, and dependency management is simplified.
I used Docker Compose to configure and run the three necessary containers:
the container for the MySQL database, the container where the Spring Boot application will run, and the container where the Keycloak authentication server will run.

**Oauth2**:
I used Oauth2 protocol for authentication and authorization with the "authorization code" flow.
So there is a token based authentication, and the process of authorizing and authenticating a user is delegated to another server.
I used the open-source Keycloak server for that. This protocol is highly extensible, and for example it can be integrated with Single Sign-On
For now, the endpoints are secured and accessible based on two user roles (user and admin). I used Spring Security for that.

**Builder design pattern**:
I used builder design pattern for simplifying object creation for unit test data. In this way I also improved the readability of the tests and provided options for partial object construction

**Spring profiles**
I used Spring profiles to isolate configurations for local and Docker environments. With the docker profile, I mimic a production environment.
Also for the Docker environment, the configurations will be injected using environment variables.

**Web client**:
I provided an additional feature: to fetch other details of a book by providing ISBN to Google Books API
I used web client for accessing the external API, which is non-blocking, so more efficient.

**Spring Data JPA Specification interface**:
I provided an additional feature: to search for authors based on flexible criteria (name, or keywords from their BIO).
For this, I used the Specification interface, to be able to have dynamic queries.


## Technical debt
- add an endpoint for user registration and use Keycloack Admin Client to send requests tu keycloack server in order to save the new user
- configure SpringDoc for Oauth2
- follow best practice to secure credentials like api keys


## API Reference

#### Create a new book

```http
  POST /books/addBook
```

| Parameter | Type     | Description                        |
| :-------- | :------- |:-----------------------------------|
| `body`    | `json`   | **Required**. The book to be added |

Request body example:

```json
{
  "title": "title1",
  "description": "desc1",
  "isbn":"isbn1",
  "authorId": 1
}
```

#### Update book

```http
  PUT /books/${id}/
```

| Parameter | Type            | Description                               |
|:----------|:----------------|:------------------------------------------|
| `id`      | `string`        | **Required**. Id of book will be updated |

#### Delete a Book by id

```http
  DELETE /books/${id}
```

| Parameter | Type     | Description                                              |
| :-------- | :------- |:---------------------------------------------------------|
| `id`      | `string` | **Required**. The book with indicated id will be deleted | 


#### Get book by id

```http
  GET /books/id
```

#### Get all books

```http
  GET /books/
```

#### Get details of a book by ISBN

```http
  GET /books/details
```

#### Create a new author

```http
  POST /authors/addAuthor
```

| Parameter | Type     | Description                          |
| :-------- | :------- |:-------------------------------------|
| `body`    | `json`   | **Required**. The author to be added |

Request body example:

```json
{
  "name": "name1",
  "bio": "bio1"
}
```

### Update author

```http
  PUT /authors/${id}/
```

| Parameter | Type     | Description                                            |
|:----------|:---------|:-------------------------------------------------------|
| `id`      | `string` | **Required**. Author with indicated id will be updated|

#### Delete a author by id

```http
  DELETE /author/${id}
```

| Parameter | Type     | Description                                                |
| :-------- | :------- |:-----------------------------------------------------------|
| `id`      | `string` | **Required**. The author with indicated id will be deleted | 

#### Get authors by id

```http
  GET /authors/id
```

#### Get all authors

```http
  GET /authors/
```

#### Get all authors by name and bio

```http
  GET /authors/search
```

## API Authentication and Authorization

Keycloak Setup and OAuth2 Authorization Code Flow
To securely manage user authentication and authorization, our application utilizes Keycloak, configured with OAuth2 Authorization Code flow. Here's how it works:

- Initial Setup: Configure Keycloak realm and client settings. Ensure redirection URIs are set for your application.
- Authorization Request: The user's client (e.g., a browser) sends a request to the Keycloak authorization endpoint, including parameters like client ID and secret
- Authentication: The user logs in through Keycloak's login form.
- Code Exchange: After successful authentication, Keycloak redirects to the specified URI with an authorization code.
- Token Request: The client exchanges the authorization code for an access token using Keycloak's token endpoint.

For all endpoints, the following authorization header should be included in HTTP requests headers:

```http
  Authorization: Bearer <access_token>
```

## Prerequisites

For building and running the application you need:
- JDK 1.8 or higher
- Maven 3

For building and running the application with Docker, you need:

- Docker Desktop (for Windows and Mac) or Docker Engine (for Linux)
- Docker Compose (optional, for orchestrating multi-container applications)

Keycloak Integration
Keycloak is integrated into the Docker setup for authentication and authorization purposes.
Ensure that you configure Keycloak properly and provide the necessary environment variables for seamless integration.

## Dependencies

You don't need any additional dependencies.
All dependecies related to database management, server management, security management and so on, will be automatically injected by Maven using the pom.xml file located in the root folder of the project.
## Installation

Clone the project

```bash
  git clone https://github.com/FloBut/library
```

Go to the project directory

```bash
  cd my-project
```

## Run Locally

Use maven to build the app and, to run it, and to start the local embedded Tomcat server

```bash
  mvn spring-boot:run
```


## Running Tests

To run tests, run the following command

```bash
  mvn test
```

## Running Locally with Docker

### Build the Docker Image

Navigate to the root directory of your project where the Dockerfile is located, and run the following command:

```http
docker build -t library .
```

### Run the Application

### Run using Docker Compose (Optional)

If your application requires running multiple services (like an app server and a database), you can use Docker Compose to manage these services. Here is an example docker-compose.yml file for your application

```http
docker-compose up
```

This command builds and starts both the application and the database containers. The application will connect to the MySQL database as configured in your application's properties.

## Deployment with Docker
Deploying your dockerized application can vary based on your hosting provider. Typically, you would push your Docker image to a container registry (e.g., Docker Hub, GitHub Container Registry) and then pull and run it on your production server. Here are the basic steps for pushing to Docker Hub:

### Tag your image
```http
docker tag library yourusername/library:latest
```

### Push your image to the registry
```http
docker push yourusername/library:latest
```

After pushing your image, you can follow your hosting provider instructions.


## Usage

You can use Postman to test all the endpoints

First, obtain an access token by choosing Authorization - Oauth 2.0

After that, fill details of you client id, client secret, acces token URL, auth URL and click "Get New Access Token".

You will be redirected to the Keycloak login page, where you can type your username and password. After that, a JWT token will be generated and added as header to your Postman request.

## Roadmap

In the future, application can be extended with following:

- adding reviews
- statistics about authors and books
- recommendation engine
- and many, many others

## Badges


![Maintained](https://img.shields.io/badge/Maintained%3F-yes-green.svg)
![GIT](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![JWT](https://img.shields.io/badge/json%20web%20tokens-323330?style=for-the-badge&logo=json-web-tokens&logoColor=pink)