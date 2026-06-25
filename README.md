# 💳 Credit Score Engine API

A REST API for credit score analysis built with **Kotlin + Spring Boot**, simulating the core logic of a credit bureau like Serasa or Boa Vista. Designed for fintech and digital banking use cases.

## 🚀 Tech Stack

- **Kotlin** + **Java 21**
- **Spring Boot 3.3**
- **Spring Security** + **JWT** (jjwt 0.12.5)
- **Spring Data JPA** + **H2 Database**
- **SpringDoc OpenAPI** (Swagger UI)
- **JUnit 5** + **MockK**

## 🔐 Authentication

The API uses **JWT Bearer Token**. Authentication flow:

### 1. Register
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "email": "john@email.com",
  "password": "123456"
}
```

### 2. Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "john@email.com",
  "password": "123456"
}
```

### 3. Use the token
```http
Authorization: Bearer <token>
```

## 📋 Endpoints

### Customers
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/customers` | Create customer |
| GET | `/api/v1/customers` | List all customers |
| GET | `/api/v1/customers/{id}` | Find by ID |
| PUT | `/api/v1/customers/{id}` | Update customer |
| DELETE | `/api/v1/customers/{id}` | Delete customer |

### Financial Events & Score
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/financial-events` | Register financial event |
| GET | `/api/v1/customers/{id}/financial-events` | List customer events |
| GET | `/api/v1/customers/{id}/score` | Calculate credit score |

## 📊 Score Engine

The score starts at **500 points** (base) and varies according to the customer's financial history:

| Event | Impact |
|-------|--------|
| `PAYMENT_ON_TIME` | +50 pts |
| `PAYMENT_LATE` | -80 pts |
| `DEBT_NEGOTIATED` | -30 pts |
| `DEBT_DEFAULTED` | -150 pts |
| `CREDIT_INQUIRY` | -10 pts |

### Risk Classification

| Score | Classification |
|-------|---------------|
| 0 – 300 | `VERY_LOW` |
| 301 – 500 | `LOW` |
| 501 – 700 | `MEDIUM` |
| 701 – 900 | `HIGH` |
| 901 – 1000 | `VERY_HIGH` |

### Response example

```json
{
  "customerId": 1,
  "customerName": "John Doe",
  "score": 650,
  "classification": "MEDIUM",
  "totalEvents": 3
}
```

## 👨‍💻 Author

**João Victor**  
[![GitHub](https://img.shields.io/badge/GitHub-joaodddev-181717?style=flat&logo=github)](https://github.com/joaodddev)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-joaodddev-0A66C2?style=flat&logo=linkedin)](https://linkedin.com/in/joaodddev)