# Employee Management System

## Project Overview
This project is a Spring Boot-based Employee Management System that allows adding employees, retrieving managers at different levels, and sending email notifications to level-1 managers when a new employee is added.

## Prerequisites
Ensure you have the following installed:
- Java 17 or later
- Spring Boot
- Apache CouchDB
- Maven
- An SMTP email account (Gmail, etc.)

## Setup Instructions

### 1. Clone the Repository
```sh
 git clone https://github.com/SayedHaneef31/Employee-Management.git
 cd employee-management-system
```

### 2. Configure Database
Modify `application.properties` with your CouchDB credentials:
```properties
# CouchDB Connection Properties
couchdb.protocol=http
couchdb.host=127.0.0.1
couchdb.port=5984
couchdb.database= < Database Name >
couchdb.username= < DB User >
couchdb.password= < DB Password >
```

### 3. Configure Email Service
Set up email credentials for sending notifications:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-email-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### 4. Build and Run the Application
For Maven:
```sh
mvn clean install
mvn spring-boot:run
```

# API Documentation

## Base URL
```
http://localhost:8080/api/employees
```


This API provides functionalities for managing employees, including CRUD operations and retrieving hierarchical relationships.

## Endpoints

### 1. Add Employee
**URL:** `/api/employees`  
**Method:** `POST`  
**Request Body:**
```json
{
  "employeeName": "John Doe",
  "phoneNumber": "+911234567890",
  "email": "john.doe@example.com",
  "reportsTo": "manager-id",
  "profileImageUrl": "http://example.com/image.jpg"
}
```
**Response:**
```json
"employee-id"
```

---

### 2. Delete Employee
**URL:** `/api/employees/{id}`  
**Method:** `DELETE`  
**Response:**
```json
"Employee deleted successfully"
```

---

### 3. Update Employee
**URL:** `/api/employees/{id}`  
**Method:** `PUT`  
**Request Body:**
```json
{
  "employeeName": "John Updated",
  "phoneNumber": "+911234567891",
  "email": "john.updated@example.com",
  "reportsTo": "new-manager-id",
  "profileImageUrl": "http://example.com/new-image.jpg"
}
```
**Response:**
```json
"Employee updated successfully"
```

---

### 4. Get Nth Level Manager
**URL:** `/api/employees/{employeeId}/manager?level={level}`  
**Method:** `GET`  
**Response:**
```json
{
  "id": "manager-id",
  "name": "Manager Name",
  "email": "manager@example.com"
}
```

---

### 5. Get All Employees (Paginated)
**URL:** `/api/employees?page={page}&size={size}&sortBy={sortBy}&sortDirection={asc|desc}`  
**Method:** `GET`  
**Response:**
```json
{
  "content": [
    {
      "id": "employee-id",
      "employeeName": "John Doe",
      "email": "john.doe@example.com"
    }
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 100,
  "totalPages": 10,
  "isLastPage": false
}
```
## Contact
For any queries, reach out to `haneefatwork01@gmail.com`.
