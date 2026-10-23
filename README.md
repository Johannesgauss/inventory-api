# 📦 Inventory Management API

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.4-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

A modern, robust RESTful API for Product Catalog and Inventory Stock Control, built with **Spring Boot 3**, **Spring Data JPA**, and **PostgreSQL**.

---

## 🚀 Features

- **Product Catalog CRUD:** Create, read, update, and delete products with category segregation.
- **Inventory Threshold Automations:** Automatic status transitions (`ACTIVE`, `LOW_STOCK`, `OUT_OF_STOCK`) based on safety stock levels.
- **Stock Delta Adjustments:** Dedicated endpoint (`PATCH /api/v1/products/{id}/stock?delta=N`) for inventory movements.
- **Validation & Error Handling:** Jakarta Bean Validation with RFC-7807 compliant error responses via `@RestControllerAdvice`.
- **OpenAPI 3 / Swagger Documentation:** Interactive API browser available out of the box.
- **Dockerized PostgreSQL:** Pre-configured `docker-compose.yml` for effortless local development.

---

## 🏗️ Architecture

```text
src/main/java/com/dev/inventory/
├── config/             # CORS and Swagger configurations
├── controller/         # REST API endpoints
├── dto/                # Request & Response records with validations
├── exception/          # Global Exception Handler and custom exceptions
├── model/              # JPA Entities and Enums (Product, Category, Status)
├── repository/         # Spring Data JPA repositories with JPQL search
└── service/            # Business rules, stock calculation and transactions
```

---

## 🛠️ Quick Start

### 1. Start the PostgreSQL Container
```bash
docker compose up -d
```

### 2. Run the Application
```bash
mvn clean spring-boot:run
```

The API will start at: `http://localhost:8080`

### 3. Interactive Documentation
Open your browser at:
- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI Spec:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 📡 REST Endpoints

| Method | Endpoint | Description | Status |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/products` | List products (supports `?query=` & `?category=`) | `200 OK` |
| `GET` | `/api/v1/products/{id}` | Get product details by ID | `200 OK` / `404 Not Found` |
| `POST` | `/api/v1/products` | Register new product | `201 Created` / `400 Bad Request` |
| `PUT` | `/api/v1/products/{id}` | Update existing product | `200 OK` / `400 Bad Request` |
| `DELETE` | `/api/v1/products/{id}` | Remove product | `204 No Content` |
| `PATCH` | `/api/v1/products/{id}/stock`| Adjust stock quantity (`?delta=+5` or `?delta=-2`) | `200 OK` |

---

## 📄 License
MIT License.
