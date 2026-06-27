# 🚀 VELO Chat Backend

A secure and scalable backend for a real-time chat application built using **Spring Boot**, **Spring Security**, **JWT Authentication**, and **PostgreSQL**.

This project provides REST APIs for user authentication, conversation management, and messaging. It is designed with clean architecture principles and is being extended with real-time communication using WebSockets.

---

## 📌 Features

### 👤 User Management
- User Registration
- User Login
- JWT Authentication
- Get Current User Profile
- Password Encryption using BCrypt

### 💬 Conversation Management
- Create Conversation
- Prevent Duplicate Conversations
- Retrieve User Conversations

### ✉️ Message Management
- Send Messages
- Retrieve Chat History
- Mark Messages as Read

### 🔐 Security
- Spring Security
- JWT Authentication
- BCrypt Password Encoding
- Protected REST APIs

### 📚 API Documentation
- Swagger/OpenAPI Integration
- Interactive API Testing

---

# 🛠 Tech Stack

| Technology | Version |
|------------|----------|
| Java | 17 |
| Spring Boot | 3.5.x |
| Spring Security | Latest |
| Spring Data JPA | Latest |
| PostgreSQL | 18 |
| JWT | JJWT |
| Maven | Latest |
| Swagger OpenAPI | SpringDoc |
| Git | Version Control |

---

# 📂 Project Structure

```
src
├── common
│   ├── mapper
│   ├── response
│   └── exception
│
├── config
│
├── security
│
├── user
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
│
├── conversation
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
│
└── message
    ├── controller
    ├── dto
    ├── entity
    ├── repository
    └── service
```

---

# 🏗 Architecture

```
Client

     │

REST API

     │

Controllers

     │

Services

     │

Repositories

     │

PostgreSQL
```

---

# 🔐 Authentication Flow

```
Register User

        │

Store BCrypt Password

        │

Login

        │

Generate JWT Token

        │

Client stores JWT

        │

JWT sent in Authorization Header

        │

Protected APIs
```

Authorization Header

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 📖 REST APIs

## Authentication

| Method | Endpoint |
|---------|----------|
| POST | /api/users/register |
| POST | /api/users/login |
| GET | /api/users/me |

---

## Conversations

| Method | Endpoint |
|---------|----------|
| POST | /api/conversations |
| GET | /api/conversations |

---

## Messages

| Method | Endpoint |
|---------|----------|
| POST | /api/messages |
| GET | /api/messages/conversation/{conversationId} |
| PATCH | /api/messages/{messageId}/read |

---

# 📚 Swagger

After starting the application, visit:

```
http://localhost:8081/swagger-ui/index.html
```

Interactive API documentation is available for all endpoints.

---

# ⚙️ Getting Started

## Clone Repository

```bash
git clone https://github.com/SivananduBharthavarapu/Velo.git
```

## Navigate

```bash
cd Velo
```

## Configure Database

Update:

```
application.properties
```

with your PostgreSQL credentials.

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/velo
spring.datasource.username=postgres
spring.datasource.password=your_password
```

---

## Run Application

```bash
mvn spring-boot:run
```

Application starts at:

```
http://localhost:8081
```

---

# 🚀 Future Roadmap

- ✅ User Authentication
- ✅ JWT Security
- ✅ Conversations
- ✅ Messaging
- ✅ Swagger Documentation
- 🔄 WebSocket Integration
- 🔄 Real-time Messaging
- 🔄 Typing Indicator
- 🔄 Online Presence
- 🔄 Delivery Status
- 🔄 Read Receipts
- 🔄 File Sharing
- 🔄 Group Chat
- 🔄 Notifications

---

# 👨‍💻 Author

**Sivanandu Bharthavarapu**

- GitHub: https://github.com/SivananduBharthavarapu

---

# ⭐ If you found this project useful

Please consider giving it a **Star ⭐** on GitHub.
