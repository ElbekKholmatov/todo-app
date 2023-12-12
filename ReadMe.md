
---

# TodoApp 📋

This project is a Todo application built with Java 17 and Spring Boot 3.2.0. It provides functionality for managing todos, authentication using email, and more.

## Features 🚀

- **Todo Management**: Create, update, delete, and retrieve todo items.
- **Authentication with Email**: User registration, activation via email, access token generation.
- **Docker Integration**: Dockerized deployment for easy setup.

## Prerequisites 🛠️

Before running the application, ensure you have the following installed:

- Java 17
- Spring Boot  3.2.0
- Docker

## Getting Started 🏁

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/ElbekKholmatov/todo-app.git
   cd todo-app
   ```

2. **Build the Application**:

   ```bash
   mvn clean
   mvn package -DskipTests
   ```

   This command will clean the project and package it without running tests.

3. **Run with Docker**:

   Build and start the Docker containers:

   ```bash
   docker-compose up -d
   ```

   This command orchestrates the deployment of services defined in the `docker-compose.yml` file, launching the application containers in detached mode.

4. **Access the Application**:

   Open your web browser and go to `http://localhost:8080` to access the application.

## Configuration ⚙️

- Configure email settings in `application.properties` for email-related functionalities.

## API Documentation 📖

The API endpoints are documented using Swagger. After running the application, access the API documentation at `http://localhost:8080/swagger-ui/`.

### Controllers 🎮

#### AuthController

This controller handles authentication-related operations:

- **`POST /api/v1/auth/access/token`**: Generates access and refresh tokens.
- **`POST /api/v1/auth/refresh/token`**: Generates a new access token using a refresh token.
- **`POST /api/v1/auth/register`**: User registration, activation code via email.
- **`POST /api/v1/auth/activate`**: Activates users through an activation code sent via SMS.
- **`POST /api/v1/auth/code/resend`**: Resends the activation code to the user's phone number.

#### UserController

This controller manages user-related operations:

- **`GET /api/v1/user/current`**: Fetches information of the currently logged-in user.
- **`POST /api/v1/user/reset_password`**: Resets a user's password.

#### CommentController

Handles comment-related operations for todos:

- **`POST /api/v1/comments/addComment`**: Adds a comment to a todo.
- **`GET /api/v1/comments/get-all-comments`**: Retrieves a list of comments based on filters.

#### TodoController

Manages CRUD operations for todos:

- **`POST /api/v1/todo/create`**: Creates a new todo item.
- **`GET /api/v1/todo/get-all-todos`**: Retrieves a list of todos based on filters.
- **`PUT /api/v1/todo/update`**: Updates an existing todo item.
- **`DELETE /api/v1/todo/delete`**: Deletes a todo item.

## Contributing 🤝

Feel free to contribute by submitting bug reports, feature requests, or pull requests.

## License 📝

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
