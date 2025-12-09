# 🛡️ JobGuard – Backend (Microservices Architecture)

JobGuard is a microservice-based backend system designed to help jobseekers report, verify, and track fraudulent job offers.  
The system allows users to submit suspicious job contacts (calls, WhatsApp messages, emails, job posts) and enables admins to validate and publish verified fraud reports.

This repository contains the **backend microservices**, built using Spring Boot, Spring Cloud, Eureka Discovery, and Maven multi-module architecture.

---

## 🚀 Tech Stack

- **Java:** 17  
- **Spring Boot:** 3.3.x  
- **Spring Cloud:** 2023.x  
- **Maven Multi-Module Architecture**  
- **Eureka Discovery Server**  
- **Spring Cloud Gateway** (to be added)  
- **JWT (Auth)**  
- **MySQL** (future modules)  
- **Cloudinary (Media Upload)**  
- **Kafka (future integration)**  

---

# 📁 Project Structure

```

jobguard/
├── pom.xml                # Parent POM
├── auth-service/          # Authentication + JWT + User management
├── discovery-server/      # Eureka Service Registry
└── (api-gateway/)         # To be added

````

The parent project manages dependencies, versions, and common configuration for all microservices.

---

# 📘 Parent POM Overview

The project uses Spring Boot 3.3 and Spring Cloud 2023 with Java 17.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.jobguard</groupId>
    <artifactId>jobguard</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>

    <name>JobGuard</name>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.0</version>
    </parent>

    <properties>
        <java.version>17</java.version>
        <spring-cloud.version>2023.0.0</spring-cloud.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <modules>
        <module>auth-service</module>
        <module>discovery-server</module>
    </modules>

</project>
````

---

# 🧩 Microservices

## 1️⃣ Auth Service

Handles:

* User registration
* Login
* JWT token generation
* Password hashing (BCrypt)
* User profile basics

Built with:

* Spring Web
* Spring Security
* JWT
* JPA (future MySQL integration)

---

## 2️⃣ Discovery Server (Eureka)

Central registry where all services register and communicate.

Runs on **port 8761**

UI dashboard:
👉 [http://localhost:8761](http://localhost:8761)

---

## 3️⃣ API Gateway (Coming Soon)

Will handle:

* Routing to microservices
* Global filters
* JWT validation
* Logging
* CORS
* Rate limiting

---

# 🚀 How to Run the Project

### **1. Clone the repository**

```bash
git clone <repo-url>
cd jobguard
```

### **2. Open in IntelliJ IDEA**

Select the **parent project folder** → IntelliJ will auto-import modules.

### **3. Run Eureka Discovery Server**

Open:

```
discovery-server/src/main/java/.../DiscoveryServerApplication
```

Start it → visit: `http://localhost:8761`

### **4. Run Auth Service**

It will register itself with Eureka.

---

# 🧑‍💻 Branching Strategy

| Branch Name      | Purpose                           |
| ---------------- | --------------------------------- |
| `main`           | Production-safe, protected branch |
| `dev` (optional) | Team integration branch           |
| `feature/*`      | Feature development               |
| `fix/*`          | Bug fixes                         |

Example:

```
git checkout -b feature/auth-register-api
```

---

# 📜 Commit Message Guidelines

Follow meaningful commit messages:

```
feat: implement user registration API
fix: resolve password hashing issue
chore: update .gitignore
refactor: improve controller structure
```

---

# 🔮 Future Modules (As per SRS)

* Report Service
* Media Service
* Admin Verification Service
* Notification Service
* Search Service
* Analytics Dashboard

---

# 🛡️ Security Standards

* JWT Access & Refresh Tokens
* BCrypt password hashing
* Input sanitization
* HTTPS enforcement (production)
* Rate limiting (API Gateway)

---

# 📄 Documentation

The complete **Software Requirement Specification (SRS)** includes:

* Functional Requirements
* Non-functional Requirements
* Architecture
* User flows
* Admin workflow
* Data models
* APIs
* Security policy

(Will add link once uploaded.)

---

# 🤝 Contributing Guide

1. Create a feature branch
2. Add changes → commit
3. Push to your branch
4. Create a Pull Request
5. Request review

---

# ⭐ Summary

JobGuard backend is a scalable Spring Cloud microservices architecture designed to protect jobseekers by validating fraudulent job offers through a structured reporting and verification process.

More services will be added as the project progresses.

---

# 📬 Contact

**Name:** Vikram Gujar
**Email:** vikramgujar300@gmail.com

```