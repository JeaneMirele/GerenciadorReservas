# Condominium Reservation API

![Java](https://img.shields.io/badge/Java-21-333333?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-333333?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-latest-333333?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-333333?logo=docker&logoColor=white)

A robust and scalable RESTful API designed to manage common area reservations (event halls, sports courts, barbecue areas) within residential complexes. Built with Java 21 and Spring Boot 3, this system implements a strict Role-Based Access Control (RBAC) architecture, secure JWT authentication, and automated data mapping.

## ⚙️ Tech Stack & Architecture

*   **Core:** Java 21, Spring Boot 3.4.4
*   **Database & ORM:** PostgreSQL, Spring Data JPA, Hibernate
*   **Security:** Spring Security, JWT (JSON Web Tokens)
*   **Mapping & Boilerplate:** MapStruct, Lombok
*   **Infrastructure:** Docker, Docker Compose

## 🔐 Security & Authentication Flow

The application implements a highly secure, state-aware authentication lifecycle to ensure strict access control and data integrity. 

1.  **Account Provisioning:** The Administrator provisions a Manager account. The system generates a temporary UUID-based password.
2.  **Initial Access Restriction:** Upon the first login attempt with the temporary password, the system intercepts the request and returns a `403 Forbidden` status with a `TROCA_SENHA_OBRIGATORIA` directive.
3.  **Password Enforcement:** The user must submit the temporary password alongside a new secure password to activate the account.
4.  **Token Issuance:** Following successful activation, standard login operations return an encrypted JWT for stateless API authorization.

### Role-Based Access Control (RBAC) Matrix

The system enforces a hierarchical permission model:

| Role | Domain Responsibility | Provisioned By |
| :--- | :--- | :--- |
| **SINDICO (Admin)** | Full system administration. Manages locations, Managers, and global settings. | System / Admin |
| **GERENTE (Manager)** | Operational management. Handles Resident onboarding and oversees global reservations. | Admin |
| **MORADOR (Resident)** | End-user level. Restricted to viewing availability and managing personal reservations. | Manager |

## 🚀 Getting Started

### Prerequisites

*   Docker and Docker Compose
*   A configured `.env` file in the root directory

### Environment Configuration

Create a `.env` file in the project root containing your environment variables:

```env
DATABASE_HOST=db-reservas
DATABASE_PORT=5432
DATABASE_NAME=condominio_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=your_secure_password
SECURITY_KEY=your_secure_jwt_key
```

To generate a cryptographically secure key for the `SECURITY_KEY`, run the following command in your terminal:

```bash
openssl rand -base64 64
```
*   **What it does:** This command utilizes the OpenSSL library to generate 64 bytes of cryptographically secure pseudo-random data and encodes it into a Base64 format.
*   **What it returns:** It outputs a long, randomized string (e.g., `uD3...==`) that is highly resistant to brute-force attacks, making it ideal for securely signing JSON Web Tokens.

### Running the Application

Deploy the application and its database containerized environment using Docker:

```bash
docker-compose up -d
```
*   **What it does:** This command reads the `docker-compose.yml` file, builds the application image (if not already built or pulled from Docker Hub), and starts both the database and the API containers in detached mode (in the background).
*   **What it returns:** It outputs the creation and startup status of the network, volumes, and containers, allowing your terminal to remain free for other commands while the application runs at `http://localhost:8080`.

## 📖 API Documentation

The API is fully documented using OpenAPI/Swagger. Once the application is running, access the interactive documentation to explore and test endpoints:

**Swagger UI:** `http://localhost:8080/swagger-ui/index.html`

### Quick Test Guide

A default Admin and Manager are pre-configured for initial environment testing.

**Admin Credentials:**
```json
{
  "email": "sindico@sistema.com",
  "senha": "sindico123"
}
```

**Manager Credentials:**
```json
{
  "email": "gerente@sistema.com",
  "senha": "gerente123"
}
```

1.  Authenticate via `POST /auth/login` using the Manager credentials.
2.  Extract the JWT from the response.
3.  Inject the JWT into the Swagger UI by clicking **Authorize** and entering `Bearer <your_token>`.
4.  Proceed to provision a new Resident (`MORADOR`) via `POST /usuarios`.
5.  Execute the first-access workflow (`POST /auth/primeiro-acesso`) to activate the newly created Resident account.

## 📡 Core Endpoints

| Resource Category | Endpoint | Admin | Manager | Resident |
| :--- | :--- | :---: | :---: | :---: |
| **Authentication** | `POST /auth/login` | ✅ | ✅ | ✅ |
| **Authentication** | `POST /auth/primeiro-acesso` | ✅ | ✅ | ✅ |
| **Authentication** | `POST /auth/refresh` | ✅ | ✅ | ✅ |
| **Users** | `POST /usuarios` | ✅ | ✅ | ❌ |
| **Users** | `GET /usuarios` | ✅ | ✅ | ❌ |
| **Users** | `PATCH /usuarios/meu-perfil/foto` | ✅ | ✅ | ✅ |
| **Locations** | `POST /locais` | ✅ | ❌ | ❌ |
| **Locations** | `GET /locais` | ✅ | ✅ | ✅ |
| **Locations** | `PUT /locais/{id}` | ✅ | ❌ | ❌ |
| **Reservations** | `POST /reservas` | ✅ | ✅ | ✅ |
| **Reservations** | `GET /reservas` | ✅ | ✅ | ✅ |
| **Reservations** | `DELETE /reservas/{id}` | ✅ | ✅ | ✅ |
| **Units** | `POST /unidades` | ✅ | ✅ | ❌ |
| **Media** | `GET /arquivos/{nome}` | ✅ | ✅ | ✅ |
