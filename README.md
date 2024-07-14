# Spring Boot Project

## Running the Project

To run the project, you have two options: using Docker or Java.

### Using Docker (recommended)

1. Ensure Docker and Docker Compose are installed on your machine. If not, you can download it
   from [here](https://docs.docker.com/compose/install/).
2. Clone the project repository and navigate to the project root directory.

3. In the project repository open a terminal window and run the command

   ```
   docker-compose up --build
   ```

### Using Java

1. Install Java: Follow the instructions on [this website](https://www.java.com/en/download/help/download_options.html)
   to install Java on your machine.
2. Clone the project repository and navigate to the project root directory.
3. Run the project using one of the following commands:
    - Using Gradle: `./gradlew bootRun`

The application will be accessible at [http://localhost:8080](http://localhost:8080).

## Test Data

Upon starting the project, some test data will be loaded automatically. The following users will be available:

| ID       | Name          | Email                     | Password |
|----------|---------------|---------------------------|----------|
| 75210938 | Mercy Wanjiru | mercy.wanjiru@example.com | 12345    |
| 60834792 | Kevin Mwangi  | kevin.mwangi@example.com  | 12345    |

Feel free to use these credentials to test the application.

## Endpoint Documentation

### Swagger

When the application is running, you can access the Swagger UI at:
[http://localhost:8080/swagger/custom-swagger-ui.html](http://localhost:8080/swagger/custom-swagger-ui.html)

### Postman Collection

A Postman collection file named `AccountsBackend.postman_collection.json` is included in the root of the project. You
can import this file into Postman to view and interact with the relevant endpoints.
