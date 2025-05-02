# 📚 E-Commerce Microservices Application

This is a modular, microservices-based E-Commerce platform built using **Spring Boot**, **Spring Cloud**, **Eureka**, **API Gateway**, and **Razorpay**. The system is architected for scalability, resilience, and separation of concerns.

---

## 🏗️ Architecture Overview

- **API Gateway**: Routes incoming requests to appropriate microservices.
- **Eureka Server**: Service discovery mechanism for dynamic resolution.
- **Microservices**:
  - `CUSTOMER-SERVICE`: Manages customer profiles.
  - `ORDER-SERVICE`: Processes order placements and tracking.
  - `CATALOG-SERVICE`: Aggregates product listings.
  - `STOCK-SERVICE`: Handles stock quantity and inventory.
  - `BASKET-SERVICE`: Manages shopping cart operations.
  - `PURCHASE-SERVICE`: Orchestrates final purchase process.
  - `PAYMENT-SERVICE`: Handles payment transactions via Razorpay.
  - `NOTIFICATION-SERVICE`: Sends SMS alerts using Twilio.

---

## 🚀 Technologies Used

- Spring Boot
- Spring Cloud (Gateway, Eureka)
- Spring Data JPA
- MySQL
- Lombok
- Feign Clients
- Razorpay Java SDK
- Postman for API Testing

---

## 📌 How to Run

1. **Start Eureka Server**
   ```bash
   cd eureka-server
   mvn spring-boot:run
