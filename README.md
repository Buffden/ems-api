# 🧩 Employee Management System API (Backend - Spring Boot)

### ⚙️ Tech Stack
- **Spring Boot 3.4.0**
- **Java 17**
- **PostgreSQL** (AWS RDS or Local Docker)
- **Hibernate JPA**
- **Maven**

---

### 📁 Project Setup

#### 1️⃣ Prerequisites
- Java 17
- Maven
- PostgreSQL (local/docker/RDS)

#### 2️⃣ Clone and Configure
```bash
git clone <backend-repo-url>
cd ems-api
```

#### 3️⃣ Configure Application Properties
Edit `src/main/resources/application.properties`

For **local development**:
```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}:5432/${DB_NAME}?sslmode=require
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PWD}

spring.web.cors.allowed-origins=http://localhost:4200,http://<EC2-PUBLIC-IP>:4200
```

> Replace with appropriate environment variables or hardcoded values for testing.

---

### 🚀 Deployment (EC2 + RDS)

1. **Build JAR**:
```bash
mvn clean package -DskipTests
```

2. **Upload JAR to EC2**:
```bash
scp -i ~/.ssh/ems-key-pair-name.pem target/employee-management-system-0.0.1-SNAPSHOT.jar ubuntu@<EC2-PUBLIC-IP>:/home/ubuntu/
```

3. **Run JAR on EC2**:
```bash
# Optional: kill previous Java processes
pkill -f 'java -jar'

# Start application
nohup java -jar employee-management-system-0.0.1-SNAPSHOT.jar --server.port=8080 > app.log 2>&1 &

# Check logs
tail -f app.log
```

---

### 🔒 AWS Notes
- Open **port 8080** in EC2 security group for your IP (or temporarily `0.0.0.0/0`).
- Make sure RDS allows EC2 security group inbound access on port 5432.

---

### 🧪 Test API (Postman or curl)
```bash
curl http://<EC2-PUBLIC-IP>:8080/departments
```

---

### 🧼 Shutdown Instructions (for cost saving)
- Stop RDS and EC2 instances manually from AWS Console.
- Alternatively, configure auto-shutdown or budget alerts.

---

### 🧩 Troubleshooting
| Problem | Fix |
|---------|-----|
| Timeout from Angular | Check EC2 port 8080 rule |
| CORS error | Update CORS allowed origins |
| Java process not stopping | Use `pkill -f` or `kill -9` with `ps aux | grep java` |

---

### ✅ Confirm Running
- Backend: [http://<EC2-PUBLIC-IP>:8080/departments](http://<EC2-PUBLIC-IP>:8080/departments)
- Frontend: [http://localhost:4200](http://localhost:4200)

