# Warehouse Management System - Backend

A backend REST API for a warehouse management system (or storage management system), 
built with Java and Spring Boot.

## Contents

- [Introduction](#Introduction)
- [Features](#Features)
- [Quick Start & Installation](#Quick-Start--Installation)
- [Instructions of Use](#Instructions-of-Use)
- [Project Structure](#Project-Structure)
- [Test](#Test)
- [FAQ](#FAQ)
- [LICENSE](#LICENSE)
- [CONTACT](#CONTACT)

## Introduction

This Warehouse Management System Backend is built with Java and Spring Boot.  
The system provides warehouse related business operations, using layered architecture 
to separate http requests, business logics, and data accesses.  
This project is written for implementing and having a deeper understanding about common
technologies and designs in backend developments using Java and Spring Boot, including:
- RESTful API design
- Spring Boot / Spring MVC / Spring Security
- MySQL database
- Mybatis(-Plus) data accessing
- Authentication based on JWT
- Role-based access control (RBAC)
- Session management using access and refresh tokens
- Application logs and request tracing
- OpenAPI / Swagger documentation
- Version control using git and GitHub

The frontend of the system is a duplicate React application, which communicates with 
this system by HTTP REST API. You can find the repository on [GitHub](https://github.com/ruijie-lin-42/Warehouse-Management-System-Frontend)

## Features

- RESTful APIs - provides CRUD APIs for core businesses
- JWT Authentication - using short-lived access token for authentication
- Session Management - using long-lived refresh token to maintain session
- RBAC Authorization - protect APIs by access controlling, based on user roles
- MySQL Persistence - persistent business data using MySQL database
- MyBatis / MyBatis-Plus - supports common CRUD and complicated SQL operations
- Structured Logging - records common application activities and business operations, as well as unexpected errors
- OpenAPI / Swagger - provides documentation for business APIs and the corresponding request parameters, response structures and possible errors

## Quick Start & Installation

### Environment Requirements

- To run this project, you need:
    - Java 25+
    - MySQL 8.0.45+
    - Git
- The project uses Maven for managing Java dependencies; but you don't need to install maven: you can use maven by using the `mvnw` or `mvnw.cmd` in the project. If you already installed Maven before, you can also use `mvn` command instead.
- The versions of major third-party libraries are declared in `pom.xml` and will be downloaded automatically during the build, so no manual installation is needed.
- The project runs well in the environment described above, possibly could but not guaranteed to run in lower versions. 

### Steps

1. Clone Repository
    ```bash
    git clone https://github.com/ruijie-lin-42/Warehouse-Management-System-Backend.git
    ```
2. Create a MySQL database on your own machine
3. Configure Environment Variables(See [FAQ](#Q-How-do-I-configure-my-environment-variables) for more details)
    - The system reads sensitive data through environment variables. Please make sure you have configured the following environment variables before you start:
        - **DB_URL** - database connection url, ex. `jdbc:mysql://localhost:3306/storage_management_system?useUnicode\=true&characterEncoding\=utf-8&useSSL\=false&allowPublicKeyRetrieval\=true&serverTimezone\=UTC`
        - **DB_USERNAME** - database username, ex. `root`, or `your_own_username`
        - **DB_PASSWORD** - database password, ex. `your_own_password`
        - **JWT_SECRET** - secret string used to generate JWT, ex. `some_random_value_that_cannot_be_known`(see [FAQ](#Q-how-do-I-set-my-JWT-secret) for more details)
   - Due to security reasons, **sharing any of the environment variables above is not recommended**.
4. Run  
    - Under the project's root directory (storage_management_system_backend), run command:
      - Windows:
          ```bash
          ./mvnw.cmd spring-boot:run
          ```
      - Linux / macOS:
          ```bash
          ./mvnw spring-boot:run
          ```
    - Or equivalently, build and run the output `.jar` file
      - Windows:
        ```bash
        ./mvnw.cmd clean package
        java -jar target/storage_management_system_backend-0.0.1-SNAPSHOT.jar 
        ```
      - Linux / macOS:
        ```bash
        ./mvnw clean package
        java -jar target/storage_management_system_backend-0.0.1-SNAPSHOT.jar
        ```
    - If you have installed maven on your machine, you can replace the `./mvnw.cmd` or `./mvnw` to `mvn` to run the project.
    - Or if you are using IDEs like IntelliJ IDEA, you can also click the run button.
5. After you successfully run the application, you can now start using this application. There will be a default SUPER_ADMIN role account created in the database on your first run, whose username is `admin` and password is `12345678`. **Remember to change that password!!!**   
6. If you have your own way to send requests (postman, your own frontend application, etc.), feel free to use those; this project also has a corresponding frontend, which you can access by accessing the repository at https://github.com/ruijie-lin-42/Warehouse-Management-System-Frontend. See the README.md for more details.

## Instructions of Use

### Basic Use

- After you run the Spring Boot Application, the backend API would provide services through `http://localhost:8090`.
- Client site can then send request to the corresponding REST API.
- You can also change the port by configuring in `application.yaml`:
    ```yaml
    server:
      port: 8090
    ``` 

### Authentication

- After you created an account or logged in successfully, the system would send an access token and a refresh token.
  - Access token is a short-lived token (15 mins) based on JWT, your client needs to receive the token and carry the access token when accessing: `Authorization: Bearer <access-token>`.
  - Refresh token is used for getting a new access token after the current access token expires. Refresh token uses a randomized string instead of JWT, and is passed through `httpOnly-Cookie` while maintaining corresponding session information in server side. 

### Authorization

- The application consists of 3 different user roles, SUPER_ADMIN, ADMIN, and USER.
- Make sure you have inserted at least one SUPER_ADMIN role. This would be done on application run by Spring Boot reading data in `resources/db/sql/data.sql`, remember that the default password is `12345678` which is weak, please change it before you start using it. Also remember to change the admin's personal info.
- Different roles has different authorities, following by the rules:
  - SUPER_ADMIN can manage ADMIN, and ADMIN can manage USER. Here manage means create, alter, and delete operations, different user roles can still read each other's public info.
  - Only SUPER_ADMIN or ADMIN could manage storages (or warehouses). Here manage means create, alter and delete operations.
  - Users with role USER or above could manipulate the stocks in a storage. Manipulating the stocks would be recorded and stored as stock history.
  - Any authenticated user could manipulate their own information.

### API Documentation

The project uses OpenAPI / Swagger to document the REST APIs. The documentation includes:
- Endpoint description
- Request parameters
- Request body schemas
- Response schemas
- Authentication requirements
- API operation descriptions
- Possible errors

After you run the application, you can check the complete API documentation through configured Swagger UI / OpenAPI endpoint, or by default http://localhost:8090/swagger-ui/index.html#/

### Logging

- You can configure the application's logging system in `application.yaml`:
- The application configured application logs and rolling log files. The logging levels can be configured based on different packages, for example:
    ```yaml
    logging:
      level:
        root: INFO
        com.storage_management_system_backend: DEBUG
    ```
- The logging system would not only print logs on consoles, but would also store the logs as files. You can configure the position where the logging files are stored and the name of the file:
    ```yaml
    logging:
      file:
        name: ${LOG_DIR:logs}/app.log
    ```
- The logging files supports rolling files following by the rolling policy, to avoid the situation where a single logging file keeps growing infinitely.
    ```yaml
    logback:
    rollingpolicy:
      max-file-size: 10MB
      max-history: 30
      total-size-cap: 1GB
      file-name-pattern: ${LOG_DIR:logs}/app.%d{yyyy-MM-dd}.%i.log.gz
    ```

### Configuration

Other main configuration options are located in `src/main/resources/application.yaml`. You can also configure your database url, username, password, jwt secret and so on here.

## Project Structure

```
Storage-Management-System-Backend/
├── src/ 
│ ├── main/ 
│ │ ├── java/ 
│ │ │ ├── io/github/ruijie_lin_42/storage_management_system_backend
│ │ │ │ ├── common/         # common packages
│ │ │ │ ├── config/         # Spring bean configuration files
│ │ │ │ ├── interceptors/   # Spring interceptors
│ │ │ │ ├── modules/
│ │ │ │ │ ├── auth/           # Authentication module
│ │ │ │ │ │ ├── controller/     # REST API request dealers
│ │ │ │ │ │ ├── constraints/    # parameter validation constraints 
│ │ │ │ │ │ ├── convert/        # MapStruct type contverters
│ │ │ │ │ │ ├── filter/         # filters for Spring security
│ │ │ │ │ │ ├── mapper/         # MyBatis-Plus mapper interfaces
│ │ │ │ │ │ ├── model/          # domain model of the module
│ │ │ │ │ │ └── service/        # business logic
│ │ │ │ │ ├── items/          # items module, same structure as above
│ │ │ │ │ ├── stock_history/  # stock history module, same structure as above
│ │ │ │ │ ├── storage/        # storage module, same structure as above
│ │ │ │ │ └── user/           # user module, same structure as above
│ │ │ └── StorageManagementSystemBackendApplication.java
│ │ └── resources/ 
│ │ │ ├── db/sql/
│ │ │ │ │ ├── schema.sql    # MySQL database DDL (Data Definition Language)
│ │ │ │ │ ├── data-test.sql # test business data
│ │ │ │ │ └── data.sql      # must-have data, including default super admin account
│ │ │ ├── mapper/         # MyBatis XML SQL mapper files
│ │ │ └── application.yaml 
│ └── test/               # testing files
├── .gitignore 
├── pom.xml 
├── mvnw 
├── mvnw.cmd 
└── README.md
```

## Test

- The testing codes used is located at `/src/test`
  - You can use Maven to run the tests:
    - Windows:
        ```bash
        ./mvnw.cmd test
        ```
    - Linux / macOS:
        ```bash
        ./mvnw test
        ```
    - or if you have already installed Maven on your machine:
        ```bash
        mvn test
        ```
- The coverage of the tests would be continuously refined as project development progress.

## FAQ

### Q: It's my first run, how do I create the first account?

A: There will be a default SUPER_ADMIN user created by Spring Boot reading `resources/db/sql/data.sql`. The username is `admin` (which cannot be changed) and the password is `12345678` (**remember to change the password!!!**).  
You may also want to change the default generated personal information as well. You can do that through your profile page or through dashboard.  
There are also test business data stored in `resources/db/sql/data-test.sql`; in case you want to use your own data, these data WON'T be inserted into the database by default. If you want to get to know the app quickly, feel free to use those data; if you want to use your own data, please insert them directly into the database directly.

### Q: I can't connect to MySQL when running the project.

A: Please check the following:
- If MySQL service is running on your machine
- If the databases are correctly manually created
- If the database url, username, and password are correctly configured
- If database port is correct

### Q: Why does my request keep getting 401 authentication errors?

A: Please make sure you have successfully logged in and carries valid access token (which is given by the server when tou log in) in your request headers:
`Authorization: Bearer <your_access_token>`  
If your access token is expired, you can get a new access token by accessing the POST /auth/refresh API to get a new access token.

### Q: Why can't I perform some operations, such as create a user or create a storage?

A: These kind of APIs are protected by role-based access control, please make sure you have enough authority before you take any actions. See [Authorization](#Authorization)

### Q: How to check the API documentation?

A: After you run the Spring Boot application, you can access the Swagger UI endpoint configured, or by default http://localhost:8090/swagger-ui/index.html#/.

### Q: How do I configure my environment variables?

A: 
- Linux / macOS:
  - If you want to set it temporarily, which is only valid in current command window:
    ```bash
    export <variable_name> = <value>
    ```
  - Or if you want to set it permanently:  
    if you are using bash:
    ```bash
    echo 'export <variable_name> = <value>' >> ~/.bashrc
    ```
    or if you are using zsh:
    ```bash
    echo 'export <variable_name> = <values>' >> ~/.zshrc
    ```
    and make it work:
    ```bash
    source ~/.bashrc   # or source ~/.zshrc
    ```
- Windows:
  - If you want to set it temporarily, which is only valid in current command window:  
    in powershell:
    ```powershell
    $env:<variable_name>="<values>"
    ```
    or in cmd:
    ```cmd
    set <variable_name>=values
    ```
  - If you want to se it permanently:  
    in powershell:
    ```powershell
    [System.Environment]::SetEnvironmentVariable('<variable_name>', '<values>', 'User')
    ```
    or you can hit `win + I` and search for `environment variables` and manually edit the environment variables. 
- Or if you want to run the application using IDEs like IntelliJ IDEA, you can edit the IDES's running environment variables.

### Q: How do I set my JWT secret?

A: A strong JWT secret should be randomly generated instead of yourself coming up with a password, since it is used to sign your token; leaking it may cause you getting attacked.  
Cryptographically secure random bytes should be used—for example, by generating a 256-bit (32-byte) random value and then Base64-encoding it.  
For example, you can use OpenSSL:  
```bash
openssl rand -base64 32
```
You'll get a string with completely random characters. You can use it as your JWT secret. Remember that 32 bytes or 256 bits is a suitable benchmark; a longer string is also acceptable.

## LICENSE

This project is based on [MIT](./LICENSE) license.

## Contact

- Author: Ruijie Lin
- Email: lrj653224490@gmail.com | ruijie.lin2006@gmail.com
- Project repository: https://github.com/ruijie-lin-42/Warehouse-Management-System-Backend.git
- Issues: https://github.com/ruijie-lin-42/Warehouse-Management-System-Backend/issues
