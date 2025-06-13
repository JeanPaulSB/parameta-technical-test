# Parameta Technical Test

## Overview

The implementation includes:

- A **REST API** that receives employee data, validates it, and calls a SOAP service.
- A **SOAP Web Service** that stores employee data in a MySQL database.

## Features

- Employee registration with age validation
- SOAP service integration for employee data storage
- REST API endpoints for employee management
- Age and time in company calculations
- Document number uniqueness validation
- Database persistence

## Technologies

- Java 21
- Spring Boot 3.x
- Maven
- JPA/Hibernate
- SOAP Web Service
- RESTful API
- MySQL 8.0
- Lombok
- JUnit 5

## Project Structure

```
src/
  main/
    java/
      com/example/parameta/employee/
        client/         # SOAP consumer configuration
        config/         # Spring and SOAP configuration
        controller/     # REST controllers
        endpoint/       # SOAP endpoints
        model/          # Entity and DTOs
        repository/     # Spring Data JPA repositories
        service/        # Business logic
      com/example/parameta/exception/
        custom/         # Custom exception handler
        ErrorResponse.java
        GlobalExceptionHandler.java
      com/example/parameta/util/
        DateUtil.java   # Utility methods to convert from XMLGregorianCalendar to Date and viceversa.
    resources/
      employee.xsd      # XSD schema for SOAP service
      application.properties
  test/
    java/              # Test classes
    resources/         # Test resources
pom.xml
```

## Prerequisites

- JDK 21
- Maven 3.8+
- MySQL 8.0

## REST API Endpoints

### GET /employees

Registers a new employee and returns their information.

### Example GET request

```
http://localhost:8080/employees?name=Juan&lastName=Lopez&documentType=CC&documentNumber=15345678&birthDate=1990-05-10&hireDate=2015-03-15&position=Ingeniero&salary=5500
```

### Sample response

![Sample response](https://i.imgur.com/e7dA4a5.png)

### Sample validation error

#### Duplicated document number

![Sample duplicated exception](https://i.imgur.com/uWEOowv.png)

#### Underage employee

![Sample underage exception](https://i.imgur.com/AUtL5IF.png)

#### Request Parameters

- name (String): Employee's first name
- lastName (String): Employee's last name
- documentType (String): Type of identification document
- documentNumber (String): Unique identification number
- birthDate (Date): Date of birth (YYYY-MM-DD)
- hireDate (Date): Date of joining company (YYYY-MM-DD)
- position (String): Job position
- salary (Double): Monthly salary

#### Response

```json
{
  "employeeInfo": {
    "name": "John",
    "lastName": "Doe",
    "documentType": "ID",
    "documentNumber": "123456",
    "birthDate": "1990-01-01",
    "hireDate": "2020-01-01",
    "position": "Developer",
    "salary": 5000.0
  },
  "age": {
    "years": 33,
    "months": 5,
    "days": 12
  },
  "timeInCompany": {
    "years": 3,
    "months": 5,
    "days": 12
  }
}
```

## SOAP Service

### WSDL Location

```
http://localhost:8080/ws/employees.wsdl
```

### Example SOAP Request

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:emp="http://example.com/employee">
   <soapenv:Header/>
   <soapenv:Body>
      <emp:StoreEmployeeRequest>
         <emp:employee>
            <emp:name>John</emp:name>
            <emp:lastName>Doe</emp:lastName>
            <emp:documentType>ID</emp:documentType>
            <emp:documentNumber>123456</emp:documentNumber>
            <emp:birthDate>1990-01-01</emp:birthDate>
            <emp:hireDate>2020-01-01</emp:hireDate>
            <emp:position>Developer</emp:position>
            <emp:salary>5000.0</emp:salary>
         </emp:employee>
      </emp:StoreEmployeeRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

## Build and Run

1. Clone the repository:

```bash
git clone https://github.com/JeanPaulSB/parameta-technical-test.git
cd parameta-technical-test
```

2. Spin up the database

```bash
docker compose up
```

3. Build the project:

```bash
mvn clean install
```

4. Run the application:

```bash
mvn spring-boot:run
```

## Validation Rules

1. Employee Age:

   - Must be 18 years or older
   - Calculated based on birthDate

2. Document Number:

   - Must be unique in the system
   - Required field

3. Required Fields:
   - All fields are mandatory
   - Dates must be valid

## Error Handling

The API returns appropriate HTTP status codes:

- 200: Success
- 400: Bad Request (validation errors)
- 404: Not Found
- 500: Internal Server Error

# Author

Jean Paul Sierra Boom
