# Employee Management System API

This is a **Spring Boot-based REST API** for managing employees within an organization. The API includes endpoints for managing employee data, departments, and other related functionalities.

---

## **Features**
- RESTful APIs for managing employees and departments.
- PostgreSQL integration for database management.
- Hibernate/JPA for ORM (Object Relational Mapping).
- Configuration-based setup using `application.properties`.
- Easily deployable with Maven.

---

## **Technologies Used**
- **Java 17** (or compatible version)
- **Spring Boot** (REST APIs, JPA)
- **PostgreSQL** (Database)
- **Maven** (Dependency Management)
- **Hibernate** (ORM)

---

## **Getting Started**

### **1. Prerequisites**
Ensure you have the following installed:
- **Java 17+**
- **PostgreSQL** (running on your machine or server)
- **Maven** (if not using the Maven Wrapper)

---

### **2. Clone the Repository**
Clone the repository to your local system:

- **git clone https://github.com/Buffden/ems-api.git
- **cd ems-api

### **3. Setup the Database**
- **Install PostgreSQL and create a database named employee_management_db.
- **Update the application.properties file with your database credentials:

  spring.datasource.url=jdbc:postgresql://localhost:5432/employee_management_db
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true


### **4. Build the Project**
Use Maven to build the project:

  ./mvnw clean install

### **5. Run the Application**

Run the project using the generated JAR file:

  java -jar target/employee-management-system-api.jar
  
Alternatively, run it directly with Maven:

  ./mvnw spring-boot:run


### **6. Access the API**
The application will be available at:

  http://localhost:8080

